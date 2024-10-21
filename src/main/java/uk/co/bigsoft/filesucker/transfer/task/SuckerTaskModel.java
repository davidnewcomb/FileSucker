package uk.co.bigsoft.filesucker.transfer.task;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerTaskModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private SuckerIterable work;
	private int numSuccess = 0;
	private int numFailed = 0;
	private boolean cancelled = false;

	public SuckerTaskModel(SuckerIterable si) {
		work = si;
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public synchronized void fileDownloaded(boolean success) {
		int oldVal = getPercentComplete();
		if (success) {
			++numSuccess;
		} else {
			++numFailed;
		}
		propChangeFirer.firePropertyChange(SuckerTaskProps.FILE_FINISHED, oldVal, getPercentComplete());
	}

	public int getPercentComplete() {
		return ((numSuccess + numFailed) * 100) / work.size();
	}

	public String getTitle() {
		return work.getTaskConfig().getUrl();
	}

	public SuckerIterable getWork() {
		return work;
	}

	public void addWorkItem(SuckerItemModel item) {
		propChangeFirer.firePropertyChange(SuckerTaskProps.ADD, null, getPercentComplete());
	}

	public int getNumSuccess() {
		return numSuccess;
	}

	public int getNumFailed() {
		return numFailed;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
