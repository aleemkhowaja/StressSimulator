package com.path.simulator.gui.main.connection.section;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.path.simulator.gui.main.connection.ConnectionPanel;
import com.path.simulator.gui.main.connection.listener.ConnectBtnListener;
import com.path.simulator.gui.main.connection.listener.DisconnectBtnListener;


public class ConnectionSection extends JPanel {
	
	private ConnectionPanel parent;
	
	private JLabel hostLbl = new JLabel("Host");
	private JTextField hostField = new JTextField();
	private JLabel portLbl = new JLabel("Port");
	private JTextField portField = new JTextField();
	private JButton connectBtn = new JButton("Connect");
	private JButton btnDisconnect = new JButton("Disconnect");
	
	/**
	 * @param parent
	 */
	public ConnectionSection(ConnectionPanel parent){
		this.parent = parent;
		initGUI();
	}
	
	
	public void initGUI() {
		setLayout(null);
		setBounds(5, 10, 614, 81);
		setBorder(new TitledBorder(null, "Connect", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		// Add host field
		hostField.setBounds(54, 26, 257, 32);
		hostField.setText("192.168.16.51");
		hostField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		hostField.setColumns(10);
		hostLbl.setBounds(10, 25, 46, 32);
		hostLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(hostLbl);
		add(hostField);
		
		// Add port field
		portLbl.setBounds(321, 25, 35, 32);
		portLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		portField.setText("6508");
		portField.setBounds(361, 27, 86, 32);
		portField.setColumns(10);
		add(portLbl);
		add(portField);
		
		connectBtn.setBounds(469, 27, 119, 32);
		connectBtn.addActionListener(new ConnectBtnListener(parent));
		add(connectBtn);
		
		btnDisconnect.setBounds(469, 27, 119, 32);
		btnDisconnect.addActionListener(new DisconnectBtnListener(parent));
		btnDisconnect.setVisible(false);
		add(btnDisconnect);
		
	}

	/**
	 * @return the hostLbl
	 */
	public JLabel getHostLbl() {
		return hostLbl;
	}

	/**
	 * @param hostLbl the hostLbl to set
	 */
	public void setHostLbl(JLabel hostLbl) {
		this.hostLbl = hostLbl;
	}

	/**
	 * @return the hostField
	 */
	public JTextField getHostField() {
		return hostField;
	}

	/**
	 * @param hostField the hostField to set
	 */
	public void setHostField(JTextField hostField) {
		this.hostField = hostField;
	}

	/**
	 * @return the portLbl
	 */
	public JLabel getPortLbl() {
		return portLbl;
	}

	/**
	 * @param portLbl the portLbl to set
	 */
	public void setPortLbl(JLabel portLbl) {
		this.portLbl = portLbl;
	}

	/**
	 * @return the portField
	 */
	public JTextField getPortField() {
		return portField;
	}

	/**
	 * @param portField the portField to set
	 */
	public void setPortField(JTextField portField) {
		this.portField = portField;
	}

	/**
	 * @return the connectBtn
	 */
	public JButton getConnectBtn() {
		return connectBtn;
	}

	/**
	 * @param connectBtn the connectBtn to set
	 */
	public void setConnectBtn(JButton connectBtn) {
		this.connectBtn = connectBtn;
	}


	/**
	 * @return the btnDisconnect
	 */
	public JButton getBtnDisconnect() {
		return btnDisconnect;
	}


	/**
	 * @param btnDisconnect the btnDisconnect to set
	 */
	public void setBtnDisconnect(JButton btnDisconnect) {
		this.btnDisconnect = btnDisconnect;
	}

}
