package uk.co.bigsoft.filesucker;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.TransferHandler;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class PrefixJTextField extends JTextField {
	public PrefixJTextField(TransferHandler transferHandler) {
		setDragEnabled(true);
		setTransferHandler(transferHandler);
		setMinimumSize(new Dimension(10, 20));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		PrefixKeyListener k = new PrefixKeyListener(this);
		addKeyListener(k);
	}
}

class PrefixKeyListener implements KeyListener {
	// private PrefixJTextField m_textfield ;

	PrefixKeyListener(PrefixJTextField t) {
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
