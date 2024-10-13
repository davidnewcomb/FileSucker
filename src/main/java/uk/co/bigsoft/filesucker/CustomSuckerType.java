package uk.co.bigsoft.filesucker;

public class CustomSuckerType extends SuckerType
{
    /*
     * {n,from,to,npad} {n,buffer,from,to,npad}
     */
    private ICustomSuckerType custom;

    private String functionName;

    CustomSuckerType(String f)
    {
        super(f);

        functionName = formatTokens.nextToken();
        if ("lower".equals(functionName))
            custom = new LowerCustomSuckerType(formatTokens.nextToken());
        throw new RuntimeException("bad CustomSuckerType");
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

interface ICustomSuckerType
{
    String getText(int idx);

    int numberOfIterations();
}

class LowerCustomSuckerType implements ICustomSuckerType
{
    private String stuff;

    LowerCustomSuckerType(String s)
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
