package uk.co.bigsoft.filesucker.zjunk.sucker_types;

public class LabelSuckerType extends SuckerType {

	public LabelSuckerType(String f) {
		super(f);
	}

	@Override
	public int numberOfIterations() {
		return 1;
	}

	@Override
	public String indexOf(int idx) {
		return format;
	}
}
