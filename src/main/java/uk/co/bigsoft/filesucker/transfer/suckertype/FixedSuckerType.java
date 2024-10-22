package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

import uk.co.bigsoft.filesucker.task.view.loopers.LooperCmd;

public class FixedSuckerType extends SuckerType {

	FixedSuckerType(int looperId, List<String> params) {
		super(looperId);

		things.add(params.getFirst());
	}

	@Override
	public String toStringBraces() {
		return String.format("{%s,%d,%s}", LooperCmd.L_FIXED, getId(), things.getFirst());
	}

}
