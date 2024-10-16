package uk.co.bigsoft.filesucker.zjunk.looper.text;

public class TextLooperParms {
	private String from;

	private String to;

	TextLooperParms(String x) {
		String t[] = x.split(",");
		if (t.length < 2)
			return;

		from = t[0];
		to = t[1];
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
