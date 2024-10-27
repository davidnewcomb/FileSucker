package uk.co.bigsoft.filesucker.task.view.loopers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bigsoft.filesucker.config.ConfigModel;
import uk.co.bigsoft.filesucker.task.TaskModel;

public class LooperPanel extends JPanel {

	private static Logger L = LoggerFactory.getLogger(LooperPanel.class);

	private JPanel EMPTY = new JPanel();
	private JButton numberButton = new JButton(LooperCmd.L_NUMBER);
	private JButton textButton = new JButton(LooperCmd.L_TEXT);
	private JButton listButton = new JButton(LooperCmd.L_LIST);
	private JButton copyButton = new JButton(LooperCmd.L_COPY);
	private JButton fixedButton = new JButton(LooperCmd.L_FIXED);

	private ILooperPanel currentPanel;
	private ListLooperPanel listPanel = new ListLooperPanel();
	private NumberLooperPanel numberPanel = new NumberLooperPanel();
	private TextLooperPanel textPanel = new TextLooperPanel();
	private FixedLooperPanel fixedPanel = new FixedLooperPanel();
	private CopyLooperPanel copyPanel = new CopyLooperPanel();

	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("Ok");

	private JButton commandButtons[] = { numberButton, textButton, listButton, copyButton, fixedButton };

	private Box cancelOkBox = Box.createHorizontalBox();

	private TaskModel taskModel;
	private ConfigModel configModel;

	public LooperPanel(ConfigModel configModel, TaskModel taskModel) {
		super(new BorderLayout());

		this.taskModel = taskModel;
		this.configModel = configModel;

		Box buttonsBox = Box.createHorizontalBox();
		for (JButton jb : commandButtons) {
			jb.setMinimumSize(new Dimension(10, 20));
			jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
			buttonsBox.add(jb);
		}

		cancelOkBox.add(okButton);
		cancelOkBox.add(Box.createHorizontalGlue());
		cancelOkBox.add(cancelButton);
		cancelOkBox.setVisible(false);

		add(buttonsBox, BorderLayout.NORTH);
		add(EMPTY, BorderLayout.CENTER);
		add(cancelOkBox, BorderLayout.SOUTH);

		numberButton.addActionListener(e -> showNumberLooper());
		textButton.addActionListener(e -> showTextLooper());
		listButton.addActionListener(e -> showListLooper());
		copyButton.addActionListener(e -> showCopyLooper());
		fixedButton.addActionListener(e -> showFixedLooper());

		cancelButton.addActionListener(e -> cancelButton());
		okButton.addActionListener(e -> okButton());

	}

	private void okButton() {
		String looperText = currentPanel.toStringBraces();
		taskModel.replaceSelectedUrl(looperText);

		L.debug("looperText: " + looperText);

		for (JButton jb : commandButtons) {
			jb.setVisible(true);
		}
		cancelOkBox.setVisible(false);
		remove((JPanel) currentPanel);
		add(EMPTY, BorderLayout.CENTER);
	}

	private void cancelButton() {
		for (JButton jb : commandButtons) {
			jb.setVisible(true);
		}
		cancelOkBox.setVisible(false);
		remove((JPanel) currentPanel);
		add(EMPTY, BorderLayout.CENTER);
	}

	public void openLooper(String looperText) {

		String noBrackets = looperText.substring(1, looperText.length() - 1);
		String[] parameters = noBrackets.split(",");
		String looperType = parameters[0];
		List<String> p = Arrays.asList(parameters);
		JPanel jpanel;

		switch (looperType) {
		case LooperCmd.L_LIST: {
			currentPanel = listPanel;
			jpanel = listPanel;
			break;
		}
		case LooperCmd.L_NUMBER: {
			currentPanel = numberPanel;
			jpanel = numberPanel;
			break;
		}
		case LooperCmd.L_TEXT: {
			currentPanel = textPanel;
			jpanel = textPanel;
			break;
		}
		case LooperCmd.L_FIXED: {
			currentPanel = fixedPanel;
			jpanel = fixedPanel;
			break;
		}
		case LooperCmd.L_COPY: {
			List<Integer> ids = taskModel.getLooperIds();
			copyPanel.setOptions(ids);
			currentPanel = copyPanel;
			jpanel = copyPanel;
			break;
		}
		default: {
			L.debug("Bad looper type: " + looperType);
			return;
		}
		}

		currentPanel.fill(p);
		remove(EMPTY);
		add(jpanel, BorderLayout.CENTER);
		cancelOkBox.setVisible(true);
		validate();
		repaint();
	}

	private void showSingleButton(String ref) {
		for (JButton jb : commandButtons) {
			if (ref.equals(jb.getText())) {
				jb.setVisible(true);
			} else {
				jb.setVisible(false);
			}
		}
		cancelOkBox.setVisible(true);
	}

	private void showNumberLooper() {
		showSingleButton(LooperCmd.L_NUMBER);
		String sel = taskModel.getSelectedUrl();
		if (!LooperCmd.isLooperText(sel)) {
			int newId = LooperId.getNext();
			if (sel.length() == 0) {
				sel = String.format("{%s,%d,%d,%d,%d}", LooperCmd.L_NUMBER, newId, configModel.getNumberFrom(),
						configModel.getNumberTo(), configModel.getNumberPad());
			} else {
				int pad = sel.length();
				sel = String.format("{%s,%d,%d,%d,%d}", LooperCmd.L_NUMBER, newId, configModel.getNumberFrom(),
						configModel.getNumberTo(), pad);
			}
		}
		openLooper(sel);
	}

	private void showFixedLooper() {
		showSingleButton(LooperCmd.L_FIXED);
		String sel = taskModel.getSelectedUrl();
		if (!LooperCmd.isLooperText(sel)) {
			sel = String.format("{%s,%d,%s}", LooperCmd.L_FIXED, LooperId.getNext(), sel);
		}
		openLooper(sel);
	}

	private void showCopyLooper() {
		showSingleButton(LooperCmd.L_COPY);
		String sel = taskModel.getSelectedUrl();
		if (!LooperCmd.isLooperText(sel)) {
			sel = String.format("{%s,%d,%s}", LooperCmd.L_COPY, LooperId.getNext(), sel);
		}
		openLooper(sel);
	}

	private void showListLooper() {
		showSingleButton(LooperCmd.L_LIST);
		String sel = taskModel.getSelectedUrl();
		if (!LooperCmd.isLooperText(sel)) {
			sel = String.format("{%s,%d,%s}", LooperCmd.L_LIST, LooperId.getNext(), sel);
		}
		openLooper(sel);
	}

	private void showTextLooper() {
		showSingleButton(LooperCmd.L_TEXT);
		String sel = taskModel.getSelectedUrl();
		if (!LooperCmd.isLooperText(sel)) {
			sel = String.format("{%s,%d,%s,%s}", LooperCmd.L_TEXT, LooperId.getNext(), configModel.getTextFrom(),
					configModel.getTextTo());
		}
		openLooper(sel);
	}

}
