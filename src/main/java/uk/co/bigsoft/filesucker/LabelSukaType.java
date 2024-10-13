package uk.co.bigsoft.filesucker;

public class LabelSukaType extends SukaType
{

    LabelSukaType(String f)
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
