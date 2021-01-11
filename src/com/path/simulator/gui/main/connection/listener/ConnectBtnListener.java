package com.path.simulator.gui.main.connection.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import com.path.simulator.core.SimulatorConfiguration;
import com.path.simulator.core.StressSimulator;
import com.path.simulator.gui.main.connection.ConnectionPanel;
import com.path.simulator.gui.main.connection.section.ConnectionSection;
import com.path.simulator.gui.main.connection.section.InterfaceDefinition;
import com.path.simulator.gui.main.connection.section.InterfaceSettings;
import com.path.simulator.gui.util.JComboBoxItem;
import com.path.simulator.util.Notifier;

public class ConnectBtnListener implements ActionListener {

	private ConnectionPanel connectionPanel;

	/**
	 * @param connectionPanel
	 */
	public ConnectBtnListener(ConnectionPanel connectionPanel) {
		this.connectionPanel = connectionPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			new ConnectorWorker().execute();
		} catch (Exception x) {
			Notifier.errorMsg("Error while Connecting");
		}
	}

	/**
	 * @author MohammadAliMezzawi
	 */
	class ConnectorWorker extends SwingWorker<Integer, String> {

		@Override
		protected Integer doInBackground() throws Exception {
			try {
				// show loader;
				connectionPanel.showLoader(true);
				// validate the configuration
				
				
				// create configuration file
				SimulatorConfiguration configuration = new SimulatorConfiguration();
				
				// connector info
				ConnectionSection connectionSection = connectionPanel.getConnectionSection();
				
				
				// interface Definition Section
				InterfaceDefinition definition = connectionPanel.getInterfaceDefSection();
				//configuration.setInterfaceName(definition.getTxtInterfaceName().getText());
				//configuration.setInterfaceType((String) definition.getInterfaceType().getSelectedItem());
				
				// interface Setting Section
				InterfaceSettings settings = connectionPanel.getInterfaceSettings();
				configuration
					// Connection panel
					.setIp(connectionSection.getHostField().getText())
					.setPort(Integer.valueOf(connectionSection.getPortField().getText()))
					.setName(definition.getTxtInterfaceName().getText())
					
					// Interface settings
					.setLengthFieldLength(Integer.valueOf(settings.getLength().getText()))
					.setLengthType(((JComboBoxItem) settings.getLengthType().getSelectedItem()).getId())
					.setBitmapType(((JComboBoxItem)settings.getBitmapType().getSelectedItem()).getId())
					.setConnectorType(((JComboBoxItem)settings.getComboServerType().getSelectedItem()).getId())
					.setProtocolIdentification(settings.getProtocol().getText())
					.setHeader(settings.getHeader().getText())
					.setIncludeHeaderLength(true) // always yes

					// Flags
					.setIncludeLength(settings.getChkIncludeLength().isSelected())
					.setPosInHeader(settings.getChkPosInHeader().isSelected())
					.setSkipBitmap(settings.getChkSkipBitmap().isSelected())
					.setIsoPresent(settings.getChkIsoPresent().isSelected());
					//.setHeaderLength(settings.getHeader().getText().length())
				
				// start the simulator
				StressSimulator.getInstance().start(configuration);
				
				
				// change title
//				StressSimulator.getInstance().getMainWindow().setTitle(definition.getTxtInterfaceName().getText()
//						+ " ---  Simulator Mode :" + (connectorType.equalsIgnoreCase("TC")? " Client" : "Server"));
				
				connectionSection.getConnectBtn().setVisible(false);
				connectionSection.getBtnDisconnect().setVisible(true);
				
				//Notifier.infoMsg("Connected");
				
				// show loader
				connectionPanel.showLoader(false);
				connectionPanel.setEditable(false);
				((JTabbedPane)connectionPanel.getParent()).setEnabledAt(1, true);
				((JTabbedPane)connectionPanel.getParent()).setEnabledAt(2, true);
				((JTabbedPane)connectionPanel.getParent()).setEnabledAt(3, true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			return 1;
		}
	}
}
