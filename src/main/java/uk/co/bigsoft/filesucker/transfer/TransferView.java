package uk.co.bigsoft.filesucker.transfer;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.BoxLayout;
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

		add(jsp, BorderLayout.CENTER);
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
		transfersPanel.revalidate();
		transfersPanel.repaint();
	}
}
