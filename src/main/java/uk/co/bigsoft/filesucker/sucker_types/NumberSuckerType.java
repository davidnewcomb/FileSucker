package uk.co.bigsoft.filesucker.sucker_types;

public class NumberSuckerType extends SuckerType // implements Iterator
{
	/*
	 * {n,from,to,npad} {n,buffer,from,to,npad}
	 */
	private int from, to, pad;

	NumberSuckerType(String f) {
		super(f);

		String t = formatTokens.nextToken();

		from = Integer.parseInt(t);
		t = formatTokens.nextToken();
		to = Integer.parseInt(t);

		t = formatTokens.nextToken();
		pad = Integer.parseInt(t);
	}

	private String addPadding(StringBuffer sb) {
		int initalLength = sb.length();
		if (pad > 0 && initalLength < pad) {
			for (int i = initalLength; i < pad; ++i)
				sb.insert(0, "0");
		}
		return sb.toString();
	}

	@Override
	public int numberOfIterations() {
		return to - from;
	}

	@Override
	public String indexOf(int idx) {
		StringBuffer s = new StringBuffer("" + (from + idx));
		return addPadding(s);
	}
}
