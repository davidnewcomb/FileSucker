package uk.co.bigsoft.filesucker;

public class StringSukaType extends SukaType // implements Iterator
{
    /*
     * {t,buffer,from,to}
     */
    String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    int from, to;

    StringSukaType(String f)
    {
        super(f);

        String t = formatTokens.nextToken();

        t = formatTokens.nextToken();
        to = letters.indexOf(t) - 1;
    }

    @Override
    public int numberOfIterations()
    {
        return to - from + 1;
    }

    @Override
    public String indexOf(int idx)
    {
        int i = from + idx;
        String s = letters.substring(i, i + 1);
        return s;
    }

}
