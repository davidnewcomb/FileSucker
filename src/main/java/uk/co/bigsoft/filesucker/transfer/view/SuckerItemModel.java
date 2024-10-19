package uk.co.bigsoft.filesucker.transfer.view;

import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.TransferProps;

public class SuckerItemModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private long bytesDownloaded = 0;
	private long bytesToDownload = -1;
	private String url;
	private Exception error = null;

	public SuckerItemModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public long getBytesDownloaded() {
		return bytesDownloaded;
	}

	public void setBytesDownloaded(long x) {
		long oldVal = getPercentComplete();
		bytesDownloaded = x;
		propChangeFirer.firePropertyChange(TransferProps.F_FILE_PROGRESS, oldVal, getPercentComplete());
	}

	public long getBytesToDownload() {
		return bytesToDownload;
	}

	public void setBytesToDownload(long x) {
		long oldVal = getPercentComplete();
		bytesToDownload = x;
		propChangeFirer.firePropertyChange(TransferProps.F_FILE_PROGRESS, oldVal, getPercentComplete());
	}

	public int getPercentComplete() {
		if (bytesToDownload < 1) {
			return -1;
		}

		return (int) ((bytesDownloaded * 100)/ bytesToDownload);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void completed() {
		propChangeFirer.firePropertyChange(TransferProps.F_COMPLETED, null, "ok");
	}

	public void setError(Exception e) {
		error = e;
		propChangeFirer.firePropertyChange(TransferProps.F_COMPLETED, null, "failed");
	}

	public Exception getError() {
		return error;
	}

}
