package uk.co.bigsoft.filesucker.tools;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface MousePressListener extends MouseListener {

	@Override
	default void mouseClicked(MouseEvent arg0) { /* empty */
	}

	@Override
	default void mouseReleased(MouseEvent arg0) { /* empty */
	}

	@Override
	default void mouseEntered(MouseEvent arg0) { /* empty */
	}

	@Override
	default void mouseExited(MouseEvent arg0) { /* empty */
	}
}
