package com.pascalrouw.jxplorer.topMenu;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.pascalrouw.jxplorer.JXColor;

/**
 * This class manages the hover for the top Menu bar buttons
 * @author Pascal
 * @version 07.05.14
 */
public class JXMenuBarMouseListener implements MouseListener{
	private JXMenuBarView barView;
	private Color backgroundColor;
	private Color foregroundColor;
	
	/**
	 * Sets the default colors for resetting and sets the barview for toggling menus
	 * @param barView the view the menus have to be toggled in
	 * @param backgroundColor the default color of the element
	 * @param foregroundColor the default foreground color of the element
	 */
	public JXMenuBarMouseListener(JXMenuBarView barView, Color backgroundColor, Color foregroundColor){
		this.barView = barView;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel label = (JLabel)e.getSource();
		label.setBackground(JXColor.hoverBackground);
		label.setForeground(JXColor.hoverForeground);
		label.setBorder(BorderFactory.createLineBorder(JXColor.hoverBorder));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel)e.getSource();
		label.setBackground(backgroundColor);
		label.setForeground(foregroundColor);
		label.setBorder(BorderFactory.createLineBorder(backgroundColor));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		barView.toggleMenuPanel(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
