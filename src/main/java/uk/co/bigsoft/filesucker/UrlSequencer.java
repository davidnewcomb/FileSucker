package uk.co.bigsoft.filesucker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UrlSequencer
{
    private String orginalString;

    private ArrayList<SukaType> urlChunks = new ArrayList<SukaType>();

    private HashMap<Integer, SukaType> lineParms = new HashMap<Integer, SukaType>();

    private int totalFilesToGet = 1;

    /**
     * Create a url sequencer
     * 
     * @param s
     *            url sequence
     */
    public UrlSequencer(String s)
    {
        orginalString = s;

        urlChunks = new ArrayList<SukaType>();
        StringBuffer tok = new StringBuffer();
        SukaType stype;
        boolean inBrackets = false;

        for (int i = 0 ; i < orginalString.length() ; i++)
        {
            if (orginalString.charAt(i) == '{'
                    || orginalString.charAt(i) == '}')
            {
                stype = getSukaType(inBrackets, tok.toString());
                if (orginalString.charAt(i) == '{')
                    inBrackets = true;
                else
                    inBrackets = false;

                if (stype == null)
                    continue;

                Integer saveB = stype.getSaveBuffer();
                if (!(stype instanceof CopySukaType))
                {
                    if (saveB != null && saveB.intValue() != 0)
                    {
                        lineParms.put(saveB, stype);
                    }
                    totalFilesToGet *= stype.numberOfIterations();
                }
                urlChunks.add(stype);
                tok = new StringBuffer();
                continue;
            }

            tok.append(orginalString.charAt(i));
        }

        if (tok.toString().length() > 0)
        {
            stype = getSukaType(inBrackets, tok.toString());
            urlChunks.add(stype);
        }
    }

    private SukaType getSukaType(boolean inBrackets, String tok)
    {
        if (inBrackets)
            return SukaType.getSukaType(tok.toString());
        if ("".equals(tok))
            return null;
        return new LabelSukaType(tok.toString());
    }

    public Iterator<UrlSequenceIteration> iterator()
    {
        CIterator ci = new CIterator(urlChunks);
        return ci.iterator();
    }

    public int size()
    {
        return totalFilesToGet;
    }
}
