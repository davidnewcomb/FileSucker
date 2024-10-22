package uk.co.bigsoft.filesucker.task;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.task.looper.LooperCmd;

public class TaskModel {
	private static final Pattern looperPattern = Pattern.compile("\\{[^}]*\\}");
	private SwingPropertyChangeSupport propChangeFirer;
	private String url = "";
	private int urlCaretStart = 0;
	private int urlCaretEnd = 0;
	private String selectedUrl = "";
	private String originalAddress = "";
	private String selectedOriginalAddress = "";
	private String directory = "";
	private String errorMessage = "";
	private String prefix = "";
	private int prefixCaretStart = 0;
	private int prefixCaretEnd = 0;
	private String selectedPrefix = "";
	private String suffix = "";
	private int suffixCaretStart = 0;
	private int suffixCaretEnd = 0;
	private String selectedSuffix = "";
	private boolean suffixEnd = false;
	private boolean saveUrl = false;
	private boolean saveOnly = false;

	public TaskModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public List<Integer> getLooperIds() {
		ArrayList<Integer> l = new ArrayList<>();
		Matcher matcher = looperPattern.matcher(url);
		while (matcher.find()) {
			String s = matcher.group();
			if (LooperCmd.isLooperText(s)) {
				String[] params = LooperCmd.getLooperGutsArray(s);
				l.add(Integer.valueOf(params[1]));
			}
		}
		return l;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String x) {
		String oldVal = url;
		url = x;
		propChangeFirer.firePropertyChange(TaskProps.F_URL, oldVal, url);
	}

	public String getSelectedUrl() {
		if (LooperCmd.isLooperText(selectedUrl)) {
			return "{" + LooperCmd.getLooperGutsArray(selectedUrl)[1] + "}";
		}
		return selectedUrl;
	}

	public void setSelectedUrl(String x) {
		String oldVal = selectedUrl;
		selectedUrl = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SELECTED_URL, oldVal, selectedUrl);
	}

	public String getSelectedPrefix() {
		if (LooperCmd.isLooperText(selectedPrefix)) {
			return "{" + LooperCmd.getLooperGutsArray(selectedPrefix)[1] + "}";
		}
		return selectedPrefix;
	}

	public void setSelectedPrefix(String x) {
		String oldVal = selectedPrefix;
		selectedPrefix = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SELECTED_PREFIX, oldVal, selectedPrefix);
	}

	public String getSelectedSuffix() {
		if (LooperCmd.isLooperText(selectedSuffix)) {
			return "{" + LooperCmd.getLooperGutsArray(selectedSuffix)[1] + "}";
		}
		return selectedSuffix;
	}

	public void setSelectedSuffix(String x) {
		String oldVal = selectedSuffix;
		selectedSuffix = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SELECTED_SUFFIX, oldVal, selectedSuffix);
	}

	public String getOriginalAddress() {
		return this.originalAddress;
	}

	public void setOriginalAddress(String x) {
		String oldVal = originalAddress;
		originalAddress = x;
		propChangeFirer.firePropertyChange(TaskProps.F_ORIGINAL_ADDRESS, oldVal, originalAddress);
	}

	public String getSelectedOriginalAddress() {
		return selectedOriginalAddress;
	}

	public void setSelectedOriginalAddress(String x) {
		String oldVal = selectedOriginalAddress;
		selectedOriginalAddress = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SELECTED_ORIGINAL_ADDRESS, oldVal, selectedOriginalAddress);
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String x) {
		String oldVal = directory;
		directory = x;
		propChangeFirer.firePropertyChange(TaskProps.F_DIRECTORY, oldVal, directory);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String x) {
		String oldVal = errorMessage;
		errorMessage = x;
		propChangeFirer.firePropertyChange(TaskProps.F_ERROR_MESSAGE, oldVal, errorMessage);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String x) {
		String oldVal = prefix;
		prefix = x;
		propChangeFirer.firePropertyChange(TaskProps.F_PREFIX, oldVal, prefix);
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String x) {
		String oldVal = suffix;
		suffix = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SUFFIX, oldVal, suffix);
	}

	public boolean isSuffixEnd() {
		return suffixEnd;
	}

	public void setSuffixEnd(boolean x) {
		boolean oldVal = suffixEnd;
		suffixEnd = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SUFFIX_END, oldVal, suffixEnd ? "0" : "1");
	}

	public boolean isSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(boolean x) {
		boolean oldVal = saveUrl;
		saveUrl = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SAVE_URL, oldVal, saveUrl ? "0" : "1");
	}

	public boolean isSaveOnly() {
		return saveOnly;
	}

	public void setSaveOnly(boolean x) {
		boolean oldVal = saveOnly;
		saveOnly = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SAVE_ONLY, oldVal, saveOnly ? "0" : "1");
	}

	public int getUrlCaretStart() {
		return urlCaretStart;
	}

	public void setUrlCaretStart(int urlCaretStart) {
		this.urlCaretStart = urlCaretStart;
	}

	public int getUrlCaretEnd() {
		return urlCaretEnd;
	}

	public void setUrlCaretEnd(int urlCaretEnd) {
		this.urlCaretEnd = urlCaretEnd;
	}

	public int getPrefixCaretStart() {
		return prefixCaretStart;
	}

	public void setPrefixCaretStart(int prefixCaretStart) {
		this.prefixCaretStart = prefixCaretStart;
	}

	public int getPrefixCaretEnd() {
		return prefixCaretEnd;
	}

	public void setPrefixCaretEnd(int prefixCaretEnd) {
		this.prefixCaretEnd = prefixCaretEnd;
	}

	public int getSuffixCaretStart() {
		return suffixCaretStart;
	}

	public void setSuffixCaretStart(int suffixCaretStart) {
		this.suffixCaretStart = suffixCaretStart;
	}

	public int getSuffixCaretEnd() {
		return suffixCaretEnd;
	}

	public void setSuffixCaretEnd(int suffixCaretEnd) {
		this.suffixCaretEnd = suffixCaretEnd;
	}

	public void replaceSelectedUrl(String looperText) {
		String oldVal = url;

		StringBuilder s = new StringBuilder(url);
		if (urlCaretStart != urlCaretEnd) {
			s.delete(urlCaretStart, urlCaretEnd);
			urlCaretEnd = urlCaretStart + looperText.length();
		}
		s.insert(urlCaretStart, looperText);
		url = s.toString();
		propChangeFirer.firePropertyChange(TaskProps.F_URL, oldVal, url);
	}

	public void replaceSelectedPrefix(String looperText) {
		String oldVal = prefix;

		StringBuilder s = new StringBuilder(prefix);
		if (prefixCaretStart != prefixCaretEnd) {
			s.delete(prefixCaretStart, prefixCaretEnd);
			prefixCaretEnd = prefixCaretStart + looperText.length();
		}
		s.insert(prefixCaretStart, looperText);
		prefix = s.toString();
		propChangeFirer.firePropertyChange(TaskProps.F_PREFIX, oldVal, prefix);
	}

	public void replaceSelectedSuffix(String looperText) {
		String oldVal = suffix;

		StringBuilder s = new StringBuilder(suffix);
		if (suffixCaretStart != suffixCaretEnd) {
			s.delete(suffixCaretStart, suffixCaretEnd);
			suffixCaretEnd = suffixCaretStart + looperText.length();
		}
		s.insert(suffixCaretStart, looperText);
		suffix = s.toString();
		propChangeFirer.firePropertyChange(TaskProps.F_SUFFIX, oldVal, suffix);
	}

}
