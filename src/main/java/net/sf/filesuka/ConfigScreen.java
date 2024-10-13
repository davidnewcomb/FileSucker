package net.sf.filesuka;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ConfigScreen extends JPanel
{
    private final static int MAX_WID_HEIGHT = 15;

    protected final JButton saveB;

    protected JTextField baseTF;

    protected JTextField numberFromTF;

    protected JTextField numberToTF;

    protected JTextField numberPadTF;

    protected JTextField textPostPrefixTF;

    protected JTextField textFromTF;

    protected JTextField textToTF;

    protected JTextField maxTasksFT;

    protected JTextField maxSubTaskFT;

    protected JTextField findFilesFT;

    protected JTextField delaySockReadMsTF;

    protected JTextField delayFilesMsTF;

    protected JTextField helperWebTF;

    protected JTextField helperTextTF;

    protected JTextField openDirectoryTF;

    protected LaunchProfileConfigPanel launchProfileConfig;

    ConfigScreen()
    {
        super(new BorderLayout());

        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        add(centre, BorderLayout.CENTER);

        saveB = new JButton("Save");
        saveB.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    saveB.setEnabled(false);

                    FileSuka.configData.setScreenBaseDir(baseTF.getText());
                    FileSuka.configData.setNumberFrom(numberFromTF.getText());
                    FileSuka.configData.setNumberTo(numberToTF.getText());
                    FileSuka.configData.setNumberPad(numberPadTF.getText());
                    FileSuka.configData.setTextFrom(textFromTF.getText());
                    FileSuka.configData.setTextTo(textToTF.getText());
                    FileSuka.configData.setPostPrefix(textPostPrefixTF
                            .getText());
                    FileSuka.configData.setMaxTasks(maxTasksFT.getText());
                    FileSuka.configData.setMaxSubTasks(maxSubTaskFT.getText());
                    FileSuka.configData.setFindExtn(findFilesFT.getText());
                    FileSuka.configData.setDelaySockReadMs(delaySockReadMsTF
                            .getText());
                    FileSuka.configData.setDelayFilesMs(delayFilesMsTF
                            .getText());
                    FileSuka.configData.setHelperWeb(helperWebTF.getText());
                    FileSuka.configData.setHelperWeb(helperTextTF.getText());
                    FileSuka.configData.setOpenDirectory(openDirectoryTF
                            .getText());

                    FileSuka.configData.save();

                    saveB.setEnabled(true);
                }
            });
        add(saveB, BorderLayout.SOUTH);

        // Base Directory
        centre.add(new JLabel("Home directory"));
        Box hbox = Box.createHorizontalBox();

        baseTF = new JTextField(FileSuka.configData.getScreenBaseDir());
        baseTF.setMinimumSize(new Dimension(10, 20));
        baseTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        baseTF.setEditable(true);

        final JButton baseBrowseButton = new JButton("Browse");
        baseBrowseButton.addActionListener(
        // {{{
                new ActionListener() // which does the actual
                // Delete operation
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            JFileChooser fc = new JFileChooser(
                                    FileSuka.configData.getBaseDir());
                            fc
                                    .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                            int returnVal = fc.showOpenDialog(baseBrowseButton);

                            if (returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                File file = fc.getSelectedFile();
                                FileSuka.configData.setScreenBaseDir(file);
                                // System.out.println("File="+file.toString()+"|");
                            }
                        }
                    });
        hbox.add(baseTF);
        hbox.add(baseBrowseButton);

        centre.add(hbox);

        // Web browser launch
        centre.add(new JLabel("Web browser helper (%s for url)"));
        hbox = Box.createHorizontalBox();

        helperWebTF = new JTextField(FileSuka.configData.getHelperWeb());
        helperWebTF.setMinimumSize(new Dimension(10, 20));
        helperWebTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        helperWebTF.setEditable(true);
        helperWebTF
                .setToolTipText("File location of web browser (%s for url substitution)");

        hbox.add(helperWebTF);

        centre.add(hbox);

        // Helper for text files
        centre.add(new JLabel("Text file helper (%s for file)"));
        hbox = Box.createHorizontalBox();

        helperTextTF = new JTextField(FileSuka.configData.getHelperText());
        helperTextTF.setMinimumSize(new Dimension(10, 20));
        helperTextTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        helperTextTF.setEditable(true);
        helperTextTF
                .setToolTipText("Path to text file viewer (%s for url substitution)");

        hbox.add(helperTextTF);

        centre.add(hbox);

        // Open directory launch
        centre.add(new JLabel(
                "Open directory helper (%s for the directory name)"));
        hbox = Box.createHorizontalBox();

        openDirectoryTF = new JTextField(FileSuka.configData.getOpenDirectory());
        openDirectoryTF.setMinimumSize(new Dimension(10, 20));
        openDirectoryTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        openDirectoryTF.setEditable(true);
        openDirectoryTF
                .setToolTipText("File location of web browser (%s for url substitution)");

        hbox.add(openDirectoryTF);

        centre.add(hbox);

        // Open directory launch
        centre.add(new JLabel("Launch Profiles"));
        hbox = Box.createHorizontalBox();
        launchProfileConfig = new LaunchProfileConfigPanel();
        hbox.add(launchProfileConfig);
        centre.add(hbox);

        // Iteration Defaults
        centre.add(new JLabel("Iteration Defaults"));

        JPanel jp = new JPanel(new GridLayout(5, 4));
        jp.setBorder(new LineBorder(Color.BLACK));
        jp.setMinimumSize(new Dimension(10, MAX_WID_HEIGHT));
        jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, MAX_WID_HEIGHT * 6));

        numberFromTF = new JTextField(FileSuka.configData.getScreenNumberFrom());
        numberToTF = new JTextField(FileSuka.configData.getScreenNumberTo());
        numberPadTF = new JTextField(FileSuka.configData.getScreenNumberPad());

        textFromTF = new JTextField(FileSuka.configData.getScreenTextFrom());
        textToTF = new JTextField(FileSuka.configData.getScreenTextTo());
        textPostPrefixTF = new JTextField(FileSuka.configData
                .getScreenPostPrefix());

        findFilesFT = new JTextField(Utility.implode(FileSuka.configData
                .getFindExtn(), ","));
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

        maxTasksFT = new JTextField(FileSuka.configData.getScreenMaxTasks());
        maxSubTaskFT = new JTextField(FileSuka.configData
                .getScreenMaxSubTasks());

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

        delaySockReadMsTF = new JTextField(FileSuka.configData
                .getScreenDelaySockReadMs());
        delayFilesMsTF = new JTextField(FileSuka.configData
                .getScreenDelayFilesMs());

        jp.add(new JLabel("Delay between socket reads (ms)"));
        jp.add(new JLabel("Delay between file downloads (ms)"));
        jp.add(delaySockReadMsTF);
        jp.add(delayFilesMsTF);

        centre.add(jp);
    }

    void setBaseDirectory(String b)
    {
        baseTF.setText(b);
    }
}
