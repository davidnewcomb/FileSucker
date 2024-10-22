package uk.co.bigsoft.filesucker.task;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import uk.co.bigsoft.filesucker.FileAndTextTransferHandler;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.task.looper.LooperCmd;
import uk.co.bigsoft.filesucker.task.looper.LooperPanel;
import uk.co.bigsoft.filesucker.task.view.HistoryJComboBox;
import uk.co.bigsoft.filesucker.task.view.RunYetComponent;
import uk.co.bigsoft.filesucker.tools.MousePressListener;

public class TaskView extends JPanel {
	// Drag & Drop
	private FileAndTextTransferHandler ddHandler = new FileAndTextTransferHandler();

	// Wired in

	private JTextField urlTF = new JTextField();
	private JTextField originalAddressTF = new JTextField();
	private JButton originalAddressLaunchButton = new JButton("L");
	private JButton helperDirectoryButton = new JButton("LD");
	private JButton homeButton = new JButton("H");
	private JButton copyToToolsButton = new JButton("CT");
	private JButton subDirectoryPathButton = new JButton("/D");
	private JButton homeDirectoryPrefixButton = new JButton("HDP");
	private JButton subDirectoryAndPrefixButton = new JButton("/DP_");
	private JButton directoryAndPrefixButton = new JButton("_DP");
	private JButton directoryExtensionButton = new JButton("_D");
	private JButton clipboardAsDirectoryButton = new JButton("C");
	private JButton subDirectoryAndPrefixFromClipboardButton = new JButton("/CP_");
	private JButton subDirectoryFromClipboardButton = new JButton("/C");
	private JButton directoryClipboardButton = new JButton("_C");
	private JButton directoryBrowseButton = new JButton("Browse");
	private JLabel errorMessagesLabel = new JLabel("");

	private JTextField prefixTF = new JTextField();
	private JTextField suffixTF = new JTextField();

	private JButton prefixButton = new JButton("Prefix");
	private JButton prefixLowerButton = new JButton("Lower");
	private JButton prefixClipButton = new JButton("Clip");
	private JButton prefixClearButton = new JButton("Clear");

	private JButton suffixButton = new JButton("Suffix");
	private JCheckBox suffixEndCB = new JCheckBox("B4Extn");
	private JButton suffixLowerButton = new JButton("Lower");
	private JButton suffixClipButton = new JButton("Clip");
	private JButton suffixClearButton = new JButton("Clear");

	private HistoryJComboBox directoryCB = new HistoryJComboBox("directory", ddHandler);

	private RunYetComponent runYet = new RunYetComponent();
	private JCheckBox saveUrl = new JCheckBox();
	private JCheckBox saveOnly = new JCheckBox();

	private JButton runTaskButton = new JButton("Run Task");
	private JButton findFilesButton = new JButton("FindFiles");

	// Not wired in!

	private LooperPanel looperPanel;

	public TaskView(LooperPanel looperPanel) {
		super(new BorderLayout());
		this.looperPanel = looperPanel;

		setTransferHandler(ddHandler);

		JPanel centre = new JPanel();
		centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

		add(centre, BorderLayout.CENTER);

		// Base Directory
		directoryCB.setMinimumSize(new Dimension(10, 20));
		directoryCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		directoryCB.addMouseListener((MousePressListener) e -> mousePressed(e));

		// Prefix and Suffix
		saveOnly.setToolTipText("RunTask - save but without running");
		saveOnly.setSelected(false);

		runTaskButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		JPanel bot = new JPanel();
		bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
		bot.add(errorMessagesLabel);

		JPanel rp = new JPanel(new BorderLayout());
		rp.add(BorderLayout.CENTER, runTaskButton);
		rp.add(BorderLayout.EAST, saveOnly);
		bot.add(rp);
		bot.add(runYet);
		add(bot, BorderLayout.SOUTH);

		centre.add(new JLabel("Get"));

		urlTF = new UrlTextField(ddHandler);

		// URL
		// urlTF = new JTextField
		// ("http://djn:djn@dr-shi/bigsoft/fs/01.php",
		// ddHandler) ;
		// urlTF = new JTextField
		// ("http://www.pornstar-galaxy.com/pornstars/hungarian_pantera/pics03.htm",
		// ddHandler);
		// urlTF = new JTextField
		// ("http://www.pornstar-galaxy.com/pornstars/hungarian_pantera/images/full/panrdchrbg028.jpg",
		// ddHandler);
//		urlTF = new UrlTextField(ddHandler);

		// originalAddressTF = new OriginalAddressTextField();
		// findFileTF.setEditable (false) ;

		copyToToolsButton.setToolTipText("Copy text to ToolScreen");

		saveUrl.setToolTipText("Save download instructions");
		saveUrl.setSelected(true);

		findFilesButton
				.setToolTipText("Suck down webpage, fish out all the filenames and fill up '" + LooperCmd.L_LIST + "'");

		Box hbox = Box.createHorizontalBox();
		hbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		hbox.add(new JLabel("URL"));
		hbox.add(urlTF);
		hbox.add(saveUrl);
		hbox.add(findFilesButton);
		hbox.add(copyToToolsButton);
		centre.add(hbox);

		// OriginalAddressLaunchButton findFileLaunchB = new
		// OriginalAddressLaunchButton(originalAddressTF);

		centre.add(new JLabel(" "));

		JPanel findFileJP = new JPanel(new BorderLayout());
		findFileJP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		findFileJP.add(new JLabel("Orginal Address"), BorderLayout.WEST);
		findFileJP.add(originalAddressTF, BorderLayout.CENTER);
		findFileJP.add(originalAddressLaunchButton, BorderLayout.EAST);
		centre.add(findFileJP);

		centre.add(new JLabel("Save To"));

		// JButton browseButton = new BrowseButton(directoryCB);
		// JButton hButton = new HomeButton(directoryCB);

		homeButton.setToolTipText("Initialises the directory to base directory");
		homeButton.setMinimumSize(new Dimension(0, 0));
		homeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton dButton = new SubDirectoryPathButton(urlTF, directoryCB);

		subDirectoryPathButton.setToolTipText("Appends highlighted url text as new sub-directory");
		subDirectoryPathButton.setMinimumSize(new Dimension(0, 0));
		subDirectoryPathButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton dsButton = new DirectoryExtensionButton(urlTF, directoryCB);

		directoryExtensionButton.setToolTipText("Appends highlighted url text to directory");
		directoryExtensionButton.setMinimumSize(new Dimension(0, 0));
		directoryExtensionButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton dpButton = new SubDirectoryAndPrefixButton(urlTF, directoryCB,
		// prefixTF);

		subDirectoryAndPrefixButton.setToolTipText("Appends highlighted url text to /directory and prefix_");
		subDirectoryAndPrefixButton.setMinimumSize(new Dimension(0, 0));
		subDirectoryAndPrefixButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton pdpButton = new DirectoryAndPrefixButton(urlTF, directoryCB,
		// prefixTF);

		directoryAndPrefixButton.setToolTipText("Appends highlighted url text to _directory and _prefix");
		directoryAndPrefixButton.setMinimumSize(new Dimension(0, 0));
		directoryAndPrefixButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton cButton = new ClipboardAsDirectoryButton(directoryCB);

		clipboardAsDirectoryButton.setToolTipText("Paste clipboard as new sub-directory");
		clipboardAsDirectoryButton.setMinimumSize(new Dimension(0, 0));
		clipboardAsDirectoryButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton cpButton = new SubDirectoryAndPrefixFromClipboardButton(directoryCB,
		// prefixTF);

		subDirectoryAndPrefixFromClipboardButton.setToolTipText("Appends clipboard text to /directory and prefix_");
		subDirectoryAndPrefixFromClipboardButton.setMinimumSize(new Dimension(0, 0));
		subDirectoryAndPrefixFromClipboardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton cdButton = new SubDirectoryFromClipboardButton(directoryCB);

		subDirectoryFromClipboardButton.setToolTipText("Appends clipboard as new sub-directory");
		subDirectoryFromClipboardButton.setMinimumSize(new Dimension(0, 0));
		subDirectoryFromClipboardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton csButton = new DirectoryClipboardButton(directoryCB);

		directoryClipboardButton.setToolTipText("Appends clipboard to end of directory");
		directoryClipboardButton.setMinimumSize(new Dimension(0, 0));
		directoryClipboardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		// JButton hpdpButton = new HomeDirectoryPrefix(urlTF, directoryCB, prefixTF);

		homeDirectoryPrefixButton
				.setToolTipText("Clears defaults and prefix then appends highlighted url text to directory and prefix");
		homeDirectoryPrefixButton.setMinimumSize(new Dimension(0, 0));
		homeDirectoryPrefixButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

		JPanel directoryPanel = new JPanel();
		directoryPanel.setBorder(new LineBorder(Color.BLUE));
		directoryPanel.setLayout(new BoxLayout(directoryPanel, BoxLayout.Y_AXIS));
		directoryPanel.setMinimumSize(new Dimension(0, 0));
		directoryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

		hbox = Box.createHorizontalBox();
		hbox.add(directoryBrowseButton);
		hbox.add(subDirectoryPathButton);
		hbox.add(homeDirectoryPrefixButton);
		hbox.add(subDirectoryAndPrefixButton);
		hbox.add(directoryAndPrefixButton);
		hbox.add(directoryExtensionButton);
		hbox.add(clipboardAsDirectoryButton);
		hbox.add(subDirectoryAndPrefixFromClipboardButton);
		hbox.add(subDirectoryFromClipboardButton);
		hbox.add(directoryClipboardButton);
		directoryPanel.add(hbox);

		JPanel dirLayoutCentre = new JPanel(new BorderLayout());
		dirLayoutCentre.add(new JLabel("Directory"), BorderLayout.WEST);
		dirLayoutCentre.add(directoryCB, BorderLayout.CENTER);

		JPanel dirLayout = new JPanel(new BorderLayout());
		dirLayout.add(helperDirectoryButton, BorderLayout.EAST);
		dirLayout.add(dirLayoutCentre, BorderLayout.CENTER);
		dirLayout.add(homeButton, BorderLayout.WEST);

		directoryPanel.add(dirLayout);

		centre.add(directoryPanel);

		JPanel jp = new JPanel(new GridLayout(1, 2));

		suffixEndCB.setToolTipText("Do you want the suffux to go at the end?");
		suffixEndCB.setMinimumSize(new Dimension(10, 20));
		suffixEndCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		prefixTF.setMinimumSize(new Dimension(10, 20));
		prefixTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		suffixTF.setMinimumSize(new Dimension(10, 20));
		suffixTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		hbox = Box.createVerticalBox();
		hbox.setBorder(new LineBorder(Color.BLUE));
		hbox.add(new JLabel("Filename modifiers"));

		JPanel jpt = new JPanel(new GridLayout());
		jpt.setMinimumSize(new Dimension(10, 20));
		jpt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		jpt.add(prefixButton);
		jpt.add(prefixLowerButton);
		jpt.add(prefixClipButton);
		jpt.add(prefixClearButton);
		hbox.add(jpt);

		hbox.add(prefixTF);

		jpt = new JPanel(new GridLayout());
		jpt.setMinimumSize(new Dimension(10, 20));
		jpt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		jpt.add(suffixButton);
		jpt.add(suffixEndCB);
		jpt.add(suffixLowerButton);
		jpt.add(suffixClipButton);
		jpt.add(suffixClearButton);
		hbox.add(jpt);

		hbox.add(suffixTF);

		jp.add(hbox);

//		iteratorJP = new JPanel(new BorderLayout());
//
//		hbox = Box.createHorizontalBox();
//		hbox.add(numberB);
//		hbox.add(textB);
//		hbox.add(listB);
//		hbox.add(copyB);
//		hbox.add(staticB);
//
//		JPanel iter_jp = new JPanel(new BorderLayout());
//		iter_jp.setBorder(new LineBorder(Color.BLUE));
//		iter_jp.add(hbox, BorderLayout.NORTH);
//		iter_jp.add(iteratorJP, BorderLayout.CENTER);
//
//		jp.add(iter_jp);
//		centre.add(jp);

		jp.add(looperPanel);
		centre.add(jp);

	}

	private void mousePressed(MouseEvent me) {
		int clickedButton = me.getButton();
		if (clickedButton == 3) // r-click
		{
			String s = Utility.getClipboard();
			if (s != null) {
				directoryCB.setSelectedItem(s);
			}
		}
	}

	public JButton getCopyToToolsButton() {
		return copyToToolsButton;
	}

	public JTextField getOriginalAddressTextField() {
		return originalAddressTF;
	}

	public JButton getHelperDirectoryButton() {
		return helperDirectoryButton;
	}

	public HistoryJComboBox getDirectoryComboBox() {
		return directoryCB;
	}

	public JButton getHomeButton() {
		return homeButton;
	}

	public JLabel getErrorMessagesLabel() {
		return errorMessagesLabel;
	}

	public FileAndTextTransferHandler getDdHandler() {
		return ddHandler;
	}

	public JButton getSubDirectoryPathButton() {
		return subDirectoryPathButton;
	}

	public JButton getHomeDirectoryPrefixButton() {
		return homeDirectoryPrefixButton;
	}

	public JButton getSubDirectoryAndPrefixButton() {
		return subDirectoryAndPrefixButton;
	}

	public JButton getDirectoryAndPrefixButton() {
		return directoryAndPrefixButton;
	}

	public JButton getDirectoryExtensionButton() {
		return directoryExtensionButton;
	}

	public JButton getClipboardAsDirectoryButton() {
		return clipboardAsDirectoryButton;
	}

	public JButton getSubDirectoryAndPrefixFromClipboardButton() {
		return subDirectoryAndPrefixFromClipboardButton;
	}

	public JButton getSubDirectoryFromClipboardButton() {
		return subDirectoryFromClipboardButton;
	}

	public JButton getDirectoryClipboardButton() {
		return directoryClipboardButton;
	}

	public JButton getDirectoryBrowseButton() {
		return directoryBrowseButton;
	}

	public JTextField getPrefixTextField() {
		return prefixTF;
	}

	public JTextField getSuffixTextField() {
		return suffixTF;
	}

	public JButton getRunTaskButton() {
		return runTaskButton;
	}

	public JButton getFindFilesButton() {
		return findFilesButton;
	}

	// Below: waiting for refactor

	public void enableRunButton(boolean e) {
		runTaskButton.setEnabled(e);
	}

	public JTextField getUrlTextField() {
		return urlTF;
	}

	public String getUrlText() {
		return urlTF.getText();
	}

	public void setUrlText(String x) {
		urlTF.setText(x);
	}

	public void changed() {
		runYet.setModifed();
	}

	public LooperPanel getLooperPanel() {
		return looperPanel;
	}

	public RunYetComponent getRunYet() {
		return runYet;
	}

	public JCheckBox getSaveUrl() {
		return saveUrl;
	}

	public JCheckBox getSaveOnly() {
		return saveOnly;
	}

	public JButton getPrefixButton() {
		return prefixButton;
	}

	public JButton getPrefixLowerButton() {
		return prefixLowerButton;
	}

	public JButton getPrefixClipButton() {
		return prefixClipButton;
	}

	public JButton getPrefixClearButton() {
		return prefixClearButton;
	}

	public JButton getSuffixButton() {
		return suffixButton;
	}

	public JButton getSuffixLowerButton() {
		return suffixLowerButton;
	}

	public JButton getSuffixClipButton() {
		return suffixClipButton;
	}

	public JButton getSuffixClearButton() {
		return suffixClearButton;
	}

	public JCheckBox getSuffixEndCB() {
		return suffixEndCB;
	}

	public JButton getOriginalAddressLaunchButton() {
		return originalAddressLaunchButton;
	}

}
