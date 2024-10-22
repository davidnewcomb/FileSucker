package uk.co.bigsoft.filesucker.tools.launch_profile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.tools.ToolsModel;

public class LaunchProfileController extends JPanel {
	private LaunchProfileModel model;
	private LaunchProfileView view;

	public LaunchProfileController(LaunchProfileModel m, LaunchProfileView v) {
		model = m;
		view = v;
		initView();
	}

	private void initView() {
		refreshCombo(view.getLaunchProfileComboBox());
	}

	public void initController(ConfigModel configModel, ToolsModel toolsModel) {
		view.getLaunchProfileButton().addActionListener(e -> runLaunchProfile(configModel, toolsModel));
	}

	private void runLaunchProfile(ConfigModel configModel, ToolsModel toolsModel) {

		String sub = toolsModel.getWorking();
		if (sub.equals("")) {
			return;
		}

		try {
			sub = sub.replaceAll(" ", "+");
			sub = URLEncoder.encode(sub, "UFT-8");
		} catch (UnsupportedEncodingException ex) {
			// nothing
		}

		String url = model.getSelectedItem();
		url = url.replaceAll("%s", sub);

		try {
			String helper = configModel.getHelperWeb().replaceAll("%s", url);
			Utility.runShellCommand(helper);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void refreshCombo(JComboBox<String> list) {
		list.removeAllItems();
		List<String> opts = Arrays.asList(""); // FileSucker.configData.getLaunchProfiles();
		for (String s : opts) {
			list.addItem(s);
		}
	}
}
