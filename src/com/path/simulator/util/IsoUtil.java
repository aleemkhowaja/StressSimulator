package com.path.simulator.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import io.netty.util.CharsetUtil;

/**
 * @author MohammadAliMezzawi
 *
 */
public class IsoUtil {

    public static ByteBuffer writeStringToBuffer(String payload,int lengthBytes, 
    		boolean includeLengthFieldLen, boolean useAscii) {
    	
    	// validate length bytes
    	if (lengthBytes > 4)
    		throw new IllegalArgumentException("The length header can have at most 4 bytes");

    	// @testing
    	//setIsoHeader("ISOHEADER45678");
    	
    	
    	byte[] data = payload.getBytes();
    	//ByteBuffer buf = ByteBuffer.allocate(lengthBytes + data.length + (getEtx() > -1 ? 1 : 0));
    	ByteBuffer buf = ByteBuffer.allocate(lengthBytes + data.length + 0);
    	//
    	if (lengthBytes > 0) {
    		
    		// get length
    		int l = data.length;
    				
    		// include Length field length to length
    	    l = includeLengthFieldLen? l + lengthBytes: l;
    		
//    		if (getEtx() > -1)
//    			l++;
    		
    		if(useAscii) {
    			String asciiLength = convertLenToAscii(l,lengthBytes);
    			buf.put(asciiLength.getBytes(CharsetUtil.ISO_8859_1));
    			System.out.println(convertLenFromAscii(asciiLength));
    			
    		}else {
    			
    			if (lengthBytes == 4){
                    buf.put((byte)((l & 0xff000000) >> 24));
                    System.out.println((byte)((l & 0xff000000) >> 24));
    			}
    			
        		if (lengthBytes > 2)
                    buf.put((byte)((l & 0xff0000) >> 16));
        		
        		if (lengthBytes > 1)
                    buf.put((byte)((l & 0xff00) >> 8));
        		
                buf.put((byte)(l & 0xff));
    		}
    	}
    	buf.put(data);
    	//ETX
//    	if (getEtx() > -1) {
//    		buf.put((byte)getEtx());
//    	}
    	buf.flip();
    	
    	byte[] bytes = buf.array();
    	String v = new String( bytes, StandardCharsets.UTF_8 );
    	System.out.println(v);
    	return buf;
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
