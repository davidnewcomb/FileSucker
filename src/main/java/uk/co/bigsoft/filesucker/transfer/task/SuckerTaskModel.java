package uk.co.bigsoft.filesucker.transfer.task;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.TransferProps;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerTaskModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private SuckerIterable work;
	private int numSuccess = 0;
	private int numFailed = 0;
	private LinkedList<SuckerItemModel> files = new LinkedList<>();

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
}
