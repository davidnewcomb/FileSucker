package uk.co.bigsoft.filesucker.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import uk.co.bigsoft.filesucker.view.LaunchProfileConfigPanel;

public class ConfigView extends JPanel {
	private final static int MAX_WID_HEIGHT = 15;

	private JButton saveB = new JButton("Save");
	private JTextField baseTF = new JTextField();
	private JTextField numberFromTF = new JTextField();
	private JTextField numberToTF = new JTextField();
	private JTextField numberPadTF = new JTextField();
	private JTextField textPostPrefixTF = new JTextField();
	private JTextField textFromTF = new JTextField();
	private JTextField textToTF = new JTextField();
	private JTextField maxTasksFT = new JTextField();
	private JTextField maxSubTaskFT = new JTextField();
	private JTextField findFilesFT = new JTextField();
	private JTextField delaySockReadMsTF = new JTextField();
	private JTextField delayFilesMsTF = new JTextField();
	private JTextField helperWebTF = new JTextField();
	private JTextField helperTextTF = new JTextField();
	private JTextField helperDirectoryTF = new JTextField();
	// private LaunchProfileConfigPanel launchProfileConfig = new
	// LaunchProfileConfigPanel();
	private JButton baseBrowseButton = new JButton("Browse");

	public ConfigView() {
		super(new BorderLayout());

		JPanel centre = new JPanel();
		centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

		add(centre, BorderLayout.CENTER);
		add(saveB, BorderLayout.SOUTH);

		// Base Directory
		centre.add(new JLabel("Home directory"));
		Box hbox = Box.createHorizontalBox();

		baseTF.setMinimumSize(new Dimension(10, 20));
		baseTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		baseTF.setEditable(true);

		// final JButton baseBrowseButton = new JButton("Browse");
//		baseBrowseButton.addActionListener(
//
//				new ActionListener() // which does the actual
//				// Delete operation
//				{
//					public void actionPerformed(ActionEvent e) {
//						JFileChooser fc = new JFileChooser(FileSucker.configData.getBaseDir());
//						fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//						int returnVal = fc.showOpenDialog(baseBrowseButton);
//
//						if (returnVal == JFileChooser.APPROVE_OPTION) {
//							File file = fc.getSelectedFile();
//							FileSucker.configData.setScreenBaseDir(file);
//							// System.out.println("File="+file.toString()+"|");
//						}
//					}
//				});
		hbox.add(baseTF);
		hbox.add(baseBrowseButton);

		centre.add(hbox);

		// Web browser launch
		centre.add(new JLabel("Web browser helper (%s for url)"));
		hbox = Box.createHorizontalBox();

		helperWebTF.setMinimumSize(new Dimension(10, 20));
		helperWebTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		helperWebTF.setEditable(true);
		helperWebTF.setToolTipText("File location of web browser (%s for url substitution)");

		hbox.add(helperWebTF);

		centre.add(hbox);

		// Helper for text files
		centre.add(new JLabel("Text file helper (%s for file)"));
		hbox = Box.createHorizontalBox();

		helperTextTF.setMinimumSize(new Dimension(10, 20));
		helperTextTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		helperTextTF.setEditable(true);
		helperTextTF.setToolTipText("Path to text file viewer (%s for url substitution)");

		hbox.add(helperTextTF);

		centre.add(hbox);

		// Open directory launch
		centre.add(new JLabel("Open directory helper (%s for the directory name)"));
		hbox = Box.createHorizontalBox();

		helperDirectoryTF.setMinimumSize(new Dimension(10, 20));
		helperDirectoryTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		helperDirectoryTF.setEditable(true);
		helperDirectoryTF.setToolTipText("File location of web browser (%s for url substitution)");

		hbox.add(helperDirectoryTF);

		centre.add(hbox);

		// Open directory launch
		centre.add(new JLabel("Launch Profiles"));
		hbox = Box.createHorizontalBox();
		// launchProfileConfig = new LaunchProfileConfigPanel();
		// hbox.add(launchProfileConfig);
		hbox.add(new JLabel("TODO LaunchProfileConfigPanel"));
		centre.add(hbox);

		// Iteration Defaults
		centre.add(new JLabel("Iteration Defaults"));

		JPanel jp = new JPanel(new GridLayout(5, 4));
		jp.setBorder(new LineBorder(Color.BLACK));
		jp.setMinimumSize(new Dimension(10, MAX_WID_HEIGHT));
		jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_WID_HEIGHT * 6));

		jp.add(new JLabel("")); // blank
		jp.add(new JLabel("From"));
		jp.add(new JLabel("To"));
		jp.add(new JLabel("Pad"));

		jp.add(new JLabel("Number"));
		jp.add(numberFromTF);
		jp.add(numberToTF);
		jp.add(numberPadTF);

		jp.add(new JLabel("Text"));
		jp.add(textFromTF);
		jp.add(textToTF);
		jp.add(new JLabel(""));

		jp.add(new JLabel("PostPrefix"));
		jp.add(textPostPrefixTF);
		jp.add(new JLabel(""));
		jp.add(new JLabel(""));

		jp.add(new JLabel("FindFiles"));
		jp.add(findFilesFT);
		jp.add(new JLabel(""));
		jp.add(new JLabel(""));

		centre.add(jp);

		// Thread defaults
		centre.add(new JLabel("Thread Defaults"));

		jp = new JPanel(new GridLayout(2, 2));
		jp.setBorder(new LineBorder(Color.BLACK));
		jp.setMinimumSize(new Dimension(10, MAX_WID_HEIGHT));
		jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_WID_HEIGHT * 4));

		jp.add(new JLabel("Concurrent Tasks"));
		jp.add(new JLabel("Concurrent sub Tasks"));
		jp.add(maxTasksFT);
		jp.add(maxSubTaskFT);

		centre.add(jp);

		// Hiding defaults
		centre.add(new JLabel("Hiding Defaults"));

		jp = new JPanel(new GridLayout(2, 2));
		jp.setBorder(new LineBorder(Color.BLACK));
		jp.setMinimumSize(new Dimension(10, MAX_WID_HEIGHT));
		jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_WID_HEIGHT * 4));

		jp.add(new JLabel("Delay between socket reads (ms)"));
		jp.add(new JLabel("Delay between file downloads (ms)"));
		jp.add(delaySockReadMsTF);
		jp.add(delayFilesMsTF);

		centre.add(jp);
	}

	public JButton getSaveButton() {
		return saveB;
	}

	public void setBaseDirectory(String b) {
		baseTF.setText(b);
	}

	public JTextField getBaseTextField() {
		return baseTF;
	}

	public JTextField getNumberFromTextField() {
		return numberFromTF;
	}

	public JTextField getNumberToTextField() {
		return numberToTF;
	}

	public JTextField getNumberPadTextField() {
		return numberPadTF;
	}

	public JTextField getTextPostPrefixTextField() {
		return textPostPrefixTF;
	}

	public JTextField getTextFromTextField() {
		return textFromTF;
	}

	public JTextField getTextToTextField() {
		return textToTF;
	}

	public JTextField getMaxTasksTextField() {
		return maxTasksFT;
	}

	public JTextField getMaxSubTaskTextField() {
		return maxSubTaskFT;
	}

	public JTextField getFindFilesTextField() {
		return findFilesFT;
	}

	public JTextField getDelaySockReadMsTextField() {
		return delaySockReadMsTF;
	}

	public JTextField getDelayFilesMsTextField() {
		return delayFilesMsTF;
	}

	public JTextField getHelperWebTextField() {
		return helperWebTF;
	}

	public JTextField getHelperTextTextField() {
		return helperTextTF;
	}

	public JTextField getHelperDirectoryTextField() {
		return helperDirectoryTF;
	}

	public LaunchProfileConfigPanel getLaunchProfileConfig() {
		// TODO
		return null; // launchProfileConfig;
	}

	public JButton getBaseBrowseButton() {
		return baseBrowseButton;
	}

	public void setBaseBrowseButton(JButton baseBrowseButton) {
		this.baseBrowseButton = baseBrowseButton;
	}
}
