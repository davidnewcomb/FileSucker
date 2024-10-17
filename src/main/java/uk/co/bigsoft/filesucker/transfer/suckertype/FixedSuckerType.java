package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

public class FixedSuckerType extends SuckerType {

	FixedSuckerType(int looperId, List<String> params) {
		super(looperId);
		
		things.add(params.getFirst());
	}

}
