package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.Utility;

public class OpenDirectoryButton extends JButton implements ActionListener {
	private HistoryJComboBox directory;

	public OpenDirectoryButton(HistoryJComboBox directory) {
		super("LD");

		this.directory = directory;

		setToolTipText("Open directory");

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String dir = directory.getSelectedItem().toString();
		String dirEpanded = Utility.realDirectory(dir);
		try {
			String od = FileSucker.configData.getOpenDirectory();
			String helper = od.replaceAll("%s", dirEpanded);
			Runtime.getRuntime().exec(helper);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
