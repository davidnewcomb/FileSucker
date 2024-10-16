package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NumberLooperButton extends JButton implements ActionListener {

	public NumberLooperButton() {
		super("N");

		addActionListener(this);
		setToolTipText("Creates a number looper");
		setMinimumSize(new Dimension(10, 20));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		int caretpos = TaskScreen.urlTF.getCaretPosition();
//		if (caretpos == 0)
//			return;
//
//		String selected = TaskScreen.urlTF.getSelectedText();
//		if (selected == null)
//			return;
//
//		FileSucker.taskScreen.numberB.setEnabled(false);
//		FileSucker.taskScreen.textB.setVisible(false);
//		FileSucker.taskScreen.listB.setVisible(false);
//		FileSucker.taskScreen.copyB.setVisible(false);
//		FileSucker.taskScreen.staticB.setVisible(false);
//
//		FileSucker.taskScreen.iteratorJP.removeAll();
//		FileSucker.taskScreen.iteratorJP.add(new NumberLooper(selected), BorderLayout.CENTER);
//		FileSucker.taskScreen.iteratorJP.repaint();
	}
}
