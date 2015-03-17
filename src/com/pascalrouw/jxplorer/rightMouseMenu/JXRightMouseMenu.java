package com.pascalrouw.jxplorer.rightMouseMenu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pascalrouw.jxplorer.JXColor;

/**
 * Parent class of all right mouse menus
 * @author Pascal
 * @version 05.06.14
 */
public class JXRightMouseMenu {
	protected JFrame frame;
	
	/**
	 * Creates frame and panels
	 * @param clickedObject		component rightclicked on
	 * @param mousePoint		position of the mouse
	 * @param labels			all created buttons
	 */
	protected void initFrame(Component clickedObject, Point mousePoint, JLabel[] labels){
		frame = new JFrame();
		frame.getContentPane().setBackground(JXColor.mouseDefaultBackground);
		frame.setLayout(new FlowLayout(FlowLayout.LEFT,2,1));
		frame.setUndecorated(true);
		JComponent contentPane = (JComponent)frame.getContentPane();
		contentPane.setBorder(BorderFactory.createLineBorder(JXColor.mouseSeperatorColor));
		
		frame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
			@Override
			public void windowGainedFocus(WindowEvent e) {}
		});
		
		Point objectPoint = clickedObject.getLocationOnScreen();
		frame.setLocation(mousePoint.x + objectPoint.x, mousePoint.y + objectPoint.y);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(JXColor.mouseDefaultBackground);
		leftPanel.setPreferredSize(new Dimension(25,1));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(JXColor.mouseDefaultBackground);
		rightPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, JXColor.mouseSeperatorColor));
		
		for(JLabel label : labels){
			rightPanel.add(label);
		}
		
		frame.add(leftPanel);
		frame.add(rightPanel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Creates a button with text and returns it
	 * @param text		string the button shows
	 * @return			returns a JLabel
	 */
	protected JLabel createButton(String text){
		JLabel button = new JLabel(" "+text);
		Dimension buttonSize = new Dimension(250,22);
		button.setPreferredSize(buttonSize);
		button.setMinimumSize(buttonSize);
		button.setMaximumSize(buttonSize);
		button.setBorder(BorderFactory.createLineBorder(JXColor.mouseDefaultBackground));
		button.addMouseListener(new JXRightMouseListener());
		button.setOpaque(true);
		button.setBackground(JXColor.mouseDefaultBackground);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		return button;
	}
	
	/**
	 * Creates a seperation line
	 * @return	returns a JLabel
	 */
	protected JLabel createSeperator(){
		JLabel seperator = new JLabel();
		seperator.setOpaque(true);
		seperator.setBackground(JXColor.mouseSeperatorColor);
		Dimension seperatorSize = new Dimension(250,11);
		seperator.setPreferredSize(seperatorSize);
		seperator.setMinimumSize(seperatorSize);
		seperator.setMaximumSize(seperatorSize);
		seperator.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, JXColor.mouseDefaultBackground));
		seperator.setAlignmentX(Component.CENTER_ALIGNMENT);
		return seperator;
	}
	
	/**
	 * Deletes the right mouse menu
	 */
	protected void disposeFrame(){
		frame.setVisible(false);
		frame.dispose();
	}
}
