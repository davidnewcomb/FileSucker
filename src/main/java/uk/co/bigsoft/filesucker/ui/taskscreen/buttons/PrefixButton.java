package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.Utility;

public class PrefixButton extends JButton implements ActionListener
{

    private JTextField url;
    private JTextField prefix;

    public PrefixButton(JTextField url, JTextField prefix)
    {
        super("Prefix");

        this.url = url;
        this.prefix = prefix;

        setToolTipText("Copy highlighted text from url prefix");
        
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
        url_s = url_s + FileSucker.configData.getPostPrefix();

        prefix.replaceSelection(url_s);

    }
}
