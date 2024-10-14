package uk.co.bigsoft.filesucker.tools;

public class ToolsModel {

	private String working = "";
	private String selectedWorking = "";
	private String launchProfile = "";

	public ToolsModel() {
		//
	}

	public String getWorking() {
		return working;
	}

	public void setWorking(String text) {
		this.working = text;
	}

	public String getSelectedWorking() {
		return selectedWorking;
	}

	public void setSelectedWorking(String selectedWorking) {
		this.selectedWorking = selectedWorking;
	}

	public String getLaunchProfile() {
		return launchProfile;
	}

	public void setLaunchProfile(String launchProfile) {
		this.launchProfile = launchProfile;
	}

}
