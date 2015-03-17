package com.pascalrouw.jxplorer.propertiesFrame;

import java.io.File;
import java.text.DecimalFormat;
import javax.swing.JTable;

/**
 * Runnable class that calculates the currently selected file's size 
 * @author Pascal
 * @version 07.05.14
 */
public class CalculateFolderSize implements Runnable {
	private File file;
	private JTable table;
	private long totalSize;
	
	/**
	 * Constructor of the class, sets fields
	 * @param table JTable to give values
	 * @param file File to calculate size of
	 */
	public CalculateFolderSize(JTable table, File file) {
		this.file = file;
		this.table = table;
	}
	
	/**
	 * Runs on Thread.start()
	 * Calculates folder size if the file is a folder, else it returns the file size directly
	 */
	@Override
	public void run() {
		totalSize = 0;
		if(file.isDirectory()){
			getFolderSize(file);
		} else {
			totalSize = file.length();
		}
		table.setValueAt(readableFileSize(totalSize), 2, 1);
	}
	
	/**
	 * Repeats until all subfolders are checked for files
	 * @param file Calculates file size
	 * @return returns total size of folder currently calculating
	 */
	private long getFolderSize(File file){
		long folderSize = 0;
		File[] filelist = file.listFiles();
		for (File singleFile : filelist) {
			if (singleFile.isDirectory()) {
				long size = getFolderSize(singleFile);
				folderSize += size;
			} else {
				long size = singleFile.length();
				folderSize += size;
				totalSize += size;
			}
			table.setValueAt(readableFileSize(totalSize) + " (calculating...)", 2, 1);
		}
		return folderSize;
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
