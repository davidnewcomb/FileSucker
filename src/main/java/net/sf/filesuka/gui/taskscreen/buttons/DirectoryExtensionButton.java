package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.HistoryJComboBox;
import net.sf.filesuka.Utility;

public class DirectoryExtensionButton extends JButton implements ActionListener
{
    private JTextField url;
    private HistoryJComboBox directory;

    public DirectoryExtensionButton(JTextField url, HistoryJComboBox directory)
    {
        super("_D");

        this.url = url;
        this.directory = directory;

        setToolTipText("Appends highlighted url text to directory");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        String url_s = url.getSelectedText();
        if (url_s == null)
            return;
        url_s = Utility.getSukaLable(url_s);

        url_s = Utility.cleanString(url_s);

        String curDir = directory.getSelectedItem().toString();
        StringBuffer newDir = new StringBuffer();
        if (curDir.length() > 1 && !curDir.endsWith("_"))
        {
            newDir.append('_');
        }
        newDir.append(url_s);
        if (curDir.equals(""))
            newDir.insert(0, FileSuka.configData.getScreenBaseDir());
        else
            newDir.insert(0, curDir);

        directory.setSelectedItem(newDir.toString());
    }

}
