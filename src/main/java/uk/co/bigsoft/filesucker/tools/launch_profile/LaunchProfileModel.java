package uk.co.bigsoft.filesucker.tools.launch_profile;

import java.util.ArrayList;

public class LaunchProfileModel {
	private ArrayList<String> choices = new ArrayList<String>();
	private int selectedItem = -1;

	public LaunchProfileModel() {
		//
	}

	public ArrayList<String> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}

	public String getSelectedItem() {
		if (selectedItem < 0) {
			return "";
		}
		return choices.get(selectedItem);
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

}
