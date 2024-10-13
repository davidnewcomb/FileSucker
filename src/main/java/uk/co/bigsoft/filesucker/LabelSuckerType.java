package uk.co.bigsoft.filesucker;

public class LabelSuckerType extends SuckerType
{

    LabelSuckerType(String f)
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
        return format;
    }
}
