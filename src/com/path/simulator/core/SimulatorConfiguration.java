package com.path.simulator.core;

/**
 * @author MohammadAliMezzawi
 *
 */
public class SimulatorConfiguration {
	
	/**
	 * Hold connector type TS/TC
	 */
	private String connectorType;
	
	/**
	 * host Ip
	 */
	private String ip;
	
	/**
	 * port
	 */
	private int port;
	
	/**
	 * interface name
	 */
	private String name;
	
	/**
	 * length of field length
	 */
	private int lengthFieldLength;
	
	/**
	 * length of field length
	 */
	private String protocolIdentification;
	
	/**
	 * iso header length
	 */
	private int headerLength;
	
	/**
	 * default iso header
	 */
	private String header;
	
	/**
	 * 8 / 16 
	 */
	private String bitmapType;
	
	/**
	 * base 10 / base 256
	 */
	private String lengthType;
	
	/**
	 * Include length in message length
	 */
	private boolean includeLength;
	
	/**
	 * Include length in message length
	 */
	private boolean includeHeaderLength;
	
	/**
	 * Pos in header
	 */
	private boolean posInHeader;
	
	/**
	 * Skip bitmap
	 */
	private boolean skipBitmap;
	
	/**
	 * Iso present
	 */
	private boolean isoPresent;

	/**
	 * Hold idle timeout
	 */
	private int idleTimeout;

	/**
	 * Length field offset
	 */
	private int frameLengthFieldOffset;

	/**
	 * Max frame size
	 */
	private int maxFrameLength;
	
	/**
	 * For thread and other workgroup configuration
	 * set the value here
	 */

	/**
	 * @return the connectorType
	 */
	public String getConnectorType() {
		return connectorType;
	}

	/**
	 * @param connectorType the connectorType to set
	 */
	public SimulatorConfiguration setConnectorType(String connectorType) {
		this.connectorType = connectorType;
		return this;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public SimulatorConfiguration setIp(String ip) {
		this.ip = ip;
		return this;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public SimulatorConfiguration setPort(int port) {
		this.port = port;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public SimulatorConfiguration setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return the lengthFieldLength
	 */
	public int getLengthFieldLength() {
		return lengthFieldLength;
	}

	/**
	 * @param lengthFieldLength the lengthFieldLength to set
	 */
	public SimulatorConfiguration setLengthFieldLength(int lengthFieldLength) {
		this.lengthFieldLength = lengthFieldLength;
		return this;
	}

	/**
	 * @return the protocolIdentification
	 */
	public String getProtocolIdentification() {
		return protocolIdentification;
	}

	/**
	 * @param protocolIdentification the protocolIdentification to set
	 */
	public SimulatorConfiguration setProtocolIdentification(String protocolIdentification) {
		this.protocolIdentification = protocolIdentification;
		return this;
	}

	/**
	 * @return the headerLength
	 */
	public int getHeaderLength() {
		return headerLength;
	}

	/**
	 * @param headerLength the headerLength to set
	 */
	public SimulatorConfiguration setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
		return this;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public SimulatorConfiguration setHeader(String header) {
		this.header = header;
		return this;
	}

	/**
	 * @return the bitmapType
	 */
	public String getBitmapType() {
		return bitmapType;
	}

	/**
	 * @param bitmapType the bitmapType to set
	 */
	public SimulatorConfiguration setBitmapType(String bitmapType) {
		this.bitmapType = bitmapType;
		return this;
	}

	/**
	 * @return the lengthType
	 */
	public String getLengthType() {
		return lengthType;
	}

	/**
	 * @param lengthType the lengthType to set
	 */
	public SimulatorConfiguration setLengthType(String lengthType) {
		this.lengthType = lengthType;
		return this;
	}

	/**
	 * @return the includeLength
	 */
	public boolean isIncludeLength() {
		return includeLength;
	}

	/**
	 * @param includeLength the includeLength to set
	 */
	public SimulatorConfiguration setIncludeLength(boolean includeLength) {
		this.includeLength = includeLength;
		return this;
	}

	/**
	 * @return the includeHeaderLength
	 */
	public boolean isIncludeHeaderLength() {
		return includeHeaderLength;
	}

	/**
	 * @param includeHeaderLength the includeHeaderLength to set
	 */
	public SimulatorConfiguration setIncludeHeaderLength(boolean includeHeaderLength) {
		this.includeHeaderLength = includeHeaderLength;
		return this;
	}

	/**
	 * @return the posInHeader
	 */
	public boolean isPosInHeader() {
		return posInHeader;
	}

	/**
	 * @param posInHeader the posInHeader to set
	 */
	public SimulatorConfiguration setPosInHeader(boolean posInHeader) {
		this.posInHeader = posInHeader;
		return this;
	}

	/**
	 * @return the skipBitmap
	 */
	public boolean isSkipBitmap() {
		return skipBitmap;
	}

	/**
	 * @param skipBitmap the skipBitmap to set
	 */
	public SimulatorConfiguration setSkipBitmap(boolean skipBitmap) {
		this.skipBitmap = skipBitmap;
		return this;
	}

	/**
	 * @return the isoPresent
	 */
	public boolean isIsoPresent() {
		return isoPresent;
	}

	/**
	 * @param isoPresent the isoPresent to set
	 */
	public SimulatorConfiguration setIsoPresent(boolean isoPresent) {
		this.isoPresent = isoPresent;
		return this;
	}

	/**
	 * @param lengthType the lengthType to set
	 */
	public SimulatorConfiguration setIdleTimeout(int timeout) {
		this.idleTimeout = timeout;
		return this;
	}
	
	/**
	 * @return
	 */
	public int getIdleTimeout() {
		return idleTimeout;
	}

	/**
	 * @param fieldOffset
	 * @return
	 */
	public SimulatorConfiguration setFrameLengthFieldOffset(int fieldOffset) {
		frameLengthFieldOffset = fieldOffset;
		return this;
	}

	/**
	 * @return the frameLengthFieldOffset
	 */
	public int getFrameLengthFieldOffset() {
		return frameLengthFieldOffset;
	}

	/**
	 * @return the maxFrameLength
	 */
	public int getMaxFrameLength() {
		return maxFrameLength;
	}

	/**
	 * @param max
	 * @return
	 */
	public SimulatorConfiguration setMaxFrameLength(int max) {
		maxFrameLength = max;
		return this;
	}
}
