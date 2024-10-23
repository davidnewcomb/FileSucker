package uk.co.bigsoft.filesucker.task.view.loopers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.task.view.UpDownNumberJTextField;

public class NumberLooperPanel extends JPanel implements ILooperPanel {

	private UpDownNumberJTextField toTF = new UpDownNumberJTextField();
	private UpDownNumberJTextField padTF = new UpDownNumberJTextField();
	private UpDownNumberJTextField fromTF = new UpDownNumberJTextField();

	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public NumberLooperPanel() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		padTF.setMinimumSize(new Dimension(10, 20));
		padTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		add(looperTitle);
		add(new JLabel("From:"));
		add(fromTF);
		add(new JLabel("To:"));
		add(toTF);
		add(new JLabel("Pad:"));
		add(padTF);
	}

	@Override
	public String toStringBraces() {

		int from = fromTF.getVal();
		int to = toTF.getVal();
		int pad = padTF.getVal();

		String guts = String.format("%d,%d,%d", from, to, pad);

		String full = String.format("{%s,%d,%s}", LooperCmd.L_NUMBER, looperId, guts);
		return full;
	}

	@Override
	public void fill(List<String> parameters) {
		looperId = Integer.parseInt(parameters.get(1));
		looperTitle.setText("Number: " + looperId);

		int from = Integer.parseInt(parameters.get(2));
		int to = Integer.parseInt(parameters.get(3));
		int pad = Integer.parseInt(parameters.get(4));

		fromTF.setStartingValue(from);
		toTF.setStartingValue(to);
		padTF.setStartingValue(pad);
	}
}
