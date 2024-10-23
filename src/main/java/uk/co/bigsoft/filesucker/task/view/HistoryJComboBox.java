package uk.co.bigsoft.filesucker.task.view;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryJComboBox extends JComboBox<String> {

	private static Logger L = LoggerFactory.getLogger(HistoryJComboBox.class);

	private static final String clearHistory = "--Clear history--";
	private static final String culHistory = "--Cul history--";
	private static final String prefix = "hjcb_";
	public static final String ITEM_SEPERATOR = "\t";
	public static final String ITEM_SUB_SEPERATOR = ";";

	private String preferenceName = null;
	private Preferences preferences = null;
	private String p_items = null;

	public HistoryJComboBox(String pn) {
		init(pn);
	}

	private void init(String pn) {
		preferenceName = prefix + pn;
		String[] items = loadPrefs();

		// Load history from preferences
		for (String item : items) {
			addItem(item);
		}

		addItem(clearHistory);
		addItem(culHistory);
		setEditable(true);
		addActionListener(this);
	}

	private String[] loadPrefs() {
		preferences = Preferences.userNodeForPackage(HistoryJComboBox.class);
		p_items = preferences.get(preferenceName, "");
		String[] items = p_items.split(ITEM_SEPERATOR);
		TreeSet<String> tp = new TreeSet<String>();
		for (String item : items) {
			String[] t = item.split(ITEM_SUB_SEPERATOR);
			String s = t[0];

			if ("".equals(s)) {
				continue;
			}
			if (s.charAt(s.length() - 1) == File.separatorChar) {
				s = s.substring(0, s.length() - 1);
			}
			tp.add(s);
		}
		items = tp.toArray(new String[tp.size()]);
		return items;
	}

	private void wipePrefs() {
		removeAllItems();
		addItem("");
		addItem(clearHistory);
		addItem(culHistory);
		preferences.put(preferenceName, "");
		p_items = "";
	}

	private void culPrefs() {
		removeAllItems();

		p_items = preferences.get(preferenceName, "");
		String[] items = p_items.split(ITEM_SEPERATOR);
		TreeSet<HistoryJComboBoxLife> tp = new TreeSet<HistoryJComboBoxLife>();
		for (String item : items) {
			tp.add(new HistoryJComboBoxLife(item));
		}

		int cul = tp.size() / 2;
		StringBuffer sb = new StringBuffer();
		for (Iterator<HistoryJComboBoxLife> it = tp.iterator(); it.hasNext() && cul > 0; --cul) {
			HistoryJComboBoxLife v = it.next();
			addItem(v.getItem());
			sb.append(v.toString());
			sb.append(HistoryJComboBox.ITEM_SEPERATOR);
		}

		addItem(clearHistory);
		addItem(culHistory);

		p_items = sb.toString();
		preferences.put(preferenceName, p_items);
	}

	public void savePrefs(String item) {
		String value;
		boolean notInList = false;
		value = item == null ? getSelectedName() : item;
		if (value == null || value.equals("")) {
			return;
		}

		if (p_items.equals("")) {
			p_items = value + ITEM_SUB_SEPERATOR + System.currentTimeMillis();
			notInList = false;
		} else {
			// Replace selected item as the first in the list
			StringBuffer sb = new StringBuffer(value);

			String[] items = p_items.split(ITEM_SEPERATOR);
			for (String s : items) {
				HistoryJComboBoxLife x = new HistoryJComboBoxLife(s);
				if (x.getItem().equals(value)) {
					notInList = true;
					continue;
				}
				sb.append(ITEM_SEPERATOR);
				sb.append(x.toString());
			}
			p_items = sb.toString();
		}

		if (notInList == false) {
			addItem(value);
		}

		if (p_items.length() >= Preferences.MAX_VALUE_LENGTH) {
			// TODO do something proper here
			L.debug("HistoryJCombo:" + preferenceName + " " + p_items.length() + " >= " + Preferences.MAX_VALUE_LENGTH);
			culPrefs();
			L.debug("History automatically culled");
			return;
		}
		preferences.put(preferenceName, p_items);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String selected = getSelectedName();
		if (clearHistory.equals(selected)) {
			if (JOptionPane.showConfirmDialog(this, "Are you sure you want to clear history?", "Clear History",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				wipePrefs();
				return;
			}
			setSelectedIndex(0);
		} else if (culHistory.equals(selected)) {
			if (JOptionPane.showConfirmDialog(this, "Are you sure you want to cul history?", "Cul History",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				culPrefs();
				return;
			}
			setSelectedIndex(0);
		}
	}

	public String getSelectedName() {
		return super.getSelectedItem().toString().trim();
	}
}
