package uk.co.bigsoft.filesucker.transfer.view;

import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.TransferProps;


public class SuckerItemModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private int bytesDownloaded = 0;
	private int bytesToDownload = -1;
	
	SuckerItemModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}
	
	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public int getBytesDownloaded() {
		return bytesDownloaded;
	}

	public void setBytesDownloaded(int x) {
		int oldVal = getPercentComplete();
		bytesDownloaded = x;
		propChangeFirer.firePropertyChange(TransferProps.F_FILE_PROGRESS, oldVal, getPercentComplete());
	}

	public int getBytesToDownload() {
		return bytesToDownload;
	}

	public void setBytesToDownload(int x) {
		int oldVal = getPercentComplete();
		bytesToDownload = x;
		propChangeFirer.firePropertyChange(TransferProps.F_FILE_PROGRESS, oldVal, getPercentComplete());
	}
	
	public int getPercentComplete() {
		if (bytesToDownload < 1) {
			return -1;
		}
		return bytesDownloaded / bytesToDownload;
	}
}
