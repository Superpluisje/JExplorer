package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JOptionPane;

/**
 * This class handles the actions for the "New Folder" button in the start-menu
 * @author Pascal
 * @version 07.05.14
 */
public class JXMenuNewFolderButton implements MouseListener {
	private JXMenuBarView barView;
	
	/**
	 * Sets the barView for creating a new folder later on
	 * @param barView
	 */
	public JXMenuNewFolderButton(JXMenuBarView barView){
		this.barView = barView;
	}
	
	/**
	 * Shows a JOptionPane with inputfield, creates folder when Ok is pressed 
	 * and updates GUI afterwards
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		String outputString = (String)JOptionPane.showInputDialog(
                null,
                "Folder name:",
                "New Folder",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "New Folder");
		if(outputString != null && outputString != ""){
			File newDir = new File(barView.getData().getCurrentFile().getPath()+"/"+outputString);
			newDir.mkdir();
			barView.getData().updateGUI();
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
