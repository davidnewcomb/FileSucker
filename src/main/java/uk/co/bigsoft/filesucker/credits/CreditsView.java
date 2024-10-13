package uk.co.bigsoft.filesucker.credits;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import uk.co.bigsoft.filesucker.FileSucker;

public class CreditsView extends JPanel {

	private JLabel numFiles = new JLabel("0");
	private JLabel numBytes = new JLabel("0");
	private JLabel totalNumFiles = new JLabel("0");
	private JLabel totalNumBytes = new JLabel("0");
	private JButton resetCounters = new JButton("Reset");

	public CreditsView() {
		super(new BorderLayout());

		JPanel n = new JPanel(new GridLayout(5, 2));
		n.setBorder(new LineBorder(Color.BLACK));
		n.add(new JLabel("Session Files"));
		n.add(numFiles);
		n.add(new JLabel("Session Download"));
		n.add(numBytes);

		n.add(new JLabel("Total Files"));
		n.add(totalNumFiles);
		n.add(new JLabel("Total Download"));
		n.add(totalNumBytes);

		n.add(new JLabel(""));
		n.add(resetCounters);

		JLabel c = new JLabel();
		c.setText("<html>" + "<body>" + "<center>" + "<table border=1>" + "<tr><td align=center valign=middle nowrap>"
				+ "<b>FileSucker</b><br><hr>" + FileSucker.version + "<br>" + "[" + FileSucker.versionDate + "]"
				+ "</td></tr>" + "</table>" + "</center>" + "</body>" + "</html>");
		c.setHorizontalAlignment(SwingConstants.CENTER);
		c.setVerticalAlignment(SwingConstants.CENTER);

		JLabel s = new JLabel();
		s.setText("<html>" + "<body>" + "<center>" + "<table border=1>" + "<tr><td align=center valign=middle nowrap>"
				+ "David Newcomb<br>" + "(c) BigSoft Limited 2000-2024" + "</td></tr>"
				+ "<tr><td><a href='http://www.bigsoft.co.uk'>http://www.bigsoft.co.uk</a></td></tr>" + "</table>"
				+ "</center>" + "</body>" + "</html>");
		s.setHorizontalAlignment(SwingConstants.CENTER);
		s.setVerticalAlignment(SwingConstants.CENTER);

		add(n, BorderLayout.NORTH);
		add(c, BorderLayout.CENTER);
		add(s, BorderLayout.SOUTH);
	}

	public JLabel getNumFilesLabel() {
		return numFiles;
	}

	public JLabel getNumBytesLabel() {
		return numBytes;
	}

	public JLabel getTotalNumFilesLabel() {
		return totalNumFiles;
	}

	public JLabel getTotalNumBytesLabel() {
		return totalNumBytes;
	}

	public JButton getResetCountersButton() {
		return resetCounters;
	}

}
