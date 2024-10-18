package uk.co.bigsoft.filesucker.zjunk.sucker_types;

public class FixedSuckerType extends SuckerType {
	private int from = 1;

	public FixedSuckerType(String f) {
		super(f);

		String t = formatTokens.nextToken();
		from = Integer.parseInt(t);
	}

	@Override
	public String indexOf(int idx) {
		from += 1;
		return Integer.toString(from);
	}

	@Override
	public int numberOfIterations() {
		return 1;
	}

}
