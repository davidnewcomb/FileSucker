package uk.co.bigsoft.filesucker.transfer.task;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class SuckerTaskViewMouseAdapter extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		System.out.println("mousePressed");
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
			JPopupMenu popUpMenu = new JPopupMenu();

			JMenuItem t = new JMenuItem("Pause All");
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent ee) {
//					SuckeringFileJProgressBar[] cs = getFiles();
//					for (int i = 0; i < cs.length; ++i) {
//						SuckeringFileJProgressBar s = cs[i];
//						RunnableSucker rs = s.getDownloadInfo();
//						rs.pauseThread();
//					}
				}
			});
			popUpMenu.add(t);

			t = new JMenuItem("Resume All");
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent ee) {
//					SuckeringFileJProgressBar[] cs = getFiles();
//					for (int i = 0; i < cs.length; ++i) {
//						SuckeringFileJProgressBar s = cs[i];
//						RunnableSucker rs = s.getDownloadInfo();
//						rs.resumeThread();
//					}
				}
			});
			popUpMenu.add(t);

			t = new JMenuItem("Cancel");
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent ee) {
//					suckerThread.cancelThread();
//					SuckeringFileJProgressBar[] cs = getFiles();
//					// while
//					// (true)
//					// {
//					for (int i = 0; i < cs.length; ++i) {
//						SuckeringFileJProgressBar s = cs[i];
//						RunnableSucker rs = s.getDownloadInfo();
//						rs.cancelThread();
//					}
//					cs = getFiles();

//					if (cs.length == 0) {
//						break;
//					}
//					try {
//						wait(100);
//					} catch (Exception ex) {
//						// empty
//					}
//					cs = getFiles();

				}
			});
			popUpMenu.add(t);

			t = new JMenuItem("Save");
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent ee) {
					System.out.println("Save: ");
					// TaskScreenParams.save(parms);
					// suckerThread.save();
				}
			});
			popUpMenu.add(t);

			t = new JMenuItem("Load");
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent ee) {
					System.out.println("Load: " + "xxx");
					// TaskScreen.load(parms);
				}
			});
			popUpMenu.add(t);

			popUpMenu.show((Component) e.getSource(), e.getX(), e.getY());
		}

	}
}
