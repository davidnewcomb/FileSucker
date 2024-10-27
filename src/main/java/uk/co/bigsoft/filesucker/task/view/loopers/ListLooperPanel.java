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

		String prefix = items.stream().reduce((a, b) -> {
			int i = 0;
			for (; i < a.length(); ++i) {
				if (a.charAt(i) != b.charAt(i)) {
					break;
				}
			}
			return a.substring(0, i);
		}).get();

		if (!"".equals(prefix)) {
			final int prefixIdx = prefix.length();
			items = items.stream().map(s -> s.substring(prefixIdx)).collect(Collectors.toList());
		}
		String suffix = items.stream().reduce((a, b) -> {
			int enda = a.length() - 1;
			int endb = b.length() - 1;
			int ia = enda;
			int ib = endb;
			for (; ia != -1 && ib != -1; --ia, --ib) {
				if (a.charAt(ia) != b.charAt(ib)) {
					break;
				}
			}
			String s = a.substring(ia + 1, enda + 1);
			return s;
		}).get();

		if (!"".equals(suffix)) {
			final int suffixIdx = suffix.length();
			items = items.stream().map(s -> s.substring(0, s.length() - suffixIdx)).collect(Collectors.toList());
		}

		StringBuilder sb = new StringBuilder();
		String guts = String.join(",", items);

		sb.append(prefix);
		sb.append("{");
		sb.append(LooperCmd.L_LIST);
		sb.append(",");
		sb.append(looperId);
		sb.append(",");
		sb.append(guts);
		sb.append("}");
		sb.append(suffix);
		return sb.toString();
	}

}
