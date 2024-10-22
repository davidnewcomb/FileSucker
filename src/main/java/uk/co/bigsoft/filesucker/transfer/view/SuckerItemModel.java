package uk.co.bigsoft.filesucker.transfer.view;

import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.credits.CreditsProps;
import uk.co.bigsoft.filesucker.transfer.TransferProps;
import uk.co.bigsoft.filesucker.transfer.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskProps;

public class SuckerItemModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private long bytesDownloaded = 0;
	private long bytesToDownload = -1;
	private Exception error = null;
	private SuckerItem workItem;

	public SuckerItemModel(SuckerItem item) {
		workItem = item;
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public long getBytesDownloaded() {
		return bytesDownloaded;
	}

	public void setBytesDownloaded(long x, int bytes) {
		long oldVal = getPercentComplete();
		bytesDownloaded = x;
		propChangeFirer.firePropertyChange(TransferProps.F_FILE_PROGRESS, oldVal, getPercentComplete());
		propChangeFirer.firePropertyChange(CreditsProps.CRED_DOWNLOADED_PART, 0, bytes);
	}

	public long getBytesToDownload() {
		return bytesToDownload;
	}

	public void setBytesToDownload(long x) {
		long oldVal = getPercentComplete();
		bytesToDownload = x;
		propChangeFirer.firePropertyChange(TransferProps.F_FILE_PROGRESS, oldVal, getPercentComplete());
	}

	public boolean isSuccessful() {
		return error == null;
	}

	public int getPercentComplete() {
		if (bytesToDownload < 1) {
			return -1;
		}

		return (int) ((bytesDownloaded * 100) / bytesToDownload);
	}

//	public String getUrl() {
//		return workItem.getUrl();
//	}

//	public void setUrl(String url) {
//		this.url = url;
//	}

	public void started() {
		propChangeFirer.firePropertyChange(SuckerTaskProps.FILE_START, null, "ok");
	}

	public void completed() {
		propChangeFirer.firePropertyChange(SuckerTaskProps.FILE_COMPLETED, null, "ok");
	}

	public void setError(Exception e) {
		error = e;
		propChangeFirer.firePropertyChange(SuckerTaskProps.FILE_COMPLETED, null, "failed");
	}

	public Exception getError() {
		return error;
	}

	public SuckerItem getWorkItem() {
		return workItem;
	}

}
