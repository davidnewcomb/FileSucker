package uk.co.bigsoft.filesucker.task.view.loopers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import uk.co.bigsoft.filesucker.stream.StringFi;

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
		List<String> items = listTextArea.getText().lines().map(StringFi.trim).filter(StringFi.notEmpty)
				.collect(Collectors.toSet()).stream().sorted().collect(Collectors.toList());

		if (items.size() == 0) {
			return String.format("{%s,%d}", LooperCmd.L_LIST, looperId);
		}

		// Can only be done when listlooper closes
		String first = items.get(0);
		String common = "";
		if (first.contains("/")) {
			int lastSlash = first.lastIndexOf("/");
			String pref = first.substring(0, lastSlash + 1);
			boolean hasSamePrefix = items.stream().allMatch(s -> s.startsWith(pref));
			if (hasSamePrefix) {
				items = items.stream().map(s -> s.substring(pref.length())).collect(Collectors.toList());
				common = pref;
			}
		}

		StringBuilder sb = new StringBuilder();
		String guts = String.join(",", items);

		sb.append(common);
		sb.append("{");
		sb.append(LooperCmd.L_LIST);
		sb.append(",");
		sb.append(looperId);
		sb.append(",");
		sb.append(guts);
		sb.append("}");
		return sb.toString();
	}

}
