package uk.co.bigsoft.filesucker.zjunk.looper.copy;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import uk.co.bigsoft.filesucker.zjunk.looper.Looper;
import uk.co.bigsoft.filesucker.zjunk.ui.taskscreen.TaskScreen;

public class CopyLooper extends Looper {
	private int id;

	private JComboBox<Integer> idCB;

	public CopyLooper(String sel) {
		super(sel);
		id = 1;
		createLayout();
	}

	void createLayout() {
		boolean first = true;
		Vector<Integer> al = new Vector<Integer>();
		String url = TaskScreen.getUrlText();
		int idx = 0;

		while (idx < url.length()) {
			url = url.substring(idx);
			idx = url.indexOf("{");
			if (idx == -1) {
				if (first) {
					TaskScreen.setErrorMessage("There are no other variables to choose from");
					addB.setEnabled(false);
					return;
				}
				break;
			}
			first = false;
			idx += 3; // {n,
			StringBuffer sb = new StringBuffer();
			char c = url.charAt(idx);
			while (c != ',' && c != '}') {
				sb.append(c);
				idx++;
				c = url.charAt(idx);
			}
			Integer newVal = Integer.valueOf(sb.toString());
			if (al.indexOf(newVal) == -1)
				al.add(newVal);
		}
		idCB = new JComboBox<Integer>(al);
		idCB.setMinimumSize(new Dimension(10, 20));
		idCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		getCentrePanel().add(new JLabel("ID to copy:"));
		getCentrePanel().add(idCB);
	}

	void convert() {
		id = (Integer) idCB.getSelectedItem();
	}

	@Override
	public String toStringBraces() {
		convert();
		return "{c," + id + "}";
	}

	@Override
	public boolean setParameters() {
		return true;
	}
}
