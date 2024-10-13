package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.filesuka.Utility;

public class SuffixCopyButton extends JButton implements ActionListener
{

    private JTextField suffix;

    public SuffixCopyButton(JTextField suffix)
    {
        super("CS");

        this.suffix = suffix;

        setToolTipText("Replace highlighted suffix text with clipboard text");

        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String s = Utility.getClipboard();
        s = Utility.getSukaLable(s);
        s = Utility.cleanString(s);
        suffix.replaceSelection("_" + s);
    }
}
