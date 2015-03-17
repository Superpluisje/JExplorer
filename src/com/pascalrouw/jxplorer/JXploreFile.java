package com.pascalrouw.jxplorer;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreeNode;

/** 
 * This class represents a folder or file that can later be used by JXplorer
 * @author Pascal
 * @version 07.05.14
 */
public class JXploreFile implements TreeNode{	
	private File file;
	private JXploreFile[] cache;
	
	/**
	 * Basic constructor
	 */
	public JXploreFile() {
		this(FileSystemView.getFileSystemView().getRoots()[0]);
	}
	
	/**
	 * Constructor with new path for file
	 * @param path is the path for the new jxplorefile
	 */
	public JXploreFile(String path) {
		this(new File(path));
	}
	
	/**
	 * Constructor with new file
	 * @param file is the file for this class
	 */
	public JXploreFile(File file) {
		if (file == null) file = FileSystemView.getFileSystemView().getRoots()[0];
		this.file = file;
	}
	
	/**
	 * returns if the file is a drive
	 * @return boolean is it a drive or not
	 */
	public boolean isDrive() {
		return FileSystemView.getFileSystemView().isDrive(file);
	}
	
	/**
	 * returns all subfiles from the current file
	 * @return JXploreFile array contains all subfiles
	 */
	public JXploreFile[] getSubFiles() {
		return makeFiles(FileSystemView.getFileSystemView().getFiles(file,JXListView.hideHidden));
	}
	
	/**
	 * initiates the cache
	 * @param cache contains all files
	 */
	private void initCache(JXploreFile[] cache){
		this.cache = cache;
	}
	
	/**
	 * returns all subfolders from the current file
	 * @return JXploreFile array contains all subfolders
	 */
	public JXploreFile[] getSubFolders() {
		if (cache == null) { 
			ArrayList<JXploreFile> folders = new ArrayList<JXploreFile>();
			File[] files = FileSystemView.getFileSystemView().getFiles(file, JXListView.hideHidden); 
			for (File file : files) {
				if (!file.isFile()) {
						folders.add(new JXploreFile(file));
				}
			}
			initCache(folders.toArray(new JXploreFile[folders.size()]));
			return folders.toArray(new JXploreFile[folders.size()]);
		} else {
			return cache;
		}
	}
	
	/**
	 * makes from a file array a JXploreFile array
	 * @param input file array
	 * @return JXploreFile array
	 */
	private JXploreFile[] makeFiles(File[] input) {
		JXploreFile[] output = new JXploreFile[input.length];
		for (int index = 0; index < input.length; index++) {
			output[index] = new JXploreFile(input[index]);
		}
		return output;
	}
	
	/**
	 * Opens the current file with the OS' default selected program
	 */
	public void openFile() {
		try {
			if (!Desktop.isDesktopSupported()) {
				System.out.println("Desktop is not supported");
			}
			Desktop desktop = Desktop.getDesktop();
			if (file.exists() && file.canRead()){
				desktop.open(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Enumeration<?> children() {
		return new Enumeration<Object>() {
			int index = 0;

			@Override
			public boolean hasMoreElements() {
				return index < getSubFolders().length;
			}

			@Override
			public JXploreFile nextElement() {
				return getSubFolders()[index++];
			}

		};
	}
	
	@Override
	public int getIndex(TreeNode node) {
		JXploreFile[] folders = getSubFolders();
		for (int n=0; n<folders.length; n++) 
			if (folders[n].equals(node))
				return n;
		// Niet gevonden
		return -1;
	}
	
	@Override
	public TreeNode getParent() {
		if (file.getParentFile() == null)
			return null;
		return new JXploreFile(file.getParentFile());
	}
	
	@Override
	public boolean isLeaf() {
		return false;
		//Uncomment if your pc is fast enough:
		//return (getSubFolders().length == 0);
	}
	
	@Override
	public boolean getAllowsChildren() {
		return this.isFolder();
	}
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		return getSubFolders()[childIndex];
	}
	
	@Override
	public int getChildCount() {
		return getSubFolders().length;
	}
	
	/**
	 * returns if the current file is a folder
	 * @return boolean is folder or not
	 */
	public boolean isFolder() {
		return !file.isFile();
	}
	
	/**
	 * returns the file field of this class
	 * @return File field
	 */
	public File getFile(){
		return file;
	}
	
	/**
	 * returns the name of the system file
	 * @return String name of the file
	 */
	public String getName() {
		return FileSystemView.getFileSystemView().getSystemDisplayName(file);
	}
	
	/**
	 * returns the path of the file field
	 * @return Sstring path
	 */
	public String getPath() {
		if (FileSystemView.getFileSystemView().isFileSystem(file))
			return file.getPath();
		else
			return getName();
	}
	
	/**
	 * returns the system icon of the current file
	 * @return Icon systemicon
	 */
	public Icon getIcon() {		  
		return FileSystemView.getFileSystemView().getSystemIcon(file);
	}
	
	/**
	 * Takes the icon from the current file, resizes it and converts it to an ImageIcon object
	 * @param w width in pixels
	 * @param h height in pixels
	 * @return returns a resized file icon as ImageIcon
	 */
	public ImageIcon getImageIcon(int w, int h){
		Icon icon = getIcon();
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TRANSLUCENT);
		icon.paintIcon(null, image.getGraphics(), 0, 0);
		Image resizedImage = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
		
		return new ImageIcon(resizedImage);
	}
}
