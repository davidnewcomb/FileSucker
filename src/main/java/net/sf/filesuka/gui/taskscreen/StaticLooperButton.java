package net.sf.filesuka.gui.taskscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.StaticLooper;

public class StaticLooperButton extends JButton implements ActionListener
{

    public StaticLooperButton()
    {
        super("S");

        addActionListener(this);
        setToolTipText("Adds an incrementing number");
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
        FileSuka.taskScreen.copyB.setVisible(false);
        FileSuka.taskScreen.staticB.setEnabled(false);

        FileSuka.taskScreen.iteratorJP.removeAll();
        FileSuka.taskScreen.iteratorJP.add(new StaticLooper(TaskScreen.urlTF
                .getSelectedText()), BorderLayout.CENTER);
        FileSuka.taskScreen.iteratorJP.repaint();
    }
}
