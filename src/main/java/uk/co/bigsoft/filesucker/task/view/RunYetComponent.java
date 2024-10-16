package uk.co.bigsoft.filesucker.task.view;

import javax.swing.JLabel;

public class RunYetComponent extends JLabel {
	private boolean modifed;

	public RunYetComponent() {
		modifed = false;
		change();
	}

	public void setModifed() {
		if (modifed)
			return;
		modifed = true;
		change();
	}

	public void setReset() {
		if (!modifed)
			return;
		modifed = false;
		change();
	}

	private void change() {
		setText(modifed ? "modifed" : "same");
		// TODO
		// TaskScreen.runB.setEnabled(modifed);
	}
}
