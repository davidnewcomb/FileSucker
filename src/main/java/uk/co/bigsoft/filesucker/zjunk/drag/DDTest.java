package uk.co.bigsoft.filesucker.zjunk.drag;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

public class DDTest extends JPanel {
	// private JTextArea textComp ;
	private JFrame frame;

	public DDTest(JFrame f) {
		super(new BorderLayout());
		frame = f;

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// The TabbedPaneController manages the panel that
		// contains the tabbed pane. When there are no files
		// the panel contains a plain text area. Then, as
		// files are dropped onto the area, the tabbed panel
		// replaces the file area.

		JPanel panel = new JPanel(new BorderLayout());
		add(panel, BorderLayout.CENTER);

		MyTransferHandler ddHandler = new MyTransferHandler();

		JComboBox<String> directory = new JComboBox<>();
		directory.setEditable(true);
		directory.setTransferHandler(ddHandler);

		JTextField tf1 = new JTextField("Drag this to the other combo box");
		tf1.setDragEnabled(true);
		tf1.setTransferHandler(ddHandler);

		JTextField tf2 = new JTextField("destiniation");
		tf2.setDragEnabled(true);
		tf2.setTransferHandler(ddHandler);

		add(tf1, BorderLayout.NORTH);
		add(directory, BorderLayout.CENTER);
		add(tf2, BorderLayout.SOUTH);
	}

	protected static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("DD Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the menu bar and content pane.
		DDTest fViewer = new DDTest(frame);
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

}

class MyTransferHandler extends TransferHandler {
	private DataFlavor fileFlavor, stringFlavor;

	private JTextField source;

	private boolean shouldRemove;

	protected String newline = "\n";

	// Start and end position in the source text.
	// We need this information when performing a MOVE
	// in order to remove the dragged text from the source.
	private Position posStart = null, posFinish = null;

	MyTransferHandler() {
		System.out.println("MyTransferHandler:Constructor");
		fileFlavor = DataFlavor.javaFileListFlavor;
		stringFlavor = DataFlavor.stringFlavor;
	}

	@Override
	public boolean importData(JComponent c, Transferable t) {
		System.out.println("MyTransferHandler:importData");
		JTextField tc;

		if (!canImport(c, t.getTransferDataFlavors())) {
			return false;
		}
		// TODO: A real application would load the file in another
		// thread in order to not block the UI. This step
		// was omitted here to simplify the code.
		try {
			if (hasFileFlavor(t.getTransferDataFlavors())) {
				List<File> files = (List<File>) t.getTransferData(fileFlavor);
				File file = files.get(0);
				// Tell the tabbedpane controller to add
				// a new tab with the name of this file
				// on the tab. The text area that will
				// display the contents of the file is returned.
				// tc = ts.addTab (file.toString ()) ;
				System.out.println("Drag & Drop: Handling: " + file.toString());
				// TaskScreenParams.load (file);
				// ts.setSelInd (0) ;
				System.out.println("Drag & Drop: Handling: completed");
				return true;

			} else if (hasStringFlavor(t.getTransferDataFlavors())) {
				tc = (JTextField) c;

				System.out.println("MyTransferHandler:importData:String=" + tc.getText());
				if (tc.equals(source) && tc.getCaretPosition() >= posStart.getOffset()
						&& tc.getCaretPosition() <= posFinish.getOffset()) {
					shouldRemove = true;
					// return true ;
				}
				shouldRemove = false;
				String str = (String) t.getTransferData(stringFlavor);
				// str = TaskScreen.getSuckerLable (str);
				tc.replaceSelection(str);
				return true;
			}
		} catch (UnsupportedFlavorException ufe) {
			System.out.println("importData: unsupported data flavor");
		} catch (IOException ieo) {
			System.out.println("importData: I/O exception");
		}

		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		System.out.println("MyTransferHandler:createTransferable");

		source = (JTextField) c;
		int start = source.getSelectionStart();
		int end = source.getSelectionEnd();
		Document doc = source.getDocument();
		if (start == end) {
			return null;
		}
		try {
			posStart = doc.createPosition(start);
			posFinish = doc.createPosition(end);
		} catch (BadLocationException e) {
			System.out.println("Can't create position - unable to remove text from source.");
		}
		// shouldRemove = true ;
		shouldRemove = false;
		String data = source.getSelectedText();
		return new StringSelection(data);
	}

	@Override
	public int getSourceActions(JComponent c) {
		System.out.println("MyTransferHandler:getSourceActions");
		return COPY_OR_MOVE;
	}

	// Remove the old text if the action is a MOVE.
	// However, we do not allow dropping on top of the selected text,
	// so in that case do nothing.
	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		System.out.println("MyTransferHandler:exportDone");
		if (shouldRemove && action == MOVE) {
			if (posStart != null && posFinish != null && posStart.getOffset() != posFinish.getOffset()) {
				try {
					JTextComponent tc = (JTextComponent) c;
					tc.getDocument().remove(posStart.getOffset(), posFinish.getOffset() - posStart.getOffset());
				} catch (BadLocationException e) {
					System.out.println("Can't remove text from source.");
				}
			}
		}
		source = null;
	}

	@Override
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		System.out.println("MyTransferHandler:canImport");
		if (hasFileFlavor(flavors)) {
			return true;
		}
		if (hasStringFlavor(flavors)) {
			return true;
		}
		return false;
	}

	private boolean hasFileFlavor(DataFlavor[] flavors) {
		System.out.println("MyTransferHandler:hasFileFlavor");
		for (DataFlavor flavor : flavors) {
			if (fileFlavor.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasStringFlavor(DataFlavor[] flavors) {
		System.out.println("MyTransferHandler:hasStringFlavor");
		for (DataFlavor flavor : flavors) {
			if (stringFlavor.equals(flavor)) {
				return true;
			}
		}
		return false;
	}
}
