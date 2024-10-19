package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemProgressBar;
import uk.co.bigsoft.filesucker.transfer.view.SuckerTaskModel;
import uk.co.bigsoft.filesucker.transfer.view.SuckerTaskView;

public class SuckerTaskThread extends Thread {

	private int maxTasks = 10; // TODO should come from config
	private int corePoolSize = 10; // TODO should come from config
	private int maximumPoolSize = 10; // TODO should come from config
	private SuckerIterable si;
	private SuckerTaskModel taskM;
	private SuckerTaskView taskV;
	private ThreadPoolExecutor executor;
	private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxTasks);

	public SuckerTaskThread(SuckerIterable si, SuckerTaskView taskV, SuckerTaskModel taskM) {
		this.si = si;
		this.taskM = taskM;
		this.taskV = taskV;

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
			
			SuckerItemModel m = new SuckerItemModel();
			m.setUrl(item.getUrl());
			SuckerItemProgressBar v = new SuckerItemProgressBar(m);
			m.addListener(e -> itemModelListener(e, v));
			
			item.setModel(m);
			
			SuckerItemDownloader r = new SuckerItemDownloader(item);
			while (queue.offer(r) == false) {
				Utility.delay(1_000);
			}
			taskV.addSuckerProgressBar(v);
		}
	}

	private void itemModelListener(PropertyChangeEvent evt, SuckerItemProgressBar v) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case TransferProps.F_COMPLETED: {
//			String state = (String) newVal;
			taskV.removeSuckerProgressBar(v);
			break;
		}
		}
	}
	

}
