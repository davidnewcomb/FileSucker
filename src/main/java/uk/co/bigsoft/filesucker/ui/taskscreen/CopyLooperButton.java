package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.CopyLooper;
import uk.co.bigsoft.filesucker.FileSuka;

public class CopyLooperButton extends JButton implements ActionListener
{

    public CopyLooperButton()
    {
        super("C");

        addActionListener(this);
        setToolTipText("Makes a copy of another looper's value");
        setMinimumSize(new Dimension(10, 20));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int caretpos = TaskScreen.urlTF.getCaretPosition();
        if (caretpos == 0)
            return;

        FileSuka.taskScreen.numberB.setVisible(false);
        FileSuka.taskScreen.textB.setVisible(false);
        FileSuka.taskScreen.listB.setVisible(false);
        FileSuka.taskScreen.copyB.setEnabled(false);
        FileSuka.taskScreen.staticB.setVisible(false);

        FileSuka.taskScreen.iteratorJP.removeAll();
        CopyLooper cl = new CopyLooper(TaskScreen.urlTF.getSelectedText());
        FileSuka.taskScreen.iteratorJP.add(cl, BorderLayout.CENTER);
        FileSuka.taskScreen.iteratorJP.repaint();
    }

}
