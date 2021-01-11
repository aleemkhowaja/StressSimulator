package com.path.simulator.gui.main.bulk;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.path.simulator.core.vo.MessageVO;
import com.path.simulator.gui.base.BasePanel;
import com.path.simulator.gui.main.SwitchFrame;
import com.path.simulator.gui.main.bulk.listener.ItemSelectionListener;
import com.path.simulator.gui.main.bulk.listener.ReloadButtonBtnListener;
import com.path.simulator.gui.main.bulk.listener.SendBtnListener;
import javax.swing.SwingConstants;

/**
 * @author MohammadAliMezzawi
 *
 */
public class BulkMessagePanel extends /*JFrame*/BasePanel{
	
	/**
	 * list of iso messages available
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox messageProviderCombo;
	private JButton reloadButton;
	private JTextField numberOfSender;
	
	private JLabel mpmLabel;
	private JTextField messagePerMinute;
	
	private MessageGrid messagesGrid;
	private JProgressBar progressBar;
	private JButton sendButton;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatLightLaf.install(new FlatIntelliJLaf());
					UIManager.put("TextField.inactiveBackground", 
							new ColorUIResource(new Color(255, 255, 255)));
					BulkMessagePanel window = new BulkMessagePanel();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Constructor
	 */
	public BulkMessagePanel(){
		initGUI();
	}

	/**
	 * Init GUI
	 */
	private void initGUI() {
		
		setLayout(null);
		//createLoaderPanel(285,150);
		
		JLabel lblNewLabel = new JLabel("Message Provider");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 17, 110, 22);
		add(lblNewLabel);
		
		messageProviderCombo = new JComboBox();
		messageProviderCombo.setBounds(131, 14, 169, 32);
		messageProviderCombo.addItemListener(new ItemSelectionListener(this));
		add(messageProviderCombo);
		
		reloadButton = new JButton("Reload");
		reloadButton.setBounds(312, 14, 33, 32);
		reloadButton.addActionListener(new ReloadButtonBtnListener(this));
		add(reloadButton);
		
		JLabel senderLabel = new JLabel("Number of Sender");
		senderLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		senderLabel.setBounds(10, 57, 112, 32);
		add(senderLabel);
		
		numberOfSender = new JTextField();
		numberOfSender.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfSender.setText("1");
		numberOfSender.setBounds(131, 60, 84, 32);
		add(numberOfSender);
		numberOfSender.setColumns(10);
		
		
		mpmLabel = new JLabel("Messager Per Minute");
		mpmLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mpmLabel.setBounds(243, 58, 130, 32);
		add(mpmLabel);
		
		messagePerMinute = new JTextField();
		messagePerMinute.setHorizontalAlignment(SwingConstants.CENTER);
		messagePerMinute.setText("1000");
		messagePerMinute.setBounds(381, 60, 84, 32);
		add(messagePerMinute);
		messagePerMinute.setColumns(10);
		
		
		// draw grid
		messagesGrid = new MessageGrid(this);
		messagesGrid.setLayout(null);
		messagesGrid.setEditable(true);
		messagesGrid.setBounds(10, 100, 613, 283);
		add(messagesGrid);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 403, 499, 32);
		progressBar.setVisible(false);
		add(progressBar);
		
		
		sendButton = new JButton("Send");
		sendButton.setBounds(519, 403, 99, 32);
		add(sendButton);
		sendButton.addActionListener(new SendBtnListener(this));
		
	}
	


	/**
	 * @return the messageProviderCombo
	 */
	public JComboBox getMessageProviderCombo() {
		return messageProviderCombo;
	}

	/**
	 * @param messageProviderCombo the messageProviderCombo to set
	 */
	public void setMessageProviderCombo(JComboBox messageProviderCombo) {
		this.messageProviderCombo = messageProviderCombo;
	}

	/**
	 * @return the reloadButton
	 */
	public JButton getReloadButton() {
		return reloadButton;
	}

	/**
	 * @param reloadButton the reloadButton to set
	 */
	public void setReloadButton(JButton reloadButton) {
		this.reloadButton = reloadButton;
	}

	/**
	 * @return the numberOfSender
	 */
	public JTextField getNumberOfSender() {
		return numberOfSender;
	}

	/**
	 * @param numberOfSender the numberOfSender to set
	 */
	public void setNumberOfSender(JTextField numberOfSender) {
		this.numberOfSender = numberOfSender;
	}

	/**
	 * @return the messagePerMinute
	 */
	public JTextField getMessagePerMinute() {
		return messagePerMinute;
	}

	/**
	 * @param messagePerMinute the messagePerMinute to set
	 */
	public void setMessagePerMinute(JTextField messagePerMinute) {
		this.messagePerMinute = messagePerMinute;
	}

	/**
	 * @return the mpmLabel
	 */
	public JLabel getMpmLabel() {
		return mpmLabel;
	}

	/**
	 * @param mpmLabel the mpmLabel to set
	 */
	public void setMpmLabel(JLabel mpmLabel) {
		this.mpmLabel = mpmLabel;
	}

	/**
	 * @return the messagesGrid
	 */
	public MessageGrid getMessagesGrid() {
		return messagesGrid;
	}

	/**
	 * @param messagesGrid the messagesGrid to set
	 */
	public void setMessagesGrid(MessageGrid messagesGrid) {
		this.messagesGrid = messagesGrid;
	}

	/**
	 * @return the sendButton
	 */
	public JButton getSendButton() {
		return sendButton;
	}

	/**
	 * @param sendButton the sendButton to set
	 */
	public void setSendButton(JButton sendButton) {
		this.sendButton = sendButton;
	}

	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @param progressBar the progressBar to set
	 */
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}
