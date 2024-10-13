package net.sf.filesuka.gui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sf.filesuka.Utility;

public class OriginalAddressLaunchButton extends JButton implements
        ActionListener
{
    private JTextField originalAddress;

    public OriginalAddressLaunchButton(JTextField originalAddress)
    {
        super("L");

        this.originalAddress = originalAddress;

        setToolTipText("Launch original address in web browser");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String url = originalAddress.getText();
        Utility.launchBrowser(url);
    }

}
