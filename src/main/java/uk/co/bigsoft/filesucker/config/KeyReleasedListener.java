package uk.co.bigsoft.filesucker.config;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyReleasedListener extends KeyListener {

	@Override
	default void keyPressed(KeyEvent e) {
	}

	@Override
	default void keyTyped(KeyEvent e) {
	}

}
