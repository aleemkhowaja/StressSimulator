package com.path.simulator.gui.main.bulk;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.path.simulator.core.vo.MessageVO;

public class MessageGui {
	
	/**
	 * Hold reference to messageVO
	 */
	private MessageVO messageVO;
	
	JCheckBox chckbxNewCheckBox;
	JTextField textField_3;
	JTextField total;
	JTextField textField_5;
	
	/**
	 * @param messageVO
	 */
	public MessageGui(MessageVO messageVO) {
		this.messageVO = messageVO;
	}

	/**
	 * Get value
	 * @param panel
	 */
	public MessageVO getValue(){
		messageVO.setSelected(chckbxNewCheckBox.isSelected());
		messageVO.setTotal(Integer.valueOf(total.getText()));
		return messageVO;
	}
	
	/**
	 * @param panel
	 * @param rowIndex 
	 */
	public void draw(JPanel panel, int rowIndex){
		
		chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBounds(18, (rowIndex * 32 )+30, 27, 23);
		chckbxNewCheckBox.setSelected(messageVO.isSelected());
		panel.add(chckbxNewCheckBox);
		
		textField_3 = new JTextField(messageVO.getName());
		textField_3.setBounds(61, (rowIndex * 32 )+30, 252, 32);
		textField_3.setColumns(10);
		panel.add(textField_3);
		
		total = new JTextField(messageVO.getTotal());
		total.setBounds(323, (rowIndex * 32 )+30, 86, 32);
		total.setColumns(10);
		panel.add(total);
		
		JTextField textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(419, (rowIndex * 32 )+30, 86, 32);
		panel.add(textField_5);
	}

}
