package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.Utility;

public class DescriptionButton extends JButton implements ActionListener {
	private HistoryJComboBox directory;

	public DescriptionButton(HistoryJComboBox directory) {
		super("desc");

		this.directory = directory;

		setToolTipText("Edit description.txt in directory");
		setMinimumSize(new Dimension(0, 0));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String basedir = directory.getSelectedItem().toString();

			StringBuffer dir = new StringBuffer();
			dir.append(basedir);
			File d = new File(dir.toString());
			d.mkdirs();

			File f = new File(d, "description.txt");

			Utility.createDescription(f);
			Utility.launchTextFile(f);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
