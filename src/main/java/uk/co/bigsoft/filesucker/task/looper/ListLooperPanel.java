package uk.co.bigsoft.filesucker.task.looper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ListLooperPanel extends JPanel implements ILooperPanel {

	public JTextArea listTextArea = new JTextArea();
	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public ListLooperPanel() {
		super(new BorderLayout());

		add(looperTitle, BorderLayout.NORTH);

		listTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		JScrollPane jsp = new JScrollPane(listTextArea);
		jsp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(jsp, BorderLayout.CENTER);
	}

	@Override
	public void fill(List<String> parameters) {
		looperId = Integer.parseInt(parameters.get(1));
		looperTitle.setText("List: " + looperId);
		ArrayList<String> params = new ArrayList<>(parameters);
		params.remove(0); // command name
		params.remove(0); // looperId
		String txt = String.join("\n", params);
		listTextArea.setText(txt);
	}

	@Override
	public String toStringBraces() {
		List<String> items = listTextArea.getText().lines().map(s -> s.trim()).filter(f -> !"".equals(f)).sorted()
				.collect(Collectors.toList());

		StringBuilder sb = new StringBuilder("{L,");
		sb.append(looperId);
		if (items.size() > 0) {
			String joinItems = String.join(",", items);
			sb.append(",");
			sb.append(joinItems);
		}
		sb.append("}");
		return sb.toString();
	}

}
