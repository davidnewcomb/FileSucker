package uk.co.bigsoft.filesucker.zjunk.looper.text;

import java.awt.Dimension;

import javax.swing.JLabel;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.HistoryDropDown;
import uk.co.bigsoft.filesucker.MenuButton;
import uk.co.bigsoft.filesucker.MenuButtonListOwner;
import uk.co.bigsoft.filesucker.MenuButtonListener;
import uk.co.bigsoft.filesucker.UpDownTextJTextField;
import uk.co.bigsoft.filesucker.zjunk.looper.Looper;

public class TextLooper extends Looper implements MenuButtonListOwner {
	String from;

	String to;

	UpDownTextJTextField fromTF, toTF;

	MenuButton history;

	public TextLooper(String sel) {
		super(sel);
		if (setParameters() == false) {
			from = "a"; // FileSucker.configData.getTextFrom();
			to = "z"; // FileSucker.configData.getTextTo();
		}
		createLayout();
	}

	private void createLayout() {
		// fromTF = new UpDownTextJTextField(from);
		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		// toTF = new UpDownTextJTextField(to);
		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		history = new MenuButton(this);
		history.setMinimumSize(new Dimension(10, 20));
		history.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		history.addMenuButtonListener(new MenuButtonListener() {
			public void changed(String newText) {
				TextLooperParms p = new TextLooperParms(newText);
				fromTF.setValue(p.getFrom());
				toTF.setValue(p.getTo());
			}
		});

		getCentrePanel().add(new JLabel("From:"));
		getCentrePanel().add(fromTF);
		getCentrePanel().add(new JLabel("To:"));
		getCentrePanel().add(toTF);
		getCentrePanel().add(new JLabel("History:"));
		getCentrePanel().add(history);

	}

	private void convert() {
		from = fromTF.toString();
		to = toTF.toString();
	}

	@Override
	public String toStringBraces() {
		convert();

		StringBuffer s = new StringBuffer();
		s.append(from.toString());
		s.append(",");
		s.append(to.toString());
		history.addEntry(s.toString());

		s = new StringBuffer();
		s.append("{t,");
		s.append(index.toString());
		s.append(",");
		s.append(from.toString());
		s.append(",");
		s.append(to.toString());
		s.append("}");
		return s.toString();
	}

	@Override
	public boolean setParameters() {
		if (parameters.length == 0 || parameters[0].equals("t") == false)
			return false;

		from = parameters[2];
		to = parameters[3];

		return true;
	}

	public void setList(HistoryDropDown l) {
		// FileSucker.configData.setTextLooperHistory(l);
	}

	public HistoryDropDown getList() {
		return new HistoryDropDown((a, b) -> a.toHistoryString().compareTo(b.toHistoryString()));
		// return FileSucker.configData.getTextLooperHistory();
	}

}
