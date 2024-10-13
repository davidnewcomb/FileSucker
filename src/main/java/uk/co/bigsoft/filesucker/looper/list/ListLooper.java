package uk.co.bigsoft.filesucker.looper.list;

import java.awt.Dimension;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import uk.co.bigsoft.filesucker.looper.Looper;

public class ListLooper extends Looper {
	private String list;

	private JTextArea listTA;

	public ListLooper(String sel) {
		super(sel);

		if (setParameters() == false)
			list = selectedUrl == null ? "" : new String(selectedUrl + "\n");

		createLayout();
	}

	private void createLayout() {
		if (list.length() != 0) {
			if (list.charAt(list.length() - 1) != '\n')
				list = list + "\n";
		}
		listTA = new JTextArea(list);
		listTA.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		getCentrePanel().add(new JLabel("List:"));
		JScrollPane jsp = new JScrollPane(listTA);
		jsp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		getCentrePanel().add(jsp);
	}

	@Override
	public String toStringBraces() {
		TreeSet<String> ordered = new TreeSet<String>();
		String allList = listTA.getText();
		String[] words = allList.split("\n");
		if (words.length == 0)
			return "{l," + index.toString() + "}";
		StringBuffer sb = new StringBuffer("{l," + index.toString() + ",");

		for (int i = 0; i < words.length; ++i) {
			if (words[i] == null)
				continue;
			ordered.add(words[i]);
		}

		for (String s : ordered) {
			sb.append(s);
			sb.append(",");
		}

		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',')
			sb.deleteCharAt(sb.length() - 1);

		sb.append("}");
		return sb.toString();
	}

	@Override
	public boolean setParameters() {
		if (parameters.length == 0 || parameters[0].equals("l") == false)
			return false;

		StringBuffer sb = new StringBuffer();
		for (int i = 2; i < parameters.length; ++i) {
			sb.append(parameters[i]);
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		list = sb.toString();

		return true;
	}

}
