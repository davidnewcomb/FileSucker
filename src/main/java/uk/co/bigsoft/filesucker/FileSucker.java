package uk.co.bigsoft.filesucker;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipException;

import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;

public class FileSucker
{
    public static String version = "";

    public static String versionDate = "";

    public static LinkedList<SukaThread> activeFileSukaThreads = null;

    public static ConfigData configData = null;

    public static ConfigScreen configScreen = null;

    public static TaskScreen taskScreen = null;

    public static TransferScreen transferScreen = null;

    public static CreditScreen creditScreen = null;

    public static ToolsScreen toolsScreen = null;

    public static void main(String args[])
    {

        // int nTasks = 5 ;
        // long n = 1000L ;
        // int tpSize = 3 ;
        //
        // System.err.println ("Start");
        //
        // ThreadPoolExecutor tpe = new ThreadPoolExecutor (tpSize,
        // tpSize, 1L,
        // TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable> ()) ;
        //
        // Task[] tasks = new Task[nTasks] ;
        // for (int i = 0; i < nTasks; i++)
        // {
        // tasks[i] = new Task (n, "Task " + i) ;
        // System.err.println ("Task "+i);
        // tpe.execute (tasks[i]) ;
        // }
        // U.sleep (10000);
        // for (int i = 0; i < nTasks; i++)
        // {
        // tasks[i] = new Task (n, "Task " + i) ;
        // System.err.println ("Task "+i);
        // tpe.execute (tasks[i]) ;
        // }
        // U.sleep (10000);
        // for (int i = 0; i < nTasks; i++)
        // {
        // tasks[i] = new Task (n, "Task " + i) ;
        // System.err.println ("Task "+i);
        // tpe.execute (tasks[i]) ;
        // }
        // U.sleep (10000);
        //
        // tpe.shutdown () ;
        //
        // System.err.println ("End");
        //
        // System.exit(1);
        // //===
        //    
        //    
        // Read version numbers from the manifest
        try
        {
            URL url = new URL("jar:file:FileSucker.jar!/META-INF/MANIFEST.MF");
            JarURLConnection jarConnection = (JarURLConnection) url
                    .openConnection();
            Manifest manifest = jarConnection.getManifest();
            Attributes attr = manifest.getMainAttributes();
            version = attr.getValue("FileSuka-Version");
            versionDate = attr.getValue("FileSuka-Created");
        }
        catch (ZipException e)
        {
            version = "dev";
            versionDate = "today";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Build tabs
        activeFileSukaThreads = new LinkedList<SukaThread>();
        configData = new ConfigData();
        configScreen = new ConfigScreen();
        taskScreen = new TaskScreen();
        transferScreen = new TransferScreen();
        creditScreen = new CreditScreen();
        toolsScreen = new ToolsScreen();

        // Open window
        new FileSukaFrame();
    }

}
