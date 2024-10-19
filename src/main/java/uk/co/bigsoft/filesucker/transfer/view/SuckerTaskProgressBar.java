package uk.co.bigsoft.filesucker.transfer.view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

import uk.co.bigsoft.filesucker.transfer.TransferProps;

public class SuckerTaskProgressBar extends JProgressBar {

	private static final int TRANSFER_ROW_HEIGHT = 20;

	public SuckerTaskProgressBar(SuckerTaskModel sim) {
		setValue(0);
		setMinimum(0);
		setMaximum(100);
		// setStringPainted(true);
		setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		setString(null);
		sim.addListener(e -> modelListener(e));
	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Integer newVal = (Integer) evt.getNewValue();

		switch (propName) {
		case TransferProps.F_TASK_PROGRESS: {
			setValue(newVal.intValue());
			break;
		}
		}
	}

}
