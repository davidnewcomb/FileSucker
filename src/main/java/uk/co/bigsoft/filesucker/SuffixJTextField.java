package uk.co.bigsoft.filesucker;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.TransferHandler;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class SuffixJTextField extends JTextField {
	public SuffixJTextField(TransferHandler transferHandler) {
		setDragEnabled(true);
		setTransferHandler(transferHandler);
		setMinimumSize(new Dimension(10, 20));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		SuffixKeyListener k = new SuffixKeyListener(this);
		addKeyListener(k);
	}
}

class SuffixKeyListener implements KeyListener {
	// private SuffixJTextField m_textfield ;

	SuffixKeyListener(SuffixJTextField t) {
		// m_textfield = t ;
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println ("pressed") ;
	}

	public void keyReleased(KeyEvent e) {
		// System.out.println ("released") ;
	}

	public void keyTyped(KeyEvent e) {
		// System.out.println ("typed") ;
		TaskScreen.changed();
	}

}
