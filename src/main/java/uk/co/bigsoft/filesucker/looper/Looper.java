package uk.co.bigsoft.filesucker.looper;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.ui.taskscreen.AddLooper;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public abstract class Looper extends JPanel // implements Iterator
{
	static int countingIndex = 0;
	protected String selectedUrl;
	protected Integer index = Integer.valueOf(0);
	JPanel centre;
	protected AddLooper addB;
	JButton cancelB;
	protected String[] parameters = new String[0];
	static boolean active;

	public Looper(String sel) {
		super(new BorderLayout());

		selectedUrl = sel;

		// Break up parameters
		int idxi = 0;
		if (selectedUrl != null) {
			try {
				parameters = selectedUrl.substring(1, selectedUrl.length() - 1).split(",");
				String idx = parameters[1]; // command,index,extras
				idxi = Integer.parseInt(idx);
			} catch (Exception e) {
				idxi = 0;
				parameters = new String[0];
			}
		} else {
			idxi = 0;
			parameters = new String[0];
		}

		index = getIndex(idxi);

		active = true;
		TaskScreen.enableRunButton(!active);
		centre = new JPanel();
		centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

		addB = new AddLooper();

		cancelB = new JButton("Cancel");
		cancelB.setToolTipText("Cancel, no action taken");
		cancelB.addActionListener(e -> resetLooper());

		Box bot = Box.createHorizontalBox();
		bot.add(addB);
		bot.add(Box.createHorizontalGlue());
		bot.add(cancelB);

		add(centre, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	public static Integer getIndex(int i) {
		if (i == 0) {
			return Integer.valueOf(++countingIndex);
		}
		return Integer.valueOf(i);
	}

	public void resetLooper() {
//		FileSucker.taskScreen.numberB.setEnabled(true);
//		FileSucker.taskScreen.textB.setEnabled(true);
//		FileSucker.taskScreen.listB.setEnabled(true);
//		FileSucker.taskScreen.copyB.setEnabled(true);
//		FileSucker.taskScreen.staticB.setEnabled(true);
//
//		FileSucker.taskScreen.numberB.setVisible(true);
//		FileSucker.taskScreen.textB.setVisible(true);
//		FileSucker.taskScreen.listB.setVisible(true);
//		FileSucker.taskScreen.copyB.setVisible(true);
//		FileSucker.taskScreen.staticB.setVisible(true);
//
//		FileSucker.taskScreen.iteratorJP.removeAll();
//		FileSucker.taskScreen.iteratorJP.repaint();
		active = false;
		TaskScreen.enableRunButton(!active);
	}

	public JPanel getCentrePanel() {
		return centre;
	}

	public static boolean isActive() {
		return active;
	}

	public abstract String toStringBraces();

	public abstract boolean setParameters();
}

// addB.addKeyListener (new KeyListener ()
// {
//
// public void keyTyped (KeyEvent e)
// {
// //System.out.println ("typed") ;
// TaskScreen.changed () ;
//
// if (!e.isAltDown())
// return;
//
// // if (Looper.isActive ())
// // {
// // TaskScreen.setErrorMessage ("Looper is active") ;
// // return ;
// // }
//
// char c = Character.toLowerCase (e.getKeyChar ());
// switch (c)
// {
// case 'c': FileSucker.taskScreen.copyB.actionPerformed ();
// break;
// case 'l': FileSucker.taskScreen.listB.actionPerformed ();
// break;
// case 'n': FileSucker.taskScreen.numberB.actionPerformed ();
// break;
// case 's': FileSucker.taskScreen.staticB.actionPerformed ();
// break;
// case 't': FileSucker.taskScreen.textB.actionPerformed ();
// break;
// }
//
// }
//
// public void keyReleased (KeyEvent e)
// {
// // Auto-generated method stub
//
// }
//
// public void keyPressed (KeyEvent e)
// {
// // Auto-generated method stub
//
// }
//
// });
