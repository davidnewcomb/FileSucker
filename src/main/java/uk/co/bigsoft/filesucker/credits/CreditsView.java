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

	private static final String URL_PROJECT = "https://github.com/davidnewcomb/FileSucker";
	private JLabel numFiles = new JLabel("0");
	private JLabel numBytes = new JLabel("0");
	private JLabel totalNumFiles = new JLabel("0");
	private JLabel totalNumBytes = new JLabel("0");
	private JButton resetCounters = new JButton("Reset");

	public CreditsView() {
		super(new BorderLayout());
		StringBuilder s;

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

		JLabel ver = new JLabel();
		s = new StringBuilder();
		s.append("<html><body><center>");
		s.append("<table border=1>");
		s.append("<tr><td align=center valign=middle nowrap>");
		s.append("<b>FileSucker</b><br><hr>");
		s.append(FileSucker.version);
		s.append("<br>");
		s.append("[");
		s.append(FileSucker.versionDate);
		s.append("]");
		s.append("</td></tr>");
		s.append("</table>");
		s.append("</center></body></html>");
		ver.setText(s.toString());
		ver.setHorizontalAlignment(SwingConstants.CENTER);
		ver.setVerticalAlignment(SwingConstants.CENTER);

		JLabel own = new JLabel();
		s = new StringBuilder();
		s.append("<html><body><center>");
		s.append("<table border=1>");
		s.append("<tr><td><a href=\"" + URL_PROJECT + "\">" + URL_PROJECT + "</a></td></tr>");
		s.append("<tr><td align=center valign=middle nowrap>");
		s.append("David Newcomb<br>");
		s.append("(c) BigSoft Limited 2000-2024<br>");
		s.append("<a href=\"http://www.bigsoft.co.uk\">http://www.bigsoft.co.uk</a><br>");
		s.append("</td></tr>");
		s.append("</table>");
		s.append("</center></body></html>");
		own.setText(s.toString());
		own.setHorizontalAlignment(SwingConstants.CENTER);
		own.setVerticalAlignment(SwingConstants.CENTER);

		add(n, BorderLayout.NORTH);
		add(ver, BorderLayout.CENTER);
		add(own, BorderLayout.SOUTH);
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
