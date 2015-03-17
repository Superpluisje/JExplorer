package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.pascalrouw.jxplorer.JXColor;

/**
 * This class handles the mouse hover on most of the JButtons in this application
 * @author Pascal
 * @version 07.05.14
 */
public class JXButtonHoverListener implements MouseListener{
	
	@Override
	public void mouseEntered(MouseEvent e) {
		JComponent component = (JComponent)e.getSource();
		component.setBackground(JXColor.hoverBackground);
		component.setForeground(JXColor.hoverForeground);
		component.setBorder(BorderFactory.createLineBorder(JXColor.hoverBorder));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JComponent component = (JComponent)e.getSource();
		component.setBackground(null);
		component.setForeground(JXColor.normalForeground);
		component.setBorder(BorderFactory.createLineBorder(component.getParent().getBackground()));
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
