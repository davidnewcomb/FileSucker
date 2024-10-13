package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.HistoryJComboBox;
import net.sf.filesuka.Utility;

public class SubDirectoryAndPrefixFromClipboardButton extends JButton implements
        ActionListener
{
    private HistoryJComboBox directory;
    private JTextField prefix;

    public SubDirectoryAndPrefixFromClipboardButton(HistoryJComboBox directory,
            JTextField prefix)
    {
        super("/CP_");

        this.directory = directory;
        this.prefix = prefix;

        setToolTipText("Appends clipboard text to /directory and prefix_");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Add to dir
        String url_s = Utility.getClipboard();
        if (url_s == null)
            return;

        url_s = Utility.getSukaLable(url_s);
        url_s = Utility.cleanString(url_s);

        String curDir = directory.getSelectedItem().toString();
        StringBuffer newDir = new StringBuffer();
        if (curDir.length() > 1)
        {
            if (curDir.charAt(curDir.length() - 1) != File.separatorChar)
                newDir.append(File.separator);
        }
        newDir.append(url_s);
        if (curDir.equals(""))
            newDir.insert(0, FileSuka.configData.getScreenBaseDir());
        else
            newDir.insert(0, curDir);

        directory.setSelectedItem(newDir.toString());

        // Add to prefix
        url_s = url_s + FileSuka.configData.getPostPrefix();
        prefix.setText(url_s.toLowerCase());

    }

}
