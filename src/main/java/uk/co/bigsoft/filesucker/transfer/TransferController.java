package uk.co.bigsoft.filesucker.transfer;

import uk.co.bigsoft.filesucker.SuckerParams;

public class TransferController {

	private TransferModel transferModel;
	private TransferView transferView;

	public TransferController(TransferModel m, TransferView v) {
		transferModel = m;
		transferView = v;
		initView();
	}

	private void initView() {

	}

	public void initController() {

	}
	
	public void addTask(SuckerParams params) {
		
	}
}
