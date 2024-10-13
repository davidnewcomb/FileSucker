package net.sf.filesuka;

public class CustomSukaType extends SukaType
{
    /*
     * {n,from,to,npad} {n,buffer,from,to,npad}
     */
    private ICustomSukaType custom;

    private String functionName;

    CustomSukaType(String f)
    {
        super(f);

        functionName = formatTokens.nextToken();
        if ("lower".equals(functionName))
            custom = new LowerCustomSukaType(formatTokens.nextToken());
        throw new RuntimeException("bad CustomSukaType");
    }

    @Override
    public int numberOfIterations()
    {
        return custom.numberOfIterations();
    }

    @Override
    public String indexOf(int idx)
    {
        return custom.getText(idx);
    }

}

interface ICustomSukaType
{
    String getText(int idx);

    int numberOfIterations();
}

class LowerCustomSukaType implements ICustomSukaType
{
    private String stuff;

    LowerCustomSukaType(String s)
    {
        stuff = s.toString();
    }

    public String getText(int idx)
    {
        return stuff;
    }

    public int numberOfIterations()
    {
        return 1;
    }
}
