package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CopyLooperButton extends JButton implements ActionListener {

	public CopyLooperButton() {
		super("C");

		addActionListener(this);
		setToolTipText("Makes a copy of another looper's value");
		setMinimumSize(new Dimension(10, 20));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		int caretpos = TaskScreen.urlTF.getCaretPosition();
//		if (caretpos == 0)
//			return;
//
//		FileSucker.taskScreen.numberB.setVisible(false);
//		FileSucker.taskScreen.textB.setVisible(false);
//		FileSucker.taskScreen.listB.setVisible(false);
//		FileSucker.taskScreen.copyB.setEnabled(false);
//		FileSucker.taskScreen.staticB.setVisible(false);
//
//		FileSucker.taskScreen.iteratorJP.removeAll();
//		CopyLooper cl = new CopyLooper(TaskScreen.urlTF.getSelectedText());
//		FileSucker.taskScreen.iteratorJP.add(cl, BorderLayout.CENTER);
//		FileSucker.taskScreen.iteratorJP.repaint();
	}

}
