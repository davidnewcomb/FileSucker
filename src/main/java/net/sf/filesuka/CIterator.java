package net.sf.filesuka;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CIterator implements Iterator<UrlSequenceIteration>
{
    private int[] iter;

    private int current;

    private HashMap<Integer, String> ivariables;

    private boolean hasnext;

    private List<SukaType> urlChunks;

    public CIterator(List<SukaType> uc)
    {
        urlChunks = uc;
    }

    public Iterator<UrlSequenceIteration> iterator()
    {
        iter = new int[urlChunks.size()];
        current = 0;
        hasnext = true;

        for (int i = 0 ; i < iter.length ; ++i)
            iter[i] = 0;

        ivariables = new HashMap<Integer, String>();

        return this;
    }

    public boolean hasNext()
    {
        return hasnext; // (current < totalFilesToGet);
    }

    public UrlSequenceIteration next()
    {
        current += 1;

        // rotate iterators
        String remoteFile = convert();
        UrlSequenceIteration usi = new UrlSequenceIteration(remoteFile,
                ivariables);

        hasnext = rotate(iter.length - 1);
        return usi;
    }

    public void remove()
    {
        /* empty */
    }

    private boolean rotate(int idx)
    {
        if (idx == 0)
        {
            return false;
        }

        SukaType st = urlChunks.get(idx);

        if (st instanceof LabelSukaType == true
                || st instanceof CopySukaType == true
                || st instanceof StaticSukaType == true)
            return rotate(--idx);

        if (st.numberOfIterations() > iter[idx])
        {
            iter[idx] += 1;
            return true;
        }

        iter[idx] = 0;

        return rotate(--idx);
    }

    public String convert()
    {
        StringBuffer sb = new StringBuffer();
        int k = 0;

        for (Iterator<SukaType> i = urlChunks.iterator() ; i.hasNext() ; k++)
        {
            SukaType st = i.next();

            if (st instanceof CopySukaType == true)
                continue;

            if (st.getSaveBuffer().intValue() != 0)
                ivariables.put(st.getSaveBuffer(), st.indexOf(iter[k]));
        }

        String s;
        k = 0;
        for (Iterator<SukaType> i = urlChunks.iterator() ; i.hasNext() ; k++)
        {
            SukaType st = i.next();

            if (st instanceof StaticSukaType)
                continue;

            if (st instanceof CopySukaType)
                s = ivariables.get(st.getSaveBuffer());
            else
                s = st.indexOf(iter[k]);

            sb.append(s);
        }
        return sb.toString();
    }
}
