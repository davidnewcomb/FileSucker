package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskController;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskModel;

public class TransferModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private LinkedList<SuckerTaskController> suckerTasks = new LinkedList<>();

	public TransferModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public void addTask(SuckerTaskController taskC) {
		suckerTasks.add(taskC);
		propChangeFirer.firePropertyChange(TransferProps.F_TASK_ADDED, null, taskC);
	}
	
	public void removeTask(SuckerTaskController stm) {
		suckerTasks.remove(stm);
		propChangeFirer.firePropertyChange(TransferProps.F_TASK_REMOVED, null, stm);
	}

}
