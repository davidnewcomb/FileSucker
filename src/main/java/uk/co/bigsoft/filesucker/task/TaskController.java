package uk.co.bigsoft.filesucker.task;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;

import uk.co.bigsoft.filesucker.Downloader;
import uk.co.bigsoft.filesucker.Utility;
import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.config.KeyReleasedListener;
import uk.co.bigsoft.filesucker.task.looper.LooperCmd;
import uk.co.bigsoft.filesucker.task.looper.LooperId;
import uk.co.bigsoft.filesucker.tools.MousePressListener;
import uk.co.bigsoft.filesucker.tools.ToolsModel;
import uk.co.bigsoft.filesucker.transfer.TransferController;
import uk.co.bigsoft.filesucker.transfer.download.si.SuckerIterable;

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

	public void initController(ConfigModel configModel, ToolsModel toolsModel, TransferController transferController) {
		view.getUrlTextField().addKeyListener((KeyReleasedListener) e -> keyReleasedUrl());
		view.getUrlTextField().addCaretListener(e -> caretMovedUrl(e));
		view.getUrlTextField().addMouseListener((MousePressListener) e -> pasteIntoWorkingUrl(e));

		view.getOriginalAddressTextField().addKeyListener((KeyReleasedListener) e -> keyReleasedOrigAddr());
		view.getOriginalAddressTextField().addCaretListener(e -> caretMovedOrigAddr());
		view.getOriginalAddressTextField().addMouseListener((MousePressListener) e -> pasteIntoWorkingOrigAddr(e));
		view.getOriginalAddressLaunchButton().addActionListener(e -> originalAddressLaunch(configModel));

		view.getDirectoryComboBox().getEditor().getEditorComponent()
				.addKeyListener((KeyReleasedListener) e -> keyReleasedDirectory());

		view.getClipboardAsDirectoryButton().addActionListener(e -> clipboardAsDirectory());
		view.getCopyToToolsButton().addActionListener(e -> copyWorkingToTools(toolsModel));
		view.getDirectoryAndPrefixButton().addActionListener(e -> directoryAndPrefix(configModel));
		view.getDirectoryBrowseButton().addActionListener(e -> directoryBrowse());
		view.getDirectoryClipboardButton().addActionListener(e -> directoryClipboard(configModel));
		view.getDirectoryExtensionButton().addActionListener(e -> directoryExtension(configModel));
		view.getHelperDirectoryButton().addActionListener(e -> helperDirectory(configModel.getHelperDirectory()));
		view.getHomeButton().addActionListener(e -> directoryToHome(configModel.getBaseDir()));
		view.getHomeDirectoryPrefixButton().addActionListener(e -> homeDirectoryPrefix(configModel));
		view.getSubDirectoryAndPrefixButton().addActionListener(e -> subDirectoryAndPrefix(configModel));
		view.getSubDirectoryAndPrefixFromClipboardButton()
				.addActionListener(e -> subDirectoryAndPrefixFromClipboard(configModel));
		view.getSubDirectoryFromClipboardButton().addActionListener(e -> subDirectoryFromClipboard());
		view.getSubDirectoryPathButton().addActionListener(e -> subDirectoryPath(configModel));

		view.getRunTaskButton().addActionListener(e -> runTask(transferController));
		view.getFindFilesButton().addActionListener(e -> findFiles(configModel));

		view.getSaveOnly().addActionListener(e -> saveOnly());
		view.getSaveUrl().addActionListener(e -> saveUrl());

		view.getPrefixButton().addActionListener(e -> prefix(configModel));
		view.getPrefixLowerButton().addActionListener(e -> prefixLower());
		view.getPrefixClipButton().addActionListener(e -> prefixClip(configModel));
		view.getPrefixClearButton().addActionListener(e -> prefixClear());

		view.getSuffixButton().addActionListener(e -> suffix(configModel));
		view.getSuffixEndCB().addActionListener(e -> suffixEnd());
		view.getSuffixLowerButton().addActionListener(e -> suffixLower());
		view.getSuffixClipButton().addActionListener(e -> suffixClip(configModel));
		view.getSuffixClearButton().addActionListener(e -> suffixClear());
	}

	private void originalAddressLaunch(ConfigModel configModel) {
		String url = model.getOriginalAddress().trim();
		if ("".equals(url)) {
			model.setErrorMessage("Original address is empty");
			return;
		}
		Utility.launchBrowser(configModel, url);
	}

	private void suffixClear() {
		model.setSuffix("");
	}

	private void suffixClip(ConfigModel configModel) {
		String s = model.getSuffix() + configModel.getPostPrefix() + Utility.getClipboard();
		model.setSuffix(s);
	}

	private void suffixLower() {
		model.setSuffix(model.getSuffix().toLowerCase());
	}

	private void suffixEnd() {
		model.setSuffixEnd(view.getSuffixEndCB().isSelected());
	}

	private void suffix(ConfigModel configModel) {
		String s = model.getSuffix() + configModel.getPostPrefix() + model.getSelectedUrl();
		model.setSuffix(s);
	}

	private void prefixClear() {
		model.setPrefix("");
	}

	private void prefixClip(ConfigModel configModel) {
		String s = model.getPrefix() + Utility.getClipboard() + configModel.getPostPrefix();
		model.setPrefix(s);
	}

	private void prefixLower() {
		model.setPrefix(model.getPrefix().toLowerCase());
	}

	private void prefix(ConfigModel configModel) {
		String s = model.getPrefix() + model.getSelectedUrl() + configModel.getPostPrefix();
		model.setPrefix(s);
	}

	private void saveOnly() {
		boolean x = view.getSaveOnly().isSelected();
		model.setSaveOnly(x);
	}

	private void saveUrl() {
		boolean x = view.getSaveUrl().isSelected();
		model.setSaveUrl(x);
	}

	private void runTask(TransferController transferController) {
		String dir = Utility.expandsPercentVars(model.getDirectory());
		TaskConfig taskConfig = new TaskConfig(model.getUrl(), dir, model.getPrefix(), model.getSuffix(),
				model.isSuffixEnd());
		SuckerIterable si = new SuckerIterable(taskConfig);
		transferController.addTask(si);

		if (model.isSaveUrl()) {
//			TaskScreenParams.save(taskConfig);
			if (model.isSaveOnly()) {
				model.setErrorMessage("Saved");
				return;
			}
		}

		model.setOriginalAddress("");
		view.getRunYet().setReset();

	}

	private void findFiles(ConfigModel configModel) {
		try {
			String findFileAddress = model.getUrl();
			if (findFileAddress.length() == 0) {
				model.setErrorMessage("URL is empty");
				return;
			}
			model.setOriginalAddress(findFileAddress);

			String content = Downloader.getInstance().downloadTextFile(findFileAddress);
			List<String> extns = configModel.getFindExtn();

			Set<String> map = new TreeSet<>();
			for (String extn : extns) {
				Pattern p = Pattern.compile("[\\[\\]a-zA-Z%$./0-9_-]+." + extn);
				Matcher m = p.matcher(content);

				while (m.find()) {
					String s = m.group();
					map.add(s);
				}
			}

			if (map.size() == 0) {
				model.setErrorMessage("No matches found");
				return;
			}

			StringBuffer listLooper = new StringBuffer();
			String guts = String.join(",", map);
			listLooper.append("{");
			listLooper.append(LooperCmd.L_LIST);
			listLooper.append(",");
			listLooper.append(LooperId.getNext());
			listLooper.append(",");
			listLooper.append(guts);
			listLooper.append("}");

			view.getLooperPanel().openLooper(listLooper.toString());

		} catch (Exception ex) {
			model.setErrorMessage(ex.getMessage());
		}

	}

	private void subDirectoryPath(ConfigModel configModel) {
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
			newDir.insert(0, configModel.getBaseDir()); // FileSucker.configData.getScreenBaseDir());
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

	private void subDirectoryAndPrefixFromClipboard(ConfigModel configModel) {
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
			newDir.insert(0, configModel.getBaseDir());
		else
			newDir.insert(0, curDir);

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + configModel.getPostPrefix();
		model.setPrefix(url_s.toLowerCase());
	}

	private void subDirectoryAndPrefix(ConfigModel configModel) {
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
			newDir.insert(0, configModel.getBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + configModel.getPostPrefix();
		model.setPrefix(url_s.toLowerCase());
	}

	private void homeDirectoryPrefix(ConfigModel configModel) {
		String url_s = model.getSelectedUrl();
		if (url_s == null)
			return;
		url_s = Utility.getSuckerLable(url_s);

		url_s = Utility.cleanString(url_s);

		StringBuffer newDir = new StringBuffer(configModel.getBaseDir());
		newDir.append(url_s);

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + configModel.getPostPrefix();
		model.setPrefix(url_s.toLowerCase());
	}

	private void directoryExtension(ConfigModel configModel) {
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
			newDir.insert(0, configModel.getBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());
	}

	private void directoryClipboard(ConfigModel configModel) {
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
			newDir.insert(0, configModel.getBaseDir());
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

	private void directoryAndPrefix(ConfigModel configModel) {
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
			newDir.insert(0, configModel.getBaseDir());
		} else {
			newDir.insert(0, curDir);
		}

		model.setDirectory(newDir.toString());

		// Add to prefix
		url_s = url_s + configModel.getPostPrefix();
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
			view.getUrlTextField().setSelectionStart(model.getUrlCaretStart());
			view.getUrlTextField().setSelectionEnd(model.getUrlCaretEnd());
			break;
		}
		case TaskProps.F_SELECTED_URL: {
			// TODO enable/disable buttons

			// TODO check this
			if (newVal.startsWith("{") && newVal.endsWith("}")) {
				view.getLooperPanel().openLooper(newVal);
			}
			break;
		}
//		case TaskProps.F_SET_SELECTED_URL: {
//			// TODO check this
//			if (newVal.startsWith("{") && newVal.endsWith("}")) {
//				view.getLooperPanel().openLooper(newVal);
//			}
//			int start = view.getUrlTextField().getSelectionStart();
//			int end = view.getUrlTextField().getSelectionEnd();
//			String url = view.getUrlTextField().getText();
//
//			String oldSel = url.substring(start, end);
//			if (oldSel.equals(newVal)) {
//				return;
//			}
//
//			StringBuilder sb = new StringBuilder(url);
//			sb.delete(start, end);
//			sb.insert(start, newVal);
//			view.getUrlTextField().setText(sb.toString());
//			view.getUrlTextField().setSelectionStart(start);
//			view.getUrlTextField().setSelectionEnd(start + newVal.length());
//			break;
//		}
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
		case TaskProps.F_SUFFIX_END: {
			view.getSuffixEndCB().setSelected(newVal == "0");
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

	private void caretMovedUrl(CaretEvent e) {
		int min = Math.min(e.getDot(), e.getMark());
		int max = Math.max(e.getDot(), e.getMark());

		model.setUrlCaretStart(min);
		model.setUrlCaretEnd(max);
		if (min == max) {
			model.setSelectedUrl("");
		} else {
			model.setSelectedUrl(model.getUrl().substring(min, max));
		}
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
