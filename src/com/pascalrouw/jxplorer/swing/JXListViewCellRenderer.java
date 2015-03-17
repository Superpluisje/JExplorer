package com.pascalrouw.jxplorer.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.pascalrouw.jxplorer.JXColor;
import com.pascalrouw.jxplorer.JXListView;
import com.pascalrouw.jxplorer.JXploreFile;

/**
 * @author Pascal
 * @version 07.05.14 
 * This class makes the list display the name of the file name and icon of the file.
 */
public class JXListViewCellRenderer extends DefaultListCellRenderer{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel iconLabel;
	private JLabel nameLabel;
	private JLabel sizeLabel;
	private JLabel modefiedLabel;
	private FlowLayout layout;
	private int over = -1;
	private SimpleDateFormat sdf;

	/**
	 * Constructor that initiates a JPanel with 2 JLabels as its content
	 * A label for the file icon and a label for the file name
	 */
	public JXListViewCellRenderer() {
		sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		layout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		panel = new JPanel();
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panel.setBackground(Color.WHITE);
		
		iconLabel = new JLabel();
		iconLabel.setHorizontalAlignment(CENTER);
		iconLabel.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 4));
		
		nameLabel = new JLabel();
		nameLabel.setHorizontalAlignment(CENTER);
		
		modefiedLabel = new JLabel();
		modefiedLabel.setHorizontalAlignment(CENTER);
		
		sizeLabel = new JLabel();
		sizeLabel.setHorizontalAlignment(CENTER);
		
		panel.add(iconLabel);
		panel.add(nameLabel);
		panel.add(modefiedLabel);
		panel.add(sizeLabel);
	}

	/**
	 * This method specifies the changes that should be made.
	 * @Override
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JXploreFile file = (JXploreFile) value;
		
		if(JXListView.currentLayout == JXListView.layout.LIST){
			nameLabel.setHorizontalAlignment(LEFT);
			iconLabel.setIcon(file.getIcon());
			layout.setAlignment(FlowLayout.LEFT);
			nameLabel.setPreferredSize(null);
			sizeLabel.setText("");
			modefiedLabel.setText("");
		}
		else if(JXListView.currentLayout == JXListView.layout.TILES){
			nameLabel.setHorizontalAlignment(CENTER);
			iconLabel.setIcon(file.getImageIcon(50,50));
			layout.setAlignment(FlowLayout.CENTER);
			nameLabel.setPreferredSize(new Dimension(80,20));
			sizeLabel.setText("");
			modefiedLabel.setText("");
		}
		else if(JXListView.currentLayout == JXListView.layout.DETAILS){
			nameLabel.setHorizontalAlignment(LEFT);
			iconLabel.setIcon(file.getIcon());
			layout.setAlignment(FlowLayout.LEFT);
			nameLabel.setPreferredSize(null);
			nameLabel.setPreferredSize(new Dimension(250,20));
			modefiedLabel.setPreferredSize(new Dimension(200,20));
			modefiedLabel.setText(sdf.format(file.getFile().lastModified())+"");
			if(!file.isFolder())
				sizeLabel.setText(readableFileSize(file.getFile().length()));
			else
				sizeLabel.setText("");
		}
		
		if(JXListView.showExtensions){
			nameLabel.setText(file.getName());
		} else {
			if(!file.isFolder()){
				String name = file.getName();
				if(name.contains("."))
					nameLabel.setText(name.substring(0, name.lastIndexOf('.')));
				else
					nameLabel.setText(file.getName());
			} else
				nameLabel.setText(file.getName());
		}
		nameLabel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		if (isSelected) {
			panel.setBorder(BorderFactory.createLineBorder(JXColor.selectedBorder));
			panel.setBackground(JXColor.selectedBackground);
		} else {
			panel.setBorder(BorderFactory.createLineBorder(JXColor.normalBorder));
			panel.setBackground(JXColor.normalBackground);
			
			if (over == index){
				panel.setBackground(JXColor.hoverBackground);
				panel.setBorder(BorderFactory.createLineBorder(JXColor.hoverBorder));
			}
		}
		
		return panel;
	}
	
	/**
	 * Setter for the int over. Represents the rowID the mouse is currently hovering on
	 * @param over
	 */
	public void setOver(int over){
		this.over = over;
	}
	
	/**
	 * Generates a string for the given long
	 * @param size number that has to be generated into a string
	 * @return returns an easy readable string
	 */
	public String readableFileSize(long size) {
	     if(size <= 0) return "0 B";
	     final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
	     int digitGroups = (int) (Math.log10(size)/Math.log10(1000));
	     return new DecimalFormat("#,##0.00").format(size/Math.pow(1000, digitGroups)) + " " + units[digitGroups];
	}
}