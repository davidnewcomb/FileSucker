package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class StaticLooperButton extends JButton implements ActionListener {

	public StaticLooperButton() {
		super("S");

		addActionListener(this);
		setToolTipText("Adds an incrementing number");
		setMinimumSize(new Dimension(10, 20));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int caretpos = TaskScreen.urlTF.getCaretPosition();
		if (caretpos == 0)
			return;

//		FileSucker.taskScreen.numberB.setVisible(false);
//		FileSucker.taskScreen.textB.setVisible(false);
//		FileSucker.taskScreen.listB.setVisible(false);
//		FileSucker.taskScreen.copyB.setVisible(false);
//		FileSucker.taskScreen.staticB.setEnabled(false);
//
//		FileSucker.taskScreen.iteratorJP.removeAll();
//		FileSucker.taskScreen.iteratorJP.add(new StaticLooper(TaskScreen.urlTF.getSelectedText()), BorderLayout.CENTER);
//		FileSucker.taskScreen.iteratorJP.repaint();
	}
}
