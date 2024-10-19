package uk.co.bigsoft.filesucker.task.looper;

import java.util.List;

public class LooperCmd {

	public static final String L_NUMBER = "N";
	public static final String L_TEXT = "T";
	public static final String L_LIST = "L";
	public static final String L_COPY = "C";
	public static final String L_FIXED = "F";

	public static boolean isLooperText(String s) {
		return s.startsWith("{") && s.endsWith("}");
	}
	
	public static String getLooperGuts(String s) {
		return s.substring(1, s.length() - 2);
	}
	
	public static List<String> getLooperGutsList(String s) {
		String[] gutsAr = getLooperGutsArray(s);
		return List.of(gutsAr);
	}

	public static String[] getLooperGutsArray(String s) {
		String guts = getLooperGuts(s);
		return guts.split(",");
	}
	
	public static String getLooperReduced(String s) {
		String[] l = getLooperGutsArray(s);
		return "{" + l[2] + "}";
	}

}
