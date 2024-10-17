package uk.co.bigsoft.filesucker.transfer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TransferView extends JPanel {
	private JPanel transfersPanel;

	public TransferView() {
		super(new BorderLayout());

		transfersPanel = new JPanel();
		transfersPanel.setLayout(new BoxLayout(transfersPanel, BoxLayout.Y_AXIS));

		JScrollPane jsp = new JScrollPane(transfersPanel);

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
}
