package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class handles the action for the "Close" button in the start-menu
 * @author Pascal
 * @version 07.05.14
 */
public class JXMenuCloseButton implements MouseListener {

	@Override
	public void mouseReleased(MouseEvent e) {
		System.exit(0);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
}
