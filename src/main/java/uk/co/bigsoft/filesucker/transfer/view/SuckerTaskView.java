package uk.co.bigsoft.filesucker.transfer.view;

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
	private JProgressBar taskProgressBar;
	private Box suckerItemsContainer = Box.createVerticalBox();

	public SuckerTaskView(SuckerTaskModel model) {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new LineBorder(Color.BLACK));

		// SuckerItemModel itemModel = new SuckerItemModel();

		// SuckerIterableModel taskProgressModel = new SuckerIterableModel(69);
		taskProgressBar = new SuckerTaskProgressBar(model);

		int th = TRANSFER_ROW_HEIGHT * 20; // FileSucker.configData.getMaxTasks();

		suckerItemsContainer.setMinimumSize(new Dimension(0, th));
		suckerItemsContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, th));

		header.setText(model.getTitle());

//		System.out.println("xxx-totalNoFiles=" + totalNoFiles);
//		totalNumberOfFiles = new JProgressBar(SwingConstants.HORIZONTAL, 0, totalNoFiles);
//		totalNumberOfFiles.setValue(0);
//		totalNumberOfFiles.setStringPainted(true);
//		totalNumberOfFiles.setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
//		totalNumberOfFiles.setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
//		totalNumberOfFiles.setString(null);

		MouseAdapter allFilesContextMenu = new MyMouseAdapter();
		header.addMouseListener(allFilesContextMenu);
		// totalNumberOfFiles.addMouseListener(allFilesContextMenu);

		add(header);
		add(taskProgressBar);
		add(suckerItemsContainer);

	}

//	public void setDoneAnother() {
//		current++;
//		CreditScreen.addFiles(1);
//		totalNumberOfFiles.setValue(current);
//		totalNumberOfFiles.repaint();
//	}
//
//	public void addDownloadFile(JComponent c) {
//		files.add(c);
//		revalidate();
//	}
//
//	public void removeDownloadFile(JComponent c) {
//		files.remove(c);
//		revalidate();
//	}
//
//	protected SuckeringFileJProgressBar[] getFiles() {
//		Component[] cs = null;
//		ArrayList<SuckeringFileJProgressBar> al = new ArrayList<SuckeringFileJProgressBar>();
//
//		try {
//			cs = files.getComponents();
//			for (int i = 0; i < cs.length; ++i) {
//				if (cs[i] == null)
//					continue;
//
//				if (cs[i] instanceof SuckeringFileJProgressBar)
//					al.add((SuckeringFileJProgressBar) cs[i]);
//			}
//			SuckeringFileJProgressBar[] r = al.toArray(new SuckeringFileJProgressBar[0]);
//			return r;
//		} catch (Exception e) {
//			System.out.print("here");
//		}
//		return null;
//	}

}

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
