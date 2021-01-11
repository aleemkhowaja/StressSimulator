package com.path.simulator.core.vo;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.path.atm.engine.iso8583.IsoUtil;
import com.path.atm.engine.iso8583.MessageFactory;
import com.path.atm.engine.util.AtmEngineConstants;
import com.path.bo.common.ConstantsCommon;

public class MessageVO {

	private String name;
	
	private String payload;
	
	private boolean selected;

	private int total;

	private MessageFactory factory;
	
	
	/**
	 * Creates and returns a ByteBuffer with the data of the message, including
	 * the length header. The returned buffer is already flipped, so it is ready
	 * to be written to a Channel.
	 *
	 */
	public ByteBuffer writeToBuffer(int lengthBytes) {

		// validate length bytes
		if (lengthBytes > 4)
			throw new IllegalArgumentException("The length header can have at most 4 bytes");

		byte[] data = payload.getBytes(AtmEngineConstants.CHAR_ENCODING);

		String protocolIdentification = getFactory().getProtocolIdentification();

		ByteBuffer buf = ByteBuffer
				.allocate(protocolIdentification.length() + lengthBytes + data.length + (getEtx() > -1 ? 1 : 0));

		// Add protocol identification
		if (!protocolIdentification.isEmpty()) {
			buf.put(protocolIdentification.getBytes(AtmEngineConstants.CHAR_ENCODING));
		}

		// calculate length
		byte[] lengthValue = calculateLength(data, lengthBytes);

		if (null != lengthValue)
			buf.put(lengthValue);

		// push data to the byte buffer
		buf.put(data);

		// ETX
		if (getEtx() > -1) {
			buf.put((byte) getEtx());
		}
		buf.flip();
		return buf;
	}

	/**
	 * Calculate Message length based on the 1- Length type ( Base 10 / 256 ) 2-
	 * Include length flag 3- Include header flag ( always yes )
	 * 
	 * @param data
	 * @param lengthBytes
	 * @return
	 */
	private byte[] calculateLength(byte[] data, int lengthBytes) {
		if (lengthBytes <= 0)
			return null;

		byte[] lengthByteArray = null;
		// include header length
		int l = getFactory().isIncludeHeader() ? data.length - returnHeaderLength() : data.length;

		// include Length field length to length
		l = getFactory().isIncludeLenFieldLength() ? l : l + lengthBytes;

		if (getEtx() > -1)
			l++;

		if (getFactory().isUseAsciiEncoding()) {
			String asciiLength = IsoUtil.convertLenToAscii(l, lengthBytes);
			lengthByteArray = asciiLength.getBytes(AtmEngineConstants.CHAR_ENCODING);

		} else if (getFactory().getLengthType().equals(AtmEngineConstants.LENGTH_BASE10)) {

			String decimalLength = StringUtils.leftPad(String.valueOf(l), lengthBytes, ConstantsCommon.ZERO);
			lengthByteArray = decimalLength.getBytes(AtmEngineConstants.CHAR_ENCODING);

		} else {

			ArrayList<Byte> lengthBytesList = new ArrayList<Byte>();
			lengthByteArray = new byte[lengthBytes];

			// @todo add the prepender check at this level
			if (lengthBytes == 4)
				lengthBytesList.add((byte) ((l & 0xff000000) >> 24));

			if (lengthBytes > 2)
				lengthBytesList.add((byte) ((l & 0xff0000) >> 16));

			if (lengthBytes > 1)
				lengthBytesList.add((byte) ((l & 0xff00) >> 8));

			lengthBytesList.add((byte) (l & 0xff));

			lengthByteArray = (byte[]) ArrayUtils.toPrimitive(lengthBytesList.toArray());
		}

		return lengthByteArray;
	}
    
	private int getEtx() {
		// TODO Auto-generated method stub
		return -1;
	}


	/**
	 * Return header length
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public int returnHeaderLength() {
		return 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}


	/**
	 * @return the factory
	 */
	public MessageFactory getFactory() {
		return factory;
	}


	public void setFactory(MessageFactory factory) {
		this.factory = factory;
	}
}
