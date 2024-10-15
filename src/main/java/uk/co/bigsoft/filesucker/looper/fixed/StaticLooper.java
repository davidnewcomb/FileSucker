package uk.co.bigsoft.filesucker.looper.fixed;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.task.looper.ILooperPanel;
import uk.co.bigsoft.filesucker.task.looper.LooperCmd;

public class StaticLooper extends JPanel implements ILooperPanel { // , MenuButtonListOwner {

	private JTextField idTF = new JTextField();
	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public StaticLooper() {
		super();

		idTF = new JTextField();
		idTF.setMinimumSize(new Dimension(10, 20));
		idTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		add(looperTitle);
		add(new JLabel("From:"));
		add(idTF);
	}

	@Override
	public String toStringBraces() {
		String full = String.format("{%s,%d,%s}", LooperCmd.L_STATIC, looperId, idTF.getText());
		return full;
	}

	@Override
	public void fill(List<String> parameters) {
		looperId = Integer.parseInt(parameters.get(1));
		looperTitle.setText("Static: " + looperId);

		String stat = parameters.get(2);
		idTF.setText(stat);
	}

}
