package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.FileSukaFrame;
import net.sf.filesuka.ToolsScreen;

public class CopyToToolClearButton extends JButton implements ActionListener
{
    private JTextField url;

    public CopyToToolClearButton(JTextField url)
    {
        super("CT");
        
        this.url = url;
        
        setToolTipText("Copy text to ToolScreen");
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ToolsScreen.setConvertUrlText(url.getText());
        FileSukaFrame.tabPane.setSelectedComponent(FileSuka.toolsScreen);
    }
}
