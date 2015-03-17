package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JOptionPane;

/**
 * Handles the events for the "Rename" button in the start-menu
 * @author Pascal
 * @version 07.05.14
 */
public class JXMenuRenameButton implements MouseListener {
	private JXMenuBarView barView;
	
	/**
	 * Sets barView for the datafile and updating the GUI
	 * @param barView
	 */
	public JXMenuRenameButton(JXMenuBarView barView){
		this.barView = barView;
	}
	
	/**
	 * Shows JOptionPane with text field. Renames folder or file afterwards and updates GUI
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(barView.getData().getSelectedJXploreFile() != null){
			String outputString = (String)JOptionPane.showInputDialog(
	                null,
	                "New name:",
	                "Change Name",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                null,
	                barView.getData().getSelectedJXploreFile().getFile().getName());
			if(outputString != null && outputString != ""){
				File newFile = new File(barView.getData().getCurrentFile().getPath()+"/"+outputString);
				barView.getData().getSelectedJXploreFile().getFile().renameTo(newFile);
				barView.getData().updateGUI();
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
