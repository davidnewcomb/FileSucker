package uk.co.bigsoft.filesucker;

/**
 * FileAndTextTransferHandler is used by File_Viewer
 */
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

@SuppressWarnings("all")
public class FileAndTextTransferHandler extends TransferHandler
{
    private DataFlavor fileFlavor, stringFlavor;

    private JTextField source;

    private boolean shouldRemove;

    // Start and end position in the source text.
    // We need this information when performing a MOVE
    // in order to remove the dragged text from the source.
    private Position posStart = null, posFinish = null;

    public FileAndTextTransferHandler()
    {
        System.out.println("FileAndTextTransferHandler:Constructor");
        fileFlavor = DataFlavor.javaFileListFlavor;
        stringFlavor = DataFlavor.stringFlavor;
    }

    @Override
    public boolean importData(JComponent c, Transferable t)
    {
        System.out
                .println("FileAndTextTransferHandler:============:importData");
        JTextField tc;

        if (!canImport(c, t.getTransferDataFlavors()))
        {
            System.out
                    .println("***FileAndTextTransferHandler:importData: can't import");
            return false;
        }
        // TODO: A real application would load the file in another
        // thread in order to not block the UI. This step
        // was omitted here to simplify the code.
        try
        {
            if (hasFileFlavor(t.getTransferDataFlavors()))
            {
                List<File> files = (List<File>) t.getTransferData(fileFlavor);
                File file = files.get(0);
                // Tell the tabbedpane controller to add
                // a new tab with the name of this file
                // on the tab. The text area that will
                // display the contents of the file is returned.
                // tc = ts.addTab (file.toString ()) ;
                System.out.println("Drag & Drop: Handling: " + file.toString());
                TaskScreenParams.load(file);
                // ts.setSelInd (0) ;
                System.out.println("Drag & Drop: Handling: completed");
                return true;

            }
            else if (hasStringFlavor(t.getTransferDataFlavors()))
            {
                tc = (JTextField) c;
                System.out.println("importData:" + tc.getText());
                if (tc.equals(source)
                        && tc.getCaretPosition() >= posStart.getOffset()
                        && tc.getCaretPosition() <= posFinish.getOffset())
                {
                    shouldRemove = true;
                    // return true ;
                }
                else
                {
                    shouldRemove = false;
                }
                String str = (String) t.getTransferData(stringFlavor);
                str = Utility.getSukaLable(str);
                tc.replaceSelection(str);
                return true;
            }
            else
            {
                System.out.println("**** Found doggy drag type");
                DataFlavor[] dfAr = t.getTransferDataFlavors();
                for (int i = 0 ; i < dfAr.length ; ++i)
                {
                    System.out.println(i + ")" + dfAr[i].toString());
                }
            }
        }
        catch (UnsupportedFlavorException ufe)
        {
            System.out.println("importData: unsupported data flavor");
        }
        catch (IOException ieo)
        {
            System.out.println("importData: I/O exception");
        }

        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c)
    {
        System.out.println("FileAndTextTransferHandler:createTransferable");
        source = (JTextField) c;
        int start = source.getSelectionStart();
        int end = source.getSelectionEnd();
        Document doc = source.getDocument();
        if (start == end)
        {
            return null;
        }
        try
        {
            posStart = doc.createPosition(start);
            posFinish = doc.createPosition(end);
        }
        catch (BadLocationException e)
        {
            System.out
                    .println("Can't create position - unable to remove text from source.");
        }
        // shouldRemove = true ;
        shouldRemove = false;
        String data = source.getSelectedText();
        return new StringSelection(data);
    }

    @Override
    public int getSourceActions(JComponent c)
    {
        System.out.println("FileAndTextTransferHandler:getSourceActions");
        return COPY_OR_MOVE;
    }

    // Remove the old text if the action is a MOVE.
    // However, we do not allow dropping on top of the selected text,
    // so in that case do nothing.
    @Override
    protected void exportDone(JComponent c, Transferable data, int action)
    {
        System.out.println("FileAndTextTransferHandler:exportDone");
        JTextComponent tc1 = (JTextComponent) c;
        System.out.println("FileAndTextTransferHandler:exportDone:from:"
                + tc1.getText());
        System.out.println("FileAndTextTransferHandler:exportDone:from:"
                + source.getText());

        if (shouldRemove && action == MOVE)
        {
            if (posStart != null && posFinish != null
                    && posStart.getOffset() != posFinish.getOffset())
            {
                try
                {
                    JTextComponent tc = (JTextComponent) c;
                    System.out.println("exportDone:" + tc.getText());
                    tc.getDocument().remove(posStart.getOffset(),
                            posFinish.getOffset() - posStart.getOffset());
                    System.out.println("exportDone:" + tc.getText());
                }
                catch (BadLocationException e)
                {
                    System.out.println("Can't remove text from source.");
                }
            }
        }
        source = null;
    }

    @Override
    public boolean canImport(JComponent c, DataFlavor[] flavors)
    {
        if (hasFileFlavor(flavors))
        {
            System.out
                    .println("FileAndTextTransferHandler:canImport:hasFileFlavor:true");
            return true;
        }
        if (hasStringFlavor(flavors))
        {
            System.out
                    .println("FileAndTextTransferHandler:canImport:hasStringFlavor:true");
            return true;
        }
        System.out.println("****FileAndTextTransferHandler:canImport:FALSE");
        return false;
    }

    private boolean hasFileFlavor(DataFlavor[] flavors)
    {
        System.out.println("FileAndTextTransferHandler:hasFileFlavor");
        for (DataFlavor flavor : flavors)
        {
            if (fileFlavor.equals(flavor))
            {
                return true;
            }
        }
        return false;
    }

    private boolean hasStringFlavor(DataFlavor[] flavors)
    {
        System.out.println("FileAndTextTransferHandler:hasStringFlavor");
        for (DataFlavor flavor : flavors)
        {
            if (stringFlavor.equals(flavor))
            {
                return true;
            }
        }
        return false;
    }
}
