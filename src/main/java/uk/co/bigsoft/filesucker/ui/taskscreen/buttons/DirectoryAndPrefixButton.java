package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.Utility;

public class DirectoryAndPrefixButton extends JButton implements ActionListener
{
    private JTextField url;
    private HistoryJComboBox directory;
    private JTextField prefix;

    public DirectoryAndPrefixButton(JTextField url, HistoryJComboBox directory,
            JTextField prefix)
    {
        super("_DP");

        this.url = url;
        this.directory = directory;
        this.prefix = prefix;

        setToolTipText("Appends highlighted url text to _directory and _prefix");
        setMinimumSize(new Dimension(0, 0));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String url_s = url.getSelectedText();
        if (url_s == null)
            return;
        url_s = Utility.getSuckerLable(url_s);

        url_s = Utility.cleanString(url_s);

        String curDir = directory.getSelectedItem().toString();
        StringBuffer newDir = new StringBuffer();
        if (curDir.length() > 1 && !curDir.endsWith("_"))
        {
            newDir.append('_');
        }
        newDir.append(url_s);
        if (curDir.equals(""))
            newDir.insert(0, FileSucker.configData.getScreenBaseDir());
        else
            newDir.insert(0, curDir);

        directory.setSelectedItem(newDir.toString());

        // Add to prefix
        url_s = url_s + FileSucker.configData.getPostPrefix();
        prefix.setText(prefix.getText() + url_s.toLowerCase());
    }
}
