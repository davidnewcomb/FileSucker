package uk.co.bigsoft.filesucker.transfer.task;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import uk.co.bigsoft.filesucker.transfer.task.view.SuckerTaskViewPopupMenu;

public class SuckerTaskViewMouseAdapter extends MouseAdapter {

	private SuckerTaskViewPopupMenu popup;

	public SuckerTaskViewMouseAdapter(SuckerTaskModel stm) {
		popup = new SuckerTaskViewPopupMenu(stm);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		System.out.println("mousePressed");
		popup.show((Component) e.getSource(), e.getX(), e.getY());
	}
}
