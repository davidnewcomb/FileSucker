package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.HistoryJComboBox;
import net.sf.filesuka.Utility;

public class OpenDirectoryButton extends JButton implements ActionListener
{
    private HistoryJComboBox directory;

    public OpenDirectoryButton(HistoryJComboBox directory)
    {
        super("LD");
        
        this.directory = directory;
        
        setToolTipText("Open directory");
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String dir = directory.getSelectedItem().toString();
        String dirEpanded = Utility.realDirectory(dir);
        try
        {
            String od = FileSuka.configData.getOpenDirectory();
            String helper = od.replaceAll("%s", dirEpanded);
            Runtime.getRuntime().exec(helper);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
