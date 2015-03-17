package com.pascalrouw.jxplorer;

import javax.swing.JPanel;

/** 
 * @author Pascal
 * @version 01.04.14
 * This class initiates all panel components and updates them when needed
 */
public abstract class JXploreView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JXplorer data;
	
	/**
	 * updates all the data fields in all components and this class
	 * @param data the data that updates
	 */
	public void setData(JXplorer data){
		this.data = data;
	}
	
	/**
	 * returns current data
	 * @return data
	 */
	public JXplorer getData(){
		return data;
	}
	
	public abstract void update(JXplorer data);
}
