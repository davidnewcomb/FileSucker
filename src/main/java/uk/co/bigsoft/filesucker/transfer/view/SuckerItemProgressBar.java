package uk.co.bigsoft.filesucker.transfer.view;

import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

import uk.co.bigsoft.filesucker.transfer.TransferProps;

public class SuckerItemProgressBar extends JProgressBar {

	public SuckerItemProgressBar(SuckerItemModel sim) {
		sim.addListener(e -> modelListener(e));
	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case TransferProps.F_FILE_PROGRESS: {
			Integer i = (Integer) newVal;
			int ii = i.intValue();
			if (ii == -1) {
				setIndeterminate(true);
			} else {
				setValue(ii);
			}
			break;
		}
		}
	}

}
