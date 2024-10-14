package uk.co.bigsoft.filesucker.tools.launch_profile;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class LaunchProfileView extends JPanel {
	private JComboBox<String> list = new JComboBox<String>();
	private JButton launchProfileButton = new JButton("LaunchProfile");

	public LaunchProfileView() {
		super(new BorderLayout());

		list.setEditable(true);
		add(list, BorderLayout.CENTER);
		add(launchProfileButton, BorderLayout.EAST);
	}

	public JComboBox<String> getLaunchProfileComboBox() {
		return list;
	}

	public JButton getLaunchProfileButton() {
		return launchProfileButton;
	}

}
