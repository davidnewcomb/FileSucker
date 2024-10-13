package uk.co.bigsoft.filesucker;

import java.util.StringTokenizer;

public abstract class SuckerType
{
    String format;

    Integer saveBuffer;

    StringTokenizer formatTokens;

    public SuckerType(String f)
    {
        if (f == null)
        {
            saveBuffer = new Integer(0);
            return;
        }
        format = f;
        formatTokens = new StringTokenizer(format, ",");
        if (formatTokens.countTokens() == 0)
            return;

        String t = formatTokens.nextToken();
        if (t == null)
            return;

        try
        {
            saveBuffer = new Integer(t);
        }
        catch (Exception e)
        {
            saveBuffer = new Integer(0);
        }
    }

    @Override
    public String toString()
    {
        return format;
    }

    public static SuckerType getSuckerType(String f)
    {
        if (f.length() == 0)
            return null;

        String typeFormat = "";

        if (f.length() > 1)
            typeFormat = f.substring(2);

        if (f.charAt(0) == 'n')
            return new NumberSuckerType(typeFormat);
        if (f.charAt(0) == 't')
            return new StringSuckerType(typeFormat);
        if (f.charAt(0) == 'l')
            return new ListSuckerType(typeFormat);
        if (f.charAt(0) == 'c')
            return new CopySuckerType(typeFormat);
        if (f.charAt(0) == 's')
            return new StaticSuckerType(typeFormat);
        if (f.charAt(0) == 'z')
            return new CustomSuckerType(typeFormat);

        return new LabelSuckerType(f);
    }

    public Integer getSaveBuffer()
    {
        return saveBuffer;
    }

    abstract public String indexOf(int idx);

    abstract public int numberOfIterations();
}
