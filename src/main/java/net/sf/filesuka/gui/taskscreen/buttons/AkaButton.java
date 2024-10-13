package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import net.sf.filesuka.HistoryJComboBox;
import net.sf.filesuka.Utility;

public class AkaButton extends JButton implements ActionListener
{
    private HistoryJComboBox directory;

    public AkaButton(HistoryJComboBox directory)
    {
        super("aka");

        this.directory = directory;

        setToolTipText("Edit aka.txt in directory");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            String basedir = directory.getSelectedItem().toString();
            File dir = new File(basedir);
            dir.mkdirs();

            File file = new File(dir, "aka.txt");

            Utility.createAka(file, dir);
            Utility.launchTextFile(file);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

}
