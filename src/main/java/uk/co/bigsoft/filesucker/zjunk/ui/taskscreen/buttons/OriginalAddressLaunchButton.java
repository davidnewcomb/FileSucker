package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.Utility;

public class OriginalAddressLaunchButton extends JButton implements ActionListener {
	private JTextField originalAddress;

	public OriginalAddressLaunchButton(JTextField originalAddress) {
		super("L");

		this.originalAddress = originalAddress;

		setToolTipText("Launch original address in web browser");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String url = originalAddress.getText();
		Utility.launchBrowser("", url);
	}

}
