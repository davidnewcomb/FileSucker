package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.FileSuka;
import uk.co.bigsoft.filesucker.Looper;

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
