package uk.co.bigsoft.filesucker.transfer.task;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

public class SuckerTaskView extends JPanel {
	private static final int TRANSFER_ROW_HEIGHT = 20;

	private JLabel header = new JLabel();
	private JButton removeButton = new JButton("Remove");
	private JProgressBar taskProgressBar = new JProgressBar();
	private Box suckerItemsContainer = Box.createVerticalBox();

	public SuckerTaskView() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new LineBorder(Color.BLACK));

		header.setMinimumSize(new Dimension(0, 0));
		header.setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));

		removeButton.setMinimumSize(new Dimension(0, 0));
		removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		removeButton.setVisible(false);

		taskProgressBar.setValue(-1);
		taskProgressBar.setMinimum(-1);
		taskProgressBar.setMaximum(100);
		taskProgressBar.setStringPainted(true);
		taskProgressBar.setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
		taskProgressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
		taskProgressBar.setString("...");

		suckerItemsContainer.setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));

		add(header);
		add(taskProgressBar);
		add(suckerItemsContainer);
		add(removeButton);
	}

	public void setTitle(String title) {
		header.setText(title);
	}

	public void setTaskStats(int percentComplete, int success, int failed) {
		StringBuilder s = new StringBuilder(percentComplete + "%");
		if (failed != 0) {
			s.append(" [success=");
			s.append(success);
			s.append(", failed=");
			s.append(failed);
			s.append("]");
		}
		taskProgressBar.setString(s.toString());
		taskProgressBar.setValue(percentComplete);
	}

	public void addSuckerProgressBar(JProgressBar bar) {
		suckerItemsContainer.add(bar);
		redrawContainer();
	}

	public void removeSuckerProgressBar(JProgressBar bar) {
		suckerItemsContainer.remove(bar);
		redrawContainer();
	}

	public void redrawContainer() {
		int c = suckerItemsContainer.getComponentCount();
		int th = TRANSFER_ROW_HEIGHT * c;
		if (c == 0) {
			removeButton.setVisible(true);
		}
		suckerItemsContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, th));
		suckerItemsContainer.revalidate();
		suckerItemsContainer.repaint();
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public JLabel getHeaderLabel() {
		return header;
	}
}
