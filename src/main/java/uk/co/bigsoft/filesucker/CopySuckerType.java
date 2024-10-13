package uk.co.bigsoft.filesucker;

public class CopySuckerType extends SuckerType
{

    CopySuckerType(String f)
    {
        super(f);
    }

    @Override
    public int numberOfIterations()
    {
        return 1;
    }

    @Override
    public String indexOf(int idx)
    {
        return null;
    }
}
