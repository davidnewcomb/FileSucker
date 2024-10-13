package uk.co.bigsoft.filesucker;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class SuckeringFileJProgressBar extends JProgressBar
{
    private static final int TRANSFER_ROW_HEIGHT = 20;

    private RunnableSucker downloadInfo = null;

    SuckeringFileJProgressBar(RunnableSucker di)
    {
        downloadInfo = di;
        setValue(0);
        setStringPainted(true);
        setMinimumSize(new Dimension(0, TRANSFER_ROW_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFER_ROW_HEIGHT));
        setString(downloadInfo.getRemoteUrl());
        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    System.out.println("mousePressed");
                    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
                    {
                        JPopupMenu popUpMenu = new JPopupMenu();
                        JMenuItem popUpMenuPause;

                        if (getDownloadInfo().isRunning() == true)
                        {
                            popUpMenuPause = new JMenuItem("Pause");
                            popUpMenuPause.addMouseListener(new MouseAdapter()
                                {
                                    @Override
                                    public void mousePressed(MouseEvent ee)
                                    {
                                        TaskScreen
                                                .setErrorMessage("Pause clicked: "
                                                        + getDownloadInfo()
                                                                .getRemoteUrl());
                                        getDownloadInfo().pauseThread();
                                        // setString
                                    }
                                });
                        }
                        else
                        {
                            popUpMenuPause = new JMenuItem("Resume");
                            popUpMenuPause.addMouseListener(new MouseAdapter()
                                {
                                    @Override
                                    public void mousePressed(MouseEvent ee)
                                    {
                                        TaskScreen
                                                .setErrorMessage("Resume clicked: "
                                                        + getDownloadInfo()
                                                                .getRemoteUrl());
                                        getDownloadInfo().resumeThread();
                                    }
                                });
                        }

                        JMenuItem popUpMenuCancelNow = new JMenuItem("Cancel");
                        popUpMenuCancelNow.addMouseListener(new MouseAdapter()
                            {
                                @Override
                                public void mousePressed(MouseEvent ee)
                                {
                                    TaskScreen
                                            .setErrorMessage("CancelNow clicked: "
                                                    + getDownloadInfo()
                                                            .getRemoteUrl());
                                    getDownloadInfo().cancelThread();
                                }
                            });

                        popUpMenu.add(popUpMenuPause);
                        // popUpMenu.addSeperator();
                        popUpMenu.add(popUpMenuCancelNow);
                        popUpMenu.show(SuckeringFileJProgressBar.this, e.getX(),
                                e.getY());
                    }

                }
            });
    }

    // TODO: fns
    public void setCurrentMaxFileLength(long max)
    {
        setMaximum((int) max);
    }

    public void setCurrentDownloadedFileLength(long v)
    {
        setValue((int) v);
    }

    public void expectedLengthUnknown(boolean b)
    {
        setIndeterminate(b);
    }

    protected RunnableSucker getDownloadInfo()
    {
        return downloadInfo;
    }
}
