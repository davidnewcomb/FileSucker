package uk.co.bigsoft.filesucker.task;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.config.KeyReleasedListener;
import uk.co.bigsoft.filesucker.looper.Looper;
import uk.co.bigsoft.filesucker.tools.MousePressListener;


public class UrlTextField extends JTextField {
	public UrlTextField(TransferHandler transferHandler) {
		super();

		setDragEnabled(true);
		setTransferHandler(transferHandler);
		addMouseListener((MousePressListener) e -> mousePressed(e));
		addKeyListener((KeyReleasedListener) e -> keyPressed(e));
	}
	
	private void mousePressed(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3) {
			rightClick();
		} else {
			leftClick();
		}
	}
	
	private void keyPressed(KeyEvent e) {
		// System.out.println ("typed") ;
		// TODO TaskScreen.changed();

		if (!e.isAltDown()) {
			return;
		}

		if (Looper.isActive()) {
			// TODO TaskScreen.setErrorMessage("Looper is active");
			return;
		}

		char c = Character.toLowerCase(e.getKeyChar());
		switch (c) {
//		case 'c':
//			FileSucker.taskScreen.copyB.actionPerformed(null);
//			break;
//		case 'l':
//			FileSucker.taskScreen.listB.actionPerformed(null);
//			break;
//		case 'n':
//			FileSucker.taskScreen.numberB.actionPerformed(null);
//			break;
//		case 's':
//			FileSucker.taskScreen.staticB.actionPerformed(null);
//			break;
//		case 't':
//			FileSucker.taskScreen.textB.actionPerformed(null);
//			break;
		}
	}

	private void rightClick() {
		String s = Utility.getClipboard();
		if (s != null) {
			setText(s);
			// TODO TaskScreen.changed();
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

//class UrlKeyListener extends KeyAdapter {
//	@Override
//	public void keyTyped(KeyEvent e) {
//
//	}
//
//}

// TODO refactor into a general class
//class UrlMouseListener extends MouseAdapter {
//	private UrlTextField m_textfield;
//
//	UrlMouseListener(UrlTextField textfield) {
//		m_textfield = textfield;
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		int clickedButton = e.getButton();
//		if (clickedButton == 3)
//			rightClick();
//		else
//			leftClick();
//	}
//
//	private void rightClick() {
//		String s = Utility.getClipboard();
//		if (s != null) {
//			m_textfield.setText(s);
//			//TaskScreen.changed();
//		}
//	}
//
//	private void leftClick() {
//		String s = m_textfield.getText();
//		int pos = m_textfield.getCaretPosition();
//		int len = m_textfield.getText().length();
//		int startpos = -1, endpos = -1;
//
//		pos--;
//
//		// Backwards
//		for (int i = pos; i >= 0; --i) {
//			if (s.charAt(i) == '{') {
//				startpos = i;
//				break;
//			} else if (s.charAt(i) == '}')
//				return;
//		}
//
//		// Forwards
//		if (pos == -1)
//			pos = 0;
//		for (int i = pos; i < len; ++i) {
//			if (s.charAt(i) == '}') {
//				// Move to the next gap
//				endpos = i + 1;
//				break;
//			} else if (s.charAt(i) == '{')
//				return;
//		}
//
//		if (startpos == -1 || endpos == -1)
//			return;
//
//		m_textfield.setSelectionStart(startpos);
//		m_textfield.setSelectionEnd(endpos);
//	}
//
//}
