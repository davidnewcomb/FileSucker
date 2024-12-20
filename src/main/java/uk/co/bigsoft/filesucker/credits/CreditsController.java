package uk.co.bigsoft.filesucker.credits;

public class CreditsController {

	private static BytesToString bytesToString = new BytesToString();
	private CreditsModel model;
	private CreditsView view;

	public CreditsController(CreditsModel m, CreditsView v) {
		model = m;
		view = v;
		initView();
	}

	public void initView() {
		view.getNumBytesLabel().setText(bytesToString.convert(model.getNumBytes()));
		view.getNumFilesLabel().setText(String.valueOf(model.getNumFiles()));
		view.getTotalNumBytesLabel().setText(bytesToString.convert(model.getTotalNumBytes()));
		view.getTotalNumFilesLabel().setText(String.valueOf(model.getTotalNumFiles()));
	}

	public void initController() {
		view.getResetCountersButton().addActionListener(e -> resetCounters());
	}

	public void resetCounters() {
		model.setNumBytes(0L);
		model.setNumFiles(0L);
		model.setTotalNumBytes(0L);
		model.setTotalNumFiles(0L);
		initView();
	}

	public void addBytes(long b) {
		model.addBytes(b);
		redrawBytes();
	}

	public void addFiles(long f) {
		model.addFiles(f);
		redrawFiles();
	}

	private void redrawBytes() {
		view.getNumBytesLabel().setText(bytesToString.convert(model.getNumBytes()));
		view.getTotalNumBytesLabel().setText(bytesToString.convert(model.getTotalNumBytes()));
	}

	private void redrawFiles() {
		view.getNumFilesLabel().setText(String.valueOf(model.getNumFiles()));
		view.getTotalNumFilesLabel().setText(String.valueOf(model.getTotalNumFiles()));
	}
}
