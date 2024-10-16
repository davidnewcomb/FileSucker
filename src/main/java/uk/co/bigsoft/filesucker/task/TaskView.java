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
import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.PrefixJTextField;
import uk.co.bigsoft.filesucker.RunYetComponent;
import uk.co.bigsoft.filesucker.SuckerParams;
import uk.co.bigsoft.filesucker.SuffixJTextField;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.task.looper.LooperCmd;
import uk.co.bigsoft.filesucker.task.looper.LooperPanel;
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

	private JTextField prefixTF = new PrefixJTextField(ddHandler);
	private JTextField suffixTF = new SuffixJTextField(ddHandler);

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
		// TODO
		// directoryCB.setSelectedItem(FileSucker.configData.getBaseDir().toString());

		// Prefix and Suffix
		saveOnly.setToolTipText("RunTask - save but without running");
		saveOnly.setSelected(false);

		runTaskButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

//		openDir = new OpenDirectoryButton(directoryCB);

		// errorMessages.setFont()
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

		//OriginalAddressLaunchButton findFileLaunchB = new OriginalAddressLaunchButton(originalAddressTF);

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

		hbox = Box.createHorizontalBox();
		hbox.setBorder(new LineBorder(Color.BLUE));
		hbox.add(helperDirectoryButton);
		hbox.add(new JLabel("Directory"));
		hbox.add(directoryCB);
		hbox.add(homeButton);
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

		hbox.addMouseListener((MousePressListener) e -> mousePressed(e));

		centre.add(hbox);

		JPanel jp = new JPanel(new GridLayout(1, 2));

		suffixEndCB.setToolTipText("Do you want the suffux to go at the end?");
		suffixEndCB.setMinimumSize(new Dimension(10, 20));
		suffixEndCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

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

	// New actions
//
//	private void runTask() {
////		if (Looper.isActive()) {
////			TaskScreen.setErrorMessage("Looper is active");
////			return;
////		}
//
//		String selectedDir = directoryCB.getSelectedItem().toString().trim();
//		if (selectedDir.equals("")) {
//			TaskScreen.setErrorMessage("You must provide a directory to store the files");
//			return;
//		}
//
//		if (!selectedDir.endsWith(File.separator)) {
//			selectedDir = selectedDir + File.separator;
//			directoryCB.setSelectedItem(selectedDir);
//		}
//
//		directoryCB.savePrefs(selectedDir);
//
//		String prefix = null;
//		String suffix = null;
//
//		if (prefixTF.getText().length() > 0) {
//			prefix = prefixTF.getText();
//		}
//		if (suffixTF.getText().length() > 0) {
//			suffix = suffixTF.getText();
//		}
//
//		// // Is selectedDir in the list already
//		// boolean inList = false;
//		// int listEntries =
//		// directoryCB.getItemCount();
//		// for (int i = 0 ; i < listEntries ;
//		// ++i)
//		// {
//		// String item = (String)
//		// directoryCB.getItemAt(i);
//		// if (item.equals(selectedDir))
//		// inList = true;
//		// }
//		// if (inList == false)
//		// {
//		// directoryCB.addItem(selectedDir);
//		// }
//
//		// File f = new File (selectedDir) ;
//		// String name = f.getName () ;
//
//		if (selectedDir.endsWith(File.separator) == false) {
//			selectedDir = selectedDir + File.separator;
//			directoryCB.setSelectedItem(selectedDir);
//		}
//
//		// Cookie
//		// StringTokenizer st = new
//		// StringTokenizer(cookieTA.getText().trim(),
//		// "\n");
//		Hashtable<String, String> hm = new Hashtable<String, String>();
//		// while (st.hasMoreTokens())
//		// {
//		// String[] kv =
//		// st.nextToken().trim().split(":", 2);
//		// String k = kv[0].trim();
//		// String v = kv[1].trim();
//		// hm.put(k, v);
//		// }
//
//		// Referer
//		// String ref = refererTF.getText();
//		// if (ref.equals("") == false)
//		// {
//		// hm.put("Referer", ref);
//		// }
//		String refs[] = urlTF.getText().split("/", 4);
//		if (refs.length < 3) {
//			TaskScreen.setErrorMessage("You must enter a url");
//			return;
//		}
//
//		runYet.setReset();
//
//		String ref = refs[0] + "//" + refs[2];
//		hm.put("Referer", ref);
//
//		selectedDir = Utility.expandsPercentVars(selectedDir);
//
//		SuckerParams sp = new SuckerParams("name", urlTF.getText(), selectedDir, prefix, suffix, hm,
//				suffixEndCB.isSelected(), originalAddressTF.getText());
//		if (saveUrl.isSelected()) {
//			TaskScreenParams.save(sp);
//			if (saveOnly.isSelected()) {
//				setErrorMessage("Saved");
//				return;
//			}
//		}
//		// SuckerThread sth =
//		new SuckerThread(sp);
//
//		// SuckerThread sth = new SuckerThread (sp)
//		// ;
//		// synchronized
//		// (FileSucker.activeFileSuckerThreads)
//		// {
//		// FileSucker.activeFileSuckerThreads.add
//		// (sth) ;
//		// }
//		// TransferScreen.updateScreen () ;
//		// // Switch to other tab
//		FileSuckerFrame.tabPane.setSelectedComponent(FileSucker.transferScreen);
//
//		// for (int t = 0 ; t <
//		// FileSuckerFrame.tabPane.getComponentCount()
//		// ;
//		// t++)
//		// if
//		// (FileSuckerFrame.tabPane.getComponent(t)
//		// ==
//		// FileSucker.transferScreen)
//		//
//		originalAddressTF.setText("");
//		runYet.setReset();
//	}

	// Below: New getters

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

//	public void setErrorMessage(String m) {
//		System.err.println(m);
//		errorMessagesLabel.setText("Message: " + m);
//	}

	public void load(SuckerParams p) {
		System.out.println(p.getOrginalAddress());
		urlTF.setText(p.getOrginalUrl());
		prefixTF.setText(p.getPrefix());
		suffixTF.setText(p.getSuffix());
		directoryCB.setSelectedItem(p.getIntoDir());
		suffixEndCB.setSelected(p.isSuffixEnd());
		originalAddressTF.setText(p.getOrginalAddress());
		runYet.setModifed();
	}

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

	public void replaceUrlText(String braces) {
		StringBuffer sb = new StringBuffer(urlTF.getText());
		int carpos = urlTF.getCaretPosition();
		int startSel = urlTF.getSelectionStart();
		int endSel = urlTF.getSelectionEnd();
		if (startSel != endSel)
			sb.replace(startSel, endSel, braces);
		else
			sb.insert(carpos, braces);

		urlTF.setText(sb.toString());
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
