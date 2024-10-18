package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

import uk.co.bigsoft.filesucker.task.looper.LooperCmd;

public class TextSuckerType extends SuckerType {

	private String fromChar;
	private String toChar;

	TextSuckerType(int looperId, List<String> params) {
		super(looperId);

		String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		fromChar = params.get(0);
		toChar = params.get(1);
		int from = letters.indexOf(fromChar);
		int to = letters.indexOf(toChar);
		
		for (int i = from ; i <= to ; ++i) {
			things.add(String.valueOf(letters.charAt(i)));
		}
	}

	@Override
	public String toStringBraces() {
		return String.format("{%s,%d,%s,%s}", LooperCmd.L_TEXT, getId(), fromChar, toChar);
	}
}
