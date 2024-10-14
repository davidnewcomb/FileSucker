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
		String oldVal = this.selectedUrl;
		this.selectedUrl = selectedUrl;
		propChangeFirer.firePropertyChange(TaskProps.F_SELECTED_URL, oldVal, this.selectedUrl);
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

}
