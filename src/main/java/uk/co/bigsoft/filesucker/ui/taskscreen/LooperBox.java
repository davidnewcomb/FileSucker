package uk.co.bigsoft.filesucker.ui.taskscreen;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LooperBox extends JPanel
{
    ArrayList<JButton> commands = new ArrayList<JButton>();

    LooperBox()
    {
        super(new BorderLayout());

        commands.add(new NumberLooperButton());
        commands.add(new TextLooperButton());
        commands.add(new ListLooperButton());
        commands.add(new CopyLooperButton());
        commands.add(new StaticLooperButton());

        Box hbox = Box.createHorizontalBox();
        for (JButton a : commands)
        {
            hbox.add(a);
        }

        add(BorderLayout.NORTH, hbox);
    }

}
