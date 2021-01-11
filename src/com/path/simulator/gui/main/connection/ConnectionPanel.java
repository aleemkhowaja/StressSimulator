package com.path.simulator.gui.main.connection;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.path.simulator.core.SimulatorConfiguration;
import com.path.simulator.gui.base.BasePanel;
import com.path.simulator.gui.main.connection.section.ConnectionSection;
import com.path.simulator.gui.main.connection.section.InterfaceDefinition;
import com.path.simulator.gui.main.connection.section.InterfaceSettings;

/**
 * @author MohammadAliMezzawi
 *
 */
public class ConnectionPanel extends BasePanel {
	
	private ConnectionSection connectionSection;
	private InterfaceDefinition interfaceDefSection;
	private InterfaceSettings interfaceSettings;
	
	private JLabel lblLoader;
	private JLabel lblLoaderText;
	
	
	/**
	 * @note : Make sure that the design plugin work fine
	 */
	public ConnectionPanel() {
		this(new SimulatorConfiguration());
	}
	
	
	public ConnectionPanel(SimulatorConfiguration config) {
		initPanelGui(config);
	}

	private void initPanelGui(SimulatorConfiguration config) {
		
		setLayout(null);
		setBounds(0, 0, 100, 100);
		createLoaderPanel();
		
		connectionSection = new ConnectionSection(this);
		connectionSection.getPortField().setText(String.valueOf(config.getPort())); // 9214
		connectionSection.getHostField().setText(config.getIp());// 192.168.21.156
		add(connectionSection);
		
		interfaceDefSection = new InterfaceDefinition(config);
		add(interfaceDefSection);
		
		interfaceSettings = new InterfaceSettings(config);
		add(interfaceSettings);
	}
	

	
	/**
	 * Create loader Panel
	 */
	protected void createLoaderPanel() {
		
		ImageIcon loader = new ImageIcon(ConnectionPanel.class.getResource(
				"/com/path/simulator/images/6.gif"));

		lblLoader = new JLabel();
		lblLoader.setBounds(273, 132, 64, 64);
		add(lblLoader);
		lblLoader.setIcon(loader);
		lblLoader.setVisible(false);
		
		lblLoaderText = new JLabel("loading...");
		lblLoaderText.setBounds(278, 200, 55, 17);
		add(lblLoaderText);
		lblLoaderText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoaderText.setVisible(false);
	}

	/**
	 * @param show
	 */
	public void showLoader(boolean show) {
		lblLoaderText.setVisible(show);
		lblLoader.setVisible(show);
		setPanelEnabled(!show);
		
		// 
		lblLoaderText.setEnabled(true);
		lblLoader.setEnabled(true);
	}

	/**
	 * @return the connectionSection
	 */
	public ConnectionSection getConnectionSection() {
		return connectionSection;
	}

	/**
	 * @param connectionSection the connectionSection to set
	 */
	public void setConnectionSection(ConnectionSection connectionSection) {
		this.connectionSection = connectionSection;
	}

	/**
	 * @return the interfaceDefSection
	 */
	public InterfaceDefinition getInterfaceDefSection() {
		return interfaceDefSection;
	}

	/**
	 * @param interfaceDefSection the interfaceDefSection to set
	 */
	public void setInterfaceDefSection(InterfaceDefinition interfaceDefSection) {
		this.interfaceDefSection = interfaceDefSection;
	}

	/**
	 * @return the interfaceSettings
	 */
	public InterfaceSettings getInterfaceSettings() {
		return interfaceSettings;
	}

	/**
	 * @param interfaceSettings the interfaceSettings to set
	 */
	public void setInterfaceSettings(InterfaceSettings interfaceSettings) {
		this.interfaceSettings = interfaceSettings;
	}

	/**
	 * @return the lblLoader
	 */
	public JLabel getLblLoader() {
		return lblLoader;
	}

	/**
	 * @return the lblLoaderText
	 */
	public JLabel getLblLoaderText() {
		return lblLoaderText;
	}
}
