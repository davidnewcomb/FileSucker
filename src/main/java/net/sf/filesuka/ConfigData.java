package net.sf.filesuka;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import net.sf.filesuka.gui.taskscreen.TaskScreen;

public class ConfigData extends Properties
{
    private static final String listSeperator = "¬"; //$NON-NLS-1$

    private final String CONFIG_FILE = "FileSuka.cfg.txt"; //$NON-NLS-1$

    private final String LAB_BASE = "base"; //$NON-NLS-1$

    private final String DEF_BASE = "c:\\tmp"; //$NON-NLS-1$

    private final String LAB_NUMBERFROM = "numberFrom"; //$NON-NLS-1$

    private final String DEF_NUMBERFROM = "1"; //$NON-NLS-1$

    private final String LAB_NUMBERTO = "numberTo"; //$NON-NLS-1$

    private final String DEF_NUMBERTO = "15"; //$NON-NLS-1$

    private final String LAB_NUMBERPAD = "numberPad"; //$NON-NLS-1$

    private final String DEF_NUMBERPAD = "2"; //$NON-NLS-1$

    private final String LAB_TEXTFROM = "TextFrom"; //$NON-NLS-1$

    private final String DEF_TEXTFROM = "a"; //$NON-NLS-1$

    private final String LAB_TEXTTO = "TextTo"; //$NON-NLS-1$

    private final String DEF_TEXTTO = "z"; //$NON-NLS-1$

    private final String LAB_POSTPREFIX = "postPrefix"; //$NON-NLS-1$

    private final String DEF_POSTPREFIX = "_"; //$NON-NLS-1$

    private final String LAB_MAXTASKS = "maxTasks"; //$NON-NLS-1$

    private final String DEF_MAXTASKS = "1"; //$NON-NLS-1$

    private final String LAB_MAXSUBTASKS = "maxSubTasks"; //$NON-NLS-1$

    private final String DEF_MAXSUBTASKS = "1"; //$NON-NLS-1$

    private final String LAB_HELPER_WEB = "helperWeb"; //$NON-NLS-1$

    private final String DEF_HELPER_WEB = "cmd /c \"C:\\Program Files\\Internet Explorer\\iexplore.exe\" %s"; //$NON-NLS-1$

    private final String LAB_HELPER_TEXT = "helperText"; //$NON-NLS-1$

    private final String DEF_HELPER_TEXT = "cmd /c \"notepad %s\""; //$NON-NLS-1$

    private final String LAB_DELAYSOCKREADMS = "delaySockReadMs"; //$NON-NLS-1$

    private final String DEF_DELAYSOCKREADMS = "0"; //$NON-NLS-1$

    private final String LAB_DELAYFILESMS = "delayFilesMs"; //$NON-NLS-1$

    private final String DEF_DELAYFILESMS = "0"; //$NON-NLS-1$

    private final String LAB_FINDEXTN = "findExtension"; //$NON-NLS-1$

    private final String DEF_FINDEXTN = "jpg"; //$NON-NLS-1$

    private final String LAB_NUMBERLOOPHISTORY = "numberLooperHistory"; //$NON-NLS-1$

    private final String LAB_TEXTLOOPHISTORY = "textLooperHistory"; //$NON-NLS-1$

    private final String LAB_LAUNCHPROFILES = "LaunchProfiles"; //$NON-NLS-1$

    private final String LAB_VERSION = "Version"; //$NON-NLS-1$

    private final String LAB_OPENDIRECTORY = "OpenDirectory"; //$NON-NLS-1$

    private final String DEF_OPENDIRECTORY = "explorer %s"; //$NON-NLS-1$

    private File base;

    private Integer numberFrom;
    private Integer numberTo;
    private Integer numberPad;

    private Integer maxTasks;
    private Integer maxSubTasks;

    private String textFrom;
    private String textTo;
    private String postPrefix;
    private String helperWeb;
    private String helperText;
    private String openDirectory;

    private String[] findExtn;

    private Integer delaySockReadMs;
    private Integer delayFilesMs;

    private HistoryDropDown numberLooperHistory;
    private HistoryDropDown textLooperHistory;

    private List<String> launchProfiles;

    ConfigData()
    {
        // String error = "" ;

        try
        {
            loadDefaults();

            File config = new File(CONFIG_FILE);
            if (config.exists())
            {
                load(new FileInputStream(CONFIG_FILE));
            }

            populate();
            save();

            return;
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e.toString());
        }
    }

    void loadDefaults()
    {
        setProperty(LAB_BASE, DEF_BASE);
        setProperty(LAB_NUMBERTO, DEF_NUMBERTO);
        setProperty(LAB_NUMBERFROM, DEF_NUMBERFROM);
        setProperty(LAB_NUMBERPAD, DEF_NUMBERPAD);
        setProperty(LAB_TEXTTO, DEF_TEXTTO);
        setProperty(LAB_TEXTFROM, DEF_TEXTFROM);
        setProperty(LAB_POSTPREFIX, DEF_POSTPREFIX);
        setProperty(LAB_MAXTASKS, DEF_MAXTASKS);
        setProperty(LAB_MAXSUBTASKS, DEF_MAXSUBTASKS);
        setProperty(LAB_FINDEXTN, DEF_FINDEXTN);
        setProperty(LAB_HELPER_WEB, DEF_HELPER_WEB);
        setProperty(LAB_HELPER_TEXT, DEF_HELPER_TEXT);
        setProperty(LAB_DELAYSOCKREADMS, DEF_DELAYSOCKREADMS);
        setProperty(LAB_DELAYFILESMS, DEF_DELAYFILESMS);
        setProperty(LAB_NUMBERLOOPHISTORY, "");
        setProperty(LAB_TEXTLOOPHISTORY, "");
        setProperty(LAB_LAUNCHPROFILES, "");
        setProperty(LAB_OPENDIRECTORY, DEF_OPENDIRECTORY);
    }

    void populate()
    {
        String[] ta;
        String t = getProperty(LAB_BASE);
        base = new File(t);
        numberTo = new Integer(getIntProperty(LAB_NUMBERTO));
        numberFrom = new Integer(getIntProperty(LAB_NUMBERFROM));
        numberPad = new Integer(getIntProperty(LAB_NUMBERPAD));
        textTo = getProperty(LAB_TEXTTO);
        textFrom = getProperty(LAB_TEXTFROM);
        postPrefix = getProperty(LAB_POSTPREFIX);
        maxTasks = new Integer(getIntProperty(LAB_MAXTASKS));
        maxSubTasks = new Integer(getIntProperty(LAB_MAXSUBTASKS));
        t = getProperty(LAB_FINDEXTN);
        setFindExtn(t);
        helperWeb = getProperty(LAB_HELPER_WEB);
        helperText = getProperty(LAB_HELPER_TEXT);
        delaySockReadMs = new Integer(getIntProperty(LAB_DELAYSOCKREADMS));
        delayFilesMs = new Integer(getIntProperty(LAB_DELAYFILESMS));

        List<String> l = getListProperty(LAB_NUMBERLOOPHISTORY);
        numberLooperHistory = new HistoryDropDown(new NumberLooperSort());
        for (String s : l)
        {
            ta = s.split(":", 2);
            if (ta.length == 1)
                numberLooperHistory.add(new HistoryElement(ta[0]));
            else
                numberLooperHistory.add(new HistoryElement(ta[0], ta[1]));
        }

        l = getListProperty(LAB_TEXTLOOPHISTORY);
        textLooperHistory = new HistoryDropDown(new TextLooperSort());
        for (String s : l)
        {
            ta = s.split(":", 2);
            if (ta.length == 1)
                textLooperHistory.add(new HistoryElement(ta[0]));
            else
                textLooperHistory.add(new HistoryElement(ta[0], ta[1]));
        }

        launchProfiles = getListProperty(LAB_LAUNCHPROFILES);
        openDirectory = getProperty(LAB_OPENDIRECTORY, DEF_OPENDIRECTORY);
    }

    int getIntProperty(String lab)
    {
        String txt = getProperty(lab);

        try
        {
            return Integer.parseInt(txt);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    List<String> getListProperty(String lab)
    {
        LinkedList<String> list = new LinkedList<String>();
        String txt = getProperty(lab, "");
        if ("".equals(txt))
            return list;
        String[] ss = txt.split(listSeperator);
        for (int i = 0 ; i < ss.length ; ++i)
        {
            list.add(ss[i].trim());
        }
        return list;
    }

    String getPropertyFromList(List<String> items)
    {
        if (items.size() == 0)
            return "";
        if (items.size() == 1)
            return items.get(0).toString();

        StringBuffer sb = new StringBuffer();
        sb.append(items.get(0).toString());
        for (int i = 1 ; i < items.size() ; ++i)
        {
            sb.append(listSeperator);
            sb.append(items.get(i).toString());
        }
        return sb.toString();
    }

    String getPropertyFromList(HistoryDropDown items)
    {
        if (items.size() == 0)
            return "";
        if (items.size() == 1)
            return items.first().toString();
        StringBuffer s = new StringBuffer();
        for (HistoryElement he : items)
        {
            s.append(he.toString());
            s.append(listSeperator);
        }
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }

    void save()
    {
        StringBuffer header = new StringBuffer();
        header.append("FileSuka ");
        header.append(FileSuka.version);
        header.append(" (");
        header.append(FileSuka.versionDate);
        header.append(")");

        setProperty(LAB_VERSION, FileSuka.version);
        setProperty(LAB_BASE, base.getPath());
        setProperty(LAB_NUMBERTO, "" + numberTo);
        setProperty(LAB_NUMBERFROM, "" + numberFrom);
        setProperty(LAB_NUMBERPAD, "" + numberPad);
        setProperty(LAB_TEXTTO, textTo);
        setProperty(LAB_TEXTFROM, textFrom);
        setProperty(LAB_POSTPREFIX, postPrefix);
        setProperty(LAB_MAXTASKS, "" + maxTasks);
        setProperty(LAB_MAXSUBTASKS, "" + maxSubTasks);
        String s = Utility.implode(findExtn, ",");
        setProperty(LAB_FINDEXTN, s);
        setProperty(LAB_HELPER_WEB, helperWeb);
        setProperty(LAB_HELPER_TEXT, helperText);
        setProperty(LAB_OPENDIRECTORY, openDirectory);
        setProperty(LAB_DELAYSOCKREADMS, "" + delaySockReadMs);
        setProperty(LAB_DELAYFILESMS, "" + delayFilesMs);

        setProperty(LAB_NUMBERLOOPHISTORY,
                getPropertyFromList(numberLooperHistory));
        setProperty(LAB_TEXTLOOPHISTORY, getPropertyFromList(textLooperHistory));

        setProperty(LAB_LAUNCHPROFILES, getPropertyFromList(launchProfiles));

        try
        {
            store(new FileOutputStream(CONFIG_FILE), header.toString());
        }
        catch (Exception e)
        {
            TaskScreen
                    .setErrorMessage("Could not save config: " + e.toString());
        }
    }

    // Base directory
    public String getScreenBaseDir()
    {
        StringBuffer sb = new StringBuffer(base.toString());
        sb.append(File.separatorChar);
        return sb.toString();
    }

    public boolean setScreenBaseDir(String s)
    {
        // File f = new File (s) ;
        // if (f.exists () == false || f.isDirectory () == false)
        // return false ;
        //
        // setScreenBaseDir (f) ;
        base = new File(s);
        FileSuka.configScreen.setBaseDirectory(s);
        return true;
    }

    public void setScreenBaseDir(File f)
    {
        base = f;
        FileSuka.configScreen.setBaseDirectory(f.toString());
    }

    // Web Helper directory
    public String getScreenHelperWeb()
    {
        return helperWeb;
    }

    // Web Helper directory
    public String getScreenHelperText()
    {
        return helperText;
    }

    // Web Helper directory
    public String getOpenDirectory()
    {
        return openDirectory;
    }

    public void setScreenHelperWeb(String s)
    {
        helperWeb = s;
    }

    public void setScreenHelperText(String s)
    {
        helperText = s;
    }

    public File getBaseDir()
    {
        return base;
    }

    // Number defaults
    public String getScreenNumberFrom()
    {
        return numberFrom.toString();
    }

    public String getScreenNumberTo()
    {
        return numberTo.toString();
    }

    public String getScreenNumberPad()
    {
        return numberPad.toString();
    }

    // Text defaults
    public String getScreenTextFrom()
    {
        return textFrom;
    }

    public String getScreenTextTo()
    {
        return textTo;
    }

    public String getScreenPostPrefix()
    {
        return postPrefix;
    }

    public String getScreenMaxTasks()
    {
        return maxTasks.toString();
    }

    public String getScreenMaxSubTasks()
    {
        return maxSubTasks.toString();
    }

    public int getNumberFrom()
    {
        return numberFrom.intValue();
    }

    public int getNumberTo()
    {
        return numberTo.intValue();
    }

    public int getNumberPad()
    {
        return numberPad.intValue();
    }

    public String getTextFrom()
    {
        return textFrom;
    }

    public String getTextTo()
    {
        return textTo;
    }

    public String getPostPrefix()
    {
        return postPrefix;
    }

    public String[] getFindExtn()
    {
        return findExtn;
    }

    public int getMaxTasks()
    {
        return maxTasks.intValue();
    }

    public String getHelperWeb()
    {
        return helperWeb;
    }

    public String getHelperText()
    {
        return helperText;
    }

    public void setNumberFrom(String s)
    {
        numberFrom = new Integer(s);
    }

    public void setNumberTo(String s)
    {
        numberTo = new Integer(s);
    }

    public void setNumberPad(String s)
    {
        numberPad = new Integer(s);
    }

    public void setTextFrom(String s)
    {
        textFrom = s;
    }

    public void setTextTo(String s)
    {
        textTo = s;
    }

    public void setPostPrefix(String s)
    {
        postPrefix = s;
    }

    // Threads
    public void setMaxTasks(String s)
    {
        maxTasks = new Integer(s);
    }

    public void setMaxSubTasks(String s)
    {
        maxSubTasks = new Integer(s);
    }

    public void setFindExtn(String s)
    {
        findExtn = s.split(",");
        for (int i = 0 ; i < findExtn.length ; ++i)
            findExtn[i] = findExtn[i].trim();
    }

    public void setHelperWeb(String s)
    {
        helperWeb = s;
    }

    public void setHelperText(String s)
    {
        helperText = s;
    }

    public void setOpenDirectory(String s)
    {
        openDirectory = s;
    }

    // Hiding
    public Integer getDelaySockReadMs()
    {
        return delaySockReadMs;
    }

    public String getScreenDelaySockReadMs()
    {
        return delaySockReadMs.toString();
    }

    public void setDelaySockReadMs(String i)
    {
        delaySockReadMs = new Integer(i);
    }

    public Integer getDelayFilesMs()
    {
        return delayFilesMs;
    }

    public String getScreenDelayFilesMs()
    {
        return delayFilesMs.toString();
    }

    public void setDelayFilesMs(String i)
    {
        delayFilesMs = new Integer(i);
    }

    public HistoryDropDown getNumberLooperHistory()
    {
        return numberLooperHistory;
    }

    public void setNumberLooperHistory(HistoryDropDown x)
    {
        numberLooperHistory = x;
    }

    public HistoryDropDown getTextLooperHistory()
    {
        return textLooperHistory;
    }

    public void setTextLooperHistory(HistoryDropDown x)
    {
        textLooperHistory = x;
    }

    public List<String> getLaunchProfiles()
    {
        return launchProfiles;
    }
}
