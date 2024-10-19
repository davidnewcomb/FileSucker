package uk.co.bigsoft.filesucker.transfer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import uk.co.bigsoft.filesucker.transfer.download.SuckerProgressPanel;
import uk.co.bigsoft.filesucker.transfer.download.UrlSequencer;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.view.SuckerTaskModel;

public class SuckerTaskThread extends Thread {

	private int maxTasks = 10; // TODO should come from config
	private int corePoolSize = 10; // TODO should come from config
	private int maximumPoolSize = 10; // TODO should come from config
	private SuckerIterable si;
	private SuckerTaskModel taskM;
	private ThreadPoolExecutor executor;
	private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxTasks);

	public SuckerTaskThread(SuckerIterable si, SuckerTaskModel taskM) {
		this.si = si;
		this.taskM = taskM;

		setName("FileSucker: " + si.getTaskConfig().getUrl());

		// LinkedBlockingQueue<Runnable> queue = new
		// LinkedBlockingQueue<Runnable>(maxTasks);
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, Long.MAX_VALUE, TimeUnit.SECONDS, queue);
		executor.prestartAllCoreThreads();

//		statusPanel = new SuckerProgressPanel(this, parms, ulen);
//
//		TransferScreen.addTransferLine(statusPanel);

		start();
	}

	@Override
	public void run() {
		for (SuckerItem item : si) {
			SuckerItemDownloader r = new SuckerItemDownloader(item);
			queue.add(r);
		}
	}

}
