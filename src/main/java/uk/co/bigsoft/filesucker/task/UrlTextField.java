package uk.co.bigsoft.filesucker.task;

import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.tools.MousePressListener;

public class UrlTextField extends JTextField {
	public UrlTextField(TransferHandler transferHandler) {
		super();

		setDragEnabled(true);
		setTransferHandler(transferHandler);
		addMouseListener((MousePressListener) e -> mousePressed(e));
	}

	private void mousePressed(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3) {
			rightClick();
		} else {
			leftClick();
		}
	}

	private void rightClick() {
		String s = Utility.getClipboard();
		if (s != null) {
			setText(s);
		}
	}

	private void leftClick() {
		String s = getText();
		int pos = getCaretPosition();
		int len = getText().length();
		int startpos = -1, endpos = -1;

		pos--;

		// Backwards
		for (int i = pos; i >= 0; --i) {
			if (s.charAt(i) == '{') {
				startpos = i;
				break;
			} else if (s.charAt(i) == '}') {
				return;
			}
		}

		// Forwards
		if (pos == -1) {
			pos = 0;
		}
		for (int i = pos; i < len; ++i) {
			if (s.charAt(i) == '}') {
				// Move to the next gap
				endpos = i + 1;
				break;
			} else if (s.charAt(i) == '{')
				return;
		}

		if (startpos == -1 || endpos == -1) {
			return;
		}

		setSelectionStart(startpos);
		setSelectionEnd(endpos);
	}

}
