package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.task.view.HistoryJComboBox;

public class SubDirectoryPathButton extends JButton implements ActionListener {
	private HistoryJComboBox directory;
	private JTextField url;

	public SubDirectoryPathButton(JTextField url, HistoryJComboBox directory) {
		super("/D");

		this.directory = directory;
		this.url = url;

		setToolTipText("Appends highlighted url text as new sub-directory");
		setMinimumSize(new Dimension(0, 0));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String url_s = url.getSelectedText();
		if (url_s == null)
			return;
		url_s = Utility.getSuckerLable(url_s);

		url_s = Utility.cleanString(url_s);

		String curDir = directory.getSelectedItem().toString();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1) {
			if (curDir.charAt(curDir.length() - 1) != File.separatorChar)
				newDir.append(File.separator);
		}
		newDir.append(url_s);
		if (curDir.equals("")) {
//			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		directory.setSelectedItem(newDir.toString());
	}

}
