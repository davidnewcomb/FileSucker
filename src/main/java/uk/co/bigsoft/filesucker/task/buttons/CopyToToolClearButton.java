//package uk.co.bigsoft.filesucker.task.buttons;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JButton;
//import javax.swing.JTextField;
//
//import uk.co.bigsoft.filesucker.FileSucker;
//import uk.co.bigsoft.filesucker.view.FileSuckerFrame;
//import uk.co.bigsoft.filesucker.view.ToolsScreen;
//
//public class CopyToToolClearButton extends JButton implements ActionListener {
//
//	public CopyToToolClearButton(JTextField url) {
//		super("CT");
//		setToolTipText("Copy text to ToolScreen");
//		addActionListener(this);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		ToolsScreen.setConvertUrlText(url.getText());
//		FileSuckerFrame.tabPane.setSelectedComponent(FileSucker.toolsScreen);
//	}
//}
