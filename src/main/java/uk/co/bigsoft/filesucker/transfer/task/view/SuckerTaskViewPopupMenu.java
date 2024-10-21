package uk.co.bigsoft.filesucker.transfer.task.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import uk.co.bigsoft.filesucker.tools.MousePressListener;
import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskModel;

public class SuckerTaskViewPopupMenu extends JPopupMenu {

	public SuckerTaskViewPopupMenu(SuckerTaskModel stm) {
		JMenuItem t = new JMenuItem("Cancel");
		t.addMouseListener((MousePressListener) e -> stm.setCancelled(true));
		add(t);
	}
}
