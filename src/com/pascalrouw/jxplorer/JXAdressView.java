package com.pascalrouw.jxplorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

import com.pascalrouw.jxplorer.eventListeners.JXRightMouseListener;

/** 
 * @author Pascal
 * @version 07.05.14
 * This class creates the address bar and tool bar
 */
public class JXAdressView extends JXploreView implements MouseListener {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon goIcon;
	private ImageIcon backIcon;
	private ImageIcon upIcon;
	private ImageIcon refresh;
	private ImageIcon search;
	private JTextField addressTextField;
	
	private JPanel leftButtonPanel;
	private JPanel outerAddressFieldPanel;
	
	private JLabel backButton;
	private JLabel forwardButton;
	private JLabel upButton;
	private JLabel refreshButton;
	private JLabel goButton;
	
	private int buttonWidth = 25;
	private int buttonHeight = 25;
	
	/**
	 * Constructor of JXAdressView, creating the JPanel (with components) and initiating all icons
	 * @param jxplorer setting data that is later used in the addressbar's keylistener
	 */
	public JXAdressView(JXplorer jxplorer){
		data = jxplorer;
		
		try{
			goIcon = new ImageIcon(this.getClass().getResource("resources/arrow_go.png"));
			backIcon = new ImageIcon(this.getClass().getResource("resources/arrow_back.png"));
			upIcon = new ImageIcon(this.getClass().getResource("resources/arrow_up.png"));
			refresh = new ImageIcon(this.getClass().getResource("resources/refresh.png"));
			search = new ImageIcon(this.getClass().getResource("resources/search.png"));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		addMouseListener(new JXRightMouseListener(data));
		
		leftButtonPanel = new JPanel();
		leftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
		leftButtonPanel.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
		leftButtonPanel.setBackground(null);
		
		backButton = new JLabel(backIcon);
		backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		backButton.addMouseListener(this);
		backButton.setEnabled(false);
		backButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		
		forwardButton = new JLabel(goIcon);
		forwardButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		forwardButton.addMouseListener(this);
		forwardButton.setEnabled(false);
		forwardButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		
		upButton = new JLabel(upIcon);
		upButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		upButton.addMouseListener(this);
		upButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		upButton.setEnabled(false);
		
		refreshButton = new JLabel(refresh);
		refreshButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		refreshButton.addMouseListener(this);
		refreshButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		
		leftButtonPanel.add(backButton);
		leftButtonPanel.add(forwardButton);
		leftButtonPanel.add(upButton);
		leftButtonPanel.add(refreshButton);
		
		initAddressField(jxplorer);
		
		add(leftButtonPanel, BorderLayout.LINE_START);
		add(outerAddressFieldPanel, BorderLayout.CENTER);
		add(goButton, BorderLayout.LINE_END);
	}
	
	/**
	 * initializes the address field ( file icon, outer border, inner padding panel, text field )
	 * @param jxplorer
	 */
	private void initAddressField(JXplorer jxplorer){
		outerAddressFieldPanel = new JPanel();
		outerAddressFieldPanel.setLayout(new BorderLayout());
		outerAddressFieldPanel.setBorder(BorderFactory.createEmptyBorder(3,0,3,0));
		outerAddressFieldPanel.setBackground(Color.WHITE);
		
		JPanel innerAddressFieldPanel = new JPanel();
		innerAddressFieldPanel.setBorder(new EtchedBorder());
		innerAddressFieldPanel.setBackground(Color.white);
		innerAddressFieldPanel.setLayout(new BorderLayout());
		
		addressTextField = new JTextField();
		addressTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER)
					if (new File(addressTextField.getText()).exists())
						data.setCurrentFile(new JXploreFile(addressTextField.getText()));
					else
						JOptionPane.showMessageDialog(null, "Folder does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		addressTextField.setText(data.getCurrentFile().getPath());
		addressTextField.setBorder(null);
		
		JLabel iconLabel = new JLabel(jxplorer.getCurrentFile().getIcon());
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
		
		innerAddressFieldPanel.add(iconLabel, BorderLayout.WEST);
		innerAddressFieldPanel.add(addressTextField, BorderLayout.CENTER);
		
		goButton = new JLabel(search);
		goButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		goButton.addMouseListener(this);
		goButton.setPreferredSize(new Dimension(buttonWidth+5,buttonHeight));
		
		outerAddressFieldPanel.add(innerAddressFieldPanel, BorderLayout.CENTER);
	}
	
	/**
	 * getter for panel
	 * @return panel the main panel of this class
	 */
	public JPanel getViewPane(){
		return this;
	}
	
	/**
	 * sets the current data and checks for history
	 * @param data setter for data
	 */
	public void update(JXplorer data){
		this.data = data;
		addressTextField.setText(data.getCurrentFile().getPath());
		if(data.getHistoryPosition() == 0)
			backButton.setEnabled(false);
		else 
			backButton.setEnabled(true);
		
		if(data.getHistoryPosition() != data.getHistory().size()-1)
			forwardButton.setEnabled(true);
		else 
			forwardButton.setEnabled(false);
		
		if(FileSystemView.getFileSystemView().getRoots()[0].getPath().equals(addressTextField.getText()))
			upButton.setEnabled(false);
		else
			upButton.setEnabled(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == backButton){
			data.setPreviousHistoryFile();
		}
		if(e.getSource() == forwardButton){
			data.setNextHistoryFile();
		}
		if(e.getSource() == upButton){
			data.setCurrentFile(new JXploreFile(data.getCurrentFile().getFile().getParentFile()));
		}
		if(e.getSource() == refreshButton){
			data.setCurrentFile(data.getCurrentFile());
		}
		if(e.getSource() == goButton){
			if (new File(addressTextField.getText()).exists()){
				data.setCurrentFile(new JXploreFile(addressTextField.getText()));
			} else {
				JOptionPane.showMessageDialog(null, "Folder does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel button = (JLabel)e.getSource();
		if(button.isEnabled()){
			button.setBorder(BorderFactory.createLineBorder(new Color(150,150,255)));
			button.setBackground(new Color(220,220,255));
		}
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
	public void mouseReleased(MouseEvent e) {}
}
