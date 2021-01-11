package com.path.simulator.gui.main.bulk;

import java.util.ArrayList;

import javax.swing.JScrollPane;

import com.path.simulator.core.vo.MessageVO;
import com.path.simulator.gui.base.BasePanel;
import com.path.simulator.gui.main.bulk.helper.MessageTableModel;
import com.path.simulator.gui.main.bulk.helper.SelectAllHeader;

import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import javax.swing.JTable;

public class MessageGrid extends BasePanel {	
	/**
	 * 
	 */
	private BulkMessagePanel msgPanel;
	
	MessageTableModel gridModel;
	private JTable table;

	/**
	 * @param msgPanel
	 */
	public MessageGrid(){
		initGui();
	}
	
	/**
	 * @param msgPanel
	 */
	public MessageGrid(BulkMessagePanel msgPanel){
		this.setMsgPanel(msgPanel);
		initGui();
	}
	

	/**
	 * 
	 */
	private void initGui() {
		setLayout(null);
		setBorder(new TitledBorder(null, "Message grid", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		gridModel = new MessageTableModel(0,0);
		gridModel.addColumn("Select");
		gridModel.addColumn("Message Name");
		gridModel.addColumn("Total");
		gridModel.addColumn("");
		
		table = new JTable();
		table.setBounds(10, 26, 516, 247);
		table.setModel(gridModel);
		TableColumn selectColumn = table.getColumnModel().getColumn(0);
		selectColumn.setHeaderRenderer(new SelectAllHeader(table, 0));
		selectColumn.setPreferredWidth(40);
		
        
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.getColumnModel().getColumn(2).setPreferredWidth(67);
		
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(3).setWidth(0);
		
		
		//table.setPreferredSize(new Dimension(200,247));
		
		JScrollPane scrollPane = new JScrollPane(table);
		//scrollPane.setPreferredSize(new Dimension(380,198));
		scrollPane.setBounds(21, 26, 577, 247);
		add(scrollPane);
	}

	/**
	 * @return the gridModel
	 */
	public MessageTableModel getGridModel() {
		return gridModel;
	}

	/**
	 * @param gridModel the gridModel to set
	 */
	public void setGridModel(MessageTableModel gridModel) {
		this.gridModel = gridModel;
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(JTable table) {
		this.table = table;
	}
	
	/**
	 * @return the msgPanel
	 */
	public BulkMessagePanel getMsgPanel() {
		return msgPanel;
	}

	/**
	 * @param msgPanel the msgPanel to set
	 */
	public void setMsgPanel(BulkMessagePanel msgPanel) {
		this.msgPanel = msgPanel;
	}

	/**
	 * @param message
	 */
	public void addLine(MessageVO message) {
		
		Object[] rowData = new Object[]{
				Boolean.valueOf(message.isSelected()),
				String.valueOf(message.getName()),
				Integer.valueOf(message.getTotal()),
				String.valueOf(message.getPayload())
		};
		
		getGridModel().addRow(rowData);
		
	}

	/**
	 * 
	 */
	public void removeRows() {
		getGridModel().getDataVector().removeAllElements();
		getGridModel().fireTableDataChanged();
		
	}
	
	public ArrayList<MessageVO> getValues(){
		
		ArrayList<MessageVO> messagesVO = new ArrayList<MessageVO>();
		for (int count = 0; count < getGridModel().getRowCount(); count++) {

			boolean isSelected = (Boolean) getGridModel().getValueAt(count, 0);
			if (!isSelected)
				continue;
			
			String messageName = (String) getGridModel().getValueAt(count, 1);
			String messagePayLoad = (String)getGridModel().getValueAt(count, 3);
			Integer total = (Integer)getGridModel().getValueAt(count, 2);
			MessageVO messageVO = new MessageVO();
			messageVO.setName(messageName);
			messageVO.setPayload(messagePayLoad);
			messageVO.setTotal(total.intValue());
			messageVO.setSelected(true);
			
			messagesVO.add(messageVO);
		}
		
		return messagesVO;
	}
}
