package uk.co.bigsoft.filesucker.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class ConfigSaver extends Properties {
	private static final String listSeperator = "�"; //$NON-NLS-1$

	private final String CONFIG_FILE = "FileSucker.cfg.txt"; //$NON-NLS-1$

	private final String LAB_BASE = "base"; //$NON-NLS-1$
	private final String LAB_NUMBERFROM = "numberFrom"; //$NON-NLS-1$
	private final String LAB_NUMBERTO = "numberTo"; //$NON-NLS-1$
	private final String LAB_NUMBERPAD = "numberPad"; //$NON-NLS-1$
	private final String LAB_TEXTFROM = "TextFrom"; //$NON-NLS-1$
	private final String LAB_TEXTTO = "TextTo"; //$NON-NLS-1$
	private final String LAB_POSTPREFIX = "postPrefix"; //$NON-NLS-1$
	private final String LAB_MAXTASKS = "maxTasks"; //$NON-NLS-1$
	private final String LAB_MAXSUBTASKS = "maxSubTasks"; //$NON-NLS-1$
	private final String LAB_HELPER_WEB = "helperWeb"; //$NON-NLS-1$
	private final String LAB_HELPER_TEXT = "helperText"; //$NON-NLS-1$
	private final String LAB_DELAYSOCKREADMS = "delaySockReadMs"; //$NON-NLS-1$
	private final String LAB_DELAYFILESMS = "delayFilesMs"; //$NON-NLS-1$
	private final String LAB_FINDEXTN = "findExtension"; //$NON-NLS-1$
	private final String LAB_NUMBERLOOPHISTORY = "numberLooperHistory"; //$NON-NLS-1$
	private final String LAB_TEXTLOOPHISTORY = "textLooperHistory"; //$NON-NLS-1$
	private final String LAB_LAUNCHPROFILES = "LaunchProfiles"; //$NON-NLS-1$
	private final String LAB_VERSION = "Version"; //$NON-NLS-1$
	private final String LAB_HELPER_DIRECTORY = "OpenDirectory"; //$NON-NLS-1$

	public ConfigSaver() {
		//
	}

	public void save(ConfigModel cm) {
		Properties p = new Properties();

		StringBuffer header = new StringBuffer();
		header.append("FileSucker ");
		header.append(FileSucker.version);
		header.append(" (");
		header.append(FileSucker.versionDate);
		header.append(")");

		// TODO check type of findExtn String or String[]
		// String s = Utility.implode(cm.getFindExtn(), ",");

		p.setProperty(LAB_VERSION, FileSucker.version);
		p.setProperty(LAB_BASE, cm.getBaseDir());
		p.setProperty(LAB_NUMBERTO, String.valueOf(cm.getNumberTo()));
		p.setProperty(LAB_NUMBERFROM, String.valueOf(cm.getNumberFrom()));
		p.setProperty(LAB_NUMBERPAD, String.valueOf(cm.getNumberPad()));
		p.setProperty(LAB_TEXTTO, cm.getTextTo());
		p.setProperty(LAB_TEXTFROM, cm.getTextFrom());
		p.setProperty(LAB_POSTPREFIX, cm.getPostPrefix());
		p.setProperty(LAB_MAXTASKS, String.valueOf(cm.getMaxTasks()));
		p.setProperty(LAB_MAXSUBTASKS, String.valueOf(cm.getMaxSubTasks()));
		p.setProperty(LAB_FINDEXTN, cm.getFindExtn());
		p.setProperty(LAB_HELPER_WEB, cm.getHelperWeb());
		p.setProperty(LAB_HELPER_TEXT, cm.getHelperText());
		p.setProperty(LAB_HELPER_DIRECTORY, cm.getHelperDirectory());
		p.setProperty(LAB_DELAYSOCKREADMS, String.valueOf(cm.getDelaySockReadMs()));
		p.setProperty(LAB_DELAYFILESMS, String.valueOf(cm.getDelayFilesMs()));

		p.setProperty(LAB_NUMBERLOOPHISTORY, stringListToString(cm.getNumberLooperHistory()));
		p.setProperty(LAB_TEXTLOOPHISTORY, stringListToString(cm.getTextLooperHistory()));
		p.setProperty(LAB_LAUNCHPROFILES, stringListToString(cm.getLaunchProfiles()));

		try {
			FileOutputStream fos = new FileOutputStream(CONFIG_FILE);
			p.store(fos, header.toString());
			fos.close();
		} catch (Exception e) {
			TaskScreen.setErrorMessage("Could not save config: " + e.toString());
		}
	}

	public ConfigModel load() {
		ConfigModel cm = new ConfigModel();
		Properties p = new Properties();

		File config = new File(CONFIG_FILE);
		if (config.exists()) {
			try {
				FileInputStream fis = new FileInputStream(CONFIG_FILE);
				p.load(fis);
				fis.close();
			} catch (Exception e) {
				System.out.println("Problem reading " + CONFIG_FILE + ": " + e.toString());
				return cm;
			}
		}

		cm.setNumberTo(getIntProperty(p, LAB_NUMBERTO, cm.getNumberTo()));
		cm.setNumberFrom(getIntProperty(p, LAB_NUMBERFROM, cm.getNumberFrom()));
		cm.setNumberPad(getIntProperty(p, LAB_NUMBERPAD, cm.getNumberPad()));

		cm.setTextTo(getStringProperty(p, LAB_TEXTTO, cm.getTextTo()));
		cm.setTextFrom(getStringProperty(p, LAB_TEXTFROM, cm.getTextFrom()));

		cm.setDelayFilesMs(getIntProperty(p, LAB_DELAYFILESMS, cm.getDelayFilesMs()));
		cm.setDelaySockReadMs(getIntProperty(p, LAB_DELAYSOCKREADMS, cm.getDelaySockReadMs()));

		cm.setMaxSubTasks(getIntProperty(p, LAB_MAXSUBTASKS, cm.getMaxTasks()));
		cm.setMaxTasks(getIntProperty(p, LAB_MAXTASKS, cm.getMaxTasks()));
		
		cm.setHelperText(getStringProperty(p, LAB_HELPER_TEXT, cm.getHelperText()));
		cm.setHelperWeb(getStringProperty(p, LAB_HELPER_WEB, cm.getHelperWeb()));
		
		cm.setLaunchProfiles(getStringListProperty(p, LAB_LAUNCHPROFILES, cm.getLaunchProfiles()));
		cm.setNumberLooperHistory(getStringListProperty(p, LAB_NUMBERLOOPHISTORY, cm.getNumberLooperHistory()));
		cm.setTextLooperHistory(getStringListProperty(p, LAB_TEXTLOOPHISTORY, cm.getTextLooperHistory()));
		
		cm.setOpenDirectory(getStringProperty(p, LAB_HELPER_DIRECTORY, cm.getHelperDirectory()));
		cm.setFindExtn(getStringProperty(p, LAB_FINDEXTN, cm.getFindExtn()));

		cm.setPostPrefix(getStringProperty(p, LAB_POSTPREFIX, cm.getPostPrefix()));
		cm.setBaseDir(getStringProperty(p, LAB_BASE, cm.getBaseDir()));

		return cm;
	}

	private int getIntProperty(Properties p, String lab, int def) {

		String s = p.getProperty(lab);
		if (s == null || "".equals(s)) {
			return def;
		}

		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	private String getStringProperty(Properties p, String lab, String def) {

		String s = p.getProperty(lab);
		if (s == null || "".equals(s)) {
			return def;
		}

		return def;
	}
	
	private List<String> getStringListProperty(Properties p, String lab, List<String> def) {
		
		String txt = p.getProperty(lab, "");
		if ("".equals(txt))
			return def;
		
		List<String> l = Arrays.asList(txt.split(listSeperator))
			.stream()
			.map(s -> s.trim())
			.filter(f -> !"".equals(f))
			.collect(Collectors.toList());

		return l;
	}
	
	private String stringListToString(List<String> list) {
		if (list.size() == 0) {
			return "";
		}

		String s = String.join(listSeperator, list);
		return s;
	}

//	void populate() {
//		String[] ta;
//		String t = getProperty(LAB_BASE);
//		base = new File(t);
//		numberTo = new Integer(getIntProperty(LAB_NUMBERTO));
//		numberFrom = new Integer(getIntProperty(LAB_NUMBERFROM));
//		numberPad = new Integer(getIntProperty(LAB_NUMBERPAD));
//		textTo = getProperty(LAB_TEXTTO);
//		textFrom = getProperty(LAB_TEXTFROM);
//		postPrefix = getProperty(LAB_POSTPREFIX);
//		maxTasks = new Integer(getIntProperty(LAB_MAXTASKS));
//		maxSubTasks = new Integer(getIntProperty(LAB_MAXSUBTASKS));
//		t = getProperty(LAB_FINDEXTN);
//		setFindExtn(t);
//		helperWeb = getProperty(LAB_HELPER_WEB);
//		helperText = getProperty(LAB_HELPER_TEXT);
//		delaySockReadMs = new Integer(getIntProperty(LAB_DELAYSOCKREADMS));
//		delayFilesMs = new Integer(getIntProperty(LAB_DELAYFILESMS));
//
//		List<String> l = getListProperty(LAB_NUMBERLOOPHISTORY);
//		numberLooperHistory = new HistoryDropDown(new NumberLooperSort());
//		for (String s : l) {
//			ta = s.split(":", 2);
//			if (ta.length == 1)
//				numberLooperHistory.add(new HistoryElement(ta[0]));
//			else
//				numberLooperHistory.add(new HistoryElement(ta[0], ta[1]));
//		}
//
//		l = getListProperty(LAB_TEXTLOOPHISTORY);
//		textLooperHistory = new HistoryDropDown(new TextLooperSort());
//		for (String s : l) {
//			ta = s.split(":", 2);
//			if (ta.length == 1)
//				textLooperHistory.add(new HistoryElement(ta[0]));
//			else
//				textLooperHistory.add(new HistoryElement(ta[0], ta[1]));
//		}
//
//		launchProfiles = getListProperty(LAB_LAUNCHPROFILES);
//		openDirectory = getProperty(LAB_OPENDIRECTORY, DEF_OPENDIRECTORY);
//	}
//
//
//	List<String> getListProperty(String lab) {
//		LinkedList<String> list = new LinkedList<String>();
//		String txt = getProperty(lab, "");
//		if ("".equals(txt))
//			return list;
//		String[] ss = txt.split(listSeperator);
//		for (int i = 0; i < ss.length; ++i) {
//			list.add(ss[i].trim());
//		}
//		return list;
//	}
//
//	String getPropertyFromList(List<String> items) {
//		if (items.size() == 0)
//			return "";
//		if (items.size() == 1)
//			return items.get(0).toString();
//
//		StringBuffer sb = new StringBuffer();
//		sb.append(items.get(0).toString());
//		for (int i = 1; i < items.size(); ++i) {
//			sb.append(listSeperator);
//			sb.append(items.get(i).toString());
//		}
//		return sb.toString();
//	}
//
//	String getPropertyFromList(HistoryDropDown items) {
//		if (items.size() == 0)
//			return "";
//		if (items.size() == 1)
//			return items.first().toString();
//		StringBuffer s = new StringBuffer();
//		for (HistoryElement he : items) {
//			s.append(he.toString());
//			s.append(listSeperator);
//		}
//		s.deleteCharAt(s.length() - 1);
//		return s.toString();
//	}
//
//	// Base directory
//	public String getScreenBaseDir() {
//		StringBuffer sb = new StringBuffer(base.toString());
//		sb.append(File.separatorChar);
//		return sb.toString();
//	}
//
//	public boolean setScreenBaseDir(String s) {
//		// File f = new File (s) ;
//		// if (f.exists () == false || f.isDirectory () == false)
//		// return false ;
//		//
//		// setScreenBaseDir (f) ;
//		base = new File(s);
//		FileSucker.configScreen.setBaseDirectory(s);
//		return true;
//	}
//
//	public void setScreenBaseDir(File f) {
//		base = f;
//		FileSucker.configScreen.setBaseDirectory(f.toString());
//	}
//
//	// Web Helper directory
//	public String getScreenHelperWeb() {
//		return helperWeb;
//	}
//
//	// Web Helper directory
//	public String getScreenHelperText() {
//		return helperText;
//	}
//
//	// Web Helper directory
//	public String getOpenDirectory() {
//		return openDirectory;
//	}
//
//	public void setScreenHelperWeb(String s) {
//		helperWeb = s;
//	}
//
//	public void setScreenHelperText(String s) {
//		helperText = s;
//	}
//
//	public File getBaseDir() {
//		return base;
//	}
//
//	// Number defaults
//	public String getScreenNumberFrom() {
//		return numberFrom.toString();
//	}
//
//	public String getScreenNumberTo() {
//		return numberTo.toString();
//	}
//
//	public String getScreenNumberPad() {
//		return numberPad.toString();
//	}
//
//	// Text defaults
//	public String getScreenTextFrom() {
//		return textFrom;
//	}
//
//	public String getScreenTextTo() {
//		return textTo;
//	}
//
//	public String getScreenPostPrefix() {
//		return postPrefix;
//	}
//
//	public String getScreenMaxTasks() {
//		return maxTasks.toString();
//	}
//
//	public String getScreenMaxSubTasks() {
//		return maxSubTasks.toString();
//	}
//
//	public int getNumberFrom() {
//		return numberFrom.intValue();
//	}
//
//	public int getNumberTo() {
//		return numberTo.intValue();
//	}
//
//	public int getNumberPad() {
//		return numberPad.intValue();
//	}
//
//	public String getTextFrom() {
//		return textFrom;
//	}
//
//	public String getTextTo() {
//		return textTo;
//	}
//
//	public String getPostPrefix() {
//		return postPrefix;
//	}
//
//	public String[] getFindExtn() {
//		return findExtn;
//	}
//
//	public int getMaxTasks() {
//		return maxTasks.intValue();
//	}
//
//	public String getHelperWeb() {
//		return helperWeb;
//	}
//
//	public String getHelperText() {
//		return helperText;
//	}
//
//	public void setNumberFrom(String s) {
//		numberFrom = new Integer(s);
//	}
//
//	public void setNumberTo(String s) {
//		numberTo = new Integer(s);
//	}
//
//	public void setNumberPad(String s) {
//		numberPad = new Integer(s);
//	}
//
//	public void setTextFrom(String s) {
//		textFrom = s;
//	}
//
//	public void setTextTo(String s) {
//		textTo = s;
//	}
//
//	public void setPostPrefix(String s) {
//		postPrefix = s;
//	}
//
//	// Threads
//	public void setMaxTasks(String s) {
//		maxTasks = new Integer(s);
//	}
//
//	public void setMaxSubTasks(String s) {
//		maxSubTasks = new Integer(s);
//	}
//
//	public void setFindExtn(String s) {
//		findExtn = s.split(",");
//		for (int i = 0; i < findExtn.length; ++i)
//			findExtn[i] = findExtn[i].trim();
//	}
//
//	public void setHelperWeb(String s) {
//		helperWeb = s;
//	}
//
//	public void setHelperText(String s) {
//		helperText = s;
//	}
//
//	public void setOpenDirectory(String s) {
//		openDirectory = s;
//	}
//
//	// Hiding
//	public Integer getDelaySockReadMs() {
//		return delaySockReadMs;
//	}
//
//	public String getScreenDelaySockReadMs() {
//		return delaySockReadMs.toString();
//	}
//
//	public void setDelaySockReadMs(String i) {
//		delaySockReadMs = new Integer(i);
//	}
//
//	public Integer getDelayFilesMs() {
//		return delayFilesMs;
//	}
//
//	public String getScreenDelayFilesMs() {
//		return delayFilesMs.toString();
//	}
//
//	public void setDelayFilesMs(String i) {
//		delayFilesMs = new Integer(i);
//	}
//
//	public HistoryDropDown getNumberLooperHistory() {
//		return numberLooperHistory;
//	}
//
//	public void setNumberLooperHistory(HistoryDropDown x) {
//		numberLooperHistory = x;
//	}
//
//	public HistoryDropDown getTextLooperHistory() {
//		return textLooperHistory;
//	}
//
//	public void setTextLooperHistory(HistoryDropDown x) {
//		textLooperHistory = x;
//	}
//
//	public List<String> getLaunchProfiles() {
//		return launchProfiles;
//	}
}
