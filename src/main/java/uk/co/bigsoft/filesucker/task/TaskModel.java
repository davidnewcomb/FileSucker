package uk.co.bigsoft.filesucker.task;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.event.SwingPropertyChangeSupport;

import uk.co.bigsoft.filesucker.Utility;

public class TaskModel {
	private SwingPropertyChangeSupport propChangeFirer;
	private String url = "";
	private String selectedUrl = "";
	private String originalAddress = "";
	private String selectedOriginalAddress = "";
	private String directory = "";
	private String errorMessage = "";
	private String prefix = "";
	private String suffix = "";
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
		Pattern p = Pattern.compile("\\{[^}]*\\}");
		Matcher matcher = p.matcher(url);
		while (matcher.find()) {
			String s = matcher.group();
			List<String> params = Utility.splitLooperText(s);
			if (params != null) {
				l.add(Integer.valueOf(params.get(1)));
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
		return selectedUrl;
	}

	public void setSelectedUrl(String x) {
		String oldVal = selectedUrl;
		selectedUrl = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SET_SELECTED_URL, oldVal, selectedUrl);
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
		propChangeFirer.firePropertyChange(TaskProps.F_SUFFIX_END, oldVal, suffixEnd);
	}

	public boolean isSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(boolean x) {
		boolean oldVal = saveUrl;
		saveUrl = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SAVE_URL, oldVal, saveUrl);
	}

	public boolean isSaveOnly() {
		return saveOnly;
	}

	public void setSaveOnly(boolean x) {
		boolean oldVal = saveOnly;
		saveOnly = x;
		propChangeFirer.firePropertyChange(TaskProps.F_SAVE_ONLY, oldVal, saveOnly);
	}
}
