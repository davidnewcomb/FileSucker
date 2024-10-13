package uk.co.bigsoft.filesucker;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class FileSukaFrame extends JFrame
{
    private static final String BOUND_X = "x";

    private static final String BOUND_Y = "y";

    private static final String BOUND_HEIGHT = "height";

    private static final String BOUND_WIDTH = "width";

    private static final String TOTAL_DOWNLOADED_FILES = "totalFiles";

    private static final String TOTAL_DOWNLOADED_BYTES = "totalBytes";

    public static JTabbedPane tabPane;

    public FileSukaFrame()
    {
        super("FileSuka");
        // setIconImage (icon.getImage ()) ;
        addWindowListener(new WindowAdapter() // which exits when the
        // window is
        // closed
            {
                @Override
                public void windowClosed(WindowEvent e)
                {
                    e.getWindow().dispose();
                }

                @Override
                public void windowClosing(WindowEvent e)
                {
                    Rectangle rect = getBounds();
                    Preferences prefs = Preferences
                            .userNodeForPackage(FileSukaFrame.class);
                    prefs.putInt(BOUND_X, rect.x);
                    prefs.putInt(BOUND_Y, rect.y);
                    prefs.putInt(BOUND_HEIGHT, rect.height);
                    prefs.putInt(BOUND_WIDTH, rect.width);
                    prefs.putLong(TOTAL_DOWNLOADED_FILES, CreditScreen
                            .getTotalDownloadedFiles());
                    prefs.putLong(TOTAL_DOWNLOADED_BYTES, CreditScreen
                            .getTotalDownloadedBytes());

                    System.exit(0);
                }
            });

        tabPane = new JTabbedPane();
        getContentPane().add(tabPane);

        // Set up page
        tabPane.addTab("NewTask", null, FileSuka.taskScreen, "Create new task");
        tabPane.addTab("Transfer", null, FileSuka.transferScreen,
                "View tasks in progress");
        tabPane.addTab("Tools", null, FileSuka.toolsScreen, "Text tools");
        tabPane.addTab("Options", null, FileSuka.configScreen,
                "Change default options");
        tabPane.addTab("Credits", null, FileSuka.creditScreen,
                "Statistics & Credits");

        pack();

        Preferences prefs = Preferences.userNodeForPackage(FileSukaFrame.class);
        int x = prefs.getInt(BOUND_X, 50);
        int y = prefs.getInt(BOUND_Y, 50);
        int height = prefs.getInt(BOUND_HEIGHT, 200);
        int width = prefs.getInt(BOUND_WIDTH, 400);
        setBounds(x, y, width, height);

        CreditScreen.setTotalDownloadedFiles(prefs.getLong(
                TOTAL_DOWNLOADED_FILES, 0));
        CreditScreen.setTotalDownloadedBytes(prefs.getLong(
                TOTAL_DOWNLOADED_BYTES, 0));

        setVisible(true);
    }
}
