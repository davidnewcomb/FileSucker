package uk.co.bigsoft.filesucker.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.co.bigsoft.filesucker.Utility;

public class LaunchProfilePanel extends JPanel {
	private JComboBox<String> list = new JComboBox<String>();
	private JButton submit = new JButton("LaunchProfile");

	public LaunchProfilePanel() {
		super(new BorderLayout());

		list.setEditable(true);

		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String sub = ToolsScreen.convertUrlText.getText().trim();
				if (sub.equals("")) {
					return;
				}

				try {
					sub = sub.replace(' ', '+');
					sub = URLEncoder.encode(sub, "UFT-8");
				} catch (UnsupportedEncodingException ex) {
					// nothing
				}

				String url = (String) list.getSelectedItem();
				url = url.replaceAll("%s", sub);

				try {
					String helper = ""; // FileSucker.configData.getHelperWeb().replaceAll("%s", url);
					Utility.runShellCommand(helper);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		});

		add(list, BorderLayout.CENTER);
		add(submit, BorderLayout.EAST);
		refresh();
	}

	private void refresh() {
		list.removeAllItems();
		List<String> opts = new ArrayList<>(); // FileSucker.configData.getLaunchProfiles();
		for (String s : opts) {
			list.addItem(s);
		}
	}
}

// TODO refactor into a general class
class PasteMouseListener implements MouseListener {
	private JTextField m_textfield;

	PasteMouseListener(JTextField textfield) {
		m_textfield = textfield;
	}

	public void mousePressed(MouseEvent e) {
		int clickedButton = e.getButton();
		if (clickedButton == 3)
			rightClick();
		else
			leftClick();
	}

	public void mouseReleased(MouseEvent e) {
		// empty
	}

	public void mouseClicked(MouseEvent e) {
		// empty
	}

	public void mouseEntered(MouseEvent e) {
		// empty
	}

	public void mouseExited(MouseEvent e) {
		// empty
	}

	private void rightClick() {
		String s = Utility.getClipboard();
		if (s != null) {
			m_textfield.setText(s);
			// taskScreen.changed();
		}
	}

	private void leftClick() {
		String s = m_textfield.getText();
		int pos = m_textfield.getCaretPosition();
		int len = m_textfield.getText().length();
		int startpos = -1, endpos = -1;
		// System.out.println("mousePressed: "+pos);
		pos--;
		// Backwards
		for (int i = pos; i >= 0; --i) {
			if (s.charAt(i) == '{') {
				startpos = i;
				break;
			} else if (s.charAt(i) == '}')
				return;
		}
		// Forwards
		if (pos == -1)
			pos = 0;
		for (int i = pos; i < len; ++i) {
			if (s.charAt(i) == '}') {
				endpos = i + 1; // Move to the next gap
				break;
			} else if (s.charAt(i) == '{')
				return;
		}
		if (startpos == -1 || endpos == -1)
			return;
		m_textfield.setSelectionStart(startpos);
		m_textfield.setSelectionEnd(endpos);
	}

}
