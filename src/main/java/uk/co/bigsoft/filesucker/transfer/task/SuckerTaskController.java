package uk.co.bigsoft.filesucker.transfer.task;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.transfer.TransferController;
import uk.co.bigsoft.filesucker.transfer.download.SuckerItemDownloader;
import uk.co.bigsoft.filesucker.transfer.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.task.view.SuckerItemProgressBar;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerTaskController extends Thread {

	private TransferController transferController;
	private SuckerTaskModel model;
	private SuckerTaskView view;
	private int maxTasks = 10;
	private HashMap<SuckerItemModel, SuckerItemProgressBar> mappings = new HashMap<>();

	public SuckerTaskController(TransferController tc, SuckerTaskModel m, SuckerTaskView v) {
		transferController = tc;
		model = m;
		view = v;
		initView();
		model.addListener(e -> modelListener(e));
	}

	private void initView() {
		view.setTitle(model.getTitle());
	}

	public void initController() {
		view.getRemoveButton().addActionListener(e -> transferController.removeTask(this));
		view.getHeaderLabel().addMouseListener(new SuckerTaskViewMouseAdapter(model));
	}

	private void modelListener(PropertyChangeEvent evt) {
		Object source = evt.getSource();
		String propName = evt.getPropertyName();
		// Object newVal = evt.getNewValue();

		switch (propName) {
		case SuckerTaskProps.FILE_START: {
			// add progress bar
			SuckerItemModel m = (SuckerItemModel) source;
			System.out.println("FILE_START: " + m.getWorkItem().getUrl());
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
			System.out.println("FILE_COMPLETED: " + m.getWorkItem().getUrl());
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
			System.out.println("FILE_PROGRESS: " + m.getWorkItem().getUrl());
			SuckerItemProgressBar v;
			synchronized (mappings) {
				v = mappings.get(m);
			}

			v.setPercentComplete(m.getPercentComplete());
			break;
		}
		case SuckerTaskProps.FILE_FINISHED: {
			System.out.println("FILE_FINISHED: " + model.getPercentComplete() + " " + model.getNumSuccess() + " "
					+ model.getNumFailed());
			view.setTaskStats(model.getPercentComplete(), model.getNumSuccess(), model.getNumFailed());
			break;
		}
		default: {
			throw new RuntimeException("Bad SuckerTaskProps:" + propName);
		}
		}
	}

	public void addWorkItem() {
		throw new RuntimeException("not implemeneted");
	}

	public void removeWorkItem() {
		throw new RuntimeException("not implemeneted");
	}

	public SuckerTaskView getView() {
		return view;
	}

	public void run() {

		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxTasks);
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

		System.out.println("All jobs queued, waiting for queue to empty");
		while (!model.isCancelled() && !queue.isEmpty()) {
			Utility.delay(1_000);
		}

		System.out.println("Queue is empty");
		executor.close();
		if (model.isCancelled()) {
			executor.shutdownNow();
		} else {
			executor.shutdown();
		}
		System.out.println("Task finished");
	}

}

//m.setUrl(item.getUrl());
//SuckerItemProgressBar v = new SuckerItemProgressBar();
//v.setString(m.getWorkItem().getUrl());
//
//mappings.put(m, v);

//m.addListener(e -> itemModelListener(e, v));

//item.setModel(m);

//SuckerItemDownloader r = new SuckerItemDownloader(m);
//
//while (queue.offer(r) == false) {
//	Utility.delay(1_000);
//}

//taskV.addSuckerProgressBar(v);