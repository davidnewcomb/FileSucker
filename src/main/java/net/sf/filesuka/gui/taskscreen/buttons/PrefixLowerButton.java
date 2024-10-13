package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class PrefixLowerButton extends JButton implements ActionListener
{

    private JTextField prefix;

    public PrefixLowerButton(JTextField prefix)
    {
        super("Lower");

        this.prefix = prefix;

        setToolTipText("Changes highlighted suffix text to lowercase");
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String url_s = prefix.getSelectedText();
        if (url_s == null)
        {
            url_s = prefix.getText().toLowerCase();
            prefix.setText(url_s);
        }
        else
            prefix.replaceSelection(url_s.toLowerCase());
    }
}
