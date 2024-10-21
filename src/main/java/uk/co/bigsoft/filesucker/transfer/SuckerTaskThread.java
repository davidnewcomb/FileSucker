package uk.co.bigsoft.filesucker.transfer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskModel;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerTaskThread extends Thread {

	private int maxTasks = 10; // TODO should come from config
	private SuckerTaskModel taskM;
	private ThreadPoolExecutor executor;
	private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxTasks);

	public SuckerTaskThread(SuckerTaskModel taskM) {
		this.taskM = taskM;

		setName("FileSucker: " + taskM.getTitle());
		executor = new ThreadPoolExecutor(maxTasks, maxTasks, Long.MAX_VALUE, TimeUnit.SECONDS, queue);
		executor.prestartAllCoreThreads();

		start();
	}

	@Override
	public void run() {

		for (SuckerItem item : taskM.getWork()) {

			SuckerItemModel m = new SuckerItemModel(item);
			m.addListener(null);
			SuckerItemDownloader r = new SuckerItemDownloader(m);

			while (queue.offer(r) == false) {
				Utility.delay(1_000);
			}
		}

		System.out.println("All jobs queued, waiting for queue to empty");
		while (!queue.isEmpty()) {
			Utility.delay(1_000);
		}
		System.out.println("Queue is empty");
		executor.close();
		executor.shutdown();
		System.out.println("Task finished");
	}

}
