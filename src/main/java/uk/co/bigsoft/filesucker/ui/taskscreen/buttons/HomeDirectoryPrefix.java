package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.Utility;

public class HomeDirectoryPrefix extends JButton implements ActionListener
{
    private JTextField url;
    private HistoryJComboBox directory;
    private JTextField prefix;

    public HomeDirectoryPrefix(JTextField url, HistoryJComboBox directory,
            JTextField prefix)
    {
        super("HDP");

        this.url = url;
        this.directory = directory;
        this.prefix = prefix;

        setToolTipText("Clears defaults and prefix then appends highlighted url text to directory and prefix");
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

        StringBuffer newDir = new StringBuffer(FileSucker.configData
                .getScreenBaseDir());
        newDir.append(url_s);

        directory.setSelectedItem(newDir.toString());

        // Add to prefix
        url_s = url_s + FileSucker.configData.getPostPrefix();
        prefix.setText(url_s.toLowerCase());

    }

}
