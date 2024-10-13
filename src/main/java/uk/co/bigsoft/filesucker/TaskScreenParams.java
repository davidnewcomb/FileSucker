package uk.co.bigsoft.filesucker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class TaskScreenParams
{
    private final static String PROGRESS_FILE = "z_FileSuka_progress";

    private static final String LAB_VERSION = "Version";

    private static final String VAL_VERSION = "2";

    private static final String LAB_NAME = "Name";

    private static final String LAB_URL = "OrginalUrl";

    private static final String LAB_INTODIR = "IntoDir";

    private static final String LAB_PREFIX = "Prefix";

    private static final String LAB_SUFFIX = "Suffix";

    private static final String LAB_HEADERS = "Headers";

    private static final String LAB_SUFFIX_END = "SuffixEnd";

    private static final String LAB_ORGINAL_ADDRESS = "OrginalAddress";

    private static final String LAB_OLD_URL = "url";

    private static final String LAB_OLD_PREFIX = "prefix";

    private static final String LAB_OLD_INTODIR = "base";

    private static final String LAB_OLD_SUFFIX = "suffix";

    public static void save(SukaParams parms)
    {
        String base = parms.getIntoDir();
        int idx = base.indexOf("{");
        if (idx != -1)
        {
            while (idx > 0 && base.charAt(idx) == File.separatorChar)
                idx--;
            base = base.substring(0, idx);
        }

        String prefix = parms.getPrefix() == null ? "" : parms.getPrefix();
        String suffix = parms.getSuffix() == null ? "" : parms.getSuffix();
        String suffixEnd = parms.isSuffixEnd() ? "true" : "false";
        Properties props = new Properties();
        props.setProperty(LAB_VERSION, VAL_VERSION);
        props.setProperty(LAB_URL, parms.getOrginalUrl());
        props.setProperty(LAB_INTODIR, base);
        props.setProperty(LAB_PREFIX, prefix);
        props.setProperty(LAB_SUFFIX, suffix);
        props.setProperty(LAB_SUFFIX_END, suffixEnd);
        props.setProperty(LAB_ORGINAL_ADDRESS, parms.getOrginalAddress());
        File basef = new File(base);
        basef.mkdirs();

        try
        {
            for (int i = 0 ; i < 10000 ; i++)
            {
                String progressFilename = PROGRESS_FILE + i + ".txt";
                File progressFile = new File(basef, progressFilename);
                if (progressFile.exists() == false)
                {
                    FileOutputStream fos = new FileOutputStream(progressFile);
                    props.store(fos, "");
                    fos.close();
                    break;
                }
            }
        }
        catch (Exception e)
        {
            TaskScreen.setErrorMessage("Exception: " + e.toString());
        }
    }

    public static void load(File file)
    {
        System.out.println("TaskScreenParams:load: " + file.toString());
        Properties p = new Properties();
        FileInputStream is = null;

        try
        {
            is = new FileInputStream(file);
            p.load(is);
            String ver = p.getProperty(LAB_VERSION, "1");
            SukaParams sp;
            if ("1".equals(ver))
                sp = readVersion1(p);
            else if ("2".equals(ver))
                sp = readVersion2(p);
            else
            {
                System.out.println("Invalid progress version number: " + ver);
                return;
            }

            TaskScreen.load(sp);
        }
        catch (IOException ioe)
        {
            System.out.println("importData: Unable to read from file "
                    + file.toString());
            return;
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException ioe)
                {
                    System.out.println("importData: Unable to close file "
                            + file.toString());
                }
            }
        }
    }

    private static SukaParams readVersion1(Properties p)
    {
        String name = "";
        String url = p.getProperty(LAB_OLD_URL, "");
        String into = p.getProperty(LAB_OLD_INTODIR, "");
        String prefix = p.getProperty(LAB_OLD_PREFIX, "");
        String suffix = p.getProperty(LAB_OLD_SUFFIX, "");
        boolean suffixEnd = false;

        SukaParams sp = new SukaParams(name, url, into, prefix, suffix,
                new Hashtable<String, String>(), suffixEnd, "");
        return sp;
    }

    private static SukaParams readVersion2(Properties p)
    {
        String name = p.getProperty(LAB_NAME, "");
        String url = p.getProperty(LAB_URL, "");
        String into = p.getProperty(LAB_INTODIR, "");
        String prefix = p.getProperty(LAB_PREFIX, "");
        String suffix = p.getProperty(LAB_SUFFIX, "");
        String suffixEndString = p.getProperty(LAB_SUFFIX_END, "");
        boolean suffixEnd = false;
        if ("true".equals(suffixEndString))
            suffixEnd = true;
        String headersString = p.getProperty(LAB_HEADERS, "");
        String orginalAddress = p.getProperty(LAB_ORGINAL_ADDRESS, "");
        Hashtable<String, String> ht = string2Headers(headersString);

        SukaParams sp = new SukaParams(name, url, into, prefix, suffix, ht,
                suffixEnd, orginalAddress);
        return sp;

    }

    private static Hashtable<String, String> string2Headers(String str)
    {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        if ("".equals(str))
            return ht;
        String[] headersStringAr = str.split("�");
        String[] lineAr;
        for (int i = 0 ; i < headersStringAr.length ; ++i)
        {
            lineAr = headersStringAr[i].split(":", 2);
            ht.put(lineAr[0], lineAr[1]);
        }
        return ht;
    }
}
