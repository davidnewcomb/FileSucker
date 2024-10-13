package uk.co.bigsoft.filesucker;

public class CopySukaType extends SukaType
{

    CopySukaType(String f)
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
