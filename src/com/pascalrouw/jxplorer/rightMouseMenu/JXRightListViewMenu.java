package com.pascalrouw.jxplorer.rightMouseMenu;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.pascalrouw.jxplorer.JXListView;
import com.pascalrouw.jxplorer.JXploreFile;
import com.pascalrouw.jxplorer.JXplorer;
import com.pascalrouw.jxplorer.propertiesFrame.JXProperties;

/**
 * This class handles the right mouse menu for the JXListView
 * @author Pascal
 * @version 05.06.14
 */
public class JXRightListViewMenu extends JXRightMouseMenu{
	/**
	 * This constructor creates the buttons and adds listeners
	 * @param startPoint		x and y point of the mouse when right clicked
	 * @param clickedObject		component that is right clicked on
	 * @param jxplorer			current jxplorer
	 * @param file				file clicked on
	 */
	public JXRightListViewMenu(Point startPoint, JList<?> clickedObject, final JXplorer jxplorer, final JXploreFile file){
		JLabel openFile = createButton("Open");
		if(file.isFolder()){
			openFile.setText(" Open in explorer");
		}
		openFile.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				file.openFile();
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel listView = createButton("List-view");
		listView.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				jxplorer.updateListLayout(JXListView.layout.LIST);
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel detailView = createButton("Detail-view");
		detailView.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				jxplorer.updateListLayout(JXListView.layout.DETAILS);
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel tileView = createButton("Tile-view");
		tileView.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				jxplorer.updateListLayout(JXListView.layout.TILES);
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel refresh = createButton("Refresh");
		refresh.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				jxplorer.setCurrentFile(jxplorer.getCurrentFile());
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel newFolder = createButton("New folder");
		newFolder.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String outputString = (String)JOptionPane.showInputDialog(null,
		                "Folder name:", "New Folder",
		                JOptionPane.PLAIN_MESSAGE,
		                null, null, "New Folder");
				if(outputString != null && outputString != ""){
					File newDir = new File(jxplorer.getCurrentFile().getPath()+"/"+outputString);
					newDir.mkdir();
					jxplorer.updateGUI();
				}
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel properties = createButton("Properties");
		properties.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				new JXProperties(file);
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel delete = createButton("Delete");
		delete.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String fileMessage = "Are you sure you want to delete this file?";
				String folderMessage = "Are you sure you want to delete this folder?";
				if (JOptionPane.showConfirmDialog(null, file.isFolder() ? folderMessage : fileMessage, "Delete?", 
					    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
					    == JOptionPane.YES_OPTION){
					if(!file.isFolder()){
						file.getFile().delete();
						jxplorer.updateGUI();
					} else {
						if(file.getFile().list().length > 0){
							JOptionPane.showMessageDialog(jxplorer.getFrame(), "The folder has to be empty to delete it!", "Error",  JOptionPane.ERROR_MESSAGE);
						} else {
							file.getFile().delete();
							jxplorer.updateGUI();
						}
					}
				}
				disposeFrame();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JLabel[] labels = new JLabel[13];
		labels[0] = openFile;
		labels[1] = createSeperator();
		labels[2] = listView;
		labels[3] = detailView;
		labels[4] = tileView;
		labels[5] = createSeperator();
		labels[6] = refresh;
		labels[7] = createSeperator();
		labels[8] = newFolder;
		labels[9] = createSeperator();
		labels[10] = delete;
		labels[11] = createSeperator();
		labels[12] = properties; 
		
		initFrame(clickedObject, startPoint, labels);
	}
}
