package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AddLooper extends JButton implements ActionListener {
	public AddLooper() {
		super("Add");

		addActionListener(this);
		setToolTipText("Inserts the looper into the url at caret position");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		Looper lp = null; // (Looper) FileSucker.taskScreen.iteratorJP.getComponent(0);
//		String braces = lp.toStringBraces();
//		TaskScreen.replaceUrlText(braces);
//		lp.resetLooper();
//		TaskScreen.changed();
	}

}
