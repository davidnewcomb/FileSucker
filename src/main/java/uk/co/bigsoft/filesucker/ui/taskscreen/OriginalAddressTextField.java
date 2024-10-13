package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.Utility;

public class OriginalAddressTextField extends JTextField implements MouseListener {
	public OriginalAddressTextField() {
		super();

		setToolTipText("The original address from the which the find files was from");
		addMouseListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3)
			rightClick();
	}

	private void rightClick() {
		String s = Utility.getClipboard();
		if (s != null)
			setText(s);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//
	}

}
