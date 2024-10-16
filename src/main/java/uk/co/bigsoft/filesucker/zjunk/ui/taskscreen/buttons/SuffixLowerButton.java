package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class SuffixLowerButton extends JButton implements ActionListener {

	private JTextField suffix;

	public SuffixLowerButton(JTextField suffix) {
		super("Lower");

		this.suffix = suffix;

		setToolTipText("Changes highlighted prefix text to lowercase");

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String url_s = suffix.getSelectedText();
		if (url_s == null) {
			url_s = suffix.getText().toLowerCase();
			suffix.setText(url_s);
		} else
			suffix.replaceSelection(url_s.toLowerCase());

	}
}
