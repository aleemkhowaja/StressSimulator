package com.path.atm.engine.iso8583;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.path.atm.vo.engine.AtmInterfaceCO;
import com.path.bo.common.ConstantsCommon;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.solab.iso8583.CustomField;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.codecs.CompositeField;
import com.solab.iso8583.parse.FieldParseInfo;

/**
 * This class will serve as a wrapper for the 3d party Message factory
 * 
 * @author MohammadAliMezzawi http://j8583.sourceforge.net/encoders.html
 *         https://libraries.io/github/Tochemey/Iso85834Net
 */
public class MessageFactory extends com.solab.iso8583.MessageFactory<AtmIsoMessage> {

	/**
	 * Hold reference to the interface CO
	 * <p>
	 * This property will never be exposed to public and any property needed
	 * from it should be retrieved indirectly using a method in the factory.
	 * This approach will allow us later on to make the message factory self
	 * contained. Below is the list of property retrieved indirectly from this
	 * object 1- Interface code : the interface code to which belong this
	 * factory 2- Length type : Base 256/ Base 10 3- includeHeader: include the
	 * header length in the length field ( always true ). 4- includeLength:
	 * include length field length in the length value. 5-
	 * ProtocolIdentification : the protocol identification value. 6- Length of
	 * "FieldLength" : the length of the field that contain the length value 7-
	 * Header length : the length of the header 8- List of Iso fields definition
	 */
	private final AtmInterfaceCO interfaceCO;

	/**
	 * Flag to specify if we should use base 256 to encode length
	 */
	private boolean useAsciiEncoding;

	/**
	 * Create a message factory
	 */
	public MessageFactory(AtmInterfaceCO interfaceCO) {
		this.interfaceCO = interfaceCO;
	}

	/**
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	public AtmIsoMessage parseMessage(byte[] bytes) throws UnsupportedEncodingException, ParseException {
		return super.parseMessage(bytes, getHeaderLength());
	}

	/**
	 * @todo fix the global variables
	 * 
	 *       Convert HashMap to ISO message
	 * @param isoMsgHm
	 * @return
	 */
	public AtmIsoMessage convertToIsoMsg(HashMap<String, Object> isoMsgHm) {

		if (null == isoMsgHm || isoMsgHm.get("type") == null)
			throw new IllegalArgumentException("Type is mandatory");

		AtmIsoMessage isoMessage = new AtmIsoMessage();
		isoMessage.setType((int) isoMsgHm.get("type"));
		isoMessage.setCharacterEncoding(getCharacterEncoding());

		try {
			PathPropertyUtil.copyProperties(isoMsgHm, isoMessage);
		} catch (Exception e) {
			// @todo implement exception handling
			e.printStackTrace();
		}

		// get the template of this type
		IsoMessage isoMsgTemplate = getMessageTemplate(isoMessage.getType());

		if (null == isoMsgTemplate)
			throw new RuntimeException("no template");

		for (int i = 2; i <= 128; i++) {

			// skip non existence values
			if (!isoMsgTemplate.hasField(i))
				continue;

			IsoValue<?> isoTemplField = isoMsgTemplate.getAt(i);
			IsoValue<?> isoValue = createIsoFieldByTemplate(isoTemplField, isoMsgHm, String.valueOf(i), true);

			if (isoValue != null)
				isoValue.setCharacterEncoding(getCharacterEncoding());

			isoMessage.setField(i, isoValue);

		}

		return isoMessage;
	}

	/**
	 * Create iso field
	 * 
	 * @param isoTemplField
	 * @param index
	 * @param topLevel
	 * @return
	 */
	private IsoValue<?> createIsoFieldByTemplate(IsoValue<?> isoTemplField, HashMap<String, Object> values,
			String index, boolean topLevel) {

		// check if composite field
		if (isoTemplField.getValue() instanceof CompositeField) {

			CompositeField isoTemplCompField = (CompositeField) isoTemplField.getValue();
			CompositeField newCompfield = new CompositeField();

			int subIndexInc = 1;

			for (IsoValue<?> subTemplField : isoTemplCompField.getValues()) {

				// build the sub field key
				String subIndex = index.concat(IsoDefinitionConstants.ISO_HM_SUBFIELD_SEPARATOR)
						.concat(String.valueOf(subIndexInc));

				// create iso field
				IsoValue<?> sv = createIsoFieldByTemplate(subTemplField, values, subIndex, false);

				// set sub field encoding
				if (sv != null) {
					sv.setCharacterEncoding(getCharacterEncoding());
					newCompfield.addValue(sv);
				}

				subIndexInc++;
			}

			/**
			 * If Composite field doesn't have any subfields we will consider it
			 * as null
			 */
			if (null == newCompfield.getValues())
				return null;

			// composite field
			return isoTemplField.getType().needsLength()
					? new IsoValue<>(isoTemplField.getType(), newCompfield, isoTemplField.getLength(), newCompfield)
					: new IsoValue<>(isoTemplField.getType(), newCompfield, newCompfield);
		}

		// has encoder, index is the field number
		final CustomField<Object> cf = topLevel ? getCustomField(Integer.valueOf(index)) : null;

		/**
		 * @note @todo : we should check about the value type should we convert
		 *       it to string , do we need to use the parser?? does the parse do
		 *       so ?? or it's the encoder this is a very important point to
		 *       check
		 */
		Object value = values.get(IsoDefinitionConstants.ISO_HM_FIELD_PREFIX.concat(index));

		/**
		 * We may reuse parser if needed but the issue for now is that Amount is
		 * a BigDecimal and if we send an empty string we will get a number
		 * format exception Binary will have the same issue also.
		 * 
		 * Seems that create field from template using new contain an error
		 */
		// set default value empty
		if (value == null)
			return null;

		// subfield or Simple field without encoder
		if (cf == null)
			return isoTemplField.getType().needsLength()
					? new IsoValue<>(isoTemplField.getType(), value, isoTemplField.getLength())
					: new IsoValue<>(isoTemplField.getType(), value);

		// subfield or Simple field with encoder
		return isoTemplField.getType().needsLength()
				? new IsoValue<>(isoTemplField.getType(), value, isoTemplField.getLength(), cf)
				: new IsoValue<>(isoTemplField.getType(), value, cf);

	}

	/**
	 * Convert AtmIsoMessage to hashMap
	 * 
	 * @param isoMessage
	 * @return
	 */
	public HashMap<String, Object> convertToHashMap(AtmIsoMessage isoMessage) {

		HashMap<String, Object> isoMsgHm = new HashMap<>();

		// @todo @note : if we promote fields to protected or public
		// we should make sure to skip it from convertToMap
		isoMsgHm = PathPropertyUtil.convertToMap(isoMessage);

		// set the iso MTI code
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_MTICODE, IsoUtil.convertMtiToHex((int) isoMsgHm.get("type")));

		// populate the basic fields
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_COMPCODE, isoMessage.getCompCode());
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_COMPNAME, isoMessage.getCompBriefName());

		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_BRCODE, isoMessage.getTrxBranchCode());
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_TELLCODE, isoMessage.getTellerId());
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_TELLERUSER, isoMessage.getTellerUserId());

		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_TRXCODE, isoMessage.getTrxType());
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_CHARGETRXCODE, isoMessage.getChargeTrxType());

		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_MERACC, isoMessage.getMerchAdditionalRef());
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_TERMINAL, isoMessage.getTerminalCode());
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_TERMDESC, isoMessage.getTerminalDesc());

		// @todo fix this
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_RESP_RESULT, isoMessage.getTerminalCode());

		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_INTERFACE_CODE, isoMessage.getInterfaceCode());
		isoMsgHm.put(IsoDefinitionConstants.ISO_HEADER, isoMessage.getIsoHeader());

		// get iso Fields
		HashMap<String, Object> isoFieldsHm = extractIsoFieldsHm(isoMessage);

		// set auth code
		String authCodeKey = IsoDefinitionConstants.ISO_HM_FIELD_PREFIX
				.concat(String.valueOf(IsoDefinitionConstants.BIT_APPROVAL_CODE));
		isoMsgHm.put(IsoDefinitionConstants.ISO_FIELD_AUTHCODE, isoFieldsHm.get(authCodeKey));
		isoMsgHm.putAll(isoFieldsHm);

		log.debug("Convert to hashMap:" + isoMsgHm);
		return isoMsgHm;
	}

	/**
	 * This method extract IsoFields from Atm Iso Message as HashMap
	 * 
	 * @todo include parameters to control the iso null include
	 * @return
	 */
	public HashMap<String, Object> extractIsoFieldsHm(AtmIsoMessage isoMessage) {

		HashMap<String, Object> isoFieldsHm = new HashMap<>();
		// get the parse template for this mti
		Map<Integer, FieldParseInfo> parseGuide = parseMap.get(isoMessage.getType());
		List<Integer> index = parseOrder.get(isoMessage.getType());

		// @todo handle case where type doesn't exist
		for (Integer i : index) {
			FieldParseInfo fpi = parseGuide.get(i);
			IsoValue<?> isoField = isoMessage.getField(i);

			// we should log a warning here
			if (!isoMessage.hasField(i)) {
				isoFieldsHm.put(IsoDefinitionConstants.ISO_HM_FIELD_PREFIX.concat(String.valueOf(i)), null);
				continue;
			}

			/**
			 * extract composite fields
			 */
			if (isoField.getValue() instanceof CompositeField) {
				CompositeField field = (CompositeField) isoField.getValue();

				int subFieldId = 1;
				for (IsoValue<?> v : field.getValues()) {
					String key = IsoDefinitionConstants.ISO_HM_FIELD_PREFIX.concat(String.valueOf(i))
							.concat(IsoDefinitionConstants.ISO_HM_SUBFIELD_SEPARATOR)
							.concat(String.valueOf(subFieldId));
					isoFieldsHm.put(key, v.getValue());
					subFieldId++;
				}

				// @todo check for the date,since we are handling it as string
				String parentkey = IsoDefinitionConstants.ISO_HM_FIELD_PREFIX.concat(String.valueOf(i));
				isoFieldsHm.put(parentkey, field.encodeField(field));

				continue;
			}

			isoFieldsHm.put(IsoDefinitionConstants.ISO_HM_FIELD_PREFIX.concat(String.valueOf(i)), isoField.getValue());
		}

		return isoFieldsHm;
	}

	/**
	 * Create Iso Message based on the given type and populate it using the the
	 * supplied message data.
	 * 
	 * <p>
	 * For network messages the fields existence can be overridden in the
	 * message definitions which make the ISO message template defined in the
	 * configuration invalid for that we are including the new parameter
	 * includedFields
	 * 
	 * @param type
	 * @param messageData
	 * @param includedFields
	 * @return
	 */
	public AtmIsoMessage createMessage(int type, HashMap<String, Object> messageData,
			ArrayList<Integer> includedFields) {

		/**
		 * Return the same header unless we have an ISO Header for this MTI
		 */
		String isoHeader = null != getIsoHeader(type) ? getIsoHeader(type)
				: (String) messageData.get(IsoDefinitionConstants.ISO_HEADER);

		// create an empty response
		AtmIsoMessage isoMsg = createIsoMessage(isoHeader);

		isoMsg.setFactory(this);
		isoMsg.setType(type);
		isoMsg.setCharacterEncoding(getCharacterEncoding());
		isoMsg.setBinary(getUseBinaryMessages());
		isoMsg.setForceSecondaryBitmap(isForceSecondaryBitmap());
		// @todo check if it's needed
		// m.setForceStringEncoding(forceStringEncoding);
		isoMsg.setBinaryBitmap(isUseBinaryBitmap());
		isoMsg.setEtx(getEtx());

		// Copy the values from the template or the request (request has
		// preference)
		IsoMessage isoMsgTemplate = getMessageTemplate(type);

		// build message
		for (int i = 2; i < 128; i++) {

			if (!isoMsgTemplate.hasField(i) || (null != includedFields && !includedFields.contains(i)))
				continue;

			IsoValue<?> isoTemplField = isoMsgTemplate.getAt(i);
			IsoValue<?> isoValue = createIsoFieldByTemplate(isoTemplField, messageData, String.valueOf(i), true);

			if (isoValue != null)
				isoValue.setCharacterEncoding(getCharacterEncoding());

			isoMsg.setField(i, isoValue);
		}

		return isoMsg;
	}

	/**
	 * Creates a Iso message, override this method in the subclass to provide
	 * your own implementations of IsoMessage.
	 * 
	 * @param header
	 *            The optional ISO header that goes before the message type
	 * @return IsoMessage
	 */
	protected AtmIsoMessage createIsoMessage(String header) {
		return new AtmIsoMessage(header);
	}

	/**
	 * Creates a Iso message with the specified binary ISO header. Override this
	 * method in the subclass to provide your own implementations of IsoMessage.
	 * 
	 * @param binHeader
	 *            The optional ISO header that goes before the message type
	 * @return IsoMessage
	 */
	protected AtmIsoMessage createIsoMessageWithBinaryHeader(byte[] binHeader) {
		return new AtmIsoMessage(binHeader);
	}

	/**
	 * Returns a string representation of the object.
	 */
	public String toString() {
		return "MessageFactory";
	}

	/**
	 * @return the interfaceCO
	 */
	private AtmInterfaceCO getInterfaceCO() {
		return interfaceCO;
	}

	/**
	 * @return the useAsciiEncoding
	 */
	public boolean isUseAsciiEncoding() {
		return useAsciiEncoding;
	}

	/**
	 * @param useAsciiEncoding
	 *            the useAsciiEncoding to set
	 */
	public void setUseAsciiEncoding(boolean useAsciiEncoding) {
		this.useAsciiEncoding = useAsciiEncoding;
	}

	/**
	 * Return length type ( base 256/10) This property is retrieved from the
	 * interfaceCO
	 * 
	 * @return
	 */
	public String getLengthType() {
		return interfaceCO.getLengthType();
	}

	/**
	 * Return true if the header length should be included in the length value
	 * This property is retrieved from the interfaceCO Note: this value is
	 * always true.
	 * 
	 * @return
	 */
	public boolean isIncludeHeader() {
		return StringUtil.nullEmptyToValue(getInterfaceCO().getIncludeHeader(), ConstantsCommon.NO)
				.equalsIgnoreCase(ConstantsCommon.NO);
	}

	/**
	 * Return true if the length of the field length should be included in the
	 * length value
	 * 
	 * @return
	 */
	public boolean isIncludeLenFieldLength() {
		return NumberUtil.nullToZero(getInterfaceCO().getIncludeLength()).equals(BigDecimal.ZERO);
	}

	/**
	 * Return protocol identification
	 * 
	 * @return
	 */
	public String getProtocolIdentification() {
		return StringUtil.nullToEmpty(getInterfaceCO().getProtocolIdentification());
	}

	/**
	 * Return the length of "Field length"
	 * 
	 * @return
	 */
	public int getLenFieldLength() {
		return NumberUtil.nullToZero(getInterfaceCO().getMsgLength()).intValue();
	}

	/**
	 * Length of the header
	 * 
	 * @return
	 */
	public int getHeaderLength() {
		return NumberUtil.nullToZero(getInterfaceCO().getHeaderLength()).intValue();
	}

	/**
	 * Return the interface code to which belong this message factory
	 * 
	 * @return
	 */
	public BigDecimal getInterfaceCode() {
		return getInterfaceCO().getCode();
	}
}
