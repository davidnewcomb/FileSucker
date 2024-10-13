package uk.co.bigsoft.filesucker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class CreditScreen extends JPanel
{
    private static final long DIV_K = 1024L;
    private static final long DIV_MB = 1024L * 1024L;
    private static final long DIV_GB = 1024L * 1024L * 1024L;
    private static final long DIV_TB = 1024L * 1024L * 1024L * 1024L;
    private static final long DIV_PB = 1024L * 1024L * 1024L * 1024L * 1024L;

    // Counters this session
    protected static long numFiles = 0;

    protected static long numBytes = 0;

    private static JLabel jlNumFiles;

    private static JLabel jlNumBytes;

    // Counters ever
    protected static long totalNumFiles = 0;

    protected static long totalNumBytes = 0;

    private static JLabel totaljlNumFiles;

    private static JLabel totaljlNumBytes;

    private static JButton resetCounters;

    CreditScreen()
    {
        super(new BorderLayout());

        jlNumFiles = new JLabel("" + numFiles);
        jlNumBytes = new JLabel("" + numBytes);
        totaljlNumFiles = new JLabel("" + totalNumFiles);
        totaljlNumBytes = new JLabel("" + totalNumBytes);

        resetCounters = new JButton("Reset");
        resetCounters.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    numFiles = 0;
                    numBytes = 0;
                    totalNumFiles = 0;
                    totalNumBytes = 0;

                    redrawFiles();
                    redrawBytes();
                }
            });
        JPanel n = new JPanel(new GridLayout(5, 2));
        n.setBorder(new LineBorder(Color.BLACK));
        n.add(new JLabel("Session Files"));
        n.add(jlNumFiles);
        n.add(new JLabel("Session Download"));
        n.add(jlNumBytes);

        n.add(new JLabel("Total Files"));
        n.add(totaljlNumFiles);
        n.add(new JLabel("Total Download"));
        n.add(totaljlNumBytes);

        n.add(new JLabel(""));
        n.add(resetCounters);

        JLabel c = new JLabel();
        c.setText("<html>" + "<body>" + "<center>" + "<table border=1>"
                + "<tr><td align=center valign=middle nowrap>"
                + "<b>FileSucker</b><br><hr>" + FileSucker.version + "<br>" + "["
                + FileSucker.versionDate + "]" + "</td></tr>" + "</table>"
                + "</center>" + "</body>" + "</html>");
        c.setHorizontalAlignment(SwingConstants.CENTER);
        c.setVerticalAlignment(SwingConstants.CENTER);

        JLabel s = new JLabel();
        s
                .setText("<html>"
                        + "<body>"
                        + "<center>"
                        + "<table border=1>"
                        + "<tr><td align=center valign=middle nowrap>"
                        + "David Newcomb<br>"
                        + "(c) BigSoft Limited 2000-2008"
                        + "</td></tr>"
                        + "<tr><td><a href='http://www.bigsoft.co.uk'>http://www.bigsoft.co.uk</a></td></tr>"
                        + "</table>" + "</center>" + "</body>" + "</html>");
        s.setHorizontalAlignment(SwingConstants.CENTER);
        s.setVerticalAlignment(SwingConstants.CENTER);

        add(n, BorderLayout.NORTH);
        add(c, BorderLayout.CENTER);
        add(s, BorderLayout.SOUTH);
    }

    public synchronized static void addBytes(long b)
    {
        numBytes += b;
        totalNumBytes += b;
        redrawBytes();
    }

    public static void redrawBytes()
    {
        jlNumBytes.setText(bytesToString(numBytes));
        totaljlNumBytes.setText(bytesToString(totalNumBytes));
    }

    public static String bytesToString(long n)
    {
        String u = "";
        double f = 0.0;
        int decimal_places = 0;

        if (n < DIV_K)
        {
            u = " b";
            f = n;
            decimal_places = 0;
        }
        else if (n < DIV_MB)
        {
            u = " K";
            f = (float) n / (float) DIV_K;
            decimal_places = 2;
        }
        else if (n < DIV_GB)
        {
            u = " MB";
            f = (float) n / (float) DIV_MB;
            decimal_places = 3;
        }
        else if (n < DIV_TB)
        {
            u = " GB";
            f = (float) n / (float) DIV_GB;
            decimal_places = 4;
        }
        else if (n < DIV_PB)
        {
            u = " TB";
            f = (float) n / (float) DIV_TB;
            decimal_places = 5;
        }

        String fs = String.valueOf(f);
        if (decimal_places != 0)
        {
            int dp_pos = fs.indexOf('.');
            if (dp_pos != -1)
            {
                int actual_dp = fs.length() - dp_pos;
                if (actual_dp < decimal_places)
                    decimal_places = actual_dp;
                fs = fs.substring(0, dp_pos + decimal_places);
            }
        }
        String dec = "" + fs + " " + u;

        return dec;
    }

    public synchronized static void addFiles(long f)
    {
        numFiles += f;
        totalNumFiles += f;
        redrawFiles();
    }

    public static void redrawFiles()
    {
        jlNumFiles.setText("" + numFiles);
        totaljlNumFiles.setText("" + totalNumFiles);
    }

    public static long getTotalDownloadedFiles()
    {
        return totalNumFiles;
    }

    public static long getTotalDownloadedBytes()
    {
        return totalNumBytes;
    }

    public static void setTotalDownloadedFiles(long l)
    {
        totalNumFiles = l;
        redrawFiles();
    }

    public static void setTotalDownloadedBytes(long l)
    {
        totalNumBytes = l;
        redrawBytes();
    }

}
