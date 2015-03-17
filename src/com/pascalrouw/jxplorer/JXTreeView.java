package com.pascalrouw.jxplorer;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.pascalrouw.jxplorer.eventListeners.JXRightMouseListener;
import com.pascalrouw.jxplorer.swing.JXTreeCellRenderer;

/** 
 * @author Pascal
 * @version 01.04.14
 * This class creates the JTree displayed on the left side of the program
 */
public class JXTreeView extends JXploreView implements MouseListener{
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private JScrollPane pane;
	private TreeModel treeModel;
	private JXTreeCellRenderer treeRenderer;
	
	/**
	 * Constructor of JXTreeView, creating the JScrollPane
	 */
	public JXTreeView(JXplorer data) {
		this.data = data;
		
		JXploreFile jxploreFile = new JXploreFile();
		treeModel = new DefaultTreeModel(jxploreFile);
		treeRenderer = new JXTreeCellRenderer();
		
		pane = new JScrollPane();
		pane.setBorder(null);
		
		
		tree = new JTree();
		tree.setModel(treeModel);
		tree.setCellRenderer(treeRenderer);
		tree.addMouseListener(this);
		tree.setLayout(new BorderLayout());
		tree.setToggleClickCount(2);
		tree.addKeyListener(new KeyAdapter() {
			   public void keyPressed(KeyEvent ke) {
				   if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					   JXploreFile path = (JXploreFile)tree.getLastSelectedPathComponent();
					   tree.expandPath(tree.getSelectionPath());
					   if(path != null){
						   getData().setCurrentFile(path);
					   }
				   }
			   }
		});
		tree.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		tree.addMouseListener(new JXRightMouseListener(data));
		
		pane.setViewportView(tree);
	}
	
	/**
	 * getter for pane
	 * @return pane returns the main JScrollPane
	 */
	public JScrollPane getViewPane(){
		return pane;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JXploreFile path = (JXploreFile)tree.getLastSelectedPathComponent();
		if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1){
			if(path != null){
				data.setCurrentFile(path);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){
		if(e.getButton() == 4){
			data.setPreviousHistoryFile();
		} else
		if(e.getButton() == 5){
			data.setNextHistoryFile();
		}
		
	}
	@Override
	public void update(JXplorer data) {}
}