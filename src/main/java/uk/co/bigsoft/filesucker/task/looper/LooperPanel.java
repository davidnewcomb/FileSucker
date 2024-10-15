package uk.co.bigsoft.filesucker.task.looper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.task.TaskModel;

public class LooperPanel extends JPanel {

	public static final String L_NUMBER = "N";
	public static final String L_TEXT = "T";
	public static final String L_LIST = "L";
	public static final String L_COPY = "C";
	public static final String L_STATIC = "S";
	private static int currentLooperId = 1;

	private JPanel EMPTY = new JPanel();
	private JButton numberButton = new JButton(L_NUMBER);
	private JButton textButton = new JButton(L_TEXT);
	private JButton listButton = new JButton(L_LIST);
	private JButton copyButton = new JButton(L_COPY);
	private JButton staticButton = new JButton(L_STATIC);

	private ILooperPanel currentPanel;
	private ListLooperPanel listPanel = new ListLooperPanel();

	private JLabel numberPanel = new JLabel();
	private JLabel textPanel = new JLabel();
	private JLabel copyPanel = new JLabel();
	private JLabel staticPanel = new JLabel();

	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("Ok");

	private JButton commandButtons[] = { numberButton, textButton, listButton, copyButton, staticButton };

	// private JPanel centre = new JPanel();
	private Box cancelOkBox = Box.createHorizontalBox();
	private TaskModel taskModel;

	public LooperPanel(TaskModel taskModel) {
		super(new BorderLayout());

		this.taskModel = taskModel;

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
		staticButton.addActionListener(e -> showStaticLooper());

		cancelButton.addActionListener(e -> cancelButton());
		okButton.addActionListener(e -> okButton());

	}

	private void okButton() {
		String looperText = currentPanel.toStringBraces();
		taskModel.setSelectedUrl(looperText);

		System.out.println("looperText: " + looperText);

		for (JButton jb : commandButtons) {
			jb.setVisible(true);
		}
		cancelOkBox.setVisible(false);
		remove((JPanel) currentPanel);
		add(EMPTY, BorderLayout.CENTER);
		// validate();
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

		switch (looperType) {
		case LooperPanel.L_LIST: {
			currentPanel = listPanel;
			listPanel.fill(p);

			// works -
			// https://stackoverflow.com/questions/2711104/swapping-out-the-center-jpanel-in-a-borderlayout
//				remove(EMPTY);
//				add(listPanel, BorderLayout.CENTER);
//				repaint();
//				revalidate();

			// Also works
			remove(EMPTY);
			add(listPanel, BorderLayout.CENTER);
			validate();
			break;
		}
		default: {
			System.out.println("Bad looper type: " + looperType);
		}
		}

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
		showSingleButton(L_NUMBER);
	}

	private void showStaticLooper() {
		showSingleButton(L_STATIC);
	}

	private void showCopyLooper() {
		showSingleButton(L_COPY);
	}

	private void showListLooper() {
		showSingleButton(L_LIST);
		String sel = taskModel.getSelectedUrl();
		if (!sel.startsWith("{")) {
			sel = "{" + L_LIST + "," + currentLooperId++ + "," + sel + "}";
		}
		openLooper(sel);

	}

	private void showTextLooper() {
		showSingleButton(L_TEXT);
	}

}
