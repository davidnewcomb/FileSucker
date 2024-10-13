package uk.co.bigsoft.filesucker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;
import uk.co.bigsoft.filesucker.view.TransferScreen;

public class SuckerThread extends Thread {

	private SuckerParams parms;

	private boolean running = true;

	private boolean cancel = false;

	private UrlSequencer urlsequence;

	private ThreadPoolExecutor threadPoolExecutor;
	private BlockingQueue<Runnable> queue;
	private int remaining;
	// private Object remainingSync = new Object();
	private SuckerProgressPanel statusPanel;

	private long timeToDownload = 0L;

	// TODO: SuckerThread
	public SuckerThread(SuckerParams p) {
		parms = p;

		setName("FileSucker: " + parms.getName());

		urlsequence = new UrlSequencer(parms.getOrginalUrl());
		int ulen = urlsequence.size();

		int maxTasks = FileSucker.configData.getMaxTasks();
		queue = new LinkedBlockingQueue<Runnable>(maxTasks);
		int corePoolSize = maxTasks;
		int maximumPoolSize = maxTasks;
		threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, Long.MAX_VALUE, TimeUnit.SECONDS,
				queue);
		threadPoolExecutor.prestartAllCoreThreads();
		statusPanel = new SuckerProgressPanel(this, parms, ulen);

		TransferScreen.addTransferLine(statusPanel);

		start();
	}

	@Override
	public void run() {
		timeToDownload = System.currentTimeMillis();

		RunnableSucker r = null;
		UrlSequenceIteration usi = null;
		String localfile = null;
		StringBuffer s;

		remaining = urlsequence.size();
		for (Iterator<UrlSequenceIteration> i = urlsequence.iterator(); i.hasNext() && cancel == false;) {
			usi = i.next();
			localfile = usi.getLocalFile(parms);
			s = new StringBuffer();
			s.append("remote=");
			s.append(usi.getRemoteFile());
			s.append("|   to   ");
			s.append(localfile);
			System.out.println(s.toString());
			try {
				r = new RunnableSucker(statusPanel, usi.getRemoteFile(), localfile, this);
			} catch (Exception e) {
				finish(e.toString());
				return;
			}

			// Utility.delay(FileSucker.configData.getDelayFilesMs().intValue());
			try {
				queue.put(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		finish(null);
	}

	private void finish(String label) {
		StringBuffer labelString = new StringBuffer();
		// TODO: create a summary page
		JPanel jp = new JPanel(new BorderLayout());
		JButton done = new JButton("... Almost Finish ...");
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (remaining > 0)
					return;

				synchronized (SuckerThread.this) {
					SuckerThread.this.notify();
				}

				TransferScreen.removeTransferLine(statusPanel);
			}
		});

		jp.add(done, BorderLayout.CENTER);
		statusPanel.addDownloadFile(jp);
		statusPanel.repaint();

		// Don't wait to time out, but we do want to wait
		// while (queue.size() > 0) Utility.delay(1000) ;

		while (true) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					// empty
				}
			}

			if (remaining == 0)
				break;
			done.setText(remaining + " left");
		}

		threadPoolExecutor.shutdown();

		if (label == null) {
			timeToDownload = (System.currentTimeMillis() - timeToDownload) / 1000;
			labelString.append("Download time: ");
			labelString.append(longToTimeString(timeToDownload));
		} else
			labelString.append(label);
		done.setText(labelString.toString());

		// // TODO - thread will hang here until transfer panel has been
		// clicked.
		// // TODO - place panel in a list and free the thread
		// synchronized (this)
		// {
		// try
		// {
		// wait();
		// }
		// catch (Exception e)
		// { // empty
		// }
		// }

		// TransferScreen.removeTransferLine(statusPanel);
	}

	public void load() {
		TaskScreen.load(parms);
	}

	public String getSuckerName() {
		return parms.getName();
	}

	public String getSuckerUrl() {
		return parms.getOrginalUrl();
	}

	public void cancelThread() {
		TaskScreen.setErrorMessage("");
		cancel = true;
		threadPoolExecutor.shutdownNow();
		removeFromTransfersList();
		synchronized (this) {
			notify();
		}
	}

	public void removeFromTransfersList() {
		synchronized (FileSucker.activeFileSuckerThreads) {
			FileSucker.activeFileSuckerThreads.remove(this);
		}
	}

	public boolean isRunning() {
		return running;
	}

	private String longToTimeString(long _l) {
		long l = _l;
		long seconds = 0;
		long minutes = 0, nminute = 60;
		long hours = 0, nhour = nminute * 60;
		long days = 0, nday = nhour * 24;

		days = l / nday;
		if (days > 0)
			l = l - days * nday;

		hours = l / nhour;
		if (hours > 0)
			l = l - hours * nhour;

		minutes = l / nminute;
		if (minutes > 0)
			l = l - minutes * nminute;

		seconds = l;

		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
	}

	public void jobCompleted() {
		synchronized (this) {
			--remaining;
			notify();
		}
	}

}
