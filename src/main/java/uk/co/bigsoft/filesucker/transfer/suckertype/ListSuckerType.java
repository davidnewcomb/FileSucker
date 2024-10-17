package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

public class ListSuckerType extends SuckerType {

	ListSuckerType(int looperId, List<String> params) {
		super(looperId);
		
		things.addAll(params);
	}

}
