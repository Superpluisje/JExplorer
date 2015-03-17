package com.pascalrouw.jxplorer.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JMenuBar;

/**
 * Represents a JMenuBar with custom background
 * @author Pascal
 * @version 05.06.14
 */
public class JXBackgroundMenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	Color bgColor = Color.WHITE;
	
	/**
	 * Constructor of the class
	 * sets the color field
	 * @param color 
	 */
    public void setColor(Color color){
        bgColor = color;
    }
    
    /**
     * Overrides the default backgroundcolor of the JMenuBar
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
