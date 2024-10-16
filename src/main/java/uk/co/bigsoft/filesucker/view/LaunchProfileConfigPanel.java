package uk.co.bigsoft.filesucker.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import uk.co.bigsoft.filesucker.FileSucker;

public class LaunchProfileConfigPanel extends JPanel {
	protected JList<String> current;

	protected JTextField toAdd;

	protected JButton addButton;

	protected LaunchListModel model;

	public LaunchProfileConfigPanel() {
		super(new GridLayout(1, 3));
		List<String> l = new ArrayList<>(); //FileSucker.configData.getLaunchProfiles();
		model = new LaunchListModel(l);
		current = new JList<>(model);
		current.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// empty
			}

			public void keyReleased(KeyEvent e) {
				// empty
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_DELETE)
					return;
				model.delete(current.getSelectedIndex());
				current.revalidate();
				current.repaint();
			}

		});
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = toAdd.getText().trim();
				model.add(s);
				current.revalidate();
				current.repaint();
			}
		});
		toAdd = new JTextField();

		add(current, BorderLayout.CENTER);
		add(toAdd, BorderLayout.WEST);
		add(addButton, BorderLayout.EAST);
	}
}

class LaunchListModel implements ListModel<String> {
	private TreeSet<String> data;

	private List<ListDataListener> listeners;

	LaunchListModel(List<String> l) {
		listeners = new LinkedList<ListDataListener>();
		data = new TreeSet<String>(new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		if (l != null)
			data.addAll(l);
	}

	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	public String getElementAt(int index) {
		int c = 0;
		for (String o : data) {
			if (c == index)
				return o;
			c++;
		}
		return null;
	}

	public int getSize() {
		return data.size();
	}

	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	public void add(String s) {
		data.add(s);
	}

	public void delete(int i) {
		String s = (String) getElementAt(i);
		data.remove(s);
	}
	// private void tellListeners()
	// {
	// // ListDataListener l;
	// // ListDataEvent e = new ListDataEvent ();
	// //
	// // for (Iterator i = listeners.iterator () ; i.hasNext() ; )
	// // {
	// // l = (ListDataListener) i.next () ;
	// // l.intervalAdded (e);
	// // }
	// }

}
