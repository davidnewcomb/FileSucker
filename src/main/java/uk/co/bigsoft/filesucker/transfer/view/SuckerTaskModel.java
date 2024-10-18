package uk.co.bigsoft.filesucker.transfer.view;

import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.TransferProps;


public class SuckerTaskModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private int filesToDownload = 0;
	private int filesDownloaded = 0;
	private String title;
	
	public SuckerTaskModel(String title, int filesToDownload) {
		this.filesToDownload = filesToDownload;
		this.title = title;
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}
	
	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public synchronized void fileDownloaded() {
		int oldVal = getPercentComplete();
		++filesToDownload;
		propChangeFirer.firePropertyChange(TransferProps.F_TASK_PROGRESS, oldVal, getPercentComplete());
	}
	
	public int getPercentComplete() {
		if (filesToDownload < 1) {
			return -1;
		}
		return filesDownloaded / filesToDownload;
	}

	public String getTitle() {
		return title;
	}
}
