package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

public class TextSuckerType extends SuckerType {

	TextSuckerType(int looperId, List<String> params) {
		super(looperId);

		String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int from = letters.indexOf(params.get(0));
		int to = letters.indexOf(params.get(1));
		
		for (int i = from ; i <= to ; ++i) {
			things.add(String.valueOf(letters.charAt(i)));
		}
	}

}
