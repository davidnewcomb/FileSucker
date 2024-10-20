package uk.co.bigsoft.filesucker.transfer.task.view;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

import uk.co.bigsoft.filesucker.transfer.TransferProps;
import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerItemProgressBar extends JProgressBar {

	private static final int TRANSFER_ROW_HEIGHT = 20;

	// private SuckerItemModel model;

	public SuckerItemProgressBar() {
		setStringPainted(true);
		setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		setString("...");
	}
//	public SuckerItemProgressBar(SuckerItemModel sim) {
//		//model = sim;
//		setStringPainted(true);
//		setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
//		setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
//		setString(sim.getWorkItem().getUrl());
//		//sim.addListener(e -> modelListener(e));
//	}

	public void setPercentComplete(int pc) {
		if (pc == -1) {
			setIndeterminate(true);
		} else {
			setValue(pc);
		}
	}

//	private void modelListener(PropertyChangeEvent evt) {
//		String propName = evt.getPropertyName();
//		Object newVal = evt.getNewValue();
//
//		switch (propName) {
//		case TransferProps.F_FILE_PROGRESS: {
//			System.out.println("FILE_PROGRESS " + model.getUrl() + " " + newVal);
//			int pc = model.getPercentComplete();
//			if (pc == -1) {
//				setIndeterminate(true);
//			} else {
//				setValue(pc);
//			}
////			Integer i = (Integer) newVal;
////			int ii = i.intValue();
////			if (ii == -1) {
////				setIndeterminate(true);
////			} else {
////				setValue(ii);
////			}
//			break;
//		}
//		}
//	}

}
