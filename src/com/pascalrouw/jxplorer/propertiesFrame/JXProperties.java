package com.pascalrouw.jxplorer.propertiesFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.pascalrouw.jxplorer.JXploreFile;

/**
 * This class creates and shows the properties windows for files
 * @author Pascal
 * @version 18.05.14
 */
public class JXProperties {
	private Thread calculateThread;
	private JFrame mainFrame;
	
	/**
	 * This constructor creates and shows the properties window
	 * @param file
	 */
	public JXProperties(JXploreFile file) {
		mainFrame = new JFrame("Properties of " + file.getName());
		mainFrame.getContentPane().setLayout(new BorderLayout());
		mainFrame.setResizable(false);
		mainFrame.setMinimumSize(new Dimension(370, 480));
		mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				calculateThread.interrupt();
				calculateThread = null;
			}
		});

		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
		upperPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, new Color(160,160,160)));
		
		String[] infoTableColumns = { "Name", "Value" };
		Object[][] infoTableData = {
				{ new String("Type:"), file.getIcon() },
				{ "Location:", file.getPath() },
				{ "Size:", "" },
				{"Contains:", file.getSubFiles().length + " Files, " + file.getSubFolders().length + " Folders" },
				{ "Last Modified:",	new Date(file.getFile().lastModified()) } };
		JTable infoTable = new JTable(infoTableData, infoTableColumns){
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		infoTable.setRowHeight(30);
		infoTable.setShowGrid(false);
		infoTable.setIntercellSpacing(new Dimension(30,0));
		infoTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		infoTable.getColumnModel().getColumn(0).setMaxWidth(100);
		infoTable.getColumnModel().getColumn(0).setMinWidth(100);

		CalculateFolderSize sizeCalculator = new CalculateFolderSize(infoTable, file.getFile());
		calculateThread = new Thread(sizeCalculator);
		calculateThread.start();

		upperPanel.add(createIconTable(file));
		upperPanel.add(createHorizontalSeperator());
		upperPanel.add(infoTable);
		
		JPanel middleSpacerPanel = new JPanel();
		middleSpacerPanel.setPreferredSize(new Dimension(100,100));
		middleSpacerPanel.setBackground(Color.WHITE);
		middleSpacerPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(160,160,160)));
		
		JPanel lowerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setPreferredSize(new Dimension(75,23));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
		
		JButton okButton = new JButton("Ok");
		okButton.setPreferredSize(new Dimension(75,23));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
		
		lowerPanel.add(okButton);
		lowerPanel.add(cancelButton);
		
		mainFrame.add(upperPanel, BorderLayout.PAGE_START);
		mainFrame.add(middleSpacerPanel, BorderLayout.CENTER);
		mainFrame.add(lowerPanel, BorderLayout.PAGE_END);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Creates a table with on the left side the system icon and 
	 * on the right side the file name
	 * @param selectedFile
	 * @returns a JTable ready to add
	 */
	private JTable createIconTable(JXploreFile selectedFile){
		String[] columnNames = { "Icon", "Name" };
		Object[][] data = { {
			selectedFile.getImageIcon(30, 30),
			selectedFile.getName() } };
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
			
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		table.setIntercellSpacing(new Dimension(30,0));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.setRowHeight(50);
		table.setBorder(BorderFactory.createEmptyBorder());
		table.setShowGrid(false);
		return table;
	}
	
	/**
	 * Creates a JPanel with a background color and 1px height
	 * @returns a JPanel ready to add
	 */
	public JPanel createHorizontalSeperator(){
		JPanel tempPanel = new JPanel();
		tempPanel.setPreferredSize(new Dimension(0,16));
		tempPanel.setMaximumSize(new Dimension(500,16));
		tempPanel.setBorder(BorderFactory.createMatteBorder(5, 20, 10, 20, Color.WHITE));
		tempPanel.setBackground(new Color(160,160,160));
		return tempPanel;
	}
}
