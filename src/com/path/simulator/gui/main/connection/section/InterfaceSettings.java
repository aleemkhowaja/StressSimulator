package com.path.simulator.gui.main.connection.section;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import com.path.atm.engine.util.AtmEngineConstants;
import com.path.simulator.core.SimulatorConfiguration;
import com.path.simulator.gui.util.JComboBoxItem;

import java.awt.Font;

public class InterfaceSettings extends JPanel {
	
	private JLabel lblEngineType = new JLabel("Server Type");
	private JComboBox<JComboBoxItem> cbServerType = new JComboBox<JComboBoxItem>();
	
	private JLabel lblProtocol = new JLabel("Protocol id");
	private JTextField protocol;
	
	private JLabel lblHeaderLength = new JLabel("Length Field Len");
	private JTextField fieldLength = new JTextField();
	
	private JLabel lblHeader = new JLabel("Header");
	private JTextField header = new JTextField();
	
	private JLabel lblBitMapType = new JLabel("Bitmap type");
	private JComboBox<JComboBoxItem> cbBitmapType = new JComboBox<JComboBoxItem>();
	
	private JLabel lblLengthType = new JLabel("Length type");
	private JComboBox<JComboBoxItem> cbLengthType = new JComboBox<JComboBoxItem>();
	
	private JLabel lblPosInHeader = new JLabel("Pos In Header");
	private JCheckBox chkPosInHeader = new JCheckBox("");
	
	private final JLabel lIncludeLength = new JLabel("Include Length");
	private JCheckBox chkIncludeLength = new JCheckBox("");
	
	private final JLabel lblSkipBitmap = new JLabel("Skip Bitmap");
	private JCheckBox chkSkipBitmap = new JCheckBox("");

	private final JLabel lblIsoPresent = new JLabel("Iso Present");
	private JCheckBox chkIsoPresent = new JCheckBox("");
	
	
	public InterfaceSettings(SimulatorConfiguration config) {
		setLayout(null);
		setBounds(5, 227, 614, 214);
		setBorder(new TitledBorder(null, "Interface Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		//Length of field length
		lblHeaderLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHeaderLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeaderLength.setBounds(12, 17, 110, 32);
		fieldLength.setText("2");
		fieldLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fieldLength.setColumns(10);
		fieldLength.setBounds(138, 17, 110, 32);
		add(lblHeaderLength);
		add(fieldLength);
		
		// length type
		lblLengthType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLengthType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLengthType.setBounds(288, 17, 102, 32);
		cbLengthType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbLengthType.setBounds(410, 17, 110, 32);
		cbLengthType.addItem(new JComboBoxItem("1", "base 256"));
		cbLengthType.addItem(new JComboBoxItem("2", "base 10"));
		cbLengthType.setSelectedIndex(AtmEngineConstants.LENGTH_BASE10.
				equalsIgnoreCase(config.getLengthType()) ? 1 : 0);
		add(lblLengthType);
		add(cbLengthType);
		
		// bitmap type
		lblBitMapType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBitMapType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBitMapType.setBounds(12, 60, 110, 32);
		cbBitmapType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbBitmapType.setBounds(138, 60, 110, 32);
		cbBitmapType.addItem(new JComboBoxItem("1", "16 chars"));
		cbBitmapType.addItem(new JComboBoxItem("2", "8 chars"));
		cbBitmapType.setSelectedIndex(AtmEngineConstants.BITMAP_16.
				equalsIgnoreCase(config.getBitmapType())? 0 : 1);
		add(lblBitMapType);
		add(cbBitmapType);
		
		// engine type
		lblEngineType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEngineType.setBounds(288, 60, 102, 32);
		lblEngineType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbServerType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbServerType.setBounds(410, 60, 110, 32);
		cbServerType.addItem(new JComboBoxItem("TS", "Server"));
		cbServerType.addItem(new JComboBoxItem("TC", "Client"));
//		cbServerType.setSelectedIndex(config.getConnectorType()
//				.equals(AtmEngineConstants.SERVER_CONNECTOR)? 0 : 1);
		add(lblEngineType);
		add(cbServerType);
		
		
		// Protocol
		lblProtocol.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProtocol.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProtocol.setBounds(12, 103, 110, 32);
		protocol = new JTextField();
		protocol.setBounds(138, 103, 110, 32);
		protocol.setColumns(10);
		add(lblProtocol);
		add(protocol);
		
		// header
		lblHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHeader.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeader.setBounds(288, 103, 102, 32);
		header.setFont(new Font("Tahoma", Font.PLAIN, 14));
		header.setColumns(10);
		header.setBounds(410, 103, 110, 32);
		header.setText(config.getHeader());
		add(lblHeader);
		add(header);	
		
		
		// Include length
		lIncludeLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lIncludeLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lIncludeLength.setBounds(12, 144, 110, 32);
		chkIncludeLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chkIncludeLength.setBounds(138, 144, 110, 32);
		chkIncludeLength.setSelected(config.isIncludeLength());
		add(lIncludeLength);
		add(chkIncludeLength);
		
		// Pos in header
		lblPosInHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPosInHeader.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPosInHeader.setBounds(298, 144, 102, 32);
		chkPosInHeader.setSelected(config.isPosInHeader());
		chkPosInHeader.setBounds(410, 144, 110, 32);
		add(chkPosInHeader);
		add(lblPosInHeader);
		
		
		// skip bitmap
		lblSkipBitmap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSkipBitmap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSkipBitmap.setBounds(12, 177, 110, 32);
		chkSkipBitmap.setBounds(138, 177, 110, 32);
		add(chkSkipBitmap);
		add(lblSkipBitmap);
		
		
		// ISO present
		lblIsoPresent.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIsoPresent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIsoPresent.setBounds(288, 177, 102, 32);
		chkIsoPresent.setBounds(410, 177, 110, 32);
		add(lblIsoPresent);
		add(chkIsoPresent);
	}
	

	/**
	 * @return the lblHeaderLength
	 */
	public JLabel getLblHeaderLength() {
		return lblHeaderLength;
	}

	/**
	 * @param lblHeaderLength the lblHeaderLength to set
	 */
	public void setLblHeaderLength(JLabel lblHeaderLength) {
		this.lblHeaderLength = lblHeaderLength;
	}

	/**
	 * @return the headerLength
	 */
	public JTextField getLength() {
		return fieldLength;
	}

	/**
	 * @param headerLength the headerLength to set
	 */
	public void setLength(JTextField fieldLength) {
		this.fieldLength = fieldLength;
	}

	/**
	 * @return the lblHeader
	 */
	public JLabel getLblHeader() {
		return lblHeader;
	}

	/**
	 * @param lblHeader the lblHeader to set
	 */
	public void setLblHeader(JLabel lblHeader) {
		this.lblHeader = lblHeader;
	}

	/**
	 * @return the header
	 */
	public JTextField getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(JTextField header) {
		this.header = header;
	}

	/**
	 * @return the lblBitMapType
	 */
	public JLabel getLblBitMapType() {
		return lblBitMapType;
	}

	/**
	 * @param lblBitMapType the lblBitMapType to set
	 */
	public void setLblBitMapType(JLabel lblBitMapType) {
		this.lblBitMapType = lblBitMapType;
	}

	/**
	 * @return the bitmapType
	 */
	public JComboBox<JComboBoxItem> getBitmapType() {
		return cbBitmapType;
	}

	/**
	 * @param bitmapType the bitmapType to set
	 */
	public void setBitmapType(JComboBox<JComboBoxItem> bitmapType) {
		this.cbBitmapType = bitmapType;
	}

	/**
	 * @return the lblLengthType
	 */
	public JLabel getLblLengthType() {
		return lblLengthType;
	}

	/**
	 * @param lblLengthType the lblLengthType to set
	 */
	public void setLblLengthType(JLabel lblLengthType) {
		this.lblLengthType = lblLengthType;
	}

	/**
	 * @return the lengthType
	 */
	public JComboBox<JComboBoxItem> getLengthType() {
		return cbLengthType;
	}

	/**
	 * @param lengthType the lengthType to set
	 */
	public void setLengthType(JComboBox<JComboBoxItem> lengthType) {
		this.cbLengthType = lengthType;
	}

	/**
	 * @return the lblchckbxIncludeLength
	 */
	public JLabel getLblchckbxIncludeLength() {
		return lblPosInHeader;
	}

	/**
	 * @param lblchckbxIncludeLength the lblchckbxIncludeLength to set
	 */
	public void setLblchckbxIncludeLength(JLabel lblchckbxIncludeLength) {
		this.lblPosInHeader = lblchckbxIncludeLength;
	}

	/**
	 * @return the lblchckbxServer
	 */
	public JLabel getLblchckbxServer() {
		return lblEngineType;
	}

	/**
	 * @param lblchckbxServer the lblchckbxServer to set
	 */
	public void setLblchckbxServer(JLabel lblchckbxServer) {
		this.lblEngineType = lblchckbxServer;
	}

	/**
	 * @return the comboServerType
	 */
	public JComboBox<JComboBoxItem> getComboServerType() {
		return cbServerType;
	}

	/**
	 * @param comboServerType the comboServerType to set
	 */
	public void setComboServerType(JComboBox<JComboBoxItem> comboServerType) {
		this.cbServerType = comboServerType;
	}


	/**
	 * @return the protocol
	 */
	public JTextField getProtocol() {
		return protocol;
	}


	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(JTextField protocol) {
		this.protocol = protocol;
	}


	/**
	 * @return the chkPosInHeader
	 */
	public JCheckBox getChkPosInHeader() {
		return chkPosInHeader;
	}


	/**
	 * @param chkPosInHeader the chkPosInHeader to set
	 */
	public void setChkPosInHeader(JCheckBox chkPosInHeader) {
		this.chkPosInHeader = chkPosInHeader;
	}


	/**
	 * @return the chkIncludeLength
	 */
	public JCheckBox getChkIncludeLength() {
		return chkIncludeLength;
	}


	/**
	 * @param chkIncludeLength the chkIncludeLength to set
	 */
	public void setChkIncludeLength(JCheckBox chkIncludeLength) {
		this.chkIncludeLength = chkIncludeLength;
	}


	/**
	 * @return the chkSkipBitmap
	 */
	public JCheckBox getChkSkipBitmap() {
		return chkSkipBitmap;
	}


	/**
	 * @param chkSkipBitmap the chkSkipBitmap to set
	 */
	public void setChkSkipBitmap(JCheckBox chkSkipBitmap) {
		this.chkSkipBitmap = chkSkipBitmap;
	}


	/**
	 * @return the chkIsoPresent
	 */
	public JCheckBox getChkIsoPresent() {
		return chkIsoPresent;
	}


	/**
	 * @param chkIsoPresent the chkIsoPresent to set
	 */
	public void setChkIsoPresent(JCheckBox chkIsoPresent) {
		this.chkIsoPresent = chkIsoPresent;
	}
}
