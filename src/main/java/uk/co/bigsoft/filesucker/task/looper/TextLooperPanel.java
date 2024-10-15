package uk.co.bigsoft.filesucker.task.looper;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

// import uk.co.bigsoft.filesucker.MenuButton;
import uk.co.bigsoft.filesucker.UpDownTextJTextField;

public class TextLooperPanel extends JPanel implements ILooperPanel { // , MenuButtonListOwner {

	private UpDownTextJTextField toTF = new UpDownTextJTextField();
	private UpDownTextJTextField fromTF = new UpDownTextJTextField();
	// private MenuButton history;
	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public TextLooperPanel() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// fromTF = new UpDownTextJTextField(from);
		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		// toTF = new UpDownTextJTextField(to);
		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

//		history = new MenuButton(this);
//		history.setMinimumSize(new Dimension(10, 20));
//		history.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
//		history.addMenuButtonListener(new MenuButtonListener() {
//			public void changed(String newText) {
//				TextLooperParms p = new TextLooperParms(newText);
//				fromTF.setValue(p.getFrom());
//				toTF.setValue(p.getTo());
//			}
//		});

		add(looperTitle);
		add(new JLabel("From:"));
		add(fromTF);
		add(new JLabel("To:"));
		add(toTF);
//		add(new JLabel("History:"));
//		add(history);

	}

	@Override
	public String toStringBraces() {

		String from = fromTF.getVal();
		String to = toTF.getVal();

		String guts = String.format("%s,%s", from, to);
//		history.addEntry(s.toString());

		String full = String.format("{%s,%d,%s}", LooperCmd.L_TEXT, looperId, guts);
		return full;
//		StringBuffer s = new StringBuffer();
//		s.append(from.toString());
//		s.append(",");
//		s.append(to.toString());
//
//
//		s = new StringBuffer();
//		s.append("{t,");
//		s.append(index.toString());
//		s.append(",");
//		s.append(from.toString());
//		s.append(",");
//		s.append(to.toString());
//		s.append("}");
//		return s.toString();
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

//	public void setList(HistoryDropDown l) {
//		FileSucker.configData.setTextLooperHistory(l);
//	}
//
//	public HistoryDropDown getList() {
//		return FileSucker.configData.getTextLooperHistory();
//	}

}
