package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class PrefixClearButton extends JButton implements ActionListener
{

    private JTextField prefix;

    public PrefixClearButton(JTextField prefix)
    {
        super("Clear");

        this.prefix = prefix;

        setToolTipText("Clears prefix box");
        
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        prefix.setText("");
    }
}
