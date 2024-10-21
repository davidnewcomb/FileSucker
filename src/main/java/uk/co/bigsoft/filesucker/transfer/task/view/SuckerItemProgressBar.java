package uk.co.bigsoft.filesucker.transfer.task.view;

import java.awt.Dimension;

import javax.swing.JProgressBar;

public class SuckerItemProgressBar extends JProgressBar {

	private static final int TRANSFER_ROW_HEIGHT = 20;

	public SuckerItemProgressBar() {
		setStringPainted(true);
		setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		setString("...");
	}

	public void setPercentComplete(int pc) {
		if (pc == -1) {
			setIndeterminate(true);
		} else {
			setValue(pc);
		}
	}
}
