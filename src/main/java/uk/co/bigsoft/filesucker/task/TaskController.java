package uk.co.bigsoft.filesucker.task;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;

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

	}

	public void initController(ConfigModel configModel, ToolsModel toolsModel) {
		view.getUrlTextField().addKeyListener((KeyReleasedListener) e -> keyReleasedUrl());
		view.getUrlTextField().addCaretListener(e -> caretMovedUrl());
		view.getUrlTextField().addMouseListener((MousePressListener) e -> pasteIntoWorkingUrl(e));

		view.getOriginalAddressTextField().addKeyListener((KeyReleasedListener) e -> keyReleasedOrigAddr());
		view.getOriginalAddressTextField().addCaretListener(e -> caretMovedOrigAddr());
		view.getOriginalAddressTextField().addMouseListener((MousePressListener) e -> pasteIntoWorkingOrigAddr(e));

		view.getCopyToToolsButton().addActionListener(e -> copyWorkingToTools(toolsModel));
		view.getHelperDirectoryButton().addActionListener(e -> helperDirectory(configModel.getHelperDirectory()));

		view.getDirectoryComboBox().setSelectedItem(configModel.getBaseDir());
	}

	private void modelListener(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object newVal = evt.getNewValue();

		switch (propName) {
		case TaskProps.F_URL: {
			view.getUrlTextField().setText((String) newVal);
			break;
		}
		case TaskProps.F_ORIGINAL_ADDRESS: {
			view.getOriginalAddressTextField().setText((String) newVal);
			break;
		}
		default: {
			System.out.println("Unknown TaskProp: " + propName);
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
			e.printStackTrace();
			// TODO update error message
		}
	}

	private void copyWorkingToTools(ToolsModel toolsModel) {
		String u = model.getUrl();
		toolsModel.setWorking(u);
	}
}
