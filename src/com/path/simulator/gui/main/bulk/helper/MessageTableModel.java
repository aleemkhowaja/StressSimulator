package com.path.simulator.gui.main.bulk.helper;

import javax.swing.table.DefaultTableModel;

/**
 * @author MohammadAliMezzawi
 *
 */
public class MessageTableModel extends DefaultTableModel {
	
	public MessageTableModel(int i, int j) {
		super(i,j);
	}

	public Class getColumnClass(int col) {
		
		Class clazz = String.class;;
		
		switch(col){
			case 0 :
				clazz = Boolean.class;
				break;
			case 1 :
				clazz = String.class;
				break;
			case 2 :
				clazz = Integer.class;
				break;
			case 3 :
				clazz = Integer.class;
				break;
		}
		
		return clazz;
	}
}