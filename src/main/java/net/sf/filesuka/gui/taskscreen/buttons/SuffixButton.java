package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.Utility;

public class SuffixButton extends JButton implements ActionListener
{

    private JTextField url;
    private JTextField suffix;

    public SuffixButton(JTextField url, JTextField suffix)
    {
        super("Suffix");

        this.url = url;
        this.suffix = suffix;

        setToolTipText("Copy highlighted text from url suffix");

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String url_s = url.getSelectedText();
        if (url_s == null)
            return;

        url_s = Utility.getSukaLable(url_s);
        url_s = Utility.cleanString(url_s);
        url_s = FileSuka.configData.getPostPrefix() + url_s;

        suffix.replaceSelection(url_s);
    }
}
