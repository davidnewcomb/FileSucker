package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.Utility;

public class SuffixButton extends JButton implements ActionListener {

	private JTextField url;
	private JTextField suffix;

	public SuffixButton(JTextField url, JTextField suffix) {
		super("Suffix");

		this.url = url;
		this.suffix = suffix;

		setToolTipText("Copy highlighted text from url suffix");

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String url_s = url.getSelectedText();
		if (url_s == null)
			return;

		url_s = Utility.getSuckerLable(url_s);
		url_s = Utility.cleanString(url_s);
//		url_s = FileSucker.configData.getPostPrefix() + url_s;

		suffix.replaceSelection(url_s);
	}
}
