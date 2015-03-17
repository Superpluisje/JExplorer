package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.pascalrouw.jxplorer.JXploreFile;

/**
 * This class handles the events for the button "Open" in the start-menu
 * @author Pascal
 * @version 07.05.14
 */
public class JXMenuOpenButton implements MouseListener {
	private JXMenuBarView barView;
	
	/**
	 * Sets barView for opening the current selected file
	 * @param barView
	 */
	public JXMenuOpenButton(JXMenuBarView barView){
		this.barView = barView;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		JXploreFile selected = barView.getData().getSelectedJXploreFile();
		if(selected != null){
	        if(selected.isFolder()){
	        	barView.getData().setCurrentFile(selected);
	        } else {
	        	selected.openFile();
	        }
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
