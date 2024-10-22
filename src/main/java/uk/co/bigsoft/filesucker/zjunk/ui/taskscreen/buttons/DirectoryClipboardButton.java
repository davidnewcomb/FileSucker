package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.task.view.HistoryJComboBox;

public class DirectoryClipboardButton extends JButton implements ActionListener {
	private HistoryJComboBox directory;

	public DirectoryClipboardButton(HistoryJComboBox directory) {
		super("_C");

		this.directory = directory;

		setToolTipText("Appends clipboard to end of directory");
		setMinimumSize(new Dimension(0, 0));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = Utility.getClipboard();
		if (s == null)
			return;

		s = Utility.cleanString(s);

		String curDir = directory.getSelectedItem().toString();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1 && !curDir.endsWith("_")) {
			newDir.append('_');
		}
		newDir.append(s);
		if (curDir.equals("")) {
//			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		directory.setSelectedItem(newDir.toString());

	}

}
