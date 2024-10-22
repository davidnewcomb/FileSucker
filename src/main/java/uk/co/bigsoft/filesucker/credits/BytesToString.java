package uk.co.bigsoft.filesucker.credits;

public class BytesToString {
	private static final String UNIT_B = "b";
	private static final String UNIT_K = "K";
	private static final String UNIT_MB = "MB";
	private static final String UNIT_GB = "GB";
	private static final String UNIT_TB = "TB";
	private static final String UNIT_PB = "PB";

	private static final long DIV_K = 1024L;
	private static final long DIV_MB = 1024L * 1024L;
	private static final long DIV_GB = 1024L * 1024L * 1024L;
	private static final long DIV_TB = 1024L * 1024L * 1024L * 1024L;
	private static final long DIV_PB = 1024L * 1024L * 1024L * 1024L * 1024L;

	public String convert(long n) {
		String u = "";
		double f = 0.0;
		int decimal_places = 0;

		if (n < DIV_K) {
			u = UNIT_B;
			f = n;
			decimal_places = 0;
		} else if (n < DIV_MB) {
			u = UNIT_K;
			f = (float) n / (float) DIV_K;
			decimal_places = 2;
		} else if (n < DIV_GB) {
			u = UNIT_MB;
			f = (float) n / (float) DIV_MB;
			decimal_places = 3;
		} else if (n < DIV_TB) {
			u = UNIT_GB;
			f = (float) n / (float) DIV_GB;
			decimal_places = 4;
		} else if (n < DIV_PB) {
			u = UNIT_TB;
			f = (float) n / (float) DIV_TB;
			decimal_places = 5;
		} else {
			u = UNIT_PB;
			f = (float) n / (float) DIV_PB;
			decimal_places = 5;
		}

		String str = String.format("%." + decimal_places + "f " + u, f);
		return str;
	}

}
