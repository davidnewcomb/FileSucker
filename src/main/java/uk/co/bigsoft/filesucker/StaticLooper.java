package uk.co.bigsoft.filesucker;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class StaticLooper extends Looper
{
    private Integer from;

    private JTextField idTF;

    public StaticLooper(String sel)
    {
        super(sel);
        if (setParameters() == false)
        {
            from = new Integer(0);
        }
        createLayout();
    }

    void createLayout()
    {
        idTF = new JTextField(from.toString());
        idTF.setMinimumSize(new Dimension(10, 20));
        idTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        getCentrePanel().add(new JLabel("From:"));
        getCentrePanel().add(idTF);
    }

    @Override
    public String toStringBraces()
    {
        return "{s," + index.toString() + "," + idTF.getText() + "}";
    }

    @Override
    public boolean setParameters()
    {
        if (parameters.length < 2 || parameters[0].equals("s") == false)
            return false;

        String s = parameters[2];
        try
        {
            from = new Integer(s);
        }
        catch (NumberFormatException e)
        {
            from = new Integer(0);
        }

        return true;
    }

}
