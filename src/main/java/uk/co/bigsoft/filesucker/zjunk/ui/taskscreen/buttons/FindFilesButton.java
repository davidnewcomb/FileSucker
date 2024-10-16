package uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.TaskScreen;

public class FindFilesButton extends JButton implements ActionListener {

	public FindFilesButton() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			// String findFileAddress = urlTF.getText();
			// if (findFileAddress.length() == 0)
			// {
			// TaskScreen.setErrorMessage("URL is empty");
			// return;
			// }
			// originalAddress.setText(findFileAddress);
			// URL url = URI.create(findFileAddress).toURL();
			// java.net.URLConnection urlc = url.openConnection();
			// String userinfo = url.getUserInfo();
			// if (userinfo != null)
			// {
			// String[] auth = userinfo.split(":");
			// urlc.setRequestProperty("Authorization", "Basic "
			// + BasicAuth.encode(auth[0], auth[1]));
			// }
			// // To download
			// InputStream is = urlc.getInputStream();
			// byte[] buffer = new byte[4096];
			// StringBuffer sb = new StringBuffer();
			// // Map header = urlc.getHeaderFields();
			// int len = 69;
			// while (len > 0)
			// {
			// len = is.read(buffer, 0, buffer.length);
			// if (len <= 0)
			// break;
			// sb.append(new String(buffer));
			// }
			// is.close();
			//
			// // For debug - to load from a file
			// // byte[] buffer = new byte[4096];
			// // StringBuffer sb = new StringBuffer();
			// //
			// // FileInputStream is = new FileInputStream(
			// // "Z:\\dev\\FileSucker\\test_find_files_bug_index.php.html");
			// // int len = 69;
			// // while (len > 0)
			// // {
			// // len = is.read(buffer, 0, buffer.length);
			// // if (len <= 0)
			// // break;
			// // sb.append(new String(buffer));
			// // }
			// // is.close();
			//
			// TreeMap<String, String> map = new TreeMap<String, String>();
			// String[] extns = FileSucker.configData.getFindExtn();
			// for (int i = 0 ; i < extns.length ; ++i)
			// {
			// Pattern p = Pattern.compile("[\\[\\]a-zA-Z%$./0-9_-]+."+
			// extns[i]);
			// Matcher m = p.matcher(sb);
			//
			// while (m.find())
			// {
			// String s = sb.substring(m.start(), m.end());
			// map.put(s, s);
			// }
			// }
			//
			// if (map.size() == 0)
			// {
			// TaskScreen.setErrorMessage("No matches found");
			// return;
			// }
			//
			// StringBuffer found = new StringBuffer();
			// found.append("{l,");
			// found.append(Looper.getIndex(0));
			// for (String s : map.keySet())
			// {
			// found.append(",");
			// found.append(s);
			// }
			// sb = null;
			//
			// found.append("}");
			//
			// numberB.setVisible(false);
			// textB.setVisible(false);
			// listB.setEnabled(false);
			// copyB.setVisible(false);
			// staticB.setVisible(false);
			//
			// iteratorJP.removeAll();
			// iteratorJP.add(new ListLooper(found.toString()),
			// BorderLayout.CENTER);
			// iteratorJP.repaint();
			//
			// int lastSlash = urlTF.getText().lastIndexOf("/");
			// urlTF.setText(urlTF.getText().substring(0, lastSlash + 1));
		} catch (Exception ex) {
			TaskScreen.setErrorMessage(ex.getMessage());
		}

	}

}
