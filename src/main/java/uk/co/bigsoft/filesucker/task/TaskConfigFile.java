package uk.co.bigsoft.filesucker.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.Utility;

public class TaskConfigFile {

	private static final String BASE_FILE_NAME = "FileSucker-progress-%d.txt";
	private static final String LAB_VERSION = "Version";
	private static final String LAB_URL = "Url";
	private static final String LAB_PREFIX = "Prefix";
	private static final String LAB_DIRECTORY = "Directory";
	private static final String LAB_SUFFIX = "Suffix";
	private static final String LAB_SUFFIX_END = "SuffixEnd";

	public TaskConfigFile() {
		//
	}

	public TaskConfig load(File cfgFile) {
		if (!cfgFile.exists()) {
			return null;
		}
		try {
			FileInputStream fis = new FileInputStream(cfgFile);
			Properties p = new Properties();
			p.load(fis);

			String url = p.getProperty(LAB_URL, "");
			String directory = p.getProperty(LAB_DIRECTORY, "");
			String prefix = p.getProperty(LAB_PREFIX, "");
			String suffix = p.getProperty(LAB_SUFFIX, "");
			boolean suffixEnd = "1".equals(p.getProperty(LAB_SUFFIX_END, "1"));

			TaskConfig tc = new TaskConfig(url, directory, prefix, suffix, suffixEnd);
			return tc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void save(TaskConfig tc) {

		FileOutputStream fos = null;
		try {
			File dir = new File(tc.getDirectory());
			File cfgFile = findFreeFile(dir);
			dir.mkdirs();

			fos = new FileOutputStream(cfgFile);
			Properties p = new Properties();

			p.setProperty(LAB_VERSION, FileSucker.version);
			p.setProperty(LAB_URL, tc.getUrl());
			p.getProperty(LAB_DIRECTORY, tc.getDirectory());
			p.getProperty(LAB_PREFIX, tc.getPrefix());
			p.getProperty(LAB_SUFFIX, tc.getSuffix());
			p.getProperty(LAB_SUFFIX_END, tc.isSuffixEnd() ? "1" : "0");

			p.store(fos, "FileChecker");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Utility.closeSafely(fos);
		}
	}

	private File findFreeFile(File dir) {
		for (int i = 0; i < Integer.MAX_VALUE; ++i) {
			String s = String.format(BASE_FILE_NAME, i);
			File f = new File(dir, s);
			if (!f.exists()) {
				return f;
			}
		}
		return null;
	}
}
