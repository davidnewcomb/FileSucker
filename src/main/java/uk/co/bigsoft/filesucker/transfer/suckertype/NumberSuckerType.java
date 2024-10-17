package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

public class NumberSuckerType extends SuckerType {

	NumberSuckerType(int looperId, List<String> params) {
		super(looperId);
		int from = Integer.parseInt(params.get(0));
		int to = Integer.parseInt(params.get(1));
		String padFormat = "%0" + params.get(2) + "d";
		
		for (int i = from ; i <= to ; ++i) {
			things.add(String.format(padFormat, i));
		}
	}

}
