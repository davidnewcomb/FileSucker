package uk.co.bigsoft.filesucker;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import javax.swing.text.JTextComponent;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

// TODO - Refactor so that combo box is a list of classes

public class HistoryJComboBox extends JComboBox<String> {

	private static final String clearHistory = "--Clear history--";

	private static final String culHistory = "--Cul history--";

	private static final String prefix = "hjcb_";

	public static final String ITEM_SEPERATOR = "\t";

	public static final String ITEM_SUB_SEPERATOR = ";";

	private String preferenceName = null;

	private Preferences preferences = null;

	private String p_items = null;

	/**
	 * Constructor:
	 * 
	 * @param preferenceName Preference name to store history
	 */
	public HistoryJComboBox(String pn) {
		this(null, pn, new String[0]);
	}

	/**
	 * Constructor:
	 * 
	 * @param preferenceName Preference name to store history
	 * @param start_array    Array to start with
	 */
	public HistoryJComboBox(String pn, String[] start_array) {
		this(null, pn, start_array);
	}

	public HistoryJComboBox(String pn, TransferHandler transferHandler) {
		this(transferHandler, pn, new String[0]);
	}

	public HistoryJComboBox(TransferHandler transferHandler, String pn, String[] start_array) {
		super(start_array);
		init(pn);

		JTextComponent e = (JTextComponent) getEditor().getEditorComponent();
		e.setDragEnabled(true);
		e.setTransferHandler(transferHandler);
	}

	/**
	 * init: Sets up all the default values for the JComboBox
	 * 
	 * @param pn Preference name to store history
	 */
	private void init(String pn) {
		preferenceName = prefix + pn;
		String[] items = loadPrefs();

		// Load history from preferences
		for (int i = 0; i < items.length; ++i)
			addItem(items[i]);

		addItem(clearHistory);
		addItem(culHistory);
		setEditable(true);
		addActionListener(this);
	}

	/**
	 * loadPrefs: Loads preferences from user node
	 * 
	 * @return String[] of items for the list
	 */
	private String[] loadPrefs() {
		preferences = Preferences.userNodeForPackage(HistoryJComboBox.class);
		p_items = preferences.get(preferenceName, "");
		String[] items = p_items.split(ITEM_SEPERATOR);
		TreeSet<String> tp = new TreeSet<String>();
		String s;
		String t[];
		for (int i = 0; i < items.length; ++i) {
			t = items[i].split(ITEM_SUB_SEPERATOR);
			s = t[0];

			if ("".equals(s))
				continue;
			if (s.charAt(s.length() - 1) == File.separatorChar)
				s = s.substring(0, s.length() - 1);
			tp.add(s);
		}
		items = tp.toArray(new String[tp.size()]);
		return items;
	}

	/**
	 * wipePrefs: Clears the list
	 */
	private void wipePrefs() {
		removeAllItems();
		addItem("");
		addItem(clearHistory);
		addItem(culHistory);
		preferences.put(preferenceName, "");
		p_items = "";
	}

	/**
	 * wipePrefs: Clears the list
	 */
	private void culPrefs() {
		removeAllItems();

		p_items = preferences.get(preferenceName, "");
		String[] items = p_items.split(ITEM_SEPERATOR);
		TreeSet<HistoryJComboBoxLife> tp = new TreeSet<HistoryJComboBoxLife>();
		HistoryJComboBoxLife c;
		for (int idx = 0; idx < items.length; ++idx) {
			c = new HistoryJComboBoxLife(items[idx]);
			tp.add(c);
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

	/**
	 * savePrefs: Adds the parameter item or if item is null add the currently
	 * selected entry to the history.
	 * 
	 * @param item Item to be added. If null, then current is used
	 */
	public void savePrefs(String item) {
		String value;
		boolean notInList = false;
		if (item == null)
			value = getSelectedName();
		else
			value = item;
		if (value == null || value.equals(""))
			return;

		if (p_items.equals("")) {
			p_items = value + ITEM_SUB_SEPERATOR + System.currentTimeMillis();
			notInList = false;
		} else {
			// Replace selected item as the first in the list
			StringBuffer sb = new StringBuffer(value);

			String[] items = p_items.split(ITEM_SEPERATOR);
			for (int i = 0; i < items.length; ++i) {
				HistoryJComboBoxLife x = new HistoryJComboBoxLife(items[i]);
				if (x.getItem().equals(value)) {
					notInList = true;
					continue;
				}
				sb.append(ITEM_SEPERATOR);
				sb.append(x.toString());
			}
			p_items = sb.toString();
		}

		if (notInList == false)
			addItem(value);

		if (p_items.length() >= Preferences.MAX_VALUE_LENGTH) {
			// TODO do something proper here
			System.out.println(
					"HistoryJCombo:" + preferenceName + " " + p_items.length() + " >= " + Preferences.MAX_VALUE_LENGTH);
			culPrefs();
			TaskScreen.setErrorMessage("History automatically culled");
			return;
		}
		preferences.put(preferenceName, p_items);
	}

	/**
	 * setHistory: Sets the maximum number of entries in the history.
	 * 
	 * @param num Max number of history entries, 0 mean unlimited
	 */
	// public void setHistory (int num)
	// {
	// maxHistory = num ;
	// }
	/**
	 * actionPerformed: Required to intercept clicking on "Clear History"
	 * 
	 * @param e what has happend
	 */
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

	/**
	 * getSelectedName: Required to intercept clicking on "Clear History"
	 * 
	 * @return Selected trimmed item
	 */
	public String getSelectedName() {
		return super.getSelectedItem().toString().trim();
	}

}

class HistoryJComboBoxLife implements Comparable<HistoryJComboBoxLife> {
	private long age;

	private String item;

	public HistoryJComboBoxLife(String txt) {
		String[] items = txt.split(HistoryJComboBox.ITEM_SUB_SEPERATOR);
		item = items[0];
		if (items.length == 1)
			age = 0;
		else {
			try {
				age = Long.valueOf(items[1]);
			} catch (NumberFormatException e) {
				age = 0;
			}
		}
	}

	public void setAge(long x) {
		age = x;
	}

	public long getAge() {
		return age;
	}

	public void setItem(String x) {
		item = x;
	}

	public String getItem() {
		return item;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer(item);
		s.append(HistoryJComboBox.ITEM_SUB_SEPERATOR);
		s.append(age);
		return s.toString();
	}

	public int compareTo(HistoryJComboBoxLife o) {
		int x = (int) (age - o.age);
		if (x == 0) {
			return item.compareTo(o.item);
		}

		return x;

	}
}
