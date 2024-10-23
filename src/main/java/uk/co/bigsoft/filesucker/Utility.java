package uk.co.bigsoft.filesucker;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bigsoft.filesucker.config.ConfigModel;

public class Utility {
	private static Logger L = LoggerFactory.getLogger(Utility.class);

	public static String getClipboard() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();

		Transferable clipData = clipboard.getContents(clipboard);
		if (clipData != null) {
			try {
				if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					String s = (String) clipData.getTransferData(DataFlavor.stringFlavor);
					return s;
				}
				toolkit.beep();
			} catch (Exception ee) {
				L.debug("RClick: " + ee.toString());
			}
		}
		return null;
	}

	public static void setClipboard(String str) {
		L.debug("setClipboard not implemented");
	}

	public static void delay(int ms) {
		if (ms < 1) {
			return;
		}

		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			/* empty */
		}
	}

	public static String cleanString(String _s) {
		String s = _s.trim();
		s = s.replaceAll("[ \\.,-]", "_");
		s = s.replaceAll("&", "__");
		s = s.replaceAll("/", "_");
		s = s.replaceAll("__", "_");
		s = s.replaceAll("_&_", "__");
		s = s.replaceAll("_[Aa][Nn][Dd]_", "__");

		// Capitalise
		StringBuffer n = new StringBuffer();
		boolean first = true;
		boolean possibly_camelcase = false;
		boolean actually_camelcase = false;
		for (int i = 0; i < s.length(); ++i) {
			String letter = s.substring(i, i + 1);
			boolean isUpper = Character.isUpperCase(letter.charAt(0));

			if (s.charAt(i) == '_' || s.charAt(i) == '-') {
				first = true;
				n.append(letter);
				continue;
			}
			if (first) {
				first = false;
				if (isUpper) {
					possibly_camelcase = true;
					// actually_camelcase = true ;
					n.append(letter);
				} else {
					possibly_camelcase = false;
					actually_camelcase = false;
					n.append(letter.toUpperCase());
				}
			} else {
				if (possibly_camelcase) {
					possibly_camelcase = true;
					possibly_camelcase = false;
					if (Character.isLowerCase(letter.charAt(0))) {
						actually_camelcase = true;
					}
				}

				if (isUpper && actually_camelcase) {
					n.append("_");
					n.append(letter);
					possibly_camelcase = false;
					actually_camelcase = false;
				} else {
					n.append(letter.toLowerCase());
				}
			}
		}

		return n.toString();
	}

	/**
	 * @param what     Debug reason for caller
	 * @param toExpand What to expand
	 * @return Expanded string
	 */
	public static String expandsPercentVars(String toExpand) {
		if (toExpand.contains("%T")) {
			ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String timeStr = formatter.format(zdt);
			return toExpand.replaceAll("%T", timeStr);
		}
		return toExpand;
	}

	public static String realDirectory(String dir) {
		String dirEpanded = expandsPercentVars(dir);
		dirEpanded = dirEpanded.replaceAll("\\\\", "\\\\\\\\");

		int ch = dirEpanded.indexOf("{");
		if (ch != -1) {
			dirEpanded = dirEpanded.substring(0, ch);
			ch = dirEpanded.lastIndexOf(File.separator + File.separator);
			dirEpanded = dirEpanded.substring(0, ch);
		}

		return dirEpanded;
	}

	public static void launchBrowser(String helperWeb, String url) {
		if ("".equals(url)) {
			return;
		}
		try {
			String helper = helperWeb.replaceAll("%s", url);
			runShellCommand(helper);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void launchBrowser(ConfigModel configModel, String url) {
		if (url == null) {
			return;
		}
		String u = url.trim();
		if (u.trim().equals("")) {
			return;
		}

		try {
			String helper = configModel.getHelperWeb().replaceAll("%s", u);
			runShellCommand(helper);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void launchTextFile(ConfigModel configModel, File file) {
		try {
			String helper = configModel.getHelperText();
			String local = realDirectory(file.toString());
			String sub = helper.replaceAll("%s", local);
			runShellCommand(sub);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getSuckerLable(String sel) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < sel.length(); ++i) {
			if (sel.charAt(i) == '{') {
				int endBracketIdx = sel.indexOf('}', i + 1);
				if (endBracketIdx == -1) {
					// No matching '}'
					sb.append(sel.charAt(i));
					continue;
				}
				String rest = sel.substring(i + 1, endBracketIdx);
				String[] restAr = rest.split(",");
				sb.append("{");
				if (restAr.length > 1) {
					// A special set eg l,43,02,03,10. 2nd value is the number eg 43
					sb.append(restAr[1]);
				} else {
					// Just a number on its own
					sb.append(rest);
				}
				sb.append("}");
				i = endBracketIdx;
				continue;
			}
			sb.append(sel.charAt(i));
		}
		return sb.toString();
	}

	public static String unexpandsPercentVars(String what, String toExpand) {
		ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String timeStr = formatter.format(zdt);
		return toExpand.replaceAll(timeStr, "%T");
	}

	// exec(String) deprecation in java 18
	// @SuppressWarnings(value = "deprecation")
	public static void runShellCommand(String cmd) throws IOException {
		L.debug("Running: '" + cmd + "'");
		Runtime.getRuntime().exec(cmd);
	}

	public static void closeSafely(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				// don't care
			}
		}
	}

}
