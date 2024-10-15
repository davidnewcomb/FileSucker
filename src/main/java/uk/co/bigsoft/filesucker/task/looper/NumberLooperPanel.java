package uk.co.bigsoft.filesucker.task.looper;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

// import uk.co.bigsoft.filesucker.MenuButton;
import uk.co.bigsoft.filesucker.UpDownNumberJTextField;


public class NumberLooperPanel extends JPanel implements ILooperPanel { //, MenuButtonListOwner {

	private UpDownNumberJTextField toTF = new UpDownNumberJTextField();
	private UpDownNumberJTextField padTF = new UpDownNumberJTextField();
	private UpDownNumberJTextField fromTF = new UpDownNumberJTextField();
	//private MenuButton history;
	private JLabel looperTitle = new JLabel();
	private int looperId = -1;

	public NumberLooperPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		//fromTF = new UpDownNumberJTextField();
		fromTF.setMinimumSize(new Dimension(10, 20));
		fromTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		//toTF = new UpDownNumberJTextField();
		toTF.setMinimumSize(new Dimension(10, 20));
		toTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

		//padTF = new UpDownNumberJTextField();
		padTF.setMinimumSize(new Dimension(10, 20));
		padTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

//		history = new MenuButton(this);
//		history.setMinimumSize(new Dimension(10, 20));
//		history.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		
//		history.addMenuButtonListener(new MenuButtonListener() {
//			public void changed(String newText) {
//				NumberLooperParms p = new NumberLooperParms(newText);
//				fromTF.setValue(p.getFrom());
//				toTF.setValue(p.getTo());
//				padTF.setValue(p.getPadding());
//			}
//
//		});

		add(looperTitle);
		add(new JLabel("From:"));
		add(fromTF);
		add(new JLabel("To:"));
		add(toTF);
		add(new JLabel("Pad:"));
		add(padTF);
//		add(new JLabel("History:"));
//		add(history);

	}

	@Override
	public String toStringBraces() {

		int from = fromTF.getVal();
		int to = toTF.getVal();
		int pad = padTF.getVal();
		
		String guts = String.format("%d,%d,%d", from, to, pad);
		//history.addEntry(s.toString());

		String full = String.format("{%s,%d,%s}", LooperCmd.L_NUMBER, looperId, guts);
		return full;
	}

	@Override
	public void fill(List<String> parameters) {
		looperId = Integer.parseInt(parameters.get(1));
		looperTitle.setText("Number: " + looperId);
		
		int from = Integer.parseInt(parameters.get(2));
		int to = Integer.parseInt(parameters.get(3));
		int pad = Integer.parseInt(parameters.get(4));
		
		fromTF.setStartingValue(from);
		toTF.setStartingValue(to);
		padTF.setStartingValue(pad);
	}
	
//	public HistoryDropDown getList() {
//		return FileSucker.configData.getNumberLooperHistory();
//	}
//
//	public void setList(HistoryDropDown l) {
//		FileSucker.configData.setNumberLooperHistory(l);
//	}

}
