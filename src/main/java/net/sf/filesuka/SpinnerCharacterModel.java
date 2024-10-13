package net.sf.filesuka;

import javax.swing.AbstractSpinnerModel;

public class SpinnerCharacterModel extends AbstractSpinnerModel
{
    private String range = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String current;

    public SpinnerCharacterModel(String start)
    {
        current = start;
    }

    public Object getNextValue()
    {
        int i = range.indexOf(current);
        if (!(i >= 0 && i < range.length() - 1))
            return null;
        ++i;
        String newVal = range.substring(i, i + 1);
        return newVal;
    }

    public Object getPreviousValue()
    {
        int i = range.indexOf(current);
        if (!(i > 0 && i < range.length()))
            return null;
        --i;
        String newVal = range.substring(i, i + 1);
        return newVal;
    }

    public Object getValue()
    {
        return current;
    }

    public void setValue(Object newVal)
    {
        if (newVal == null || !(newVal instanceof String))
        {
            throw new IllegalArgumentException("illegal value");
        }
        if (!newVal.equals(current))
        {
            current = (String) newVal;
            fireStateChanged();
        }
    }

}
