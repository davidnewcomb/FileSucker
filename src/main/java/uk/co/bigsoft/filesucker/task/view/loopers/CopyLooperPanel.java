package uk.co.bigsoft.filesucker.task.view.loopers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CopyLooperPanel extends JPanel implements ILooperPanel {
	private JComboBox<Integer> idCB = new JComboBox<Integer>();
	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public CopyLooperPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		idCB.setMinimumSize(new Dimension(10, 20));
		idCB.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		add(looperTitle);
		add(new JLabel("ID to copy:"));
		add(idCB);
	}

	public void setOptions(List<Integer> items) {
		idCB.removeAllItems();
		for (Integer i : items) {
			idCB.addItem(i);
		}
	}

	@Override
	public String toStringBraces() {
		Object id = idCB.getSelectedItem();
		String full = String.format("{%s,%d}", LooperCmd.L_COPY, id);
		return full;
	}

	@Override
	public void fill(List<String> parameters) {
		looperId = Integer.parseInt(parameters.get(1));
		looperTitle.setText("Copy: " + looperId);
		idCB.setSelectedItem(Integer.valueOf(looperId));
	}
}
