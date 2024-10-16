package uk.co.bigsoft.filesucker.zjunk.looper.number;

public class NumberLooperParms {

	private Integer from;
	private Integer to;
	private Integer pad;

	NumberLooperParms(String x) {
		String t[] = x.split(",");
		if (t.length < 3)
			return;

		from = Integer.valueOf(t[0]);
		to = Integer.valueOf(t[1]);
		pad = Integer.valueOf(t[2]);
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getTo() {
		return to;
	}

	public Integer getPadding() {
		return pad;
	}
}
