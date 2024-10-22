package uk.co.bigsoft.filesucker.task.view;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.TransferHandler;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.task.TaskConfig;
import uk.co.bigsoft.filesucker.task.TaskConfigFile;
import uk.co.bigsoft.filesucker.task.TaskView;

public class FileTransferHandler extends TransferHandler {

	private static TaskConfigFile taskConfigFile = new TaskConfigFile();
	private DataFlavor fileFlavor = DataFlavor.javaFileListFlavor;

	public FileTransferHandler() {
		//
	}

	@Override
	public boolean canImport(TransferSupport support) {

		DataFlavor[] flavors = support.getDataFlavors();
		List<DataFlavor> flavorsList = List.of(flavors);
		Component comp = support.getComponent();
		return (comp instanceof TaskView && flavorsList.contains(fileFlavor));
	}

	@Override
	public boolean importData(TransferSupport support) {

		File file = getTransferableUnit(support);
		if (file == null) {
			return false;
		}

		TaskConfig cfg = taskConfigFile.load(file);
		FileSucker.load(cfg);

		return true;
	}

	private File getTransferableUnit(TransferSupport support) {

		try {
			Object stuff = support.getTransferable().getTransferData(fileFlavor);
			@SuppressWarnings("unchecked")
			List<File> l = (List<File>) stuff;
			return l.get(0);
		} catch (UnsupportedFlavorException | IOException e) {
			//
		}
		return null;
	}

}
