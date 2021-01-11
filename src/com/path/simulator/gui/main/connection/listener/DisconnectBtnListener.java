package com.path.simulator.gui.main.connection.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import com.path.simulator.core.StressSimulator;
import com.path.simulator.gui.main.connection.ConnectionPanel;
import com.path.simulator.gui.main.connection.section.ConnectionSection;
import com.path.simulator.util.Notifier;

public class DisconnectBtnListener implements ActionListener {

	private ConnectionPanel connectionPanel;

	/**
	 * @param connectionPanel
	 */
	public DisconnectBtnListener(ConnectionPanel connectionPanel) {
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
				
				// if connection succeed assign it to the simulator
				StressSimulator.getInstance().shutdown();
				
				ConnectionSection connectionSection = connectionPanel.getConnectionSection();
				connectionSection.getConnectBtn().setVisible(true);
				connectionSection.getBtnDisconnect().setVisible(false);
				
				//Notifier.infoMsg("Disconnected");
				StressSimulator.getInstance().getMainWindow().setTitle("");
				// show loader
				connectionPanel.showLoader(false);
				connectionPanel.setEditable(true);
				((JTabbedPane)connectionPanel.getParent()).setEnabledAt(1, false);
				((JTabbedPane)connectionPanel.getParent()).setEnabledAt(2, false);
				((JTabbedPane)connectionPanel.getParent()).setEnabledAt(3, false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			return 1;
		}
	}
}
