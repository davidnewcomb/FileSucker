package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.Utility;

public class SuffixCopyButton extends JButton implements ActionListener {

	private JTextField suffix;

	public SuffixCopyButton(JTextField suffix) {
		super("CS");

		this.suffix = suffix;

		setToolTipText("Replace highlighted suffix text with clipboard text");

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = Utility.getClipboard();
		s = Utility.getSuckerLable(s);
		s = Utility.cleanString(s);
		suffix.replaceSelection("_" + s);
	}
}
