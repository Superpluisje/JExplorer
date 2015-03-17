package com.pascalrouw.jxplorer.swing;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.pascalrouw.jxplorer.JXColor;
import com.pascalrouw.jxplorer.JXploreFile;

/**
 * @author Pascal
 * @version 07.05.14 
 * Cellrenderer for JXTreeView
 */
public class JXTreeCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 1L;
	private JLabel label;

	/**
	 * Constructor creating a new JLabel
	 */
	public JXTreeCellRenderer() {
		label = new JLabel();
		label.setOpaque(true);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JXploreFile file = (JXploreFile) value;
		label.setIcon(file.getIcon());
		label.setText(file.getName());
		if (selected) {
			label.setBackground(JXColor.selectedBackground);
			label.setForeground(JXColor.selectedForeground);
			label.setBorder(BorderFactory.createLineBorder(JXColor.selectedBorder));
		} else {
			label.setBackground(JXColor.normalBackground);
			label.setForeground(JXColor.normalForeground);
			label.setBorder(BorderFactory.createLineBorder(JXColor.normalBorder));
		}
		return label;
	}
}
