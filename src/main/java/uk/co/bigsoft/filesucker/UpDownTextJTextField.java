package uk.co.bigsoft.filesucker;

import java.awt.event.MouseWheelEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class UpDownTextJTextField extends JSpinner {
	private JFormattedTextField textbox;

	public UpDownTextJTextField() {
		super();

		textbox = ((JSpinner.DefaultEditor) getEditor()).getTextField();
		textbox.setHorizontalAlignment(SwingConstants.LEFT);
		textbox.setEditable(true);
		
		addMouseWheelListener(e -> mouseWheelMoved(e));
	}

	public void setStartingValue(String val) {
		setModel(new SpinnerCharacterModel(val));
	}
	
	public String getVal() {
		return (String) getModel().getValue();
	}
	
	private void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		Object newVal = notches > 0 ? getModel().getNextValue() : getModel().getPreviousValue();
		if (newVal != null) {
			getModel().setValue(newVal);
		}
	}
	
	@Override
	public String toString() {
		return textbox.getText();
	}

}
