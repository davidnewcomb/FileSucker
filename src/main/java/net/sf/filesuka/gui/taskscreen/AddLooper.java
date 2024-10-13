package net.sf.filesuka.gui.taskscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.Looper;

public class AddLooper extends JButton implements ActionListener
{
    public AddLooper()
    {
        super("Add");

        addActionListener(this);
        setToolTipText("Inserts the looper into the url at caret position");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Looper lp = (Looper) FileSuka.taskScreen.iteratorJP.getComponent(0);
        String braces = lp.toStringBraces();
        TaskScreen.replaceUrlText(braces);
        lp.resetLooper();
        TaskScreen.changed();
    }

}
