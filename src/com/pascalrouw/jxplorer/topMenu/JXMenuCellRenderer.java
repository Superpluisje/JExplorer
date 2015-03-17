package com.pascalrouw.jxplorer.topMenu;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.pascalrouw.jxplorer.JXColor;

/**
 * @author Pascal
 * @version 07.05.14 
 * This class makes the list display the name of the file name and icon of the file.
 */
public class JXMenuCellRenderer extends DefaultListCellRenderer{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel iconLabel;
	private JLabel nameLabel;
	private FlowLayout layout;
	private int over = -1;

	/**
	 * Constructor that initiates a JPanel with 2 JLabels as its content
	 * A label for the file icon and a label for the file name
	 */
	public JXMenuCellRenderer() {
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
		
		panel.add(iconLabel);
		panel.add(nameLabel);
	}

	/**
	 * This method specifies the changes that should be made.
	 * @Override
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JXMenuListItem item = (JXMenuListItem)value;
		
		iconLabel.setIcon(item.getIcon());
		nameLabel.setText(item.getValue());
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
}