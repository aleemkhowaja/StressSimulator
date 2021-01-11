package com.path.atm.engine.iso8583;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.path.lib.common.util.StringUtil;

/**
 * This class serve as iso util
 * 
 * @author MohammadAliMezzawi
 *
 */
public class IsoUtil {
	
	/**
	 * Array of hexadecimal characters
	 */
	static final byte[] HEX = new byte[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
    
	/**
	 * Return list of available fields in an iso message based
	 * on the given bitmap
	 * @param bitmap
	 * @return
	 */
	public static List<BigDecimal> getAvailableFields(String bitmapStr){
		
		if(null == bitmapStr || bitmapStr.length() == 0 )
			throw new IllegalArgumentException("Bitmap string can't be null or empty");
		
		String bitmapBin = hexaToBinary(bitmapStr);
		
		// if secondary bitmap not set
		if(bitmapBin.startsWith("0"))
			bitmapBin= bitmapBin.substring(0, 64);
		
		List<BigDecimal> bitMapFields = new ArrayList<BigDecimal>();
		
		char[] bitmapBits = bitmapBin.toCharArray();
		
		for(int i = 0 ; i < bitmapBits.length ; i++ ) {
			
			if('1' == bitmapBits[i])
				bitMapFields.add(new BigDecimal(i+1));
		}
		
		return bitMapFields;
		
	}
    
    
	/**
	 * Convert hexa decimal number to binary
	 * @note : to refactor
	 * @param hexaDecimal
	 * @return
	 */
	public static String hexaToBinary(String hexaDecimal) 
	{
		StringBuilder binary = new StringBuilder();
		if(!StringUtil.isEmptyString(hexaDecimal)) 
		{
			String bin = "";
			char hexa[] = hexaDecimal.toCharArray();
			for (int i = 0; i < hexa.length; i++) 
			{
				int j = Integer.parseInt(hexa[i] + "", 16);
				bin = Integer.toBinaryString(j);
				while (bin.length() != 4)
				{
					bin = "0" + bin;
				}
				binary.append(bin);
			}
		}
		return binary.toString();
	}
	
	/**
	 * This method will convert a bit set ( bitmap )
	 * to hex string
	 * @param bs
	 * @return
	 */
	public static String convertBitmapToHex(BitSet bs) {

		int pos = 0;
		int lim = bs.size() / 4;

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < lim; i++) {
			int nibble = 0;
			if (bs.get(pos++))
				nibble |= 8;
			if (bs.get(pos++))
				nibble |= 4;
			if (bs.get(pos++))
				nibble |= 2;
			if (bs.get(pos++))
				nibble |= 1;
			sb.append(new String(HEX, nibble, 1));
		}

		return sb.toString();
	}
	
	
	/**
	 * Convert mti integer to it's hex representation
	 * in the DB which is hex without x 
	 * ex: 512 int will be converted to 0200 not 0x200
	 * @return
	 */
	public static String convertMtiToHex(int mtiCode) {
		return StringUtils.leftPad(
			Integer.toHexString(mtiCode), 4, "0");
	}
	
	
	/**
	 * Convert mti hex to int
	 * Ex : 0200 will be converted to 528 after adding 0x
	 * @return
	 */
	public static int convertMtiToInt(String mtiCode) {
		mtiCode = mtiCode.startsWith("0")?
			mtiCode.replaceFirst("^0+(?!$)", "0x"): "0x".concat(mtiCode);
		return Integer.decode(mtiCode);
	}
	
	
    /**
     * Creates a BitSet for the bitmap of a given message.
     **/
	public static BitSet createBitmapBitSet(AtmIsoMessage isoMsg) {
		BitSet bs = new BitSet(isoMsg.getForceSecondaryBitmap() ? 128 : 64);
		for (int i = 2; i < 129; i++) {
			if (isoMsg.hasField(i)) {
				bs.set(i - 1);
			}
		}
		if (isoMsg.getForceSecondaryBitmap()) {
			bs.set(0);
		} else if (bs.length() > 64) {
			// Extend to 128 if needed
			BitSet b2 = new BitSet(128);
			b2.or(bs);
			bs = b2;
			bs.set(0);
		}
		return bs;
	}

	
	/**
	 * Note : If input is greater than length range we are returning it
	 * 
	 * @param inputNum
	 * @param length
	 * @return
	 */
	public static String convertLenToAscii( int inputNum, int length) {
		String asciiBase256 = fromDeci(256,inputNum);
		return StringUtils.leftPad(asciiBase256, length , (char)0);
	}
	
	
	/**
	 * 
	 * @param inputNum
	 * @param length
	 * @return
	 */
	public static int convertLenFromAscii(String asciiNb) {
		return toDeci(asciiNb,256);
	}
    
    
	/**
	 * Function to convert a given decimal number 
	 * to a base 'base' 
	 * @param base
	 * @param inputNum
	 * @return
	 */
	public static String fromDeci(int base, int inputNum) {
		
		String s = "";
		
		/**
		 * Convert input number is given
		 * base by repeatedly dividing it
		 * by base and taking remainder
		 */
		while (inputNum > 0) {
			s += (char)(inputNum % base);
			inputNum /= base;
		}
		
		StringBuilder ix = new StringBuilder();

		// append a string into StringBuilder input1
		ix.append(s);

		// Reverse the result
		return new String(ix.reverse());
	}

	
	/**
	 * Mask Field based on the given mask
	 * 
	 * @param mask
	 * @param field
	 * @return
	 */
	public static String maskField(String field,String mask) {
		
		// build the mask and complete the missing bits by applying right padding
		mask = StringUtils.rightPad(StringUtil.nullEmptyToValue(mask, "")
			,field.length(), IsoDefinitionConstants.MASK_SHOW_BIT);
		
		// if mask is full 0
		if(mask.indexOf(IsoDefinitionConstants.MASK_SHOW_BIT) < 0)
			return field.replaceAll("(?s).", String.valueOf(IsoDefinitionConstants.MASK_CHAR));
		
		// if mask is full 1
		if(mask.indexOf(IsoDefinitionConstants.MASK_HIDE_BIT) < 0)
			return field;
		
		StringBuilder maskedField = new StringBuilder();
		char[] fieldChars = field.toCharArray();
		
		for (int i = 0; i < fieldChars.length; i++) {
			maskedField.append(mask.charAt(i) == IsoDefinitionConstants.MASK_SHOW_BIT?
				fieldChars[i]:IsoDefinitionConstants.MASK_CHAR);
		}
		
		return maskedField.toString();
	}
	

	
	
	/**
	 * Convert A string from given base to decimal
	 * @param str
	 * @param base
	 * @return
	 */
	public static int toDeci(String nbAsStr, int base) {
		
		int len = nbAsStr.length();
		int power = 1;
		int num = 0;

		/**
		 * Decimal equivalent is
		 * str[len-1]*1 + str[len-1] *
		 * base + str[len-1]*(base^2) + ...
		 */
		for (int i = len - 1; i >= 0; i--) {
			/**
			 * A digit in input number must be less than
			 * number's base
			 */
			if ((int)(nbAsStr.charAt(i)) >= base)
				//Invalid Number
				return -1;

			num += (int)(nbAsStr.charAt(i)) * power;
			power = power * base;
		}

		return num;
	}
}
