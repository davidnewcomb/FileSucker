package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeEvent;

import uk.co.bigsoft.filesucker.credits.CreditsController;
import uk.co.bigsoft.filesucker.transfer.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskController;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskModel;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskView;

public class TransferController {

	private TransferModel model;
	private TransferView view;
	private CreditsController creditsController;

	public TransferController(TransferModel m, TransferView v) {
		model = m;
		view = v;
		initView();
		model.addListener(e -> modelListener(e));
	}

	private void initView() {
		//
	}

	public void initController(CreditsController creditsController) {
		this.creditsController = creditsController;
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

		SuckerTaskView stv = new SuckerTaskView();
		SuckerTaskModel stm = new SuckerTaskModel(si);
		SuckerTaskController stc = new SuckerTaskController(stm, stv);
		stc.initController(this, creditsController);

		model.addTask(stc);
	}

	public void removeTask(SuckerTaskController stc) {
		model.removeTask(stc);
	}
}
