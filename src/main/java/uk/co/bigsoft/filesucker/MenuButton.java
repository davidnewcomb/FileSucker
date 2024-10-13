package uk.co.bigsoft.filesucker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MenuButton extends JButton
{
    protected HistoryDropDown entries;

    private int maxEntries = 10;

    private LinkedList<MenuButtonListener> listeners = new LinkedList<MenuButtonListener>();

    private MenuButtonListOwner listOwner;

    MenuButton(MenuButtonListOwner listowner)
    {
        super();
        init(listowner);
    }

    private void init(MenuButtonListOwner listowner)
    {
        listOwner = listowner;
        entries = listOwner.getList();
        if (entries.size() > 1)
            setText(entries.first().toString());

        addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    JPopupMenu popUpMenu = new JPopupMenu();
                    for (HistoryElement he : entries)
                    {
                        JMenuItem popUp = new JMenuItem(he.toString());
                        popUp.addActionListener(new ActionListener()
                            {
                                public void actionPerformed(ActionEvent ae)
                                {
                                    JMenuItem m = (JMenuItem) ae.getSource();
                                    MenuButton.this.tellListeners(m.getText());
                                    MenuButton.this.setText(m.getText());
                                }
                            });
                        popUpMenu.add(popUp);
                    }
                    popUpMenu.show(MenuButton.this, e.getX() - 50,
                            e.getY() - 10);
                }
            });
    }

    public void addEntry(String s)
    {
        if (s == null)
            return;

        HistoryElement he = new HistoryElement(s);

        // Refresh the time stamp
        entries.remove(he);
        entries.add(he);

        if (entries.size() > maxEntries)
            entries.removeOldest();

        FileSuka.configData.save();
        listOwner.setList(entries);
    }

    public void addMenuButtonListener(MenuButtonListener x)
    {
        listeners.add(x);
    }

    public void removeMenuButtonListener(MenuButtonListener x)
    {
        listeners.remove(x);
    }

    public void tellListeners(String s)
    {
        for (MenuButtonListener mbl : listeners)
        {
            mbl.changed(s);
        }
    }
}
