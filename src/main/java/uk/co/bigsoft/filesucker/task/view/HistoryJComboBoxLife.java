package uk.co.bigsoft.filesucker.task.view;

public class HistoryJComboBoxLife implements Comparable<HistoryJComboBoxLife> {
	private long age;
	private String item;

	public HistoryJComboBoxLife(String txt) {
		String[] items = txt.split(HistoryJComboBox.ITEM_SUB_SEPERATOR);
		item = items[0];
		if (items.length == 1) {
			age = 0;
		} else {
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
