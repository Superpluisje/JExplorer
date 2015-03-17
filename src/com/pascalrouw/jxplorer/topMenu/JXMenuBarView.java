package com.pascalrouw.jxplorer.topMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.pascalrouw.jxplorer.JXListView;
import com.pascalrouw.jxplorer.JXploreView;
import com.pascalrouw.jxplorer.JXplorer;

/**
 * This class represents the top menu bar
 * @author Pascal
 * @version 05.06.14
 */
public class JXMenuBarView extends JXploreView{
	private static final long serialVersionUID = 1L;
	
	private Dimension buttonDimension = new Dimension(60,23);
	private JPanel barPanel;
	private JPanel menuPanel;
	
	private JLabel start;
	private JLabel view;
	
	private JPanel startMenu;
	private JPanel viewMenu;
	private JList<JXMenuListItem> layoutList;
	private JXMenuCellRenderer cellRenderer;
	private JCheckBox showExtensions;
	private JCheckBox showHidden;
	
	/**
	 * Creates all the bars and adds them to a main panel
	 * @param jxplorer
	 */
	public JXMenuBarView(JXplorer jxplorer){
		data = jxplorer;
		
		setLayout(new BorderLayout());
		
		barPanel = new JPanel();
		barPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200,200,200)));
		barPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		barPanel.setBackground(Color.WHITE);
		barPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		start = createMenuItem("Start", new Color(25,121,202), Color.WHITE);
		view = createMenuItem("View", Color.WHITE, null);
		
		barPanel.add(start);
		barPanel.add(view);
		
		menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setBackground(new Color(245,246,247));
		menuPanel.setBackground(null);
		menuPanel.setVisible(false);
		
		add(barPanel, BorderLayout.PAGE_START);
		add(menuPanel, BorderLayout.PAGE_END);
		
		startMenu = createStartMenu();
		viewMenu = createViewMenu();
		menuPanel.add(startMenu);
		menuPanel.add(viewMenu);
	}
	
	/**
	 * creates the most left menu and returns it
	 * @return	returns a JPanel with all the components ready
	 */
	private JPanel createStartMenu(){
		ImageIcon openIcon = null;
		ImageIcon propertiesIcon = null;
		ImageIcon changeNameIcon = null;
		ImageIcon newFolderIcon = null;
		ImageIcon closeIcon = null;
		try{
			propertiesIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/properties.png"));
			openIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/open.png"));
			changeNameIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/change_name.png"));
			newFolderIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/new_folder.png"));
			closeIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/exit.png"));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		
		JPanel propertiesButton = tileButtonPanel(propertiesIcon, "Properties");
		propertiesButton.addMouseListener(new JXMenuPropertiesButton(data));
		
		JPanel openButton = tileButtonPanel(openIcon, "Open");
		openButton.addMouseListener(new JXMenuOpenButton(this));
		
		JPanel changeNameButton = tileButtonPanel(changeNameIcon, "Change Name");
		changeNameButton.addMouseListener(new JXMenuRenameButton(this));
		
		JPanel newFolderButton = tileButtonPanel(newFolderIcon, "New Folder");
		newFolderButton.addMouseListener(new JXMenuNewFolderButton(this));
		
		JPanel closeButton = tileButtonPanel(closeIcon, "Close");
		closeButton.addMouseListener(new JXMenuCloseButton());
		
		startPanel.add(propertiesButton);
		startPanel.add(openButton);
		startPanel.add(changeNameButton);
		startPanel.add(seperatorPanel());
		startPanel.add(newFolderButton);
		startPanel.add(seperatorPanel());
		startPanel.add(closeButton);
		
		startPanel.setVisible(false);
		
		return startPanel;
	}
	
	/**
	 * creates the second menu and returns it
	 * @return	returns a JPanel with all the components ready
	 */
	public JPanel createViewMenu(){
		ImageIcon navPaneIcon = null;
		ImageIcon tilesIcon = null;
		ImageIcon listIcon = null;
		ImageIcon detailsIcon = null;
		try{
			navPaneIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/nav_pane.png"));
			tilesIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/tiles.png"));
			listIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/list.png"));
			detailsIcon = new ImageIcon(getClass().getClassLoader().getResource("com/pascalrouw/jxplorer/resources/details.png"));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		JPanel viewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		
		JPanel navPaneButton = tileButtonPanel(navPaneIcon, "Navigation Pane");
		navPaneButton.addMouseListener(new JXMenuNavPaneButton(data));
		
		cellRenderer = new JXMenuCellRenderer();
		JXMenuListItem[] items = {new JXMenuListItem("Tiles",tilesIcon),new JXMenuListItem("List",listIcon),new JXMenuListItem("Details",detailsIcon)};
		layoutList = new JList<JXMenuListItem>(items);
		layoutList.setCellRenderer(cellRenderer);
		layoutList.setPreferredSize(new Dimension(200,62));
		layoutList.setBorder(BorderFactory.createLineBorder(new Color(226,227,228), 1));
		layoutList.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e) {
				cellRenderer.setOver(layoutList.locationToIndex(e.getPoint()));
				layoutList.repaint();
			}
			
		});
		layoutList.addMouseListener(new MouseListener(){
			@Override
			public void mouseExited(MouseEvent e) {
				cellRenderer.setOver(-1);
				layoutList.repaint();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		layoutList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = layoutList.getSelectedIndex();
				switch(index){
					case 0: data.updateListLayout(JXListView.layout.TILES);
					break;
					case 1: data.updateListLayout(JXListView.layout.LIST);
					break;
					case 2: data.updateListLayout(JXListView.layout.DETAILS);
					break;
				}
			}
		});
		
		JPanel checkboxPanel = new JPanel(new BorderLayout());
		
		showExtensions = new JCheckBox("Show file extensions");
		showExtensions.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					JXListView.showExtensions = true;
					data.updateGUI();
				} else {
					JXListView.showExtensions = false;
					data.updateGUI();
				}
			}
		});
		
		showHidden = new JCheckBox("Show hidden files");
		showHidden.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					JXListView.hideHidden = false;
					data.updateGUI();
				} else {
					JXListView.hideHidden = true;
					data.updateGUI();
				}
			}
		});
		
		checkboxPanel.add(showExtensions, BorderLayout.PAGE_START);
		checkboxPanel.add(showHidden, BorderLayout.PAGE_END);
		
		viewPanel.add(navPaneButton);
		viewPanel.add(seperatorPanel());
		viewPanel.add(layoutList);
		viewPanel.add(seperatorPanel());
		viewPanel.add(checkboxPanel);
		
		viewPanel.setVisible(false);
		return viewPanel;
	}
	
	/**
	 * Creates an item for in the top menu bar
	 * @param text				shown string
	 * @param backgroundColor	background color of the button
	 * @param foregroundColor	foreground color of the button
	 * @return					returns the button as JLabel
	 */
	private JLabel createMenuItem(String text, Color backgroundColor, Color foregroundColor){
		JLabel label = new JLabel(text);
		if(backgroundColor != null){
			label.setBackground(backgroundColor);
			label.setBorder(BorderFactory.createLineBorder(backgroundColor));
		} else {
			label.setBackground(this.getBackground());
			label.setBorder(BorderFactory.createLineBorder(this.getBackground()));
		}
		if(foregroundColor != null)
			label.setForeground(foregroundColor);
		label.setOpaque(true);
		label.setPreferredSize(buttonDimension);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.addMouseListener(new JXMenuBarMouseListener(this, backgroundColor, foregroundColor));
		
		return label;
	}
	
	/**
	 * Creates a button for inside a menu
	 * @param icon		icon for the button to show
	 * @param text		text for the button to show
	 * @return			returns a JPanel as button
	 */
	public JPanel tileButtonPanel(ImageIcon icon, String text){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setMinimumSize(new Dimension(60,60));
		panel.setBorder(BorderFactory.createLineBorder(panel.getBackground()));
		panel.addMouseListener(new JXButtonHoverListener());
		
		JLabel topLabel = new JLabel(icon);
		topLabel.setBorder(BorderFactory.createEmptyBorder(10,10,1,10));
		
		JLabel bottomLabel = new JLabel(text);
		bottomLabel.setHorizontalAlignment(JLabel.CENTER);
		bottomLabel.setBorder(BorderFactory.createEmptyBorder(1,6,5,6));
		
		panel.add(topLabel, BorderLayout.PAGE_START);
		panel.add(bottomLabel, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * Creates a small seperation stripe
	 * @return	returns a JPanel
	 */
	public JPanel seperatorPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1,80));
		panel.setBackground(new Color(226,227,228));
		panel.setBorder(BorderFactory.createEmptyBorder());
		panel.add(new JLabel("TEST"));
		return panel;
	}
	
	/**
	 * Checks if other menus are visible
	 * @param e		MouseEvent gained from buttons' MouseListener
	 */
	public void toggleMenuPanel(MouseEvent e){
		JLabel sourceLabel = (JLabel)e.getSource();
		
		if(sourceLabel == start){
			if(startMenu.isVisible()){
				startMenu.setVisible(false);
			} else {
				startMenu.setVisible(true);
				viewMenu.setVisible(false);
			}
		}
		if(sourceLabel == view){
			if(viewMenu.isVisible()){
				viewMenu.setVisible(false);
			} else {
				viewMenu.setVisible(true);
				startMenu.setVisible(false);
			}
		}
		if(!startMenu.isVisible() && !viewMenu.isVisible()){
			menuPanel.setVisible(false);
		} else {
			menuPanel.setVisible(true);
		}
	}
	
	/**
	 * Stays empty because it doesn't have to update anything
	 */
	@Override
	public void update(JXplorer data) {
	}
}
