package uk.co.bigsoft.filesucker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.iharder.Base64;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class ToolsScreen extends JPanel
{
    public static JTextField convertUrlText;

    ToolsScreen()
    {
        super(new BorderLayout());

        Box hbox = Box.createHorizontalBox();
        Box vbox = Box.createVerticalBox();

        vbox.add(hbox);

        add(vbox, BorderLayout.CENTER);

        // hbox.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        // MAX_WID_HEIGHT)) ;

        convertUrlText = new JTextField("");
        convertUrlText.addMouseListener(new MouseListener()
            {
                public void mousePressed(MouseEvent e)
                {
                    int clickedButton = e.getButton();
                    if (clickedButton == 3) // r-click
                    {
                        String s = Utility.getClipboard();
                        if (s != null)
                            convertUrlText.setText(s);
                    }
                }

                public void mouseClicked(MouseEvent arg0)
                { /* empty */
                }

                public void mouseReleased(MouseEvent arg0)
                { /* empty */
                }

                public void mouseEntered(MouseEvent arg0)
                { /* empty */
                }

                public void mouseExited(MouseEvent arg0)
                { /* empty */
                }
            });

        JButton convertHexNum = new JButton("Convert");
        convertHexNum.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    String s = convertUrlText.getText();
                    StringBuffer sb = new StringBuffer();

                    for (int i = 0 ; i < s.length() ; ++i)
                    {
                        char c = s.charAt(i);
                        if (c == '%')
                        {
                            try
                            {
                                StringBuffer num = new StringBuffer();
                                num.append(s.charAt(i + 1));
                                num.append(s.charAt(i + 2));
                                c = (char) Integer.parseInt(num.toString(), 16);
                                i += 2;
                            }
                            catch (StringIndexOutOfBoundsException ex)
                            {
                                // c will not be
                                // affected
                            }
                            catch (NumberFormatException ex)
                            {
                                // c will not be
                                // affected
                            }
                        }
                        sb.append(c);
                    }

                    convertUrlText.setText(sb.toString());
                }
            });

        JButton convertMiddle = new JButton("LastHttp");
        convertMiddle.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    String ss = convertUrlText.getText();
                    int s = ss.indexOf("http");
                    if (s == 0)
                        s = ss.indexOf("http", 1);
                    int another = s;

                    while (another != -1)
                    {
                        s = ss.indexOf("http", another);
                        another = ss.indexOf("http", s + 1);
                    }

                    if (s == -1)
                        return;

                    int end = ss.length();
                    ss = ss.substring(s);
                    s = 0;
                    int ie = ss.indexOf("?");

                    if (ie != -1)
                    {
                        if (end > ie)
                            end = ie;
                    }
                    ie = ss.indexOf("&");
                    if (ie != 1)
                    {
                        if (end > ie)
                            end = ie;
                    }

                    if (end != -1)
                        ss = ss.substring(s, end);

                    convertUrlText.setText(ss);
                }
            });

        JButton convertB64 = new JButton("B64");
        convertB64.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        String selected = convertUrlText.getSelectedText();
                        if (selected == null)
                            return;

                        byte[] decodedBytes = Base64.decode(selected,
                                Base64.DECODE);
                        String decoded = new String(decodedBytes);

                        convertUrlText.setText(decoded);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Base64");
                        ex.printStackTrace();
                    }
                }
            });

        JButton convertB64auto = new JButton("B64auto");
        convertB64auto.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        String text = convertUrlText.getText();
                        // int start =
                        // text.indexOf("aHR0cDovL");
                        int start = text.indexOf("aHR0c");
                        if (start == -1)
                            return;
                        int end = text.indexOf("&", start + 1);
                        if (end == -1)
                            end = text.length();

                        String selected = text.substring(start, end);
                        System.out.println("substring:" + selected + "|");

                        byte[] decodedBytes = Base64.decode(selected,
                                Base64.DECODE);
                        String decoded = new String(decodedBytes);

                        convertUrlText.setText(decoded);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("B64auto");
                        ex.printStackTrace();
                    }
                }
            });

        JButton generateWebPage = new JButton("GenWebPage");
        generateWebPage.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        String text = convertUrlText.getText();
                        text = text.trim();
                        if (text.length() == 0)
                            return;

                        File f = File.createTempFile("FileSuka-", ".html");

                        // TODO - FileWrite does not
                        // handle \n properly
                        FileWriter fw = new FileWriter(f);
                        fw.write("<html>\n<head>\n<title>FileSuka v");
                        fw.write(FileSuka.version);
                        fw.write("</title>\n</head>\n<body>\n<p>\n");

                        StringBuffer s;
                        String remoteFile;
                        UrlSequencer urls = new UrlSequencer(text);
                        UrlSequenceIteration urlsi;
                        for (Iterator<UrlSequenceIteration> i = urls.iterator() ; i
                                .hasNext() ;)
                        {
                            urlsi = i.next();
                            remoteFile = urlsi.getRemoteFile();
                            s = new StringBuffer();
                            s.append("<a href=\"");
                            s.append(remoteFile);
                            s.append("\">");
                            s.append(remoteFile);
                            s.append("</a><br>\n");
                            fw.write(s.toString());
                        }
                        fw.write("\n</body></html>\n");
                        fw.close();

                        // fucking DOS does not honour a
                        // second quoted string in the
                        // command
                        // line
                        // String path = "\"" +
                        // f.toString ().replaceAll
                        // ("\\\\", "\\\\\\\\")
                        // + "\"";
                        String path = f.toString().replaceAll("\\\\",
                                "\\\\\\\\");
                        Utility.launchBrowser(path);
                        f.deleteOnExit();
                    }
                    catch (Exception ex)
                    {
                        System.out.println("GenWebPage");
                        ex.printStackTrace();
                    }
                }
            });

        JButton generateImageWebPage = new JButton("GenImageWebPage");
        generateImageWebPage.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        String text = convertUrlText.getText();
                        text = text.trim();
                        if (text.length() == 0)
                            return;

                        File f = File.createTempFile("FileSuka-", ".html");

                        // TODO - FileWrite does not
                        // handle \n properly
                        FileWriter fw = new FileWriter(f);
                        fw.write("<html>\n<head>\n<title>FileSuka v");
                        fw.write(FileSuka.version);
                        fw.write("</title>\n</head>\n<body>\n<p>\n");

                        StringBuffer s;
                        String remoteFile;
                        UrlSequencer urls = new UrlSequencer(text);
                        UrlSequenceIteration urlsi;
                        for (Iterator<UrlSequenceIteration> i = urls.iterator() ; i
                                .hasNext() ;)
                        {
                            urlsi = i.next();
                            remoteFile = urlsi.getRemoteFile();
                            s = new StringBuffer();
                            s.append("<table><tr><td>");
                            s.append("<a href=\"");
                            s.append(remoteFile);
                            s.append("\"><img src=\"");
                            s.append(remoteFile);
                            s
                                    .append("\" border=\"0\"></a></td></tr><tr><td><a href=\"");
                            s.append(remoteFile);
                            s.append("\">");
                            s.append(remoteFile);
                            s.append("</a></td></tr></table>\n");
                            fw.write(s.toString());
                        }
                        fw.write("\n</body></html>\n");
                        fw.close();

                        // fucking DOS does not honour a
                        // second quoted string in the
                        // command
                        // line
                        // String path = "\"" +
                        // f.toString ().replaceAll
                        // ("\\\\", "\\\\\\\\")
                        // + "\"";
                        String path = f.toString().replaceAll("\\\\",
                                "\\\\\\\\");
                        Utility.launchBrowser(path);
                        f.deleteOnExit();
                    }
                    catch (Exception ex)
                    {
                        System.out.println("GenImageWebPage");
                        ex.printStackTrace();
                    }
                }
            });

        JButton linksPageButton = new JButton("LinksPage");
        linksPageButton.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        int idx;
                        StringBuffer s;
                        String text = convertUrlText.getText();
                        text = text.trim();
                        if (text.length() == 0)
                            return;

                        StringBuffer sb = Utility.downloadFile(text);
                        if (sb.length() == 0)
                            return;

                        List<String> links = getLinks(sb);

                        File f = File.createTempFile("FileSuka-", ".html");

                        // TODO - FileWrite does not
                        // handle \n properly
                        FileWriter fw = new FileWriter(f);
                        fw.write("<html>\n<head>\n<title>FileSuka v");
                        fw.write(FileSuka.version);
                        fw.write("</title>\n</head>\n<body>\n<p>\n");

                        for (String l : links)
                        {
                            s = new StringBuffer();

                            idx = l.lastIndexOf(".") + 1;
                            String extn = l.substring(idx, l.length())
                                    .toLowerCase();
                            if (extn.endsWith("jpg") || extn.endsWith("jpeg"))
                            {
                                s.append("<img src=\"");
                                s.append(l);
                                s.append("\">\n");
                            }
                            else
                            {
                                s.append("<a href=\"");
                                s.append(l);
                                s.append("\">");
                                s.append(l);
                                s.append("</a><br>\n");
                                idx = l.indexOf("&");
                                if (idx != -1)
                                {
                                    l = l.substring(0, idx);
                                    s.append("<a href=\"");
                                    s.append(l);
                                    s.append("\">");
                                    s.append(l);
                                    s.append("</a><br>\n");
                                }
                            }
                            s.append("<br>\n");
                            fw.write(s.toString());
                        }
                        fw.write("\n</body></html>\n");
                        fw.close();

                        // fucking DOS does not honour a
                        // second quoted string in the
                        // command
                        // line
                        // String path = "\"" +
                        // f.toString ().replaceAll
                        // ("\\\\", "\\\\\\\\")
                        // + "\"";
                        String path = f.toString().replaceAll("\\\\",
                                "\\\\\\\\");
                        Utility.launchBrowser(path);
                        f.deleteOnExit();
                    }
                    catch (Exception ex)
                    {
                        TaskScreen.setErrorMessage(ex.getMessage());
                    }

                }
            });

        JButton launchButton = new JButton("Launch");
        launchButton.addActionListener(new ActionListener()
            {

                public void actionPerformed(ActionEvent e)
                {
                    String url = convertUrlText.getText();
                    Utility.launchBrowser(url);
                }
            });

        // JButton launchProfileButton = new JButton ("LaunchProfile") ;
        // launchProfileButton.addActionListener (new ActionListener ()
        // {
        //
        // public void actionPerformed (ActionEvent e)
        // {
        // String url = convertUrlText.getText () ;
        // if (url == null)
        // return ;
        // url = url.trim () ;
        // if (url.trim ().equals (""))
        // return ;
        // try
        // {
        // String sub = list.getSel
        // String helper = FileSuka.configData.getWebHelper
        // ().replaceAll ("%s",
        // url) ;
        // Runtime.getRuntime ().exec (helper) ;
        // }
        // catch (Exception ex)
        // {
        // ex.printStackTrace () ;
        // }
        // }
        // }) ;

        hbox.add(convertHexNum);
        hbox.add(convertMiddle);
        hbox.add(convertB64);
        hbox.add(convertB64auto);
        hbox.add(generateImageWebPage);
        hbox.add(generateWebPage);
        hbox.add(linksPageButton);
        hbox.add(launchButton);

        vbox.add(hbox);

        hbox = Box.createHorizontalBox();
        hbox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        hbox.add(new JLabel("Convert"));
        hbox.add(convertUrlText);

        vbox.add(hbox);
        LaunchProfilePanel lpp = new LaunchProfilePanel();
        lpp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        vbox.add(lpp);
    }

    public static void setConvertUrlText(String s)
    {
        convertUrlText.setText(s);
    }

    /**
     * Converts all urls like "www.google.com" into hyperlinks in the text.
     * 
     * @param initialText
     *            The text to convert
     * @return Converted text.
     */
    protected static List<String> getLinks(StringBuffer initialText)
    {
        LinkedList<String> list = new LinkedList<String>();
        // Pattern p = Pattern.compile
        // ("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?")
        // ;
        Pattern p = Pattern
                .compile("([Hh][Rr][Ee][Ff]=)?([Hh][Rr][Ee][Ff]=)?([Hh][Tt][Tt][Pp]://)(['\"])?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");

        Matcher m = p.matcher(initialText);
        while (m.find())
        {
            String href = m.group();
            if (href.length() < 8)
                continue;

            String h = href.substring(0, 7).toLowerCase();
            if (!h.equals("http://"))
            {
                continue;
            }
            if (!list.contains(href))
                list.add(href);
        }
        return list;
    }
}
