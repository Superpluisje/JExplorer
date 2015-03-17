package com.pascalrouw.jxplorer.topMenu;

import javax.swing.ImageIcon;

/**
 * List items for inside the "View" menu bar
 * @author Pascal
 * @version 05.06.14
 */
public class JXMenuListItem {
	private String value;
	private ImageIcon icon;
	
	/**
	 * sets fields
	 * @param value		string showed
	 * @param icon		icon showed
	 */
	public JXMenuListItem(String value, ImageIcon icon){
		this.value = value;
		this.icon = icon;
	}
	
	/**
	 * getter for icon
	 * @returns icon
	 */
	public ImageIcon getIcon(){
		return icon;
	}
	
	/**
	 * getter for value
	 * @returns value
	 */
	public String getValue(){
		return value;
	}
}
