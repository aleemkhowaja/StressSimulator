package com.path.atm.engine.iso8583;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import org.codehaus.jackson.annotate.JsonIgnore;
import com.path.atm.engine.util.AtmEngineConstants;
import com.path.bo.common.ConstantsCommon;
import com.solab.iso8583.IsoMessage;


/**
 * This class house all the ATM ISO message behaviors
 * @todo we may remove the reference to the objects
 * @author MohammadAliMezzawi
 *
 */
public class AtmIsoMessage extends IsoMessage implements Serializable {
	
	/**
	 * Iso Message id
	 * inherited from task
	 */
	private BigDecimal id;
	
	/**
	 * Interface code
	 * inherited from task
	 */
	private BigDecimal interfaceCode;
	
	/**
	 * Acquirer
	 */
	private BigDecimal acquirerId;
	
	/**
	 * Hold reference to comp code
	 */
	private BigDecimal compCode;
	
	/**
	 * Hold reference to comp brief Name
	 */
	private String compBriefName;
	
	/**
	 * Hold reference to Branch code
	 */
	private BigDecimal trxBranchCode;
	
	/**
	 * Teller
	 */
	private BigDecimal tellerId;
	
	/**
	 * Hold reference to teller user Id
	 */
	private String tellerUserId;

	/**
	 * transaction type id
	 */
	private BigDecimal trxType;
	
	/**
	 * Charge transaction type id
	 */
	private BigDecimal chargeTrxType;
	
	/**
	 * Merchant
	 */
	private BigDecimal merchantCode;
	
	/**
	 * Merchant Additional Reference
	 */
	private String merchAdditionalRef;
	
	/**
	 * Merchant Iban Acc no
	 */
	private String merchIbanAccNo;
	
	/**
	 * Terminal
	 */
	private String terminalCode;
	
	/**
	 * Terminal
	 */
	private String terminalDesc;
	
	/**
	 * Hold error desc
	 * Mainly used to hold response error Desc
	 */
	private String errorDesc;
	
	/**
	 * Hold core trxNb 
	 */
	private BigDecimal trxNb;
	
	
	/**
	 * Reference to the creator
	 */
	@JsonIgnore
	private MessageFactory factory;
	
	
	/**
	 * Default constructor
	 */
	public AtmIsoMessage() {
		super();
	}
	
	/**
	 * @param header
	 */
	public AtmIsoMessage(String header) {
		super(header);
	}
	
	/**
	 * @param binHeader
	 */
	public AtmIsoMessage(byte[] binHeader) {
		super(binHeader);
	}
	


	
	
	/**
	 * Creates and returns a ByteBuffer with the data of the message, including the length header.
	 * The returned buffer is already flipped, so it is ready to be written to a Channel.
	 *
	 */
    public ByteBuffer writeToBuffer(int lengthBytes) {
    	
    	// validate length bytes
    	if (lengthBytes > 4)
    		throw new IllegalArgumentException("The length header can have at most 4 bytes");

    	byte[] data = writeData();
    	
    	String protocolIdentification = ""; /*StringUtil.nullToEmpty(getFactory()
    		.getInterfaceCO().getProtocolIdentification());*/
    	
    	ByteBuffer buf = ByteBuffer.allocate( protocolIdentification.length() + 
    		lengthBytes + data.length + (getEtx() > -1 ? 1 : 0));
    	
    	// Add protocol identification
    	if(!protocolIdentification.isEmpty()){
    		buf.put(protocolIdentification.getBytes(AtmEngineConstants.CHAR_ENCODING));
    	}
    	
    	//
    	if (lengthBytes > 0) {
    		
    		// include header length ( this is always yes )
    		int l = "Y"
    			.equalsIgnoreCase(ConstantsCommon.NO)? data.length - returnHeaderLength() : data.length;
    		
    		// include Length field length to length
    		l = !getFactory().isIncludeLenFieldLength()? l : l + lengthBytes;
    		
    		if (getEtx() > -1)
    			l++;
    		
    		if(getFactory().isUseAsciiEncoding()) {
    			String asciiLength = IsoUtil.convertLenToAscii(l,lengthBytes);
    			buf.put(asciiLength.getBytes(AtmEngineConstants.CHAR_ENCODING));
    			
    		}else {
    			
    			// @todo add the prepender check at this level
    			if (lengthBytes == 4)
                    buf.put((byte)((l & 0xff000000) >> 24));
    			
        		if (lengthBytes > 2)
                    buf.put((byte)((l & 0xff0000) >> 16));
        		
        		if (lengthBytes > 1)
                    buf.put((byte)((l & 0xff00) >> 8));
        		
                buf.put((byte)(l & 0xff));
    		}
    	}
    	
    	buf.put(data);
    	
    	//ETX
    	if (getEtx() > -1) {
    		buf.put((byte)getEtx());
    	}
    	buf.flip();
    	return buf;
    }
    
    /**
	 * Return header length
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public int returnHeaderLength() {

		if (getIsoHeader() != null)
			try {
				return getIsoHeader().getBytes(getCharacterEncoding()).length;
			} catch (UnsupportedEncodingException e) {

				// @todo Throw runtime exception
			}

		if (getBinaryIsoHeader() != null)
			return getBinaryIsoHeader().length;

		return 0;
	}

	/**
	 * @return the id
	 */
	public BigDecimal getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}

	/**
	 * @return the interfaceCode
	 */
	public BigDecimal getInterfaceCode() {
		return interfaceCode;
	}

	/**
	 * @param interfaceCode the interfaceCode to set
	 */
	public void setInterfaceCode(BigDecimal interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	/**
	 * @return the acquirerId
	 */
	public BigDecimal getAcquirerId() {
		return acquirerId;
	}

	/**
	 * @param acquirerId the acquirerId to set
	 */
	public void setAcquirerId(BigDecimal acquirerId) {
		this.acquirerId = acquirerId;
	}

	/**
	 * @return the compCode
	 */
	public BigDecimal getCompCode() {
		return compCode;
	}

	/**
	 * @param compCode the compCode to set
	 */
	public void setCompCode(BigDecimal compCode) {
		this.compCode = compCode;
	}

	/**
	 * @return the compBriefName
	 */
	public String getCompBriefName() {
		return compBriefName;
	}

	/**
	 * @param compBriefName the compBriefName to set
	 */
	public void setCompBriefName(String compBriefName) {
		this.compBriefName = compBriefName;
	}

	/**
	 * @return the trxBranchCode
	 */
	public BigDecimal getTrxBranchCode() {
		return trxBranchCode;
	}

	/**
	 * @param trxBranchCode the trxBranchCode to set
	 */
	public void setTrxBranchCode(BigDecimal trxBranchCode) {
		this.trxBranchCode = trxBranchCode;
	}

	/**
	 * @return the tellerUserId
	 */
	public String getTellerUserId() {
		return tellerUserId;
	}

	/**
	 * @param tellerUserId the tellerUserId to set
	 */
	public void setTellerUserId(String tellerUserId) {
		this.tellerUserId = tellerUserId;
	}

	/**
	 * @return the tellerId
	 */
	public BigDecimal getTellerId() {
		return tellerId;
	}

	/**
	 * @param tellerId the tellerId to set
	 */
	public void setTellerId(BigDecimal tellerId) {
		this.tellerId = tellerId;
	}

	/**
	 * @return the trxType
	 */
	public BigDecimal getTrxType() {
		return trxType;
	}

	/**
	 * @param trxType the trxType to set
	 */
	public void setTrxType(BigDecimal trxType) {
		this.trxType = trxType;
	}

	/**
	 * @return the chargeTrxType
	 */
	public BigDecimal getChargeTrxType() {
		return chargeTrxType;
	}

	/**
	 * @param chargeTrxType the chargeTrxType to set
	 */
	public void setChargeTrxType(BigDecimal chargeTrxType) {
		this.chargeTrxType = chargeTrxType;
	}

	/**
	 * @return the merchantCode
	 */
	public BigDecimal getMerchantCode() {
		return merchantCode;
	}

	/**
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(BigDecimal merchantCode) {
		this.merchantCode = merchantCode;
	}
	

	/**
	 * @return the merchAdditionalRef
	 */
	public String getMerchAdditionalRef() {
		return merchAdditionalRef;
	}

	/**
	 * @param merchAdditionalRef the merchAdditionalRef to set
	 */
	public void setMerchAdditionalRef(String merchAdditionalRef) {
		this.merchAdditionalRef = merchAdditionalRef;
	}

	/**
	 * @return the merchIbanAccNo
	 */
	public String getMerchIbanAccNo() {
		return merchIbanAccNo;
	}

	/**
	 * @param merchIbanAccNo the merchIbanAccNo to set
	 */
	public void setMerchIbanAccNo(String merchIbanAccNo) {
		this.merchIbanAccNo = merchIbanAccNo;
	}

	/**
	 * @return the terminalCode
	 */
	public String getTerminalCode() {
		return terminalCode;
	}

	/**
	 * @param terminalCode the terminalCode to set
	 */
	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	/**
	 * @return the terminalDesc
	 */
	public String getTerminalDesc() {
		return terminalDesc;
	}

	/**
	 * @param terminalDesc the terminalDesc to set
	 */
	public void setTerminalDesc(String terminalDesc) {
		this.terminalDesc = terminalDesc;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the trxNb
	 */
	public BigDecimal getTrxNb() {
		return trxNb;
	}

	/**
	 * @param trxNb the trxNb to set
	 */
	public void setTrxNb(BigDecimal trxNb) {
		this.trxNb = trxNb;
	}

	/**
	 * @return the factory
	 */
	public MessageFactory getFactory() {
		return factory;
	}

	/**
	 * @param factory the factory to set
	 */
	public void setFactory(MessageFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * @todo temporary until we patch the iso8583 jar
	 */
	public int getEtx() {
		return -1;
	}
}
