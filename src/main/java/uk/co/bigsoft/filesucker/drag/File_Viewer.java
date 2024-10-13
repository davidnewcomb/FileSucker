package uk.co.bigsoft.filesucker.drag;

/*
 * File_Viewer.java
 */
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class File_Viewer extends JPanel {
	public File_Viewer(JFrame f) {
		super(new BorderLayout());
		frame = f;

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// The TabbedPaneController manages the panel that
		// contains the tabbed pane. When there are no files
		// the panel contains a plain text area. Then, as
		// files are dropped onto the area, the tabbed panel
		// replaces the file area.

		JPanel tabPanel = new JPanel(new BorderLayout());
		tabbedPane = new JTabbedPane();
		// tpc =
		new TabbedPaneController(tabbedPane, tabPanel);
		JPanel vPanel = new JPanel(new BorderLayout());
		vPanel.add(tabPanel, BorderLayout.CENTER);
		add(vPanel, BorderLayout.CENTER);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	protected static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("File Viewer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the menu bar and content pane.
		File_Viewer fViewer = new File_Viewer(frame);
		frame.setContentPane(fViewer);
		// Display the window.
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	// private JTextArea textComp ;
	private JTabbedPane tabbedPane;

	private JFrame frame;
	// private TabbedPaneController tpc ;
}
