package uk.co.bigsoft.filesucker.task;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import uk.co.bigsoft.filesucker.BasicAuth;
import uk.co.bigsoft.filesucker.FileAndTextTransferHandler;
import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.HistoryJComboBox;
import uk.co.bigsoft.filesucker.PrefixJTextField;
import uk.co.bigsoft.filesucker.RunYetComponent;
import uk.co.bigsoft.filesucker.SuckerParams;
import uk.co.bigsoft.filesucker.SuckerThread;
import uk.co.bigsoft.filesucker.SuffixJTextField;
import uk.co.bigsoft.filesucker.TaskScreenParams;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.looper.Looper;
import uk.co.bigsoft.filesucker.looper.list.ListLooper;
import uk.co.bigsoft.filesucker.ui.taskscreen.CopyLooperButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.ListLooperButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.NumberLooperButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.StaticLooperButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;
import uk.co.bigsoft.filesucker.ui.taskscreen.TextLooperButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.UrlTextField;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.OriginalAddressLaunchButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixClearButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixCopyButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixLowerButton;

import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixClearButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixCopyButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixLowerButton;
import uk.co.bigsoft.filesucker.view.FileSuckerFrame;

public class TaskView extends JPanel {
	// Drag & Drop
	private FileAndTextTransferHandler ddHandler = new FileAndTextTransferHandler();

	// Wired in

	private JTextField urlTF = new JTextField();
	private JTextField originalAddressTF = new JTextField();

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

	private HistoryJComboBox directoryCB = new HistoryJComboBox("directory", ddHandler);

	// Not wired in!

	private RunYetComponent runYet = new RunYetComponent();
	private JButton runTaskButton = new JButton("Run Task");
	private JCheckBox saveOnly = new JCheckBox();
	private JButton findFilesButton = new JButton("FindFiles");

	private JCheckBox saveUrl = new JCheckBox();
	private JCheckBox suffixEndCB = new JCheckBox("B4Extn");
	private JPanel iteratorJP;

	// Loopers
	private NumberLooperButton numberB = new NumberLooperButton();
	private TextLooperButton textB = new TextLooperButton();
	private ListLooperButton listB = new ListLooperButton();
	private CopyLooperButton copyB = new CopyLooperButton();
	private StaticLooperButton staticB = new StaticLooperButton();

	public TaskView() {
		super(new BorderLayout());
		JButton t;

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

		runTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Looper.isActive()) {
					TaskScreen.setErrorMessage("Looper is active");
					return;
				}

				String selectedDir = directoryCB.getSelectedItem().toString().trim();
				if (selectedDir.equals("")) {
					TaskScreen.setErrorMessage("You must provide a directory to store the files");
					return;
				}

				if (!selectedDir.endsWith(File.separator)) {
					selectedDir = selectedDir + File.separator;
					directoryCB.setSelectedItem(selectedDir);
				}

				directoryCB.savePrefs(selectedDir);

				String prefix = null;
				String suffix = null;

				if (prefixTF.getText().length() > 0) {
					prefix = prefixTF.getText();
				}
				if (suffixTF.getText().length() > 0) {
					suffix = suffixTF.getText();
				}

				// // Is selectedDir in the list already
				// boolean inList = false;
				// int listEntries =
				// directoryCB.getItemCount();
				// for (int i = 0 ; i < listEntries ;
				// ++i)
				// {
				// String item = (String)
				// directoryCB.getItemAt(i);
				// if (item.equals(selectedDir))
				// inList = true;
				// }
				// if (inList == false)
				// {
				// directoryCB.addItem(selectedDir);
				// }

				// File f = new File (selectedDir) ;
				// String name = f.getName () ;

				if (selectedDir.endsWith(File.separator) == false) {
					selectedDir = selectedDir + File.separator;
					directoryCB.setSelectedItem(selectedDir);
				}

				// Cookie
				// StringTokenizer st = new
				// StringTokenizer(cookieTA.getText().trim(),
				// "\n");
				Hashtable<String, String> hm = new Hashtable<String, String>();
				// while (st.hasMoreTokens())
				// {
				// String[] kv =
				// st.nextToken().trim().split(":", 2);
				// String k = kv[0].trim();
				// String v = kv[1].trim();
				// hm.put(k, v);
				// }

				// Referer
				// String ref = refererTF.getText();
				// if (ref.equals("") == false)
				// {
				// hm.put("Referer", ref);
				// }
				String refs[] = urlTF.getText().split("/", 4);
				if (refs.length < 3) {
					TaskScreen.setErrorMessage("You must enter a url");
					return;
				}

				runYet.setReset();

				String ref = refs[0] + "//" + refs[2];
				hm.put("Referer", ref);

				selectedDir = Utility.expandsPercentVars(selectedDir);

				SuckerParams sp = new SuckerParams("name", urlTF.getText(), selectedDir, prefix, suffix, hm,
						suffixEndCB.isSelected(), originalAddressTF.getText());
				if (saveUrl.isSelected()) {
					TaskScreenParams.save(sp);
					if (saveOnly.isSelected()) {
						setErrorMessage("Saved");
						return;
					}
				}
				// SuckerThread sth =
				new SuckerThread(sp);

				// SuckerThread sth = new SuckerThread (sp)
				// ;
				// synchronized
				// (FileSucker.activeFileSuckerThreads)
				// {
				// FileSucker.activeFileSuckerThreads.add
				// (sth) ;
				// }
				// TransferScreen.updateScreen () ;
				// // Switch to other tab
				FileSuckerFrame.tabPane.setSelectedComponent(FileSucker.transferScreen);

				// for (int t = 0 ; t <
				// FileSuckerFrame.tabPane.getComponentCount()
				// ;
				// t++)
				// if
				// (FileSuckerFrame.tabPane.getComponent(t)
				// ==
				// FileSucker.transferScreen)
				//
				originalAddressTF.setText("");
				runYet.setReset();
			}
		});

//		openDir = new OpenDirectoryButton(directoryCB);

		setErrorMessage("");
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
		urlTF = new UrlTextField(ddHandler);

		// originalAddressTF = new OriginalAddressTextField();
		// findFileTF.setEditable (false) ;

		copyToToolsButton.setToolTipText("Copy text to ToolScreen");

		saveUrl.setToolTipText("Save download instructions");
		saveUrl.setSelected(true);

		findFilesButton.setToolTipText("Suck down webpage, fish out all the filenames and fill up 'L'");
		// which does the actual Delete operation
		findFilesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String findFileAddress = urlTF.getText();
					if (findFileAddress.length() == 0) {
						TaskScreen.setErrorMessage("URL is empty");
						return;
					}
					originalAddressTF.setText(findFileAddress);
					URL url = URI.create(findFileAddress).toURL();
					java.net.URLConnection urlc = url.openConnection();
					String userinfo = url.getUserInfo();
					if (userinfo != null) {
						String[] auth = userinfo.split(":");
						urlc.setRequestProperty("Authorization", "Basic " + BasicAuth.encode(auth[0], auth[1]));
					}
					// To download
					InputStream is = urlc.getInputStream();
					byte[] buffer = new byte[4096];
					StringBuffer sb = new StringBuffer();
					// Map header = urlc.getHeaderFields();
					int len = 69;
					while (len > 0) {
						len = is.read(buffer, 0, buffer.length);
						if (len <= 0)
							break;
						sb.append(new String(buffer));
					}
					is.close();

					// For debug - to load from a file
					// byte[] buffer = new byte[4096];
					// StringBuffer sb = new StringBuffer();
					//
					// FileInputStream is = new FileInputStream(
					// "Z:\\dev\\FileSucker\\test_find_files_bug_index.php.html");
					// int len = 69;
					// while (len > 0)
					// {
					// len = is.read(buffer, 0, buffer.length);
					// if (len <= 0)
					// break;
					// sb.append(new String(buffer));
					// }
					// is.close();

					TreeMap<String, String> map = new TreeMap<String, String>();
					String[] extns = FileSucker.configData.getFindExtn();
					for (int i = 0; i < extns.length; ++i) {
						Pattern p = Pattern.compile("[\\[\\]a-zA-Z%$./0-9_-]+." + extns[i]);
						Matcher m = p.matcher(sb);

						while (m.find()) {
							String s = sb.substring(m.start(), m.end());
							map.put(s, s);
						}
					}

					if (map.size() == 0) {
						TaskScreen.setErrorMessage("No matches found");
						return;
					}

					StringBuffer found = new StringBuffer();
					found.append("{l,");
					found.append(Looper.getIndex(0));
					for (String s : map.keySet()) {
						found.append(",");
						found.append(s);
					}
					sb = null;

					found.append("}");

					numberB.setVisible(false);
					textB.setVisible(false);
					listB.setEnabled(false);
					copyB.setVisible(false);
					staticB.setVisible(false);

					iteratorJP.removeAll();
					iteratorJP.add(new ListLooper(found.toString()), BorderLayout.CENTER);
					iteratorJP.repaint();

					int lastSlash = urlTF.getText().lastIndexOf("/");
					urlTF.setText(urlTF.getText().substring(0, lastSlash + 1));
				} catch (Exception ex) {
					TaskScreen.setErrorMessage(ex.getMessage());
				}

			}
		});

		Box hbox = Box.createHorizontalBox();
		hbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		hbox.add(new JLabel("URL"));
		hbox.add(urlTF);
		hbox.add(saveUrl);
		hbox.add(findFilesButton);
		hbox.add(copyToToolsButton);
		centre.add(hbox);

		OriginalAddressLaunchButton findFileLaunchB = new OriginalAddressLaunchButton(originalAddressTF);

		centre.add(new JLabel(" "));

		JPanel findFileJP = new JPanel(new BorderLayout());
		findFileJP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		findFileJP.add(new JLabel("Orginal Address"), BorderLayout.WEST);
		findFileJP.add(originalAddressTF, BorderLayout.CENTER);
		findFileJP.add(findFileLaunchB, BorderLayout.EAST);
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

		hbox.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent me) {
				int clickedButton = me.getButton();
				if (clickedButton == 3) // r-click
				{
					String s = Utility.getClipboard();
					if (s != null)
						directoryCB.setSelectedItem(s);
				}
			}

			public void mouseClicked(MouseEvent me) {
				/* empty */
			}

			public void mouseReleased(MouseEvent me) {
				/* empty */
			}

			public void mouseEntered(MouseEvent me) {
				/* empty */
			}

			public void mouseExited(MouseEvent me) {
				/* empty */
			}
		});

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

		t = new PrefixButton(urlTF, prefixTF);
		jpt.add(t);

		t = new PrefixLowerButton(prefixTF);
		jpt.add(t);

		t = new PrefixCopyButton(prefixTF);
		jpt.add(t);

		t = new PrefixClearButton(prefixTF);
		jpt.add(t);
		hbox.add(jpt);

		hbox.add(prefixTF);

		jpt = new JPanel(new GridLayout());
		jpt.setMinimumSize(new Dimension(10, 20));
		jpt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		t = new SuffixButton(urlTF, suffixTF);
		jpt.add(t);
		jpt.add(suffixEndCB);

		t = new SuffixLowerButton(suffixTF);
		jpt.add(t);

		t = new SuffixCopyButton(suffixTF);
		jpt.add(t);

		t = new SuffixClearButton(suffixTF);
		jpt.add(t);
		hbox.add(jpt);

		hbox.add(suffixTF);

		jp.add(hbox);

		iteratorJP = new JPanel(new BorderLayout());

		hbox = Box.createHorizontalBox();
		hbox.add(numberB);
		hbox.add(textB);
		hbox.add(listB);
		hbox.add(copyB);
		hbox.add(staticB);

		JPanel iter_jp = new JPanel(new BorderLayout());
		iter_jp.setBorder(new LineBorder(Color.BLUE));
		iter_jp.add(hbox, BorderLayout.NORTH);
		iter_jp.add(iteratorJP, BorderLayout.CENTER);

		jp.add(iter_jp);
		centre.add(jp);
	}

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
	
	// Below: waiting for refactor

	public void setErrorMessage(String m) {
		System.err.println(m);
		StringBuffer s = new StringBuffer("Message: ");
		s.append(m);
		errorMessagesLabel.setText(s.toString());
	}

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


}
