package uk.co.bigsoft.filesucker.transfer.task;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.credits.CreditsController;
import uk.co.bigsoft.filesucker.credits.CreditsProps;
import uk.co.bigsoft.filesucker.transfer.TransferController;
import uk.co.bigsoft.filesucker.transfer.download.SuckerItemDownloader;
import uk.co.bigsoft.filesucker.transfer.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.task.view.SuckerItemProgressBar;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerTaskController extends Thread {

	private static Logger L = LoggerFactory.getLogger(SuckerTaskController.class);

	private CreditsController creditsController;
	private SuckerTaskModel model;
	private SuckerTaskView view;
	private int maxTasks = 10;
	private HashMap<SuckerItemModel, SuckerItemProgressBar> mappings = new HashMap<>();

	public SuckerTaskController(SuckerTaskModel m, SuckerTaskView v) {
		model = m;
		view = v;
		initView();
		model.addListener(e -> modelListener(e));
	}

	private void initView() {
		view.setTitle(model.getTitle());
	}

	public void initController(TransferController transferController, CreditsController creditsController) {
		this.creditsController = creditsController;
		view.getRemoveButton().addActionListener(e -> transferController.removeTask(this));
		view.addMouseListener(new SuckerTaskViewMouseAdapter(this));
	}

	private void modelListener(PropertyChangeEvent evt) {
		Object source = evt.getSource();
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case SuckerTaskProps.FILE_START: {
			// add progress bar
			SuckerItemModel m = (SuckerItemModel) source;
			L.debug("FILE_START: " + m.getWorkItem().getUrl());
			SuckerItemProgressBar v = new SuckerItemProgressBar();
			v.setString(m.getWorkItem().getUrl());
			view.addSuckerProgressBar(v);
			synchronized (mappings) {
				mappings.put(m, v);
			}
			break;
		}
		case SuckerTaskProps.FILE_COMPLETED: {
			// remove progress bar
			SuckerItemModel m = (SuckerItemModel) source;
			L.debug("FILE_COMPLETED: " + m.getWorkItem().getUrl());
			SuckerItemProgressBar v = mappings.get(m);
			view.removeSuckerProgressBar(v);
			synchronized (mappings) {
				mappings.remove(m);
			}
			model.fileDownloaded(m.isSuccessful());
			break;
		}
		case SuckerTaskProps.FILE_PROGRESS: {
			// update progress bar
			SuckerItemModel m = (SuckerItemModel) source;
			L.debug("FILE_PROGRESS: " + m.getWorkItem().getUrl());
			SuckerItemProgressBar v;
			synchronized (mappings) {
				v = mappings.get(m);
			}

			v.setPercentComplete(m.getPercentComplete());
			break;
		}
		case SuckerTaskProps.FILE_FINISHED: {
			L.debug("FILE_FINISHED: " + model.getPercentComplete() + " " + model.getNumSuccess() + " "
					+ model.getNumFailed());
			view.setTaskStats(model.getPercentComplete(), model.getNumSuccess(), model.getNumFailed());
			creditsController.addFiles(1);
			break;
		}
		case CreditsProps.CRED_DOWNLOADED_PART: {
			int bytes = ((Integer) newVal).intValue();
			creditsController.addBytes(bytes);
			break;
		}
		default: {
			throw new RuntimeException("Bad SuckerTaskProps:" + propName);
		}
		}
	}

	public SuckerTaskView getView() {
		return view;
	}

	public void run() {

		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxTasks);
		// executor needs to be closed in java > 19
		ThreadPoolExecutor executor = new ThreadPoolExecutor(maxTasks, maxTasks, Long.MAX_VALUE, TimeUnit.SECONDS,
				queue);
		executor.prestartAllCoreThreads();

		for (SuckerItem item : model.getWork()) {

			if (model.isCancelled()) {
				break;
			}

			SuckerItemModel m = new SuckerItemModel(item);
			m.addListener(e -> modelListener(e));

			SuckerItemDownloader r = new SuckerItemDownloader(m);

			while (!model.isCancelled() && queue.offer(r) == false) {
				Utility.delay(100);
			}

		}

		L.debug("All jobs queued, waiting for queue to empty");
		while (!model.isCancelled() && !queue.isEmpty()) {
			Utility.delay(1_000);
		}

		L.debug("Queue is empty");
		// Available in java > 19
		// executor.close();
		if (model.isCancelled()) {
			executor.shutdownNow();
		} else {
			executor.shutdown();
		}
		L.debug("Task finished");
	}

	public void cancel() {
		model.setCancelled(true);
	}

	@Override
	public void interrupt() {
		model.setCancelled(true);
		super.interrupt();
	}
}
