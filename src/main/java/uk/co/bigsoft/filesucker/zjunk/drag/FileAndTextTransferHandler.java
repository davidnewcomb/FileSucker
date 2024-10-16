package uk.co.bigsoft.filesucker.zjunk.drag;

/**
 * FileAndTextTransferHandler is used by File_Viewer
 */
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

public class FileAndTextTransferHandler extends TransferHandler {
	private DataFlavor fileFlavor, stringFlavor;

	private TabbedPaneController tpc;

	private JTextArea source;

	private boolean shouldRemove;

	protected String newline = "\n";

	// Start and end position in the source text.
	// We need this information when performing a MOVE
	// in order to remove the dragged text from the source.
	Position p0 = null, p1 = null;

	FileAndTextTransferHandler(TabbedPaneController t) {
		tpc = t;
		fileFlavor = DataFlavor.javaFileListFlavor;
		stringFlavor = DataFlavor.stringFlavor;
	}

	@Override
	public boolean importData(JComponent c, Transferable t) {
		JTextArea tc;

		if (!canImport(c, t.getTransferDataFlavors())) {
			return false;
		}
		// TODO: A real application would load the file in another
		// thread in order to not block the UI. This step
		// was omitted here to simplify the code.
		try {
			if (hasFileFlavor(t.getTransferDataFlavors())) {
				String str = null;
				List<File> files = (List<File>) t.getTransferData(fileFlavor);
				for (File file : files) {
					// Tell the tabbedpane controller to add
					// a new tab with the name of this file
					// on the tab. The text area that will
					// display the contents of the file is
					// returned.
					tc = tpc.addTab(file.toString());

					BufferedReader in = null;

					try {
						in = new BufferedReader(new FileReader(file));

						while ((str = in.readLine()) != null) {
							tc.append(str + newline);
						}
						tc.setCaretPosition(0);
						tc.getCaret().setSelectionVisible(true);
						tc.getCaret().setVisible(true);

					} catch (IOException ioe) {
						System.out.println("importData: Unable to read from file " + file.toString());
					} finally {
						if (in != null) {
							try {
								in.close();
							} catch (IOException ioe) {
								System.out.println("importData: Unable to close file " + file.toString());
							}
						}
					}
				}
				tpc.setSelInd(0);
				return true;

			} else if (hasStringFlavor(t.getTransferDataFlavors())) {
				tc = (JTextArea) c;
				if (tc.equals(source) && tc.getCaretPosition() >= p0.getOffset()
						&& tc.getCaretPosition() <= p1.getOffset()) {
					shouldRemove = false;
					return true;
				}
				String str = (String) t.getTransferData(stringFlavor);
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
		source = (JTextArea) c;
		int start = source.getSelectionStart();
		int end = source.getSelectionEnd();
		Document doc = source.getDocument();
		if (start == end) {
			return null;
		}
		try {
			p0 = doc.createPosition(start);
			p1 = doc.createPosition(end);
		} catch (BadLocationException e) {
			System.out.println("Can't create position - unable to remove text from source.");
		}
		shouldRemove = true;
		String data = source.getSelectedText();
		return new StringSelection(data);
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	// Remove the old text if the action is a MOVE.
	// However, we do not allow dropping on top of the selected text,
	// so in that case do nothing.
	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		if (shouldRemove && action == MOVE) {
			if (p0 != null && p1 != null && p0.getOffset() != p1.getOffset()) {
				try {
					JTextComponent tc = (JTextComponent) c;
					tc.getDocument().remove(p0.getOffset(), p1.getOffset() - p0.getOffset());
				} catch (BadLocationException e) {
					System.out.println("Can't remove text from source.");
				}
			}
		}
		source = null;
	}

	@Override
	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		if (hasFileFlavor(flavors)) {
			return true;
		}
		if (hasStringFlavor(flavors)) {
			return true;
		}
		return false;
	}

	private boolean hasFileFlavor(DataFlavor[] flavors) {
		for (DataFlavor flavor : flavors) {
			if (fileFlavor.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasStringFlavor(DataFlavor[] flavors) {
		for (DataFlavor flavor : flavors) {
			if (stringFlavor.equals(flavor)) {
				return true;
			}
		}
		return false;
	}
}
