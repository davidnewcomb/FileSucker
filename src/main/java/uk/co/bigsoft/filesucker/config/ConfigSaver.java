package uk.co.bigsoft.filesucker.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bigsoft.filesucker.FileSucker;

public class ConfigSaver extends Properties {
	private static Logger L = LoggerFactory.getLogger(ConfigSaver.class);

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
		p.setProperty(LAB_FINDEXTN, stringListToString(cm.getFindExtn()));
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
			L.debug("Could not save config: " + e.toString());
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
				L.debug("Problem reading " + CONFIG_FILE + ": " + e.toString());
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
		cm.setFindExtn(getStringListProperty(p, LAB_FINDEXTN, cm.getFindExtn()));

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
		if ("".equals(txt)) {
			return def;
		}
		List<String> l = Arrays.asList(txt.split(listSeperator)) //
				.stream() //
				.map(s -> s.trim()) //
				.filter(f -> !"".equals(f)) //
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
}
