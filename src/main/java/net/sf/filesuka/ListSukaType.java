package net.sf.filesuka;

import java.util.ArrayList;

public class ListSukaType extends SukaType // implements Iterator
{
    /*
     * {n,from,to,npad} {n,buffer,from,to,npad}
     */
    private ArrayList<String> list;

    ListSukaType(String f)
    {
        super(f);

        list = new ArrayList<String>(20);
        String t;
        do
        {
            t = formatTokens.nextToken();
            list.add(t);
        } while (formatTokens.hasMoreTokens());
    }

    @Override
    public int numberOfIterations()
    {
        return list.size() - 1;
    }

    @Override
    public String indexOf(int idx)
    {
        String s = list.get(idx);
        return s;
    }
}
