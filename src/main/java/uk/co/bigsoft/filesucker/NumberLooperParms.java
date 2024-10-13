package uk.co.bigsoft.filesucker;

public class NumberLooperParms
{
    private Integer from;

    private Integer to;

    private Integer pad;

    NumberLooperParms(String x)
    {
        String t[] = x.split(",");
        if (t.length < 3)
            return;

        from = new Integer(t[0]);
        to = new Integer(t[1]);
        pad = new Integer(t[2]);
    }

    public Integer getFrom()
    {
        return from;
    }

    public Integer getTo()
    {
        return to;
    }

    public Integer getPadding()
    {
        return pad;
    }
}
