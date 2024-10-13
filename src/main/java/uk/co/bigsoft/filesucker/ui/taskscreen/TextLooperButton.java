package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import uk.co.bigsoft.filesucker.FileSuka;
import uk.co.bigsoft.filesucker.TextLooper;

public class TextLooperButton extends JButton implements ActionListener
{

    public TextLooperButton()
    {
        super("T");

        addActionListener(this);
        setToolTipText("Creates a text looper");
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
        FileSuka.taskScreen.textB.setEnabled(false);
        FileSuka.taskScreen.listB.setVisible(false);
        FileSuka.taskScreen.copyB.setVisible(false);
        FileSuka.taskScreen.staticB.setVisible(false);

        FileSuka.taskScreen.iteratorJP.removeAll();
        FileSuka.taskScreen.iteratorJP.add(new TextLooper(TaskScreen.urlTF
                .getSelectedText()), BorderLayout.CENTER);
        FileSuka.taskScreen.iteratorJP.repaint();
    }

}
