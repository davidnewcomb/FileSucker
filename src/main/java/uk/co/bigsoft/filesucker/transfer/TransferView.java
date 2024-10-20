package uk.co.bigsoft.filesucker.transfer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.co.bigsoft.filesucker.transfer.task.SuckerTaskView;

public class TransferView extends JPanel {
	private JPanel transfersPanel = new JPanel();
	private LinkedList<JPanel> panels = new LinkedList<>();

	public TransferView() {
		super(new BorderLayout());

		transfersPanel.setLayout(new BoxLayout(transfersPanel, BoxLayout.Y_AXIS));
		JScrollPane jsp = new JScrollPane(transfersPanel);

		// TODO is this still needed?
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(e -> updateScreen());

		add(jsp, BorderLayout.CENTER);
		add(updateButton, BorderLayout.SOUTH);
	}

	private void updateScreen() {
		transfersPanel.revalidate();
	}

	public void addTask(SuckerTaskView panel) {
		panels.addFirst(panel);
		refreshPanels();
	}

	public void removeTask(SuckerTaskView panel) {
		panels.remove(panel);
		refreshPanels();
	}

	private void refreshPanels() {
		transfersPanel.removeAll();
		panels.stream().forEach(p -> transfersPanel.add(p));
		transfersPanel.revalidate(); // TODO needed?
		transfersPanel.repaint(); // TODO needed?
	}
}
