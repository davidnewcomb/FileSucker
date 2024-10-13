package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.FileSuckerFrame;
import uk.co.bigsoft.filesucker.ToolsScreen;

public class CopyToToolClearButton extends JButton implements ActionListener {
	private JTextField url;

	public CopyToToolClearButton(JTextField url) {
		super("CT");

		this.url = url;

		setToolTipText("Copy text to ToolScreen");

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ToolsScreen.setConvertUrlText(url.getText());
		FileSuckerFrame.tabPane.setSelectedComponent(FileSucker.toolsScreen);
	}
}
