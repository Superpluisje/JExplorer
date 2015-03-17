package com.pascalrouw.jxplorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import com.pascalrouw.jxplorer.eventListeners.JXJFrameMouseListener;
import com.pascalrouw.jxplorer.eventListeners.JXRightMouseListener;
import com.pascalrouw.jxplorer.topMenu.JXMenuBarView;

/**
 * @author Pascal
 * @version 01.04.14
 * This is the main class containing the current file and initiating the GUI
 */
public class JXplorer {	
	private JFrame frame;
	private JSplitPane splitPane;
	private JXploreFile file;
	private ArrayList<JXploreView> views;
	private ArrayList<JXploreFile> history = new ArrayList<JXploreFile>();
	private int historyPosition = 0;
	
	/**
	 * Constructor of JXplorer, creating an empty JXploreFile
	 */
	public JXplorer() {
		file = new JXploreFile();
		history.add(file);
	}

	/**
	 * Constructor of JXplorer, creating an JXploreFile with file
	 */
	public JXplorer(JXploreFile file) {
		this.file = file;
		history.add(file);
	}
	
	/**
	 * @return getter for file
	 */
	public JXploreFile getCurrentFile(){
		return file;
	}
	
	/** 
	 * setting file to the previous set file
	 */
	public void setPreviousHistoryFile(){
		if(historyPosition > 0){
			historyPosition--;
			this.file = history.get(historyPosition);
			updateGUI();
		}
	}
	
	/** 
	 * setting file to the next set file
	 */
	public void setNextHistoryFile(){
		if(historyPosition < history.size()-1){
			historyPosition++;
			this.file = history.get(historyPosition);
			updateGUI();
		}
	}
	
	/**
	 * setting current file and adding the last file to history
	 * @param file setter for file
	 */
	public void setCurrentFile(JXploreFile file){
		if(historyPosition != history.size()-1){
			int size = history.size()-1;
			for(int i = historyPosition; i < size; i++){
				if(history.get(i) != null){
					history.remove(i);
				}
			}
		}
		if(!file.getName().equals(this.file.getName())){
			history.add(file);
			historyPosition++;
		}
		this.file = file;
		updateGUI();
	}
	
	/**
	 * Updates every JXploreView object in the views ArrayList
	 */
	public void updateGUI() {
		for (JXploreView view : views) {
			view.update(this);
		}
		setFrameInfo();
	}
	
	/**
	 * Makes a new JXploreView thus initiating the whole GUI
	 */
	private void buildGUI(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(new BorderLayout(5,0));
		frame.setMinimumSize(new Dimension(600,400));
		frame.setPreferredSize(new Dimension(900,700));
		frame.getContentPane().addMouseListener(new JXJFrameMouseListener(this));
		frame.getContentPane().addMouseListener(new JXRightMouseListener(this));
		setFrameInfo();
		
		JPanel viewPanel = new JPanel(new BorderLayout());
		
		views = new ArrayList<JXploreView>();
		JXAdressView addressView = new JXAdressView(this);
		views.add(addressView);
		JXListView listView = new JXListView(this);
		views.add(listView);
		JXStatusView statusView = new JXStatusView(this);
		views.add(statusView);
		JXTreeView treeView = new JXTreeView(this);
		views.add(treeView);
		JXMenuBarView menuView = new JXMenuBarView(this);
		views.add(menuView);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treeView.getViewPane(),listView.getViewPane());
		splitPane.setBackground(Color.WHITE);
		splitPane.setResizeWeight(0.25);
		splitPane.setDividerSize(4);
		splitPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		
		viewPanel.add(addressView, BorderLayout.PAGE_START);
		viewPanel.add(splitPane, BorderLayout.CENTER);
		viewPanel.add(statusView, BorderLayout.PAGE_END);
		
		frame.add(menuView, BorderLayout.PAGE_START);
		frame.add(viewPanel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private void setFrameInfo(){
		// set icon
		Icon icon = file.getIcon();
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TRANSLUCENT);
		icon.paintIcon(null, image.getGraphics(), 0, 0);
		frame.setIconImage(image);
		// set title
		frame.setTitle(file.getName());	
	}
	
	public void updateListLayout(JXListView.layout newLayout){
		JXListView view = (JXListView)views.get(1);
		view.updateLayout(newLayout);
	}
	
	public JXploreFile getSelectedJXploreFile(){
		JXListView view = (JXListView)views.get(1);
		return view.getList().getSelectedValue();
	}
	
	public void setTreeVisible(boolean value){
		JXTreeView view = (JXTreeView)views.get(3);
		view.getViewPane().setVisible(value);
		if(value){
			splitPane.setDividerLocation(0.25);
			splitPane.setDividerSize(4);
		}	
		else {
			splitPane.setDividerLocation(0.0);
			splitPane.setDividerSize(0);
		}
	}
	
	public boolean getTreeVisible(){
		JXTreeView view = (JXTreeView)views.get(3);
		return view.getViewPane().isVisible();
	}
	
	/**
	 * getter for history
	 * @return history contains all previous and next set datas
	 */
	public ArrayList<JXploreFile> getHistory(){
		return history;
	}
	
	/**
	 * getter for historyPosition
	 * @return historyPosition is the index the current data is in the history ArrayList
	 */
	public int getHistoryPosition(){
		return historyPosition;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	/**
	 * Initiates the program
	 * @param args (empty)
	 */
	public static void main(String [] args){
		try	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e){
			System.out.println("Can't find systems default L&F, falling back to javas default L&F");
		}
		
		JXplorer xplorer = new JXplorer();
		xplorer.buildGUI();
	}
}
