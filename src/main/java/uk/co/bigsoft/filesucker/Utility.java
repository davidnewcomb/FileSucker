package uk.co.bigsoft.filesucker;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class Utility {
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
				TaskScreen.setErrorMessage("RClick: " + ee.toString());
			}
		}
		return null;
	}

	public static void setClipboard(String str) {
		TaskScreen.setErrorMessage("setClipboard not implemented");
		System.out.println("setClipboard not implemented");
	}

	public static void delay(int t) {
		if (t < 1)
			return;

		try {
			Thread.sleep(t);
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
		String letter;
		boolean isUpper;
		for (int i = 0; i < s.length(); ++i) {
			letter = s.substring(i, i + 1);
			isUpper = Character.isUpperCase(letter.charAt(0));

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

	public static String implode(String[] a, String sep) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; ++i) {
			sb.append(a[i]);
			sb.append(sep);
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * @param what     Debug reason for caller
	 * @param toExpand What to expand
	 * @return Expanded string
	 */
	public static String expandsPercentVars(String toExpand) {
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String timeStr = sdf.format(d);

		String s = toExpand.replaceAll("%T", timeStr);
		return s;
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

	// Works
	// public static String cleanString (String s)
	// {
	// s = s.trim () ;
	// s = s.replaceAll (" & ", "__") ;
	// s = s.replaceAll ("[ \\.]", "_") ;
	// s = s.replaceAll ("&", "__") ;
	// s = s.replaceAll ("__", "_") ;
	//
	// // Capitalise
	// StringBuffer n = new StringBuffer() ;
	// boolean first = true ;
	// boolean possibly_camelcase = false ;
	// boolean actually_camelcase = false ;
	// String letter ;
	// for (int i = 0; i < s.length (); ++i)
	// {
	// letter = s.substring (i, i + 1) ;
	// if (s.charAt (i) == '_' || s.charAt (i) == '-')
	// {
	// first = true ;
	// n.append (letter) ;
	// continue ;
	// }
	// if (first)
	// {
	// first = false ;
	// if (Character.isUpperCase (letter.charAt (0)))
	// {
	// possibly_camelcase = true ;
	// actually_camelcase = true ;
	// n.append (letter) ;
	// }
	// else
	// {
	// possibly_camelcase = false ;
	// actually_camelcase = false ;
	// n.append (letter.toUpperCase ()) ;
	// }
	// }
	// else
	// {
	// if (possibly_camelcase)
	// {
	// possibly_camelcase = true ;
	// if (Character.isLowerCase (letter.charAt (0)))
	// {
	// actually_camelcase = true ;
	// }
	// }
	//
	// if (Character.isUpperCase (letter.charAt (0)) && actually_camelcase)
	// {
	// n.append ("_") ;
	// n.append (letter) ;
	// possibly_camelcase = false ;
	// actually_camelcase = false ;
	// }
	// else
	// {
	// n.append (letter.toLowerCase ()) ;
	// }
	// }
	// //n.append (letter) ;
	// }
	//
	// return n.toString () ;
	// }

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

	public static void launchBrowser(String url) {
		if (url == null)
			return;
		String u = url.trim();
		if (u.trim().equals(""))
			return;

		try {
			String helper = FileSucker.configData.getHelperWeb().replaceAll("%s", u);
			runShellCommand(helper);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void launchTextFile(File file) {
		try {
			String helper = FileSucker.configData.getHelperText();
			String local = realDirectory(file.toString());
			String sub = helper.replaceAll("%s", local);
			runShellCommand(sub);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static StringBuffer downloadFile(String url) {
		StringBuffer sb = new StringBuffer();
		try {
			URL u = URI.create(url).toURL();
			java.net.URLConnection urlc = u.openConnection();
			String userinfo = u.getUserInfo();
			if (userinfo != null) {
				String[] auth = userinfo.split(":");
				urlc.setRequestProperty("Authorization", "Basic " + BasicAuth.encode(auth[0], auth[1]));
			}
			InputStream is = urlc.getInputStream(); // To download
			byte[] buffer = new byte[4096];
			// Map header = urlc.getHeaderFields();
			int len = 69;
			while (len > 0) {
				len = is.read(buffer, 0, buffer.length);
				if (len <= 0)
					break;
				sb.append(new String(buffer));
			}
			is.close();
			return sb;

			// For debug - to load from a file
			// byte[] buffer = new byte[4096] ;
			//
			// FileInputStream is = new FileInputStream
			// ("c:\\tmp\\so.html") ;
			// int len = 69 ;
			// while (len > 0)
			// {
			// len = is.read (buffer, 0, buffer.length) ;
			// if (len <= 0)
			// break ;
			// sb.append (new String (buffer)) ;
			// }
			// is.close () ;
			// return sb ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringBuffer();
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
					// A special set eg l,43,02,03,10. 2nd
					// value is the number eg 43
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
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String timeStr = sdf.format(d);

		String s = toExpand.replaceAll("%T", timeStr);
		return s;
	}
	
	@SuppressWarnings(value = "deprecation")
	public static void runShellCommand(String cmd) throws IOException {
		System.out.println("Running: '" + cmd + "'");
		Runtime.getRuntime().exec(cmd);
	}
}
