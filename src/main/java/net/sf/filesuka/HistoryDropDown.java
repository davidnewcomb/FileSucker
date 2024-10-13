package net.sf.filesuka;

import java.util.Comparator;
import java.util.TreeSet;

public class HistoryDropDown extends TreeSet<HistoryElement>
{
    HistoryDropDown(Comparator<HistoryElement> c)
    {
        super(c);
    }

    public void removeOldest()
    {
        HistoryElement oldest = first();
        for (HistoryElement o : this)
        {
            if (o.isOlder(oldest))
                oldest = o;
        }
        remove(oldest);
    }
}
