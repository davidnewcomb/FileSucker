package uk.co.bigsoft.filesucker.task;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.FileSucker;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.config.KeyReleasedListener;
import uk.co.bigsoft.filesucker.tools.MousePressListener;
import uk.co.bigsoft.filesucker.tools.ToolsModel;

public class TaskController {

	private TaskModel model;
	private TaskView view;

	public TaskController(TaskModel m, TaskView v) {
		model = m;
		view = v;
		initView();
		model.addListener(e -> modelListener(e));
	}

	private void initView() {
		view.getUrlTextField().setText(model.getUrl());
		view.getOriginalAddressTextField().setText(model.getOriginalAddress());
		view.getDirectoryComboBox().setSelectedItem(model.getDirectory());
	}

	public void initController(ConfigModel configModel, ToolsModel toolsModel) {
		view.getUrlTextField().addKeyListener((KeyReleasedListener) e -> keyReleasedUrl());
		view.getUrlTextField().addCaretListener(e -> caretMovedUrl());
		view.getUrlTextField().addMouseListener((MousePressListener) e -> pasteIntoWorkingUrl(e));

		view.getOriginalAddressTextField().addKeyListener((KeyReleasedListener) e -> keyReleasedOrigAddr());
		view.getOriginalAddressTextField().addCaretListener(e -> caretMovedOrigAddr());
		view.getOriginalAddressTextField().addMouseListener((MousePressListener) e -> pasteIntoWorkingOrigAddr(e));

		view.getDirectoryComboBox().getEditor().getEditorComponent()
				.addKeyListener((KeyReleasedListener) e -> keyReleasedDirectory());

		view.getClipboardAsDirectoryButton().addActionListener(e -> clipboardAsDirectory());
		view.getCopyToToolsButton().addActionListener(e -> copyWorkingToTools(toolsModel));
		view.getDirectoryAndPrefixButton().addActionListener(e -> directoryAndPrefix());
		view.getDirectoryBrowseButton().addActionListener(e -> directoryBrowse());
		view.getDirectoryClipboardButton().addActionListener(e -> directoryClipboard());
		view.getDirectoryExtensionButton().addActionListener(e -> directoryExtension());
		view.getHelperDirectoryButton().addActionListener(e -> helperDirectory(configModel.getHelperDirectory()));
		view.getHomeButton().addActionListener(e -> directoryToHome(configModel.getBaseDir()));
		view.getHomeDirectoryPrefixButton().addActionListener(e -> homeDirectoryPrefix());
		view.getSubDirectoryAndPrefixButton().addActionListener(e -> subDirectoryAndPrefix());
		view.getSubDirectoryAndPrefixFromClipboardButton().addActionListener(e -> subDirectoryAndPrefixFromClipboard());
		view.getSubDirectoryFromClipboardButton().addActionListener(e -> subDirectoryFromClipboard());
		view.getSubDirectoryPathButton().addActionListener(e -> subDirectoryPath());

		view.getRunTaskButton().addActionListener(e -> runTask());
	}

	private Object runTask() {
		// TODO Auto-generated method stub
		return null;
	}

	private void subDirectoryPath() {
		String url_s = model.getSelectedUrl();
		if ("".equals(url_s)) {
			return;
		}

		url_s = Utility.getSuckerLable(url_s);
		url_s = Utility.cleanString(url_s);

		String curDir = model.getDirectory();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1) {
			if (curDir.charAt(curDir.length() - 1) != File.separatorChar)
				newDir.append(File.separator);
		}
		newDir.append(url_s);
		if (curDir.equals("")) {
			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());
	}

	private void subDirectoryFromClipboard() {
		String s = Utility.getClipboard();
		if (s == null)
			return;

		s = Utility.cleanString(s);

		StringBuffer sb = new StringBuffer(model.getDirectory());
		if (sb.charAt(sb.length() - 1) != File.separatorChar)
			sb.append(File.separator);

		sb.append(s);
		model.setDirectory(sb.toString());
	}

	private void subDirectoryAndPrefixFromClipboard() {
		// Add to dir
		String url_s = Utility.getClipboard();
		if (url_s == null)
			return;

		url_s = Utility.getSuckerLable(url_s);
		url_s = Utility.cleanString(url_s);

		String curDir = model.getDirectory();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1) {
			if (curDir.charAt(curDir.length() - 1) != File.separatorChar)
				newDir.append(File.separator);
		}
		newDir.append(url_s);
		if (curDir.equals(""))
			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		else
			newDir.insert(0, curDir);

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + FileSucker.configData.getPostPrefix();
		model.setPrefix(url_s.toLowerCase());
	}

	private void subDirectoryAndPrefix() {
		// Add to dir
		String url_s = model.getSelectedUrl();
		if (url_s == null)
			return;

		url_s = Utility.getSuckerLable(url_s);
		url_s = Utility.cleanString(url_s);

		String curDir = model.getDirectory();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1) {
			if (curDir.charAt(curDir.length() - 1) != File.separatorChar) {
				newDir.append(File.separator);
			}
		}
		newDir.append(url_s);
		if (curDir.equals("")) {
			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + FileSucker.configData.getPostPrefix();
		model.setPrefix(url_s.toLowerCase());
	}

	private void homeDirectoryPrefix() {
		String url_s = model.getSelectedUrl();
		if (url_s == null)
			return;
		url_s = Utility.getSuckerLable(url_s);

		url_s = Utility.cleanString(url_s);

		StringBuffer newDir = new StringBuffer(FileSucker.configData.getScreenBaseDir());
		newDir.append(url_s);

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + FileSucker.configData.getPostPrefix();
		model.setPrefix(url_s.toLowerCase());
	}

	private void directoryExtension() {
		String url_s = model.getSelectedUrl();
		if (url_s == null) {
			return;
		}
		url_s = Utility.getSuckerLable(url_s);
		url_s = Utility.cleanString(url_s);

		String curDir = model.getDirectory();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1 && !curDir.endsWith("_")) {
			newDir.append('_');
		}
		newDir.append(url_s);
		if (curDir.equals("")) {
			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());
	}

	private void directoryClipboard() {
		String s = Utility.getClipboard();
		if (s == null)
			return;

		s = Utility.cleanString(s);

		String curDir = model.getDirectory();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1 && !curDir.endsWith("_")) {
			newDir.append('_');
		}
		newDir.append(s);
		if (curDir.equals("")) {
			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());
	}

	private void directoryBrowse() {
		String dir = model.getDirectory();
		String dirEpanded = Utility.expandsPercentVars(dir);
		JFileChooser fc = new JFileChooser(dirEpanded);

		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s = Utility.unexpandsPercentVars("Directory-browse", file.toString());
			model.setDirectory(s);
		}
	}

	private void directoryAndPrefix() {
		String url_s = model.getSelectedUrl();
		if (url_s == null) {
			return;
		}
		url_s = Utility.getSuckerLable(url_s);

		url_s = Utility.cleanString(url_s);

		String curDir = model.getDirectory();
		StringBuffer newDir = new StringBuffer();
		if (curDir.length() > 1 && !curDir.endsWith("_")) {
			newDir.append('_');
		}
		newDir.append(url_s);
		if (curDir.equals("")) {
			newDir.insert(0, FileSucker.configData.getScreenBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + FileSucker.configData.getPostPrefix();
		model.setPrefix(model.getPrefix() + url_s.toLowerCase());
	}

	private void clipboardAsDirectory() {
		String s = Utility.getClipboard();
		if (s == null)
			return;
		model.setDirectory(s);
	}

	private void keyReleasedDirectory() {
		String val = ((JTextField) view.getDirectoryComboBox().getEditor().getEditorComponent()).getText();
		model.setDirectory(val);
	}

	private void directoryToHome(String configBase) {
		model.setDirectory(configBase);
	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		String newVal = (String) evt.getNewValue();

		switch (propName) {
		case TaskProps.F_URL: {
			view.getUrlTextField().setText(newVal);
			break;
		}
		case TaskProps.F_SELECTED_URL: {
			// TODO enable/disable buttons
			break;
		}
		case TaskProps.F_ORIGINAL_ADDRESS: {
			view.getOriginalAddressTextField().setText(newVal);
			break;
		}
		case TaskProps.F_SELECTED_ORIGINAL_ADDRESS: {
			// TODO enable/disable buttons
			break;
		}
		case TaskProps.F_DIRECTORY: {
			view.getDirectoryComboBox().setSelectedItem(newVal);
			break;
		}
		case TaskProps.F_ERROR_MESSAGE: {
			view.getErrorMessagesLabel().setText(newVal);
			break;
		}
		case TaskProps.F_PREFIX: {
			view.getPrefixTextField().setText(newVal);
			break;
		}
		case TaskProps.F_SUFFIX: {
			view.getSuffixTextField().setText(newVal);
			break;
		}
		default: {
			String s = "Unknown TaskProp: " + propName + " -> " + newVal;
			model.setErrorMessage(s);
			// System.out.println(s);
		}
		}
	}

	private void pasteIntoWorkingUrl(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3) { // r-click
			String s = Utility.getClipboard();
			if (s != null) {
				model.setUrl(s);
			}
		}
	}

	private void caretMovedUrl() {
		String s = view.getUrlTextField().getSelectedText();
		if (s == null) {
			s = "";
		}
		model.setSelectedUrl(s);
	}

	private void keyReleasedUrl() {
		String s = view.getUrlTextField().getText();
		model.setUrl(s);
	}

	private void pasteIntoWorkingOrigAddr(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3) { // r-click
			String s = Utility.getClipboard();
			if (s != null) {
				model.setOriginalAddress(s);
			}
		}
	}

	private void caretMovedOrigAddr() {
		String s = view.getOriginalAddressTextField().getSelectedText();
		if (s == null) {
			s = "";
		}
		model.setSelectedOriginalAddress(s);
	}

	private void keyReleasedOrigAddr() {
		String s = view.getOriginalAddressTextField().getText();
		model.setOriginalAddress(s);
	}

	private void helperDirectory(String helperDirectory) {
		String d = model.getDirectory();
		String realDir = Utility.realDirectory(d);

		File dir = new File(realDir);
		if (dir.isFile()) {
			System.out.println("Can not create folder at '" + realDir + "' as file exists");
			return;
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String cmd = helperDirectory.replaceAll("%s", realDir);
		try {
			Utility.runShellCommand(cmd);
		} catch (IOException e) {
			model.setErrorMessage(e.toString());
		}
	}

	private void copyWorkingToTools(ToolsModel toolsModel) {
		String u = model.getUrl();
		toolsModel.setWorking(u);
	}
}
