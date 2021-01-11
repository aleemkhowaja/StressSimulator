package com.path.simulator.gui.main;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.path.simulator.core.SimulatorConfiguration;
import com.path.simulator.gui.main.bulk.BulkMessagePanel;
import com.path.simulator.gui.main.connection.ConnectionPanel;
import com.path.simulator.gui.main.console.ConsolePanel;
import com.path.simulator.gui.main.plain.PlainMessagePanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import java.awt.Font;

public class SwitchFrame extends JFrame {
	
	/**
	 * 
	 */
	JTabbedPane tabbedPane;
	
	/**
	 * Console panel
	 */
	ConsolePanel consolePanel;
	
	
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
					SwitchFrame window = new SwitchFrame(null);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Make sure that the design plugin work fine
	 */
	public SwitchFrame() {
		this(new SimulatorConfiguration());
	}

	/**
	 * Create the application.
	 * @param config 
	 */
	public SwitchFrame(SimulatorConfiguration config) {
		initialize(config);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param config 
	 */
	private void initialize(SimulatorConfiguration config) {
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 634, 479);
		getContentPane().add(tabbedPane);
		setBounds(100, 100, 670, 536);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// connection tab
		ConnectionPanel connectionPanel = new ConnectionPanel(config);
		tabbedPane.addTab("Connection", connectionPanel);	
		
		PlainMessagePanel messagePanel = new PlainMessagePanel();
		messagePanel.getMessagePayload().setFont(new Font("Monospaced", Font.PLAIN, 18));
		messagePanel.getMessagePayload().setLineWrap(true);
		tabbedPane.addTab("Plain Message", messagePanel);
		tabbedPane.setEnabledAt(1, false);
		
		BulkMessagePanel bulkMessagePanel = new BulkMessagePanel();
		tabbedPane.addTab("Bulk Messages", bulkMessagePanel);
		tabbedPane.setEnabledAt(2, false);
		
		consolePanel = new ConsolePanel();
		tabbedPane.addTab("Console", consolePanel);
		tabbedPane.setEnabledAt(3, false);
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * @param tabbedPane the tabbedPane to set
	 */
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	/**
	 * @return the consolePanel
	 */
	public ConsolePanel getConsolePanel() {
		return consolePanel;
	}

	/**
	 * @param consolePanel the consolePanel to set
	 */
	public void setConsolePanel(ConsolePanel consolePanel) {
		this.consolePanel = consolePanel;
	}
}
