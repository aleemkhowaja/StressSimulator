package com.path.simulator.gui.main.console;

import com.path.simulator.gui.base.BasePanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class ConsolePanel extends BasePanel {

	private JTextArea consoleTxt;

	public ConsolePanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(null);
		createLoaderPanel(285, 150);
				
		consoleTxt = new JTextArea();
		consoleTxt.setBounds(10, 11, 611, 321);
		consoleTxt.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		consoleTxt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JScrollPane scroll = new JScrollPane (consoleTxt, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 11, 611, 321);
		add(scroll);
		
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(clearConsolerListener());
		btnNewButton.setBounds(530, 343, 89, 34);
		add(btnNewButton);

	}

	/**
	 * @return
	 */
	private ActionListener clearConsolerListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consoleTxt.setText("");
			}
		};
	}

	/**
	 * @return the consoleTxt
	 */
	public JTextArea getConsoleTxt() {
		return consoleTxt;
	}

	/**
	 * @param consoleTxt the consoleTxt to set
	 */
	public void setConsoleTxt(JTextArea consoleTxt) {
		this.consoleTxt = consoleTxt;
	}

	public void write(String formatIsoMessage) {
		String text = getConsoleTxt().getText();
		getConsoleTxt().setText(formatIsoMessage.concat(text));
	}
}
