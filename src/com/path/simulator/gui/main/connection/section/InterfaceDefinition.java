package com.path.simulator.gui.main.connection.section;

import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.path.simulator.core.SimulatorConfiguration;

public class InterfaceDefinition extends JPanel {
	
	private JLabel lblInterfaceName = new JLabel("Interface name");
	private JTextField txtInterfaceName = new JTextField();
	private JLabel lblInterfaceType = new JLabel("Interface Type");
	private JComboBox<String> interfaceType = new JComboBox<String>();
	
	public InterfaceDefinition(SimulatorConfiguration config) {
		setLayout(null);
		setBounds(5, 100, 614, 118);
		setBorder(new TitledBorder(null, "Interface Definition", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblInterfaceName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblInterfaceName.setBounds(13, 19, 102, 32);
		lblInterfaceName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtInterfaceName.setColumns(10);
		txtInterfaceName.setBounds(126, 20, 200, 32);
		txtInterfaceName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtInterfaceName.setText(config.getName());
		//txtInterfaceName.addKeyListener(saveKeyListener);
		
		lblInterfaceType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInterfaceType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInterfaceType.setBounds(13, 65, 102, 32);
		interfaceType.setBounds(126, 68, 92, 32);
		interfaceType.addItem("ISO 8583");
		interfaceType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblInterfaceName);
		add(txtInterfaceName);
		add(lblInterfaceType);
		add(interfaceType);
	}

	/**
	 * @return the lblInterfaceName
	 */
	public JLabel getLblInterfaceName() {
		return lblInterfaceName;
	}

	/**
	 * @param lblInterfaceName the lblInterfaceName to set
	 */
	public void setLblInterfaceName(JLabel lblInterfaceName) {
		this.lblInterfaceName = lblInterfaceName;
	}

	/**
	 * @return the txtInterfaceName
	 */
	public JTextField getTxtInterfaceName() {
		return txtInterfaceName;
	}

	/**
	 * @param txtInterfaceName the txtInterfaceName to set
	 */
	public void setTxtInterfaceName(JTextField txtInterfaceName) {
		this.txtInterfaceName = txtInterfaceName;
	}

	/**
	 * @return the lblInterfaceType
	 */
	public JLabel getLblInterfaceType() {
		return lblInterfaceType;
	}

	/**
	 * @param lblInterfaceType the lblInterfaceType to set
	 */
	public void setLblInterfaceType(JLabel lblInterfaceType) {
		this.lblInterfaceType = lblInterfaceType;
	}

	/**
	 * @return the interfaceType
	 */
	public JComboBox<String> getInterfaceType() {
		return interfaceType;
	}

	/**
	 * @param interfaceType the interfaceType to set
	 */
	public void setInterfaceType(JComboBox<String> interfaceType) {
		this.interfaceType = interfaceType;
	}

}
