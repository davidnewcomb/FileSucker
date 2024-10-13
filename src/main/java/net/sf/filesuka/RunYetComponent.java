package net.sf.filesuka;

import javax.swing.JLabel;

import net.sf.filesuka.gui.taskscreen.TaskScreen;

public class RunYetComponent extends JLabel
{
    private boolean modifed;

    public RunYetComponent()
    {
        modifed = false;
        change();
    }

    public void setModifed()
    {
        if (modifed)
            return;
        modifed = true;
        change();
    }

    public void setReset()
    {
        if (!modifed)
            return;
        modifed = false;
        change();
    }

    private void change()
    {
        setText(modifed ? "modifed" : "same");
        TaskScreen.runB.setEnabled(modifed);
    }
}
