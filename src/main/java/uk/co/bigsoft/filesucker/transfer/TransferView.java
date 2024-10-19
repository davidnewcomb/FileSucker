package uk.co.bigsoft.filesucker.transfer;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import uk.co.bigsoft.filesucker.transfer.view.SuckerTaskView;

public class TransferView extends JPanel {
	private JPanel transfersPanel = new JPanel();

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

	public void addTransferLine(JComponent c) {
		Component[] a = transfersPanel.getComponents();
		transfersPanel.removeAll();
		transfersPanel.add(c);
		for (int i = 0; i < a.length; ++i) {
			transfersPanel.add(a[i]);
		}

		transfersPanel.revalidate();
	}

	public void removeTransferLine(JComponent c) {
		c.setVisible(false);
		transfersPanel.remove(c);
		transfersPanel.revalidate();
		transfersPanel.repaint();
	}

	public void addTask(SuckerTaskView panel) {
		transfersPanel.add(panel);
		transfersPanel.revalidate(); // TODO needed?
		transfersPanel.repaint(); // TODO needed?
	}
}
