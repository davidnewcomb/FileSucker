package net.sf.filesuka.drag;

import java.awt.Color;

import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

/*
 * File_Viewer.java
 */

// A private subclass of the default highlight painter
public class TextPainter extends DefaultHighlightPainter
{
    public TextPainter(Color color)
    {
        super(color);
    }
}
