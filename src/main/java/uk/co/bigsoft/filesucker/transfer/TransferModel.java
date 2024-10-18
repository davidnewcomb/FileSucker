package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;

public class TransferModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private LinkedList<SuckerIterable> suckerTasks = new LinkedList<>();

	public TransferModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}
	
	public void addTask(SuckerIterable si) {
		suckerTasks.add(si);
		propChangeFirer.firePropertyChange(TransferProps.F_TASK_ADDED, null, si);
	}
}
