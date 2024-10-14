package uk.co.bigsoft.filesucker.tools;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.UrlSequenceIteration;
import uk.co.bigsoft.filesucker.UrlSequencer;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.config.KeyReleasedListener;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class ToolsController {

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
			//
			break;
		}
		default: {
			System.out.println("Unknown ToolsProp: " + propName);
		}
		}
	}

	public void initController(ConfigModel configModel) {
		view.getWorkingTF().addKeyListener((KeyReleasedListener) e -> keyReleased());
		view.getWorkingTF().addCaretListener(e -> caretMoved());
		view.getWorkingTF().addMouseListener((MousePressListener) e -> pasteIntoWorking(e));
		view.getConvertHexNum().addActionListener(e -> convertHexNumber());
		view.getConvertMiddle().addActionListener(e -> convertMiddle());
		view.getConvertB64().addActionListener(e -> convertB64());
		view.getConvertB64auto().addActionListener(e -> convertB64Auto());
		view.getGenerateWebPage().addActionListener(e -> generateWebPage());
		view.getGenerateImageWebPage().addActionListener(e -> generateImageWebPage());
		view.getLinksPageButton().addActionListener(e -> linksPage());
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
		if (clickedButton == 3) // r-click
		{
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
				} catch (StringIndexOutOfBoundsException ex) {
					// c will not be
					// affected
				} catch (NumberFormatException ex) {
					// c will not be
					// affected
				}
			}
			sb.append(c);
		}

		model.setWorking(sb.toString());
	}

	private void convertMiddle() {
		String ss = model.getWorking();
		int s = ss.indexOf("http");
		if (s == 0)
			s = ss.indexOf("http", 1);
		int another = s;

		while (another != -1) {
			s = ss.indexOf("http", another);
			another = ss.indexOf("http", s + 1);
		}

		if (s == -1)
			return;

		int end = ss.length();
		ss = ss.substring(s);
		s = 0;
		int ie = ss.indexOf("?");

		if (ie != -1) {
			if (end > ie)
				end = ie;
		}
		ie = ss.indexOf("&");
		if (ie != 1) {
			if (end > ie)
				end = ie;
		}

		if (end != -1)
			ss = ss.substring(s, end);

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
			System.out.println("Base64");
			ex.printStackTrace();
		}
	}

	private void convertB64Auto() {
		try {
			String text = model.getWorking();
			// int start = text.indexOf("aHR0cDovL");
			int start = text.indexOf("aHR0c");
			if (start == -1) {
				return;
			}
			int end = text.indexOf("&", start + 1);
			if (end == -1) {
				end = text.length();
			}
			String selected = text.substring(start, end);
			System.out.println("substring:" + selected + "|");

			byte[] decodedBytes = Base64.getDecoder().decode(selected);
			String decoded = new String(decodedBytes);

			model.setWorking(decoded);
		} catch (Exception ex) {
			System.out.println("B64auto");
			ex.printStackTrace();
		}
	}

	private void generateWebPage() {
		try {
			String text = model.getWorking();
			text = text.trim();
			if (text.length() == 0) {
				return;
			}

			File f = File.createTempFile("FileSucker-", ".html");

			// TODO - FileWrite does not
			// handle \n properly
			FileWriter fw = new FileWriter(f);
			fw.write("<html>\n<head>\n<title>FileSucker v");
			fw.write(FileSucker.version);
			fw.write("</title>\n</head>\n<body>\n<p>\n");

			StringBuffer s;
			String remoteFile;
			UrlSequencer urls = new UrlSequencer(text);
			UrlSequenceIteration urlsi;
			for (Iterator<UrlSequenceIteration> i = urls.iterator(); i.hasNext();) {
				urlsi = i.next();
				remoteFile = urlsi.getRemoteFile();
				s = new StringBuffer();
				s.append("<a href=\"");
				s.append(remoteFile);
				s.append("\">");
				s.append(remoteFile);
				s.append("</a><br>\n");
				fw.write(s.toString());
			}
			fw.write("\n</body></html>\n");
			fw.close();

			// fucking DOS does not honour a
			// second quoted string in the
			// command
			// line
			// String path = "\"" +
			// f.toString ().replaceAll
			// ("\\\\", "\\\\\\\\")
			// + "\"";
			String path = f.toString().replaceAll("\\\\", "\\\\\\\\");
			Utility.launchBrowser(path);
			f.deleteOnExit();
		} catch (Exception ex) {
			System.out.println("GenWebPage");
			ex.printStackTrace();
		}
	}

	private void generateImageWebPage() {
		try {
			String text = model.getWorking();
			text = text.trim();
			if (text.length() == 0) {
				return;
			}

			File f = File.createTempFile("FileSucker-", ".html");

			// TODO - FileWrite does not
			// handle \n properly
			FileWriter fw = new FileWriter(f);
			fw.write("<html>\n<head>\n<title>FileSucker v");
			fw.write(FileSucker.version);
			fw.write("</title>\n</head>\n<body>\n<p>\n");

			StringBuffer s;
			String remoteFile;
			UrlSequencer urls = new UrlSequencer(text);
			UrlSequenceIteration urlsi;
			for (Iterator<UrlSequenceIteration> i = urls.iterator(); i.hasNext();) {
				urlsi = i.next();
				remoteFile = urlsi.getRemoteFile();
				s = new StringBuffer();
				s.append("<table><tr><td>");
				s.append("<a href=\"");
				s.append(remoteFile);
				s.append("\"><img src=\"");
				s.append(remoteFile);
				s.append("\" border=\"0\"></a></td></tr><tr><td><a href=\"");
				s.append(remoteFile);
				s.append("\">");
				s.append(remoteFile);
				s.append("</a></td></tr></table>\n");
				fw.write(s.toString());
			}
			fw.write("\n</body></html>\n");
			fw.close();

			// fucking DOS does not honour a
			// second quoted string in the
			// command
			// line
			// String path = "\"" +
			// f.toString ().replaceAll
			// ("\\\\", "\\\\\\\\")
			// + "\"";
			String path = f.toString().replaceAll("\\\\", "\\\\\\\\");
			Utility.launchBrowser(path);
			f.deleteOnExit();
		} catch (Exception ex) {
			System.out.println("GenImageWebPage");
			ex.printStackTrace();
		}
	}

	private void linksPage() {
		try {
			int idx;
			StringBuffer s;
			String text = model.getWorking();
			text = text.trim();
			if (text.length() == 0) {
				return;
			}

			StringBuffer sb = Utility.downloadFile(text);
			if (sb.length() == 0) {
				return;
			}

			List<String> links = getLinks(sb);

			File f = File.createTempFile("FileSucker-", ".html");

			// TODO - FileWrite does not
			// handle \n properly
			FileWriter fw = new FileWriter(f);
			fw.write("<html>\n<head>\n<title>FileSucker v");
			fw.write(FileSucker.version);
			fw.write("</title>\n</head>\n<body>\n<p>\n");

			for (String l : links) {
				s = new StringBuffer();

				idx = l.lastIndexOf(".") + 1;
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
				fw.write(s.toString());
			}
			fw.write("\n</body></html>\n");
			fw.close();

			// fucking DOS does not honour a
			// second quoted string in the
			// command
			// line
			// String path = "\"" +
			// f.toString ().replaceAll
			// ("\\\\", "\\\\\\\\")
			// + "\"";
			String path = f.toString().replaceAll("\\\\", "\\\\\\\\");
			Utility.launchBrowser(path);
			f.deleteOnExit();
		} catch (Exception ex) {
			TaskScreen.setErrorMessage(ex.getMessage());
		}
	}

	private void launch(String helperWeb) {
		String url = model.getWorking();
		Utility.launchBrowser(helperWeb, url);
	}

	private void launchProfile() {
		System.out.println("not-implemented: launchProfile");
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
		// Pattern p = Pattern.compile
		// ("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?")
		// ;
		Pattern p = Pattern.compile(
				"([Hh][Rr][Ee][Ff]=)?([Hh][Rr][Ee][Ff]=)?([Hh][Tt][Tt][Pp]://)(['\"])?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");

		Matcher m = p.matcher(initialText);
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
