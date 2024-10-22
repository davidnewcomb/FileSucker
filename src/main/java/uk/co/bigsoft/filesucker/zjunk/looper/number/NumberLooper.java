package uk.co.bigsoft.filesucker.zjunk.looper.number;

import java.awt.Dimension;

import javax.swing.JLabel;

import uk.co.bigsoft.filesucker.MenuButtonListOwner;
import uk.co.bigsoft.filesucker.task.view.UpDownNumberJTextField;
import uk.co.bigsoft.filesucker.view.HistoryDropDown;
import uk.co.bigsoft.filesucker.zjunk.MenuButton;
import uk.co.bigsoft.filesucker.zjunk.MenuButtonListener;
import uk.co.bigsoft.filesucker.zjunk.looper.Looper;

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
				from = Integer.valueOf(selectedUrl);
			} catch (NumberFormatException nfe) {
				from = Integer.valueOf(0); // FileSucker.configData.getNumberFrom());
			}
			if (from.intValue() > 1) {
				to = Integer.valueOf(from.intValue() + 0); // FileSucker.configData.getNumberTo());
			} else {
				to = Integer.valueOf(0); // FileSucker.configData.getNumberTo());
			}
			pad = Integer.valueOf(selectedUrl.length());
		}
		createLayout();
	}

	private void createLayout() {
		// fromTF = new UpDownNumberJTextField(from.intValue());
		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		// toTF = new UpDownNumberJTextField(to.intValue());
		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		// padTF = new UpDownNumberJTextField(pad.intValue());
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
		from = Integer.valueOf(fromTF.toString());
		to = Integer.valueOf(toTF.toString());
		pad = Integer.valueOf(padTF.toString());

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

		from = Integer.valueOf(parameters[2]);
		to = Integer.valueOf(parameters[3]);
		pad = Integer.valueOf(parameters[4]);

		// Make sure from and to are in the correct order
		if (to.intValue() < from.intValue()) {
			Integer i = from;
			from = to;
			to = i;
		}

		return true;
	}

	public HistoryDropDown getList() {
		return new HistoryDropDown((a, b) -> a.toHistoryString().compareTo(b.toHistoryString()));
	}

	public void setList(HistoryDropDown l) {
		// FileSucker.configData.setNumberLooperHistory(l);
	}
}
