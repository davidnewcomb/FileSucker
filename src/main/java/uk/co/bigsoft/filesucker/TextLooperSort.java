package uk.co.bigsoft.filesucker;

import java.util.Comparator;

public class TextLooperSort implements Comparator<HistoryElement>
{

    public int compare(HistoryElement s1, HistoryElement s2)
    {
        String[] s1a = s1.toString().split(",");
        String[] s2a = s2.toString().split(",");

        for (int i = 0 ; i < 2 ; i++)
        {
            int j = s1a[i].compareTo(s2a[i]);
            if (j != 0)
                return j;
        }
        return 0;
    }

}
