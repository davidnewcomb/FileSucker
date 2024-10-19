package uk.co.bigsoft.filesucker.transfer;

import java.beans.PropertyChangeEvent;

import uk.co.bigsoft.filesucker.transfer.download.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;
import uk.co.bigsoft.filesucker.transfer.view.SuckerTaskModel;
import uk.co.bigsoft.filesucker.transfer.view.SuckerTaskView;

public class TransferController {

	private TransferModel model;
	private TransferView view;

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
			SuckerIterable si = (SuckerIterable) newVal;
			SuckerTaskModel taskM = new SuckerTaskModel(si.getTaskConfig().getUrl(), si.size());
			SuckerTaskView taskV = new SuckerTaskView(taskM);
			view.addTask(taskV);
			new SuckerTaskThread(si, taskM);
		}
		}

	}

	public void addTask(SuckerIterable si) {

		for (SuckerItem i : si) {
			System.out.println(i.getUrl() + " -> " + i.getLocal());
		}
		model.addTask(si);
	}
}
