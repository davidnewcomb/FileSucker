package uk.co.bigsoft.filesucker.task.view;

import java.awt.event.MouseWheelEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class UpDownNumberJTextField extends JSpinner {
	private JFormattedTextField textbox;

	public UpDownNumberJTextField() {
		super();

		textbox = ((JSpinner.DefaultEditor) getEditor()).getTextField();
		textbox.setHorizontalAlignment(SwingConstants.LEFT);
		textbox.setEditable(true);

		addMouseWheelListener(e -> mouseWheelMoved(e));
	}

	public void setStartingValue(int val) {
		setModel(new SpinnerNumberModel(val, 0, Integer.MAX_VALUE, 1));
	}

	public int getVal() {
		return ((Integer) getModel().getValue()).intValue();
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
		return textbox.getText().replaceAll(",", "");
	}

}
