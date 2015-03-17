package com.pascalrouw.jxplorer.rightMouseMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.pascalrouw.jxplorer.JXColor;

/**
 * MouseListener that adds the hover colors
 * @author Pascal
 * @version 05.06.14
 */
public class JXRightMouseListener implements MouseListener{
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof JLabel){
			JLabel sourceLabel = (JLabel)e.getSource();
			sourceLabel.setBorder(BorderFactory.createLineBorder(JXColor.mouseHoverBorder));
			sourceLabel.setBackground(JXColor.mouseHoverBackground);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof JLabel){
			JLabel sourceLabel = (JLabel)e.getSource();
			sourceLabel.setBorder(BorderFactory.createLineBorder(JXColor.mouseDefaultBorder));
			sourceLabel.setBackground(JXColor.mouseDefaultBackground);
		}
	}
	
	@Override 
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
}
