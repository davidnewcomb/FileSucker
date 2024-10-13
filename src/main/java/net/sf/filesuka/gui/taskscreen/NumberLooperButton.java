package net.sf.filesuka.gui.taskscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import net.sf.filesuka.FileSuka;
import net.sf.filesuka.NumberLooper;

public class NumberLooperButton extends JButton implements ActionListener
{

    NumberLooperButton()
    {
        super("N");

        addActionListener(this);
        setToolTipText("Creates a number looper");
        setMinimumSize(new Dimension(10, 20));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int caretpos = TaskScreen.urlTF.getCaretPosition();
        if (caretpos == 0)
            return;

        String selected = TaskScreen.urlTF.getSelectedText();
        if (selected == null)
            return;

        FileSuka.taskScreen.numberB.setEnabled(false);
        FileSuka.taskScreen.textB.setVisible(false);
        FileSuka.taskScreen.listB.setVisible(false);
        FileSuka.taskScreen.copyB.setVisible(false);
        FileSuka.taskScreen.staticB.setVisible(false);

        FileSuka.taskScreen.iteratorJP.removeAll();
        FileSuka.taskScreen.iteratorJP.add(new NumberLooper(selected),
                BorderLayout.CENTER);
        FileSuka.taskScreen.iteratorJP.repaint();
    }
}
