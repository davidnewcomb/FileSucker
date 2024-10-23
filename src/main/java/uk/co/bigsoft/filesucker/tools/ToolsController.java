package uk.co.bigsoft.filesucker.tools;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bigsoft.filesucker.Downloader;
import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.config.KeyReleasedListener;
import uk.co.bigsoft.filesucker.task.TaskConfig;
import uk.co.bigsoft.filesucker.task.TaskModel;
import uk.co.bigsoft.filesucker.transfer.si.SuckerItem;
import uk.co.bigsoft.filesucker.transfer.si.SuckerIterable;

public class ToolsController {

	private static Logger L = LoggerFactory.getLogger(ToolsController.class);

	private static final Pattern urlPattern = Pattern.compile(
			"([Hh][Rr][Ee][Ff]=)?([Hh][Rr][Ee][Ff]=)?([Hh][Tt][Tt][Pp]://)(['\"])?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");
	private static final Pattern b64EncodePattern = Pattern.compile("aHR0c[a-zA-Z0-9=]*");

	private ToolsModel model;
	private ToolsView view;

	public ToolsController(ToolsModel m, ToolsView v) {
		model = m;
		view = v;
		initView();
	}

	public void initView() {
		model.addListener(e -> modelListener(e));
		view.getWorkingTF().setText(model.getWorking());
	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case ToolsProps.F_WORKING: {
			view.getWorkingTF().setText((String) newVal);
			break;
		}
		case ToolsProps.F_SELECTED_WORKING: {
			// TODO Needed?
			break;
		}
		default: {
			L.debug("Unknown ToolsProp: " + propName);
		}
		}
	}

	public void initController(ConfigModel configModel, TaskModel taskModel) {
		view.getWorkingTF().addKeyListener((KeyReleasedListener) e -> keyReleased());
		view.getWorkingTF().addCaretListener(e -> caretMoved());
		view.getWorkingTF().addMouseListener((MousePressListener) e -> pasteIntoWorking(e));
		view.getConvertHexNum().addActionListener(e -> convertHexNumber());
		view.getConvertMiddle().addActionListener(e -> convertMiddle());
		view.getConvertB64().addActionListener(e -> convertB64());
		view.getConvertB64auto().addActionListener(e -> convertB64Auto());
		view.getGenerateWebPage().addActionListener(e -> generateWebPage(configModel));
		view.getGenerateImageWebPage().addActionListener(e -> generateImageWebPage(configModel));
		view.getLinksPageButton().addActionListener(e -> linksPage(configModel, taskModel));
		view.getLaunchButton().addActionListener(e -> launch(configModel.getHelperWeb()));
		view.getLaunchProfileButton().addActionListener(e -> launchProfile());
	}

	private void keyReleased() {
		String s = view.getWorkingTF().getText();
		model.setWorking(s);
	}

	private void caretMoved() {
		String s = view.getWorkingTF().getSelectedText();
		if (s == null) {
			s = "";
		}
		model.setSelectedWorking(s);
	}

	private void pasteIntoWorking(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3) {
			// r-click
			String s = Utility.getClipboard();
			if (s != null) {
				model.setWorking(s);
			}
		}
	}

	private void convertHexNumber() {
		String s = model.getWorking();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if (c == '%') {
				try {
					StringBuffer num = new StringBuffer();
					num.append(s.charAt(i + 1));
					num.append(s.charAt(i + 2));
					c = (char) Integer.parseInt(num.toString(), 16);
					i += 2;
				} catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
					// c will not be affected
				}
			}
			sb.append(c);
		}
		model.setWorking(sb.toString());
	}

	private void convertMiddle() {
		String ss = model.getWorking();
		int s = ss.indexOf("http");
		if (s == 0) {
			s = ss.indexOf("http", 1);
		}
		int another = s;

		while (another != -1) {
			s = ss.indexOf("http", another);
			another = ss.indexOf("http", s + 1);
		}

		if (s == -1) {
			return;
		}

		int end = ss.length();
		ss = ss.substring(s);
		s = 0;
		int ie = ss.indexOf("?");

		if (ie != -1) {
			if (end > ie) {
				end = ie;
			}
		}
		ie = ss.indexOf("&");
		if (ie != 1) {
			if (end > ie) {
				end = ie;
			}
		}

		if (end != -1) {
			ss = ss.substring(s, end);
		}

		model.setWorking(ss.toString());
	}

	private void convertB64() {
		try {
			String selected = model.getSelectedWorking();
			if ("".equals(selected)) {
				return;
			}

			byte[] decodedBytes = Base64.getDecoder().decode(selected);
			String decoded = new String(decodedBytes);

			model.setWorking(decoded);
		} catch (Exception ex) {
			L.debug("Base64");
			ex.printStackTrace();
		}
	}

	private void convertB64Auto() {
		String text = model.getWorking();
		Matcher m = b64EncodePattern.matcher(text);
		if (!m.find()) {
			return;
		}
		byte[] decodedBytes = Base64.getDecoder().decode(m.group());
		String decoded = new String(decodedBytes);
		model.setWorking(decoded);
	}

	private File createTmpFile() {
		String filename = System.getenv("HOME") + File.separator + "FileSucker-" + Math.random() + ".html";
		File f = new File(filename);
		f.deleteOnExit();
		return f;
	}

	private void generateWebPage(ConfigModel configModel) {

		try {
			String text = model.getWorking();
			text = text.trim();
			if (text.length() == 0) {
				return;
			}

			StringBuilder s = new StringBuilder();

			s.append("<html>\n<head>\n <title>FileSucker v");
			s.append(FileSucker.version);
			s.append("</title>\n</head>\n<body>\n\n");

			TaskConfig tm = new TaskConfig(text, ".", "", "", false);
			SuckerIterable sit = new SuckerIterable(tm);

			for (SuckerItem st : sit) {
				String remoteFile = st.getUrl();

				s.append("<a href=\"");
				s.append(remoteFile);
				s.append("\">");
				s.append(remoteFile);
				s.append("</a><br>\n");
			}
			s.append("\n</body>\n</html>\n");

			File f = createTmpFile();
			FileWriter fw = new FileWriter(f);
			fw.write(s.toString());
			fw.close();

			// DOS does not honour a second quoted string in the command line
			// String path = "\"" + f.toString ().replaceAll ("\\\\", "\\\\\\\\") + "\"";
			String path = f.toString().replaceAll("\\\\", "\\\\\\\\");
			Utility.launchBrowser(configModel, path);
		} catch (Exception ex) {
			L.debug("GenWebPage");
			ex.printStackTrace();
		}
	}

	private void generateImageWebPage(ConfigModel configModel) {

		try {
			String text = model.getWorking();
			text = text.trim();
			if (text.length() == 0) {
				return;
			}

			TaskConfig tm = new TaskConfig(text, ".", "", "", false);
			SuckerIterable sit = new SuckerIterable(tm);

			StringBuilder s = new StringBuilder();

			s.append("<html>\n<head>\n <title>FileSucker v");
			s.append(FileSucker.version);
			s.append("</title>\n</head>\n<body>\n\n");

			for (SuckerItem st : sit) {
				String remoteFile = st.getUrl();
				s.append("<table>\n <tr>\n  <td>");
				s.append("<a href=\"");
				s.append(remoteFile);
				s.append("\"><img src=\"");
				s.append(remoteFile);
				s.append("\" border=\"0\"></a></td>\n </tr>\n <tr>\n  <td><a href=\"");
				s.append(remoteFile);
				s.append("\">");
				s.append(remoteFile);
				s.append("</a></td>\n </tr>\n</table>\n");
			}

			s.append("\n</body>\n</html>\n");

			File f = createTmpFile();
			FileWriter fw = new FileWriter(f);
			fw.write(s.toString());
			fw.close();

			// DOS does not honour a second quoted string in the command line
			// String path = "\"" + f.toString ().replaceAll("\\\\", "\\\\\\\\") + "\"";
			String path = f.toString().replaceAll("\\\\", "\\\\\\\\");
			Utility.launchBrowser(configModel, path);

		} catch (Exception ex) {
			L.debug("GenImageWebPage");
			ex.printStackTrace();
		}

	}

	private void linksPage(ConfigModel configModel, TaskModel taskModel) {
		try {
			String text = model.getWorking().trim();
			if (text.length() == 0) {
				return;
			}

			StringBuffer sb = new StringBuffer(Downloader.getInstance().downloadTextFile(text));
			if (sb.length() == 0) {
				return;
			}

			List<String> links = getLinks(sb);

			StringBuilder s = new StringBuilder();
			s.append("<html>\n<head>\n<title>FileSucker v");
			s.append(FileSucker.version);
			s.append("</title>\n</head>\n<body>\n<p>\n");

			for (String l : links) {

				int idx = l.lastIndexOf(".") + 1;
				String extn = l.substring(idx, l.length()).toLowerCase();
				if (extn.endsWith("jpg") || extn.endsWith("jpeg")) {
					s.append("<img src=\"");
					s.append(l);
					s.append("\">\n");
				} else {
					s.append("<a href=\"");
					s.append(l);
					s.append("\">");
					s.append(l);
					s.append("</a><br>\n");
					idx = l.indexOf("&");
					if (idx != -1) {
						l = l.substring(0, idx);
						s.append("<a href=\"");
						s.append(l);
						s.append("\">");
						s.append(l);
						s.append("</a><br>\n");
					}
				}
				s.append("<br>\n");
			}
			s.append("\n</body></html>\n");

			File f = createTmpFile();
			FileWriter fw = new FileWriter(f);
			fw.write(s.toString());
			fw.close();

			// DOS does not honour a second quoted string in the command line
			// String path = "\"" + f.toString ().replaceAll ("\\\\", "\\\\\\\\") + "\"";
			String path = f.toString().replaceAll("\\\\", "\\\\\\\\");
			Utility.launchBrowser(configModel, path);

		} catch (Exception ex) {
			taskModel.setErrorMessage(ex.getMessage());
		}
	}

	private void launch(String helperWeb) {
		String url = model.getWorking();
		Utility.launchBrowser(helperWeb, url);
	}

	private void launchProfile() {
		L.debug("not-implemented: launchProfile");
		String url = model.getWorking();
		if ("".equals(url)) {
			return;
		}
		try {
//			 String sub = list.getSel
//			 String helper = FileSucker.configData.getWebHelper().replaceAll ("%s", url);
//			 Runtime.getRuntime ().exec (helper) ;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Converts all urls like "www.google.com" into hyperlinks in the text.
	 * 
	 * @param initialText The text to convert
	 * @return Converted text.
	 */
	protected List<String> getLinks(StringBuffer initialText) {
		LinkedList<String> list = new LinkedList<String>();

		Matcher m = urlPattern.matcher(initialText);
		while (m.find()) {
			String href = m.group();
			if (href.length() < 8) {
				continue;
			}

			String h = href.substring(0, 7).toLowerCase();
			if (!h.equals("http://")) {
				continue;
			}
			if (!list.contains(href)) {
				list.add(href);
			}
		}
		return list;
	}

}
