package uk.co.bigsoft.filesucker.transfer.suckertype;

import uk.co.bigsoft.filesucker.task.view.loopers.LooperCmd;

public class CopySuckerType extends SuckerType {

	CopySuckerType(int looperId) {
		super(looperId);
		things.add("");
	}

	@Override
	public String toStringBraces() {
		return String.format("{%s,%d}", LooperCmd.L_COPY, getId());
	}

}
