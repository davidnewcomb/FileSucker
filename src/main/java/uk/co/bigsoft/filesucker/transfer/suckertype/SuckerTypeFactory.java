package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.ArrayList;
import java.util.List;

import uk.co.bigsoft.filesucker.task.looper.LooperCmd;
import uk.co.bigsoft.filesucker.task.looper.LooperId;

public class SuckerTypeFactory {

	public SuckerTypeFactory() {
		//
	}

	public SuckerType create(String suckerString) {
		if (suckerString.charAt(0) != '{' || suckerString.charAt(suckerString.length() - 1) != '}') {
			return new FixedSuckerType(-LooperId.getNext(), List.of(suckerString));
		}

		String guts = suckerString.substring(1, suckerString.length() - 1);

		List<String> unmodifiableParams = List.of(guts.split(","));
		List<String> params = new ArrayList<String>(unmodifiableParams);

		String type = params.remove(0);
		int id = Integer.parseInt(params.remove(0));

		switch (type) {
		case LooperCmd.L_NUMBER: {
			return new NumberSuckerType(id, params);
		}
		case LooperCmd.L_TEXT: {
			return new TextSuckerType(id, params);
		}
		case LooperCmd.L_LIST: {
			return new ListSuckerType(id, params);
		}
		case LooperCmd.L_COPY: {
			return new CopySuckerType(id);
		}
		case LooperCmd.L_FIXED: {
			return new FixedSuckerType(id, params);
		}
		}
		throw new RuntimeException("SuckerTypeFactory: Bad looper");
	}
}
