package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskModel;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskView;
import uk.co.bigsoft.filesucker.transfer.task.view.SuckerItemProgressBar;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerTaskThread extends Thread {

	private int maxTasks = 5; // TODO should come from config
	// private int corePoolSize = 10; // TODO should come from config
	// private int maximumPoolSize = 10; // TODO should come from config
	// private SuckerIterable si;
	private SuckerTaskModel taskM;
	// private SuckerTaskView taskV;
	private ThreadPoolExecutor executor;
	private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxTasks);

	public SuckerTaskThread(SuckerTaskModel taskM) {
		// this.si = si;
		this.taskM = taskM;
		// this.taskV = taskV;

		setName("FileSucker: " + taskM.getTitle());

		// LinkedBlockingQueue<Runnable> queue = new
		// LinkedBlockingQueue<Runnable>(maxTasks);
		executor = new ThreadPoolExecutor(maxTasks, maxTasks, Long.MAX_VALUE, TimeUnit.SECONDS, queue);
		executor.prestartAllCoreThreads();

//		statusPanel = new SuckerProgressPanel(this, parms, ulen);
//
//		TransferScreen.addTransferLine(statusPanel);

		start();
	}

	@Override
	public void run() {

		for (SuckerItem item : taskM.getWork()) {

			SuckerItemModel m = new SuckerItemModel(item);
			m.addListener(null);

			// m.setUrl(item.getUrl());
//			SuckerItemProgressBar v = new SuckerItemProgressBar(m);
//			m.addListener(e -> itemModelListener(e, v));

//			item.setModel(m);

			SuckerItemDownloader r = new SuckerItemDownloader(m);

			while (queue.offer(r) == false) {
				Utility.delay(1_000);
			}

			// taskV.addSuckerProgressBar(v);
		}

		System.out.println("All jobs queued, waiting for queue to empty");
		while (!queue.isEmpty()) {
			Utility.delay(1_000);
		}
//		System.out.println("Queue is empty");
//		executor.close();
//		executor.shutdown();
//		System.out.println("Task finished");
	}

	private void itemModelListener(PropertyChangeEvent evt, SuckerItemProgressBar v) {
		String propName = evt.getPropertyName();
//		Object newVal = evt.getNewValue();

//		switch (propName) {
//		case TransferProps.F_FILE_COMPLETED: {
////			String state = (String) newVal;
//			taskV.removeSuckerProgressBar(v);
//			break;
//		}
//		}
	}

}
