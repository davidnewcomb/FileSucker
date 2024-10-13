package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.HistoryJComboBox;

public class HomeButton extends JButton implements ActionListener
{
    private HistoryJComboBox directory;

    public HomeButton(HistoryJComboBox directory)
    {
        super("H");
        
        this.directory = directory;
        
        setToolTipText("Initialises the directory to base directory");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        directory.setSelectedItem(FileSuka.configData.getScreenBaseDir());

    }

}
