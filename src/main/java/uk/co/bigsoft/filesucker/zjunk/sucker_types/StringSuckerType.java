package uk.co.bigsoft.filesucker.zjunk.sucker_types;

public class StringSuckerType extends SuckerType // implements Iterator
{
	/*
	 * {t,buffer,from,to}
	 */
	String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	int from, to;

	StringSuckerType(String f) {
		super(f);

		String t = formatTokens.nextToken();

		t = formatTokens.nextToken();
		to = letters.indexOf(t) - 1;
	}

	@Override
	public int numberOfIterations() {
		return to - from + 1;
	}

	@Override
	public String indexOf(int idx) {
		int i = from + idx;
		String s = letters.substring(i, i + 1);
		return s;
	}

}
