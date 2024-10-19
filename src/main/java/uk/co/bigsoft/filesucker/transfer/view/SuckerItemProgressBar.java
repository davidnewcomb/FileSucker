package uk.co.bigsoft.filesucker.transfer.view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

import uk.co.bigsoft.filesucker.transfer.TransferProps;

public class SuckerItemProgressBar extends JProgressBar {

	private static final int TRANSFER_ROW_HEIGHT = 20;
	
	private SuckerItemModel model;
	
	public SuckerItemProgressBar(SuckerItemModel sim) {
		model = sim;
		setStringPainted(true);
		setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		setString(sim.getUrl());
		sim.addListener(e -> modelListener(e));
	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case TransferProps.F_FILE_PROGRESS: {
			System.out.println("FILE_PROGRESS " + model.getUrl() + " " + newVal);
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
