package uk.co.bigsoft.filesucker;

import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import uk.co.bigsoft.filesucker.config.ConfigController;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.config.ConfigSaver;
import uk.co.bigsoft.filesucker.config.ConfigView;
import uk.co.bigsoft.filesucker.credits.CreditsController;
import uk.co.bigsoft.filesucker.credits.CreditsModel;
import uk.co.bigsoft.filesucker.credits.CreditsView;
import uk.co.bigsoft.filesucker.prefs.FileSuckerPrefs;
import uk.co.bigsoft.filesucker.prefs.FileSuckerPrefsHandler;
import uk.co.bigsoft.filesucker.task.TaskConfig;
import uk.co.bigsoft.filesucker.task.TaskController;
import uk.co.bigsoft.filesucker.task.TaskModel;
import uk.co.bigsoft.filesucker.task.TaskView;
import uk.co.bigsoft.filesucker.task.view.loopers.LooperPanel;
import uk.co.bigsoft.filesucker.tools.ToolsController;
import uk.co.bigsoft.filesucker.tools.ToolsModel;
import uk.co.bigsoft.filesucker.tools.ToolsView;
import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileController;
import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileModel;
import uk.co.bigsoft.filesucker.tools.launch_profile.LaunchProfileView;
import uk.co.bigsoft.filesucker.transfer.TransferController;
import uk.co.bigsoft.filesucker.transfer.TransferModel;
import uk.co.bigsoft.filesucker.transfer.TransferView;
import uk.co.bigsoft.filesucker.view.FileSuckerFrame;

public class FileSucker {
	private static final FileSuckerPrefsHandler fileSuckerPrefHandler = new FileSuckerPrefsHandler();
	public static String version = "";
	public static String versionDate = "";

	private static TaskController taskController;

	public static void main(String args[]) {

		setUpVersion();
		FileSuckerPrefs p = fileSuckerPrefHandler.load();

		ConfigSaver cs = new ConfigSaver();

		ConfigModel configModel = cs.load();
		CreditsModel creditsModel = new CreditsModel();
		creditsModel.setTotalNumBytes(p.getTotalDownloadedBytes());
		creditsModel.setTotalNumFiles(p.getTotalDownloadedFiles());

		LaunchProfileModel launchProfileModel = new LaunchProfileModel();
		LaunchProfileView launchProfileView = new LaunchProfileView();
		LaunchProfileController launchProfileController = new LaunchProfileController(launchProfileModel,
				launchProfileView);

		ToolsModel toolsModel = new ToolsModel();
		TaskModel taskModel = new TaskModel();
		TransferModel transferModel = new TransferModel();

		taskModel.setDirectory(configModel.getBaseDir());

		ConfigView configView = new ConfigView();
		CreditsView creditsView = new CreditsView();

		ToolsView toolsView = new ToolsView(launchProfileView);
		LooperPanel looperPanel = new LooperPanel(configModel, taskModel);
		TaskView taskView = new TaskView(looperPanel);
		TransferView transferView = new TransferView();

		ConfigController configController = new ConfigController(configModel, configView);
		CreditsController creditsController = new CreditsController(creditsModel, creditsView);
		ToolsController toolsController = new ToolsController(toolsModel, toolsView);
		taskController = new TaskController(taskModel, taskView);
		TransferController transferController = new TransferController(transferModel, transferView);

		transferController.initController(creditsController);
		configController.initController();
		creditsController.initController();
		launchProfileController.initController(configModel, toolsModel);
		toolsController.initController(configModel, taskModel);
		taskController.initController(configModel, toolsModel, transferController);

		Downloader.getInstance(configModel);

		// Open window
		new FileSuckerFrame(taskView, configView, creditsView, toolsView, transferView, creditsModel);
	}

	private static void setUpVersion() {
		// Read version numbers from the manifest
		try {
			URL url = URI.create("jar:file:FileSucker.jar!/META-INF/MANIFEST.MF").toURL();
			JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
			Manifest manifest = jarConnection.getManifest();
			Attributes attr = manifest.getMainAttributes();
			version = attr.getValue("FileSucker-Version");
			versionDate = attr.getValue("FileSucker-Created");
		} catch (Exception e) {
			version = "dev";
			versionDate = "today";
		}
	}

	public static void load(TaskConfig tc) {
		taskController.load(tc);
	}
}
