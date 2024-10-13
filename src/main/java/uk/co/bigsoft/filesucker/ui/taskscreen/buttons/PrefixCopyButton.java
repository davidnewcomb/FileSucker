package uk.co.bigsoft.filesucker.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.Utility;

public class PrefixCopyButton extends JButton implements ActionListener
{

    private JTextField prefix;

    public PrefixCopyButton(JTextField prefix)
    {
        super("CP");

        this.prefix = prefix;

        setToolTipText("Replace highlighted prefix text with clipboard text");
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String s = Utility.getClipboard();
        s = Utility.getSukaLable(s);
        s = Utility.cleanString(s);
        prefix.replaceSelection(s + "_");
    }
}
