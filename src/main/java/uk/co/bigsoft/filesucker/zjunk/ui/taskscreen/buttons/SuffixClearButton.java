package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class SuffixClearButton extends JButton implements ActionListener {

	private JTextField suffix;

	public SuffixClearButton(JTextField suffix) {
		super("Clear");

		this.suffix = suffix;

		setToolTipText("Clears suffix box");

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		suffix.setText("");
	}
}
