package com.pascalrouw.jxplorer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.pascalrouw.jxplorer.eventListeners.JXRightMouseListener;
import com.pascalrouw.jxplorer.swing.JXListViewCellRenderer;

/** 
 * @author Pascal
 * @version 01.04.14
 * This class creates the JList displayed on the right side of the program
 */
public class JXListView extends JXploreView implements MouseListener {
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<JXploreFile> listModel;
	private JList<JXploreFile> list;
	private JScrollPane pane;
	private boolean isListView;
	private JXListViewCellRenderer cellRenderer;
	public static layout currentLayout = layout.LIST;
	public static boolean hideHidden = true;
	public static boolean showExtensions = false;
	public static enum layout{
		LIST, TILES, DETAILS;
	}
	
	/**
	 * Constructor of JXListView, creating the JScrollPane
	 */
	public JXListView(JXplorer jxplorer){
		data = jxplorer;
		pane = new JScrollPane();
		pane.setBorder(null);
		
		listModel = new DefaultListModel<JXploreFile>();
		
		cellRenderer = new JXListViewCellRenderer();
		
		list = new JList<JXploreFile>();
		list.setModel(listModel);
		list.setCellRenderer(cellRenderer);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setFixedCellWidth(300);
		list.addMouseListener(this);
		list.addMouseListener(new JXRightMouseListener(data));
	    list.setListData(sortFiles(jxplorer.getCurrentFile().getSubFiles()));
	    list.addKeyListener(new KeyAdapter() {
			   public void keyPressed(KeyEvent ke) {
				   if(ke.getKeyCode() == KeyEvent.VK_ENTER){
						JXploreFile selected = list.getSelectedValue();
						if(selected != null){
							if(selected.isFolder()){
								data.setCurrentFile(selected);
							} else {
				        		selected.openFile();
				        	}
						}
				   }
			   }
		});
		list.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e) {
				cellRenderer.setOver(list.locationToIndex(e.getPoint()));
				list.repaint();
			}
		});
	    list.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	    
	    pane.setViewportView(list);
	    add(pane);
	}
	
	public void updateLayout(layout newLayout){
		if(newLayout == layout.LIST){
			list.setLayoutOrientation(JList.VERTICAL_WRAP);
			currentLayout = layout.LIST;
			list.setFixedCellWidth(300);
			list.setFixedCellHeight(-1);
		}
		if(newLayout == layout.TILES){
			list.setFixedCellWidth(100);
			list.setFixedCellHeight(100);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			currentLayout = layout.TILES;
		}
		if(newLayout == layout.DETAILS){
			list.setFixedCellWidth(-1);
			list.setFixedCellHeight(-1);
			list.setLayoutOrientation(JList.VERTICAL);
			currentLayout = layout.DETAILS;
		}
		ListModel<JXploreFile> model = list.getModel();
		JXploreFile[] files = new JXploreFile[model.getSize()];
		for(int i = 0; i < model.getSize(); i++){
			files[i] = model.getElementAt(i);  
		}
		
		list.setListData(sortFiles(files));
	}
	
	private JXploreFile[] sortFiles(JXploreFile[] files){
		ArrayList<JXploreFile> sortedFiles = new ArrayList<JXploreFile>();
		ArrayList<JXploreFile> sortedFolders = new ArrayList<JXploreFile>();
		
		for(JXploreFile file : files){
			if(file.isFolder()){
				sortedFolders.add(file);
			} else {
				sortedFiles.add(file);
			}
		}
		
		if(currentLayout == layout.DETAILS){
			Collections.sort(sortedFiles, new Comparator<JXploreFile>() {
		        @Override public int compare(JXploreFile f1, JXploreFile f2) {
		        	Long l1 = new Long(f1.getFile().length());
		    		Long l2 = new Long(f2.getFile().length());
		    		return l2.compareTo(l1);
		        }
		    });
		} else {
			Collections.sort(sortedFiles, new Comparator<JXploreFile>() {
		        @Override public int compare(JXploreFile f1, JXploreFile f2) {
		        	String l1 = f1.getName();
		        	String l2 = f2.getName();
		    		return l1.compareTo(l2);
		        }
		    });
		}
		
		for(JXploreFile file : sortedFiles){
			sortedFolders.add(file);
		}
		
		JXploreFile[] sorted = new JXploreFile[sortedFolders.size()];
		for(int i = 0; i < sortedFolders.size(); i++){
			sorted[i] = sortedFolders.get(i);
		}
		
		return sorted;
	}

	/**
	 * getter for isListView
	 * @return isListView returns boolean if the list is in listview or in tile view
	 */
	public boolean getIsListView(){
		return isListView;
	}	
	
	/**
	 * getter for pane
	 * @return pane returns the main JScrollPane
	 */
	public JScrollPane getViewPane(){
		return pane;
	}
	
	public JList<JXploreFile> getList(){
		return list;
	}
	
	/**
	 * setter for data also sets listdata to the new data
	 * @param data setter for data
	 */
	@Override
	public void update(JXplorer data) {
		this.data = data;
		list.setListData(sortFiles(data.getCurrentFile().getSubFiles()));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
        	JXploreFile selected = list.getSelectedValue();
        	if(selected.isFolder()){
        		data.setCurrentFile(selected);
        	} else {
        		selected.openFile();
        	}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		cellRenderer.setOver(list.locationToIndex(e.getPoint()));
		list.repaint();
	}
	
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
	public void mouseExited(MouseEvent e) {
		cellRenderer.setOver(-1);
		list.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e){}
}
