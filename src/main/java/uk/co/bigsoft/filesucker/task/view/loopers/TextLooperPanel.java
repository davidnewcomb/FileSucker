package uk.co.bigsoft.filesucker.task.view.loopers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.task.view.UpDownTextJTextField;

public class TextLooperPanel extends JPanel implements ILooperPanel {

	private UpDownTextJTextField toTF = new UpDownTextJTextField();
	private UpDownTextJTextField fromTF = new UpDownTextJTextField();
	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public TextLooperPanel() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		add(looperTitle);
		add(new JLabel("From:"));
		add(fromTF);
		add(new JLabel("To:"));
		add(toTF);
	}

	@Override
	public String toStringBraces() {

		String from = fromTF.getVal();
		String to = toTF.getVal();

		String guts = String.format("%s,%s", from, to);

		String full = String.format("{%s,%d,%s}", LooperCmd.L_TEXT, looperId, guts);
		return full;
	}

	@Override
	public void fill(List<String> parameters) {
		looperId = Integer.parseInt(parameters.get(1));
		looperTitle.setText("Text: " + looperId);

		String from = parameters.get(2);
		String to = parameters.get(3);

		fromTF.setStartingValue(from);
		toTF.setStartingValue(to);
	}
}
