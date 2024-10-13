package net.sf.filesuka.gui.taskscreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class HistoryComboBox extends JComboBox
{
    private static final int HISTORY_LENGTH = 15;

    public HistoryComboBox()
    {
        setEditable(true);
        addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Object text = getSelectedItem();
                    if (text != null)
                    {
                        setText((String) text);
                    }
                }
            });
    }

    public String getText()
    {
        Object text = getSelectedItem();
        if (text == null)
        {
            return "";
        }
        return (String) text;
    }

    public void setText(String text)
    {
        removeItem(text);
        insertItemAt(text, 0);
        setSelectedItem(text);
        int length = getModel().getSize();
        while (length > HISTORY_LENGTH)
        {
            removeItemAt(--length);
        }
    }
}
