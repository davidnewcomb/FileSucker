package net.sf.filesuka;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class UpDownTextJTextField extends JSpinner
{
    private JFormattedTextField textbox;

    UpDownTextJTextField(String start)
    {
        super();
        setModel(new SpinnerCharacterModel(start));
        // setValue (start) ;

        textbox = ((JSpinner.DefaultEditor) getEditor()).getTextField();
        textbox.setHorizontalAlignment(SwingConstants.LEFT);
        textbox.setEditable(true);
        addMouseWheelListener(new MouseWheelListener()
            {
                public void mouseWheelMoved(MouseWheelEvent e)
                {
                    int notches = e.getWheelRotation();
                    Object newVal;
                    if (notches > 0)
                        newVal = getModel().getNextValue();
                    else
                        newVal = getModel().getPreviousValue();
                    if (newVal != null)
                        setValue(newVal);
                }
            });
    }

    @Override
    public String toString()
    {
        return textbox.getText();
    }

}
