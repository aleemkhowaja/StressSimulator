package com.path.simulator.gui.main.plain;

import com.path.simulator.gui.base.BasePanel;
import com.path.simulator.gui.main.plain.listener.SendBtnListener;

import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PlainMessagePanel  extends BasePanel{
	
	private JTextArea messagePayload;
	private JButton sendBtn;

	private JLabel lblLoader;
	private JLabel lblLoaderText;
	
	public PlainMessagePanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(null);
		createLoaderPanel(285,150);
		messagePayload = new JTextArea();
		
		messagePayload.setBounds(10, 11, 611, 321);
		messagePayload.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(messagePayload);
		
		sendBtn = new JButton("Send Message");
		sendBtn.setBounds(474, 343, 149, 32);
		sendBtn.addActionListener(new SendBtnListener(this));
		add(sendBtn);
	}

	/**
	 * @return the sendBtn
	 */
	public JButton getSendBtn() {
		return sendBtn;
	}

	/**
	 * @param sendBtn the sendBtn to set
	 */
	public void setSendBtn(JButton sendBtn) {
		this.sendBtn = sendBtn;
	}

	/**
	 * @return the messagePayload
	 */
	public JTextArea getMessagePayload() {
		return messagePayload;
	}

	/**
	 * @param messagePayload the messagePayload to set
	 */
	public void setMessagePayload(JTextArea messagePayload) {
		this.messagePayload = messagePayload;
	}

}
