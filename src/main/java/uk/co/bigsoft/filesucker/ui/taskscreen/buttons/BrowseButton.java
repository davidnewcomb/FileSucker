package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.Utility;

public class BrowseButton extends JButton implements ActionListener
{
    private HistoryJComboBox directory;

    public BrowseButton(HistoryJComboBox directory)
    {
        super("Browse");
        
        this.directory = directory;
        
        setToolTipText("Opens a directory viewer");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String dir = directory.getSelectedItem().toString();
        String dirEpanded = Utility.expandsPercentVars(dir);
        JFileChooser fc = new JFileChooser(dirEpanded);

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            String s = unexpandsPercentVars("Directory-browse", file.toString());
            directory.setSelectedItem(s);

        }
    }

    public String unexpandsPercentVars(String what, String toExpand)
    {
        java.util.Date d = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeStr = sdf.format(d);

        String s = toExpand.replaceAll(timeStr, "%T");
        return s;
    }

}
