package uk.co.bigsoft.filesucker;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import uk.co.bigsoft.filesucker.config.ConfigController;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.config.ConfigSaver;
import uk.co.bigsoft.filesucker.config.ConfigView;
import uk.co.bigsoft.filesucker.credits.CreditsController;
import uk.co.bigsoft.filesucker.credits.CreditsModel;
import uk.co.bigsoft.filesucker.credits.CreditsView;
import uk.co.bigsoft.filesucker.tools.ToolsController;
import uk.co.bigsoft.filesucker.tools.ToolsModel;
import uk.co.bigsoft.filesucker.tools.ToolsView;
import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileController;
import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileModel;
import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileView;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;
import uk.co.bigsoft.filesucker.view.CreditScreen;
import uk.co.bigsoft.filesucker.view.FileSuckerFrame;
import uk.co.bigsoft.filesucker.view.ToolsScreen;
import uk.co.bigsoft.filesucker.view.TransferScreen;

public class FileSucker {
	public static String version = "";
	public static String versionDate = "";

	public static LinkedList<SuckerThread> activeFileSuckerThreads = null;

	public static ConfigData configData = null;
	// public static ConfigScreen configScreen = null;
	public static TaskScreen taskScreen = null;
	public static TransferScreen transferScreen = null;
	public static CreditScreen creditScreen = null;
	public static ToolsScreen toolsScreen = null;

	public static void main(String args[]) {

		// Read version numbers from the manifest
		try {
			URL url = new URL("jar:file:FileSucker.jar!/META-INF/MANIFEST.MF");
			JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
			Manifest manifest = jarConnection.getManifest();
			Attributes attr = manifest.getMainAttributes();
			version = attr.getValue("FileSucker-Version");
			versionDate = attr.getValue("FileSucker-Created");
		} catch (Exception e) {
			version = "dev";
			versionDate = "today";
		}

		ConfigSaver cs = new ConfigSaver();
		ConfigModel configModel = cs.load();

		ConfigView configView = new ConfigView();

		ConfigController configController = new ConfigController(configModel, configView);
		configController.initController();

		CreditsModel creditsModel = new CreditsModel();
		CreditsView creditsView = new CreditsView();
		CreditsController creditsController = new CreditsController(creditsModel, creditsView);

		LaunchProfileView launchProfileView = new LaunchProfileView();
		LaunchProfileModel launchProfileModel = new LaunchProfileModel();
		LaunchProfileController launchProfileController = new LaunchProfileController(launchProfileModel,
				launchProfileView);

		ToolsView toolsView = new ToolsView(launchProfileView);
		ToolsModel toolsModel = new ToolsModel();
		ToolsController toolsController = new ToolsController(toolsModel, toolsView);

		// Build tabs
		activeFileSuckerThreads = new LinkedList<SuckerThread>();
		configData = new ConfigData();
		// configScreen = new ConfigScreen();
		taskScreen = new TaskScreen();
		transferScreen = new TransferScreen();
		creditScreen = new CreditScreen();
		toolsScreen = new ToolsScreen();

		// Open window
		new FileSuckerFrame(configView, creditsView, toolsView);
	}

}
