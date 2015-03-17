package com.pascalrouw.jxplorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pascalrouw.jxplorer.eventListeners.JXRightMouseListener;

/** 
 * @author Pascal
 * @version 01.04.14
 * This class represents the lower bar (status bar)
 */
public class JXStatusView extends JXploreView implements MouseListener{
	private static final long serialVersionUID = 1L;
	private JLabel itemCount;
	private JLabel remainingSpace;
	private JLabel listViewLabel;
	private JLabel tiledViewLabel;
	private JPanel secondPanel;
	
	private ImageIcon listViewIcon;
	private ImageIcon tiledViewIcon;
	private ImageIcon listViewIconSelected;
	private ImageIcon tiledViewIconSelected;
	
	/**
	 * Constructor that initiates all parts of the bar
	 * @param data first data
	 */
	public JXStatusView(JXplorer jxplorer){
		data = jxplorer;
		
		try{
			listViewIcon = new ImageIcon(this.getClass().getResource("resources/listView.png"));
			tiledViewIcon = new ImageIcon(this.getClass().getResource("resources/tiledView.png"));
			listViewIconSelected = new ImageIcon(this.getClass().getResource("resources/listView_selected.png"));
			tiledViewIconSelected = new ImageIcon(this.getClass().getResource("resources/tiledView_selected.png"));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout(0,0));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		addMouseListener(new JXRightMouseListener(data));
		
		itemCount = new JLabel();
		itemCount.setText(jxplorer.getCurrentFile().getSubFiles().length + " items");
		itemCount.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		
		secondPanel = new JPanel();
		secondPanel.setLayout(new FlowLayout(0,0, 0));
		secondPanel.setBackground(null);
		
		listViewLabel = new JLabel(listViewIconSelected);
		listViewLabel.addMouseListener(this);
		listViewLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		listViewLabel.setPreferredSize(new Dimension(25,25));
		
		tiledViewLabel = new JLabel(tiledViewIcon);
		tiledViewLabel.addMouseListener(this);
		tiledViewLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		tiledViewLabel.setPreferredSize(new Dimension(25,25));
		
		remainingSpace = new JLabel();
		remainingSpace.setText("remaining space: " + readableFileSize(data.getCurrentFile().getFile().getFreeSpace()));
		remainingSpace.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		
		secondPanel.add(remainingSpace);
		secondPanel.add(listViewLabel);
		secondPanel.add(tiledViewLabel);
		
		add(itemCount, BorderLayout.LINE_START);
		add(secondPanel, BorderLayout.LINE_END);
	}
	
	/**
	 * sets the data of the status labels
	 * @param data for setting the new data
	 */
	@Override
	public void update(JXplorer data) {
		this.data = data;
		itemCount.setText(data.getCurrentFile().getSubFiles().length + " items");
		remainingSpace.setText("remaining space: " + readableFileSize(data.getCurrentFile().getFile().getFreeSpace()));
	}
	
	/**
	 * returns the main view panel
	 * @return panel main view panel
	 */
	public JPanel getViewPane(){
		return this;
	}
	
	/**
	 * sets filesize to readable sizes
	 * @param size the size of the file
	 * @return returns a string of the size
	 */
	public String readableFileSize(long size) {
	     if(size <= 0) return "0";
	     final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
	     int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	     return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == listViewLabel){
			listViewLabel.setIcon(listViewIconSelected);
			tiledViewLabel.setIcon(tiledViewIcon);
			data.updateListLayout(JXListView.layout.LIST);
		}
		if(e.getSource() == tiledViewLabel){
			tiledViewLabel.setIcon(tiledViewIconSelected);
			listViewLabel.setIcon(listViewIcon);
			data.updateListLayout(JXListView.layout.TILES);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel button = (JLabel)e.getSource();
		button.setBorder(BorderFactory.createLineBorder(new Color(150,150,255)));
		button.setBackground(new Color(220,220,255));
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		JLabel button = (JLabel)e.getSource();
		button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		button.setBackground(Color.WHITE);
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
}
