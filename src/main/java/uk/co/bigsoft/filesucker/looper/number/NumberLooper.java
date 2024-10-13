package uk.co.bigsoft.filesucker.looper.number;

import java.awt.Dimension;

import javax.swing.JLabel;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.HistoryDropDown;
import uk.co.bigsoft.filesucker.MenuButton;
import uk.co.bigsoft.filesucker.MenuButtonListOwner;
import uk.co.bigsoft.filesucker.MenuButtonListener;
import uk.co.bigsoft.filesucker.UpDownNumberJTextField;
import uk.co.bigsoft.filesucker.looper.Looper;

public class NumberLooper extends Looper implements MenuButtonListOwner {
	public Integer from;

	public Integer to;

	public Integer pad;

	public UpDownNumberJTextField toTF;

	public UpDownNumberJTextField padTF;

	public UpDownNumberJTextField fromTF;

	public MenuButton history;

	public NumberLooper(String sel) {
		super(sel);
		if (setParameters() == false) {
			try {
				int s = Integer.parseInt(selectedUrl);
				from = new Integer(s);
			} catch (NumberFormatException nfe) {
				from = new Integer(FileSucker.configData.getNumberFrom());
			}
			if (from.intValue() > 1)
				to = new Integer(from.intValue() + FileSucker.configData.getNumberTo());
			else
				to = new Integer(FileSucker.configData.getNumberTo());
			pad = Integer.valueOf(selectedUrl.length());
		}
		createLayout();
	}

	private void createLayout() {
		fromTF = new UpDownNumberJTextField(from.intValue());
		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		toTF = new UpDownNumberJTextField(to.intValue());
		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		padTF = new UpDownNumberJTextField(pad.intValue());
		padTF.setMinimumSize(new Dimension(10, 20));
		padTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		history = new MenuButton(this);
		history.setMinimumSize(new Dimension(10, 20));
		history.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		history.addMenuButtonListener(new MenuButtonListener() {
			public void changed(String newText) {
				NumberLooperParms p = new NumberLooperParms(newText);
				fromTF.setValue(p.getFrom());
				toTF.setValue(p.getTo());
				padTF.setValue(p.getPadding());
			}

		});

		getCentrePanel().add(new JLabel("From:"));
		getCentrePanel().add(fromTF);
		getCentrePanel().add(new JLabel("To:"));
		getCentrePanel().add(toTF);
		getCentrePanel().add(new JLabel("Pad:"));
		getCentrePanel().add(padTF);
		getCentrePanel().add(new JLabel("History:"));
		getCentrePanel().add(history);

	}

	private void convert() {
		from = new Integer(fromTF.toString());
		to = new Integer(toTF.toString());
		pad = new Integer(padTF.toString());

		// Make sure from and to are in the correct order
		if (to.intValue() < from.intValue()) {
			Integer i = from;
			from = to;
			to = i;
		}
	}

	@Override
	public String toStringBraces() {
		convert();

		StringBuffer s = new StringBuffer();
		s.append(from.toString());
		s.append(",");
		s.append(to.toString());
		s.append(",");
		s.append(pad.toString());
		history.addEntry(s.toString());

		s = new StringBuffer();
		s.append("{n,");
		s.append(index.toString());
		s.append(",");
		s.append(from.toString());
		s.append(",");
		s.append(to.toString());
		s.append(",");
		s.append(pad.toString());
		s.append("}");
		return s.toString();
	}

	@Override
	public boolean setParameters() {
		if (parameters.length == 0 || parameters[0].equals("n") == false)
			return false;

		from = new Integer(Integer.parseInt(parameters[2]));
		to = new Integer(Integer.parseInt(parameters[3]));
		pad = new Integer(Integer.parseInt(parameters[4]));

		// Make sure from and to are in the correct order
		if (to.intValue() < from.intValue()) {
			Integer i = from;
			from = to;
			to = i;
		}

		return true;
	}

	public HistoryDropDown getList() {
		return FileSucker.configData.getNumberLooperHistory();
	}

	public void setList(HistoryDropDown l) {
		FileSucker.configData.setNumberLooperHistory(l);
	}
}
