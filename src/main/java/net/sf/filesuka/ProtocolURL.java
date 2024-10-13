package net.sf.filesuka;

import java.net.MalformedURLException;
import java.net.URL;

public class ProtocolURL
{
    private URL url;

    private int port;

    ProtocolURL(String u) throws MalformedURLException
    {
        url = new URL(u);
        port = url.getPort();
        if (port == -1)
        {
            if (url.getProtocol().equalsIgnoreCase("http"))
                port = 80;
            else if (url.getProtocol().equalsIgnoreCase("https"))
                port = 443;
        }
    }

    public String getHost()
    {
        return url.getHost();
    }

    public int getPort()
    {
        return port;
    }

    public String getFile()
    {
        return url.getFile();
    }

    public String getPath()
    {
        return url.getPath();
    }

    @Override
    public String toString()
    {
        return url.toString();
    }

    public String getUserInfo()
    {
        return url.getUserInfo();
    }

    public void setUserInfo(String up)
    {
        if (up == null)
            return;

        StringBuffer sb = new StringBuffer();
        sb.append(url.getProtocol());
        sb.append("://");
        sb.append(up);
        sb.append("@");
        sb.append(url.getHost());
        sb.append(url.getPath());

        try
        {
            url = new URL(sb.toString());
        }
        catch (Exception e)
        {
            System.out.println("setUserInfo:");
            e.printStackTrace();
        }
    }

}
