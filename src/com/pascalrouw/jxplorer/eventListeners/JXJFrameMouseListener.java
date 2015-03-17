package com.pascalrouw.jxplorer.eventListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.pascalrouw.jxplorer.JXplorer;

/**
 * This class handles the back and forward buttons of the mouse on places
 * where the frame content pane visible is
 * @author Pascal
 * @version 07.05.14
 */
public class JXJFrameMouseListener implements MouseListener{
	private JXplorer jxplorer;
	
	/**
	 * Sets jxplore for later use
	 * @param jxplorer
	 */
	public JXJFrameMouseListener(JXplorer jxplorer){
		this.jxplorer = jxplorer;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 4){
			jxplorer.setPreviousHistoryFile();
		}
		if(e.getButton() == 5){
			jxplorer.setNextHistoryFile();
		}
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
