package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.pascalrouw.jxplorer.JXplorer;
import com.pascalrouw.jxplorer.propertiesFrame.JXProperties;

/**
 * This class handles the events for the "Properties" button in the start-menu
 * @author Pascal
 * @version 07.05.14
 */
public class JXMenuPropertiesButton implements MouseListener {
	private JXplorer jxplorer;
	
	/**
	 * Sets jxplorere for later use in creating the properties frame
	 * @param jxplorer
	 */
	public JXMenuPropertiesButton(JXplorer jxplorer){
		this.jxplorer = jxplorer;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(jxplorer.getSelectedJXploreFile() != null){
			new JXProperties(jxplorer.getSelectedJXploreFile());
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
