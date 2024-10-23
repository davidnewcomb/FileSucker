package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

import uk.co.bigsoft.filesucker.task.view.loopers.LooperCmd;

public class FixedSuckerType extends SuckerType {

	FixedSuckerType(int looperId, List<String> params) {
		super(looperId);

		// getFirst only available in java > 21
		// things.add(params.getFirst()); 
		things.add(params.get(0));
	}

	@Override
	public String toStringBraces() {
		// getFirst only available in java > 21
		// return String.format("{%s,%d,%s}", LooperCmd.L_FIXED, getId(), things.getFirst());  
		return String.format("{%s,%d,%s}", LooperCmd.L_FIXED, getId(), things.get(0));
	}

}
