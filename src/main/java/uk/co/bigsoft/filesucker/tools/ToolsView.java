package uk.co.bigsoft.filesucker.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileView;

public class ToolsView extends JPanel {
	private JTextField workingTF = new JTextField("");

	private JButton convertHexNum = new JButton("Convert");
	private JButton convertMiddle = new JButton("LastHttp");
	private JButton convertB64 = new JButton("B64");
	private JButton convertB64auto = new JButton("B64auto");

	private JButton launchButton = new JButton("Launch");
	private JButton linksPageButton = new JButton("LinksPage");
	private JButton generateImageWebPage = new JButton("GenImageWebPage");
	private JButton generateWebPage = new JButton("GenWebPage");
	private JButton launchProfileButton = new JButton("LaunchProfile");

	public ToolsView(LaunchProfileView launchProfileView) {
		super(new BorderLayout());

		Box hbox = Box.createHorizontalBox();
		Box vbox = Box.createVerticalBox();

		vbox.add(hbox);

		add(vbox, BorderLayout.CENTER);

		hbox.add(convertHexNum);
		hbox.add(convertMiddle);
		hbox.add(convertB64);
		hbox.add(convertB64auto);
		hbox.add(generateImageWebPage);
		hbox.add(generateWebPage);
		hbox.add(linksPageButton);
		hbox.add(launchButton);

		vbox.add(hbox);

		hbox = Box.createHorizontalBox();
		hbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		hbox.add(new JLabel("Convert"));
		hbox.add(workingTF);

		vbox.add(hbox);

		launchProfileView.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		vbox.add(launchProfileView);
	}

	public void setConvertUrlText(String s) {
		workingTF.setText(s);
	}

	public JTextField getWorkingTF() {
		return workingTF;
	}

	public JButton getConvertHexNum() {
		return convertHexNum;
	}

	public JButton getConvertMiddle() {
		return convertMiddle;
	}

	public JButton getConvertB64() {
		return convertB64;
	}

	public JButton getConvertB64auto() {
		return convertB64auto;
	}

	public JButton getLaunchButton() {
		return launchButton;
	}

	public JButton getLinksPageButton() {
		return linksPageButton;
	}

	public JButton getGenerateImageWebPage() {
		return generateImageWebPage;
	}

	public JButton getGenerateWebPage() {
		return generateWebPage;
	}

	public JButton getLaunchProfileButton() {
		return launchProfileButton;
	}
}
