package uk.co.bigsoft.filesucker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import uk.co.bigsoft.filesucker.ui.taskscreen.AddLooper;
import uk.co.bigsoft.filesucker.ui.taskscreen.TaskScreen;


public abstract class Looper extends JPanel // implements Iterator
{
    static int countingIndex = 0;

    String selectedUrl;

    Integer index = new Integer(0);

    JPanel centre;

    AddLooper addB;

    JButton cancelB;

    String[] parameters = new String[0];

    static boolean active;

    Looper(String sel)
    {
        super(new BorderLayout());

        selectedUrl = sel;

        // Break up parameters
        int idxi = 0;
        if (selectedUrl != null)
        {
            try
            {
                parameters = selectedUrl.substring(1, selectedUrl.length() - 1)
                        .split(",");
                String idx = parameters[1]; // command,index,extras
                idxi = Integer.parseInt(idx);
            }
            catch (Exception e)
            {
                idxi = 0;
                parameters = new String[0];
            }
        }
        else
        {
            idxi = 0;
            parameters = new String[0];
        }

        index = getIndex(idxi);

        active = true;
        TaskScreen.enableRunButton(!active);
        centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));

        addB = new AddLooper();

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
        // case 'c': FileSuka.taskScreen.copyB.actionPerformed ();
        // break;
        // case 'l': FileSuka.taskScreen.listB.actionPerformed ();
        // break;
        // case 'n': FileSuka.taskScreen.numberB.actionPerformed ();
        // break;
        // case 's': FileSuka.taskScreen.staticB.actionPerformed ();
        // break;
        // case 't': FileSuka.taskScreen.textB.actionPerformed ();
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
        cancelB = new JButton("Cancel");
        cancelB.setToolTipText("Cancel, no action taken");
        cancelB.addActionListener(
        // {{{
                new ActionListener() // which does the actual
                // Delete operation
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            resetLooper();
                        }
                    });

        Box bot = Box.createHorizontalBox();
        bot.add(addB);
        bot.add(Box.createHorizontalGlue());
        bot.add(cancelB);

        add(centre, BorderLayout.CENTER);
        add(bot, BorderLayout.SOUTH);
    }

    public static Integer getIndex(int i)
    {
        if (i == 0)
            return new Integer(++countingIndex);
        return new Integer(i);
    }

    public void resetLooper()
    {
        FileSucker.taskScreen.numberB.setEnabled(true);
        FileSucker.taskScreen.textB.setEnabled(true);
        FileSucker.taskScreen.listB.setEnabled(true);
        FileSucker.taskScreen.copyB.setEnabled(true);
        FileSucker.taskScreen.staticB.setEnabled(true);

        FileSucker.taskScreen.numberB.setVisible(true);
        FileSucker.taskScreen.textB.setVisible(true);
        FileSucker.taskScreen.listB.setVisible(true);
        FileSucker.taskScreen.copyB.setVisible(true);
        FileSucker.taskScreen.staticB.setVisible(true);

        FileSucker.taskScreen.iteratorJP.removeAll();
        FileSucker.taskScreen.iteratorJP.repaint();
        active = false;
        TaskScreen.enableRunButton(!active);
    }

    JPanel getCentrePanel()
    {
        return centre;
    }

    public static boolean isActive()
    {
        return active;
    }

    public abstract String toStringBraces();

    public abstract boolean setParameters();
}
