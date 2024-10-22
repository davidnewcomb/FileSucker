package uk.co.bigsoft.filesucker.view;

public class HistoryElement {
	public char HISTORY_SEPERATER = ':';
	private String element;
	private long time;

	public HistoryElement(String _element) {
		element = _element;
		time = System.currentTimeMillis();
	}

	public HistoryElement(String _element, String _time) {
		element = _element;
		try {
			time = Long.parseLong(_time);
		} catch (Exception e) {
			time = System.currentTimeMillis();
		}
	}

	@Override
	public String toString() {
		return element;
	}

	public String toHistoryString() {
		StringBuffer s = new StringBuffer(element);
		s.append(HISTORY_SEPERATER);
		s.append(time);
		return s.toString();
	}

	public long getTime() {
		return time;
	}

	public boolean isOlder(HistoryElement x) {
		return time < x.time;
	}
}
