package com.pascalrouw.jxplorer.topMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.pascalrouw.jxplorer.JXplorer;

/**
 * MouseListener for the toggle navpane button
 * @author Pascal
 * @version 05.06.14
 */
public class JXMenuNavPaneButton implements MouseListener {
	private JXplorer jxplorer;
	
	public JXMenuNavPaneButton(JXplorer jxplorer){
		this.jxplorer = jxplorer;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(jxplorer.getTreeVisible())
			jxplorer.setTreeVisible(false);
		else 
			jxplorer.setTreeVisible(true);
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