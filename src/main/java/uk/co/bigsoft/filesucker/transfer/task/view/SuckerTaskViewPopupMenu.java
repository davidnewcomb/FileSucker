package uk.co.bigsoft.filesucker.transfer.task.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import uk.co.bigsoft.filesucker.tools.MousePressListener;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskController;

public class SuckerTaskViewPopupMenu extends JPopupMenu {

	public SuckerTaskViewPopupMenu(SuckerTaskController stc) {
		JMenuItem t;

		t = new JMenuItem("Cancel (let current jobs complete)");
		t.addMouseListener((MousePressListener) e -> stc.cancel());
		add(t);

		t = new JMenuItem("Interrupt (now)");
		t.addMouseListener((MousePressListener) e -> stc.interrupt());
		add(t);
	}
}
