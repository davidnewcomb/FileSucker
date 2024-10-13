package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.looper.text.TextLooper;

public class TextLooperButton extends JButton implements ActionListener {

	public TextLooperButton() {
		super("T");

		addActionListener(this);
		setToolTipText("Creates a text looper");
		setMinimumSize(new Dimension(10, 20));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int caretpos = TaskScreen.urlTF.getCaretPosition();
		if (caretpos == 0)
			return;

		FileSucker.taskScreen.numberB.setVisible(false);
		FileSucker.taskScreen.textB.setEnabled(false);
		FileSucker.taskScreen.listB.setVisible(false);
		FileSucker.taskScreen.copyB.setVisible(false);
		FileSucker.taskScreen.staticB.setVisible(false);

		FileSucker.taskScreen.iteratorJP.removeAll();
		FileSucker.taskScreen.iteratorJP.add(new TextLooper(TaskScreen.urlTF.getSelectedText()), BorderLayout.CENTER);
		FileSucker.taskScreen.iteratorJP.repaint();
	}

}
