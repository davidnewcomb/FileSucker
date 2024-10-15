package uk.co.bigsoft.filesucker.task;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

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

	public TaskModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener listener) {
		propChangeFirer.addPropertyChangeListener(listener);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		String oldVal = this.url;
		this.url = url;
		propChangeFirer.firePropertyChange(TaskProps.F_URL, oldVal, this.url);
	}

	public String getSelectedUrl() {
		return selectedUrl;
	}

	public void setSelectedUrl(String selectedUrl) {
		System.out.println("---");
		System.out.println("new..selectedUrl:" + selectedUrl);
		System.out.println("this.selectedUrl:" + this.selectedUrl);
//		if ("".equals(selectedUrl) || selectedUrl.equals(this.selectedUrl)) {
//			return;
//		}
		String oldVal = this.selectedUrl;
		this.selectedUrl = selectedUrl;
		propChangeFirer.firePropertyChange(TaskProps.F_SET_SELECTED_URL, oldVal, this.selectedUrl);
	}

	public String getOriginalAddress() {
		return this.originalAddress;
	}

	public void setOriginalAddress(String originalAddress) {
		String oldVal = this.originalAddress;
		this.originalAddress = originalAddress;
		propChangeFirer.firePropertyChange(TaskProps.F_ORIGINAL_ADDRESS, oldVal, this.originalAddress);
	}

	public String getSelectedOriginalAddress() {
		return selectedOriginalAddress;
	}

	public void setSelectedOriginalAddress(String selectedOriginalAddress) {
		String oldVal = this.selectedOriginalAddress;
		this.selectedOriginalAddress = selectedOriginalAddress;
		propChangeFirer.firePropertyChange(TaskProps.F_SELECTED_ORIGINAL_ADDRESS, oldVal, this.selectedOriginalAddress);
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		String oldVal = this.directory;
		this.directory = directory;
		propChangeFirer.firePropertyChange(TaskProps.F_DIRECTORY, oldVal, this.directory);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		String oldVal = this.errorMessage;
		this.errorMessage = errorMessage;
		propChangeFirer.firePropertyChange(TaskProps.F_ERROR_MESSAGE, oldVal, this.errorMessage);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		String oldVal = this.prefix;
		this.prefix = prefix;
		propChangeFirer.firePropertyChange(TaskProps.F_PREFIX, oldVal, this.prefix);
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		String oldVal = this.suffix;
		this.suffix = suffix;
		propChangeFirer.firePropertyChange(TaskProps.F_SUFFIX, oldVal, this.suffix);
	}
}
