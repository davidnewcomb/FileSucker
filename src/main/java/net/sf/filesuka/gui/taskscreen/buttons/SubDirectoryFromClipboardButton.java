package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import net.sf.filesuka.HistoryJComboBox;
import net.sf.filesuka.Utility;

public class SubDirectoryFromClipboardButton extends JButton implements
        ActionListener
{
    private HistoryJComboBox directory;

    public SubDirectoryFromClipboardButton(HistoryJComboBox directory)
    {
        super("/C");

        this.directory = directory;

        setToolTipText("Appends clipboard as new sub-directory");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String s = Utility.getClipboard();
        if (s == null)
            return;

        s = Utility.cleanString(s);

        StringBuffer sb = new StringBuffer(directory.getSelectedItem()
                .toString());
        if (sb.charAt(sb.length() - 1) != File.separatorChar)
            sb.append(File.separator);

        sb.append(s);
        directory.setSelectedItem(sb.toString());
    }

}
