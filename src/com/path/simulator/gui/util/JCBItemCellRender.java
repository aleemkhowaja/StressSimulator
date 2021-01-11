package com.path.simulator.gui.util;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class JCBItemCellRender extends JLabel implements ListCellRenderer<JComboBoxItem> {
	 
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
    @Override
    public Component getListCellRendererComponent(JList<? extends JComboBoxItem> list, JComboBoxItem item, 
    		int index, boolean isSelected, boolean cellHasFocus) {
        
    	if(null == item)
    		return new JLabel("");
    	
    	String value = item.getDescription();
    	JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
    	        isSelected, cellHasFocus);

        return renderer;
    }
     
}
