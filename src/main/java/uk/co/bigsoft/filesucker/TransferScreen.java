package uk.co.bigsoft.filesucker;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TransferScreen extends JPanel {
	private static JPanel transfersPanel;

	private static JScrollPane jsp;

	public TransferScreen() {
		super(new BorderLayout());
		transfersPanel = new JPanel();
		transfersPanel.setLayout(new BoxLayout(transfersPanel, BoxLayout.Y_AXIS));

		jsp = new JScrollPane(transfersPanel);

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateScreen();
				// transfersPanel.repaint();
			}
		});

		add(jsp, BorderLayout.CENTER);
		add(updateButton, BorderLayout.SOUTH);
	}

	protected static void updateScreen() {
		transfersPanel.revalidate();
	}

	public static void addTransferLine(JComponent c) {
		Component[] a = transfersPanel.getComponents();
		transfersPanel.removeAll();
		transfersPanel.add(c);
		for (int i = 0; i < a.length; ++i)
			transfersPanel.add(a[i]);

		transfersPanel.revalidate();
	}

	public static void removeTransferLine(JComponent c) {
		c.setVisible(false);
		transfersPanel.remove(c);
		transfersPanel.revalidate();
		transfersPanel.repaint();
	}

}
