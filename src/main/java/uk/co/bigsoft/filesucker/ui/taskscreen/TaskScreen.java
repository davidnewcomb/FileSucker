package uk.co.bigsoft.filesucker.ui.taskscreen;

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
import uk.co.bigsoft.filesucker.SuffixJTextField;
import uk.co.bigsoft.filesucker.SuckerParams;
import uk.co.bigsoft.filesucker.SuckerThread;
import uk.co.bigsoft.filesucker.TaskScreenParams;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.looper.Looper;
import uk.co.bigsoft.filesucker.looper.list.ListLooper;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.BrowseButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.ClipboardAsDirectoryButton;
//import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.CopyToToolClearButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.DirectoryAndPrefixButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.DirectoryClipboardButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.DirectoryExtensionButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.HomeButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.HomeDirectoryPrefix;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.OpenDirectoryButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.OriginalAddressLaunchButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixClearButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixCopyButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.PrefixLowerButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SubDirectoryAndPrefixButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SubDirectoryAndPrefixFromClipboardButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SubDirectoryFromClipboardButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SubDirectoryPathButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixClearButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixCopyButton;
import uk.co.bigsoft.filesucker.ui.taskscreen.buttons.SuffixLowerButton;
import uk.co.bigsoft.filesucker.view.FileSuckerFrame;

public class TaskScreen extends JPanel {
	protected static JLabel errorMessages;
	protected static RunYetComponent runYet;
	public static JButton runB;
	public static JCheckBox saveOnly;
	protected static JButton findFilesB;
	protected static JButton openDir;
	// protected static JButton tools;
	public static JTextField urlTF;
	public static OriginalAddressTextField originalAddress;
	protected static JTextField prefixTF;
	protected static JTextField suffixTF;
	protected static JCheckBox saveUrl;
	protected static JCheckBox suffixEndCB;
	protected static HistoryJComboBox directoryCB;
	public JPanel iteratorJP;
	public NumberLooperButton numberB;
	public TextLooperButton textB;
	public ListLooperButton listB;
	public CopyLooperButton copyB;
	public StaticLooperButton staticB;

	// Drag & Drop
	protected FileAndTextTransferHandler ddHandler;

	public TaskScreen() {
		super(new BorderLayout());

		JButton t;

		ddHandler = new FileAndTextTransferHandler();
		setTransferHandler(ddHandler);

		JPanel centre = new JPanel();
		centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

		add(centre, BorderLayout.CENTER);

		// Base Directory
		directoryCB = new HistoryJComboBox("directory", ddHandler);
		directoryCB.setMinimumSize(new Dimension(10, 20));
		directoryCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		directoryCB.setSelectedItem(FileSucker.configData.getBaseDir().toString());

		// Prefix and Suffix
		prefixTF = new PrefixJTextField(ddHandler);
		suffixTF = new SuffixJTextField(ddHandler);

		saveOnly = new JCheckBox();
		saveOnly.setToolTipText("RunTask - save but without running");
		saveOnly.setSelected(false);

		runB = new JButton("Run Task");
		runB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		runB.addActionListener(new ActionListener() {
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

				if (prefixTF.getText().length() > 0)
					prefix = prefixTF.getText();
				if (suffixTF.getText().length() > 0)
					suffix = suffixTF.getText();

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
						suffixEndCB.isSelected(), originalAddress.getText());
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
				originalAddress.setText("");
				runYet.setReset();
			}
		});

		runYet = new RunYetComponent();
		openDir = new OpenDirectoryButton(directoryCB);
		errorMessages = new JLabel();
		setErrorMessage("");
		// errorMessages.setFont()
		JPanel bot = new JPanel();
		bot.setLayout(new BoxLayout(bot, BoxLayout.Y_AXIS));
		bot.add(errorMessages);
		JPanel rp = new JPanel(new BorderLayout());
		rp.add(BorderLayout.CENTER, runB);
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

		originalAddress = new OriginalAddressTextField();
		// findFileTF.setEditable (false) ;

		// tools = new CopyToToolClearButton(urlTF);

		saveUrl = new JCheckBox();
		saveUrl.setToolTipText("Save download instructions");
		saveUrl.setSelected(true);

		findFilesB = new JButton("FindFiles");
		findFilesB.setToolTipText("Suck down webpage, fish out all the filenames and fill up 'L'");
		// which does the actual Delete operation
		findFilesB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String findFileAddress = urlTF.getText();
					if (findFileAddress.length() == 0) {
						TaskScreen.setErrorMessage("URL is empty");
						return;
					}
					originalAddress.setText(findFileAddress);
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
		hbox.add(findFilesB);
		// hbox.add(tools);
		centre.add(hbox);

		OriginalAddressLaunchButton findFileLaunchB = new OriginalAddressLaunchButton(originalAddress);

		centre.add(new JLabel(" "));

		JPanel findFileJP = new JPanel(new BorderLayout());
		findFileJP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		findFileJP.add(new JLabel("Orginal Address"), BorderLayout.WEST);
		findFileJP.add(originalAddress, BorderLayout.CENTER);
		findFileJP.add(findFileLaunchB, BorderLayout.EAST);
		centre.add(findFileJP);

		centre.add(new JLabel("Save To"));

		JButton browseButton = new BrowseButton(directoryCB);

		JButton hButton = new HomeButton(directoryCB);

		JButton dButton = new SubDirectoryPathButton(urlTF, directoryCB);

		JButton dsButton = new DirectoryExtensionButton(urlTF, directoryCB);

		JButton dpButton = new SubDirectoryAndPrefixButton(urlTF, directoryCB, prefixTF);

		JButton pdpButton = new DirectoryAndPrefixButton(urlTF, directoryCB, prefixTF);

		JButton cButton = new ClipboardAsDirectoryButton(directoryCB);

		JButton cpButton = new SubDirectoryAndPrefixFromClipboardButton(directoryCB, prefixTF);

		JButton cdButton = new SubDirectoryFromClipboardButton(directoryCB);

		JButton csButton = new DirectoryClipboardButton(directoryCB);

		JButton hpdpButton = new HomeDirectoryPrefix(urlTF, directoryCB, prefixTF);

		hbox = Box.createHorizontalBox();
		hbox.setBorder(new LineBorder(Color.BLUE));
		hbox.add(openDir);
		hbox.add(new JLabel("Directory"));
		hbox.add(directoryCB);
		hbox.add(hButton);
		hbox.add(browseButton);
		hbox.add(dButton);
		hbox.add(hpdpButton);
		hbox.add(dpButton);
		hbox.add(pdpButton);
		hbox.add(dsButton);
		hbox.add(cButton);
		hbox.add(cpButton);
		hbox.add(cdButton);
		hbox.add(csButton);

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

		suffixEndCB = new JCheckBox("B4Extn");
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

		// Loopers
		numberB = new NumberLooperButton();
		textB = new TextLooperButton();
		listB = new ListLooperButton();
		copyB = new CopyLooperButton();
		staticB = new StaticLooperButton();

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

	public static void setErrorMessage(String m) {
		System.err.println(m);
		StringBuffer s = new StringBuffer("Message: ");
		s.append(m);
		errorMessages.setText(s.toString());
	}

	public static void load(SuckerParams p) {
		System.out.println(p.getOrginalAddress());
		urlTF.setText(p.getOrginalUrl());
		prefixTF.setText(p.getPrefix());
		suffixTF.setText(p.getSuffix());
		directoryCB.setSelectedItem(p.getIntoDir());
		suffixEndCB.setSelected(p.isSuffixEnd());
		originalAddress.setText(p.getOrginalAddress());
		runYet.setModifed();
	}

	public static void enableRunButton(boolean e) {
		runB.setEnabled(e);
	}

	public static String getUrlText() {
		return urlTF.getText();
	}

	public static void setUrlText(String x) {
		urlTF.setText(x);
	}

	public static void replaceUrlText(String braces) {
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

	public static void changed() {
		runYet.setModifed();
	}
}
