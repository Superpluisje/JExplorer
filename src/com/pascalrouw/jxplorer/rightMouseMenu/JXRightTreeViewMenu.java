package com.pascalrouw.jxplorer.rightMouseMenu;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.pascalrouw.jxplorer.JXploreFile;
import com.pascalrouw.jxplorer.propertiesFrame.JXProperties;

/**
 * Right mouse menu for JXTreeView
 * @author Pascal
 * @version 05.06.14
 */
public class JXRightTreeViewMenu extends JXRightMouseMenu{
	/**
	 * This constructor creates the buttons and adds listeners
	 * @param startPoint		x and y point of the mouse when right clicked
	 * @param clickedObject		component that is right clicked on
	 * @param jxplorer			current jxplorer
	 * @param file				file clicked on
	 */
	public JXRightTreeViewMenu(Point startPoint, final JTree clickedObject, final JXploreFile file, final TreePath path){
		JLabel expand = createButton("Expand");
		expand.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clickedObject.expandPath(path);
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel properties = createButton("Properties");
		properties.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				new JXProperties(file);
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel[] labels = new JLabel[3];
		labels[0] = expand;
		labels[1] = createSeperator();
		labels[2] = properties;
		
		initFrame(clickedObject, startPoint, labels);
	}
}
