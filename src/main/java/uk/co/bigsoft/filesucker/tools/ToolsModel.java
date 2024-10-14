package uk.co.bigsoft.filesucker.tools;

import java.beans.PropertyChangeListener;

import javax.swing.event.SwingPropertyChangeSupport;

public class ToolsModel {

	private SwingPropertyChangeSupport propChangeFirer;
	private String working = "";
	private String selectedWorking = "";
	private String launchProfile = "";

	public ToolsModel() {
		propChangeFirer = new SwingPropertyChangeSupport(this);
	}

	public void addListener(PropertyChangeListener prop) {
		propChangeFirer.addPropertyChangeListener(prop);
	}

	public String getWorking() {
		return working;
	}

	public void setWorking(String text) {
		String oldVal = this.working;
		this.working = text;
		propChangeFirer.firePropertyChange(ToolsProps.F_WORKING, oldVal, this.working);
	}

	public String getSelectedWorking() {
		return selectedWorking;
	}

	public void setSelectedWorking(String selectedWorking) {
		String oldVal = this.selectedWorking;
		this.selectedWorking = selectedWorking;
		propChangeFirer.firePropertyChange(ToolsProps.F_SELECTED_WORKING, oldVal, this.selectedWorking);
	}

	public String getLaunchProfile() {
		return launchProfile;
	}

	public void setLaunchProfile(String launchProfile) {
		this.launchProfile = launchProfile;
	}

}
