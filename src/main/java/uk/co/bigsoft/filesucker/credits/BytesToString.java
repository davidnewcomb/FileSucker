package uk.co.bigsoft.filesucker.credits;

public class BytesToString {
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
			u = " b";
			f = n;
			decimal_places = 0;
		} else if (n < DIV_MB) {
			u = " K";
			f = (float) n / (float) DIV_K;
			decimal_places = 2;
		} else if (n < DIV_GB) {
			u = " MB";
			f = (float) n / (float) DIV_MB;
			decimal_places = 3;
		} else if (n < DIV_TB) {
			u = " GB";
			f = (float) n / (float) DIV_GB;
			decimal_places = 4;
		} else if (n < DIV_PB) {
			u = " TB";
			f = (float) n / (float) DIV_TB;
			decimal_places = 5;
		}

		String fs = String.valueOf(f);
		if (decimal_places != 0) {
			int dp_pos = fs.indexOf('.');
			if (dp_pos != -1) {
				int actual_dp = fs.length() - dp_pos;
				if (actual_dp < decimal_places)
					decimal_places = actual_dp;
				fs = fs.substring(0, dp_pos + decimal_places);
			}
		}
		String dec = "" + fs + " " + u;

		return dec;
	}

}
