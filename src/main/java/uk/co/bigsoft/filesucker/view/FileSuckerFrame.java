package uk.co.bigsoft.filesucker.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.config.ConfigView;
import uk.co.bigsoft.filesucker.credits.CreditsView;
import uk.co.bigsoft.filesucker.prefs.FileSuckerPrefs;
import uk.co.bigsoft.filesucker.prefs.FileSuckerPrefsHandler;
import uk.co.bigsoft.filesucker.task.TaskView;
import uk.co.bigsoft.filesucker.tools.ToolsView;
import uk.co.bigsoft.filesucker.transfer.TransferView;

public class FileSuckerFrame extends JFrame {

	private static final FileSuckerPrefsHandler fileSuckerPrefHandler = new FileSuckerPrefsHandler();

	public static JTabbedPane tabPane;

	public FileSuckerFrame(TaskView taskView, ConfigView configView, CreditsView creditsView, ToolsView toolsView,
			TransferView transferView) {
		super("FileSucker");
		// setIconImage (icon.getImage ()) ;
		addWindowListener(new FileSuckerWindowAdaptor());

		tabPane = new JTabbedPane();
		getContentPane().add(tabPane);

		// Set up page
		tabPane.addTab("NewTask", null, taskView, "Create new task");
		tabPane.addTab("Transfer", null, transferView, "View tasks in progress");
		tabPane.addTab("Tools", null, toolsView, "Text tools");
		tabPane.addTab("Options", null, configView, "Change default options");
		tabPane.addTab("Credits", null, creditsView, "Statistics & Credits");

		pack();

		FileSuckerPrefs p = fileSuckerPrefHandler.load();

		setBounds(p.getBoundX(), p.getBoundY(), p.getBoundWidth(), p.getBoundHeight());

//		CreditScreen.setTotalDownloadedFiles(p.getTotalDownloadedFiles());
//		CreditScreen.setTotalDownloadedBytes(p.getTotalDownloadedBytes());

		setVisible(true);
	}
}
