package uk.co.bigsoft.filesucker.transfer.suckertype;

import java.util.List;

import uk.co.bigsoft.filesucker.task.looper.LooperCmd;

public class NumberSuckerType extends SuckerType {

	private int from;
	private int to;
	private String pad;

	NumberSuckerType(int looperId, List<String> params) {
		super(looperId);
		from = Integer.parseInt(params.get(0));
		to = Integer.parseInt(params.get(1));
		pad = params.get(2);
		String padFormat = "%0" + pad + "d";
		
		for (int i = from ; i <= to ; ++i) {
			things.add(String.format(padFormat, i));
		}
	}

	@Override
	public String toStringBraces() {
		return String.format("{%s,%d,%d,%d,%s}", LooperCmd.L_NUMBER, getId(), from, to, pad);
	}

}
