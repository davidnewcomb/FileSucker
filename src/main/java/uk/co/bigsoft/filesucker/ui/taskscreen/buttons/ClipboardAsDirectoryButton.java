package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.Utility;

public class ClipboardAsDirectoryButton extends JButton implements
        ActionListener
{
    private HistoryJComboBox directory;

    public ClipboardAsDirectoryButton(HistoryJComboBox directory)
    {
        super("C");

        this.directory = directory;

        setToolTipText("Paste clipboard as new sub-directory");
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
        directory.setSelectedItem(s);
    }
}
