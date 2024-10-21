package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeEvent;

import uk.co.bigsoft.filesucker.transfer.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskController;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskModel;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskView;

public class TransferController {

	private TransferModel model;
	private TransferView view;
	// private HashMap<SuckerTaskModel, SuckerTaskView> currentTasks = new
	// HashMap<>();

	public TransferController(TransferModel m, TransferView v) {
		model = m;
		view = v;
		initView();
		model.addListener(e -> modelListener(e));
	}

	private void initView() {

	}

	public void initController() {

	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case TransferProps.F_TASK_ADDED: {
			SuckerTaskController c = (SuckerTaskController) newVal;
			view.addTask(c.getView());

			c.start();
			break;
		}
		case TransferProps.F_TASK_REMOVED: {
			SuckerTaskController c = (SuckerTaskController) newVal;
			view.removeTask(c.getView());
			break;
		}
		default: {
			throw new RuntimeException("Bad TransferProps: " + propName);
		}
		}

	}

	public void addTask(SuckerIterable si) {

//		for (SuckerItem i : si) {
//			System.out.println(i.getUrl() + " -> " + i.getLocal());
//		}
		SuckerTaskView taskV = new SuckerTaskView();
		SuckerTaskModel taskM = new SuckerTaskModel(si);
		SuckerTaskController taskC = new SuckerTaskController(this, taskM, taskV);
		taskC.initController();

		model.addTask(taskC);
	}

	public void removeTask(SuckerTaskController stc) {
		model.removeTask(stc);
	}
}
