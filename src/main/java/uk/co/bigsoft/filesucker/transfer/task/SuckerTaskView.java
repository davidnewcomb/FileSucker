package uk.co.bigsoft.filesucker.transfer.task;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

public class SuckerTaskView extends JPanel {
	private static final int TRANSFER_ROW_HEIGHT = 20;

	private JLabel header = new JLabel();
	private JProgressBar taskProgressBar = new JProgressBar();
	private Box suckerItemsContainer = Box.createVerticalBox();

	public SuckerTaskView() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new LineBorder(Color.BLACK));

		taskProgressBar.setValue(-1);
		taskProgressBar.setMinimum(-1);
		taskProgressBar.setMaximum(100);
		taskProgressBar.setStringPainted(true);
		taskProgressBar.setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
		taskProgressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		taskProgressBar.setString("...");

		int th = TRANSFER_ROW_HEIGHT * 20; // FileSucker.configData.getMaxTasks();

		suckerItemsContainer.setMinimumSize(new Dimension(0, th));
		suckerItemsContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, th));

		MouseAdapter allFilesContextMenu = new MyMouseAdapter();
		header.addMouseListener(allFilesContextMenu);

		add(header);
		add(taskProgressBar);
		add(suckerItemsContainer);
	}

	public void setTitle(String title) {
		header.setText(title);
	}

	public void setTaskStats(int percentComplete, int success, int failed) {
		StringBuilder s = new StringBuilder(percentComplete + "%");
		if (failed != 0) {
			s.append(" [success=");
			s.append(success);
			s.append(", failed=");
			s.append(failed);
			s.append("]");
		}
		taskProgressBar.setString(s.toString());
		taskProgressBar.setValue(percentComplete);
	}

	public void addSuckerProgressBar(JProgressBar bar) {
		suckerItemsContainer.add(bar);
		suckerItemsContainer.revalidate();
		suckerItemsContainer.repaint();
	}

	public void removeSuckerProgressBar(JProgressBar bar) {
		suckerItemsContainer.remove(bar);
		suckerItemsContainer.revalidate();
		suckerItemsContainer.repaint();
	}

}

//public void setDoneAnother() {
//current++;
//CreditScreen.addFiles(1);
//totalNumberOfFiles.setValue(current);
//totalNumberOfFiles.repaint();
//}
//
//public void addDownloadFile(JComponent c) {
//files.add(c);
//revalidate();
//}
//
//public void removeDownloadFile(JComponent c) {
//files.remove(c);
//revalidate();
//}
//
//protected SuckeringFileJProgressBar[] getFiles() {
//Component[] cs = null;
//ArrayList<SuckeringFileJProgressBar> al = new ArrayList<SuckeringFileJProgressBar>();
//
//try {
//	cs = files.getComponents();
//	for (int i = 0; i < cs.length; ++i) {
//		if (cs[i] == null)
//			continue;
//
//		if (cs[i] instanceof SuckeringFileJProgressBar)
//			al.add((SuckeringFileJProgressBar) cs[i]);
//	}
//	SuckeringFileJProgressBar[] r = al.toArray(new SuckeringFileJProgressBar[0]);
//	return r;
//} catch (Exception e) {
//	System.out.print("here");
//}
//return null;
//}

class MyMouseAdapter extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		// super.mousePressed (e) ;
		System.out.println("mousePressed");
//		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
//			JPopupMenu popUpMenu = new JPopupMenu();
//
//			JMenuItem t = new JMenuItem("Pause All");
//			t.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mousePressed(MouseEvent ee) {
//					SuckeringFileJProgressBar[] cs = getFiles();
//					for (int i = 0; i < cs.length; ++i) {
//						SuckeringFileJProgressBar s = cs[i];
//						RunnableSucker rs = s.getDownloadInfo();
//						rs.pauseThread();
//					}
//				}
//			});
//			popUpMenu.add(t);
//
//			t = new JMenuItem("Resume All");
//			t.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mousePressed(MouseEvent ee) {
//					SuckeringFileJProgressBar[] cs = getFiles();
//					for (int i = 0; i < cs.length; ++i) {
//						SuckeringFileJProgressBar s = cs[i];
//						RunnableSucker rs = s.getDownloadInfo();
//						rs.resumeThread();
//					}
//				}
//			});
//			popUpMenu.add(t);
//
//			t = new JMenuItem("Cancel");
//			t.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mousePressed(MouseEvent ee) {
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
////					if (cs.length == 0) {
////						break;
////					}
////					try {
////						wait(100);
////					} catch (Exception ex) {
////						// empty
////					}
////					cs = getFiles();
//
//				}
//			});
//			popUpMenu.add(t);
//
//			t = new JMenuItem("Save");
//			t.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mousePressed(MouseEvent ee) {
//					System.out.println("Save: ");
//					TaskScreenParams.save(parms);
//					// suckerThread.save();
//				}
//			});
//			popUpMenu.add(t);
//
//			t = new JMenuItem("Load");
//			t.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mousePressed(MouseEvent ee) {
//					System.out.println("Load: " + "xxx");
//					TaskScreen.load(parms);
//				}
//			});
//			popUpMenu.add(t);
//
//			popUpMenu.show((Component) e.getSource(), e.getX(), e.getY());
//		}

	}
}
