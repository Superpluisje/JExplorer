package com.pascalrouw.jxplorer.eventListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.pascalrouw.jxplorer.JXploreFile;
import com.pascalrouw.jxplorer.JXplorer;
import com.pascalrouw.jxplorer.rightMouseMenu.JXRightListViewMenu;
import com.pascalrouw.jxplorer.rightMouseMenu.JXRightTreeViewMenu;

/**
 * This is the mouselistener added to every component in the program, and is 
 * responsible for showing right mouse menus
 * @author Pascal
 * @version 18.05.14
 */
public class JXRightMouseListener implements MouseListener{
	private JXplorer jxplorer;
	
	/**
	 * Sets jxplore for later use
	 * @param jxplorer
	 */
	public JXRightMouseListener(JXplorer jxplorer){
		this.jxplorer = jxplorer;
	}
	
	/**
	 * Create right mouse menu 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			if(e.getSource() instanceof JList){
				JList<?> source = (JList<?>)e.getSource();
				source.setSelectedIndex(source.locationToIndex(e.getPoint()));
				JXploreFile file = (JXploreFile)source.getSelectedValue();
				
				new JXRightListViewMenu(e.getPoint(), source, jxplorer, file);
			} else 
			if(e.getSource() instanceof JTree){
				JTree source = (JTree)e.getSource();
				TreePath path = source.getPathForLocation(e.getPoint().x, e.getPoint().y);
				
				if(path != null){
					source.setSelectionPath(path);
					JXploreFile file = (JXploreFile)path.getLastPathComponent();
					new JXRightTreeViewMenu(e.getPoint(), source, file, path);
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
}
