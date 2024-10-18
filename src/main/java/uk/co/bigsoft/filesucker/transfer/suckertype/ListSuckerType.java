package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

import uk.co.bigsoft.filesucker.task.looper.LooperCmd;

public class ListSuckerType extends SuckerType {

	ListSuckerType(int looperId, List<String> params) {
		super(looperId);

		things.addAll(params);
	}

	@Override
	public String toStringBraces() {
		String guts = String.join(",", things);
		return String.format("{%s,%d,%s}", LooperCmd.L_LIST, getId(), guts);
	}

}
