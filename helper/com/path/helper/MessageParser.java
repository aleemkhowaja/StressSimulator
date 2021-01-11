package com.path.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.BitSet;

import com.path.atm.engine.iso8583.IsoConfigParser;
import com.path.atm.engine.iso8583.MessageFactory;
import com.path.atm.engine.util.AtmEngineConstants;
import com.path.atm.vo.engine.AtmInterfaceCO;
import com.path.bo.common.ConstantsCommon;

public class MessageParser {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		
		// [-2, -9, 111, 1, -88, -31, -22, 0]
//		hexDecode("FE");
//		
//		
//		char c1= (char)(Integer.parseInt("FE", 16));
//		char c2= (char)(Integer.parseInt("F7", 16));
//		char c3= (char)(Integer.parseInt("6F", 16));
//		char c4= (char)(Integer.parseInt("01", 16));
//		char c5= (char)(Integer.parseInt("A8", 16));
//		char c6= (char)(Integer.parseInt("E1", 16));
//		char c7= (char)(Integer.parseInt("EA", 16));
//		char c8= (char)(Integer.parseInt("00", 16));
//		
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append(c1).append(c2).append(c3).append(c4).append(c5).append(c6).append(c7).append(c8);
//		
//		sb.toString().getBytes(AtmEngineConstants.CHAR_ENCODING);
//		
//		
//		System.out.println(c1 + " || "+ c2  + " || " + c3  + " || " + c4 
//				+ " || " + c5 + " || " + c6 + " || " + c7 + " || " + c8 );
//		
//		
//		hexDecode("F7");
//		hexDecode("FEF76F01A8E1EA00");
//		hexDecode("FEF76F01A8E1EA00");
		//byte b = Byte.parseByte("01100110", 2);
		MessageFactory factory = createMessageFactory();
		byte[] message = returnMessageBytes();
		factory.parseMessage(message, "A4M09000".length());
	}

	private static byte[] returnMessageBytes() throws IOException {
		
		byte[] bitmap = hexDecode("F63866912CB0A00800000000044000A0");
		byte[] header = "A4M090000200".getBytes();
		byte[] payload = ("2011197101532141471171071"
				+ "0000000000000000000000000000011406033278080108033201146011736901000000"
				+ "0000000001100000000017336046540900546571=211252119399053002267389781100"
				+ "007909byblosbnkExport Development Bank       Omdurman                      000"
				+ "736AHFAD UNIVERSITY              003                           "
				+ "Sudsn                         00120200114BSDN      EXPD                         "
				+ "00000000000000001 938938CBSKBSDN      1800001113002919000100000000000000000000000"
				+ "00000006100000000000000001                                            004TC=2").getBytes(AtmEngineConstants.CHAR_ENCODING);
		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		outputStream.write( header );
		outputStream.write( bitmap );
		outputStream.write( payload );
		return outputStream.toByteArray( );
	}

	private static MessageFactory createMessageFactory() {
		/**
		 * Prepare interface configuration
		 */
		AtmInterfaceCO interfaceCO = new AtmInterfaceCO();
		interfaceCO.setProtocolIdentification("");
		interfaceCO.setMsgLength(new BigDecimal(2));
		interfaceCO.setHeaderData("A4M09000");
		interfaceCO.setHeaderLength(new BigDecimal(8));
		interfaceCO.setBitmapType(String.valueOf(AtmEngineConstants.BITMAP_8));
		interfaceCO.setLengthType(String.valueOf(AtmEngineConstants.LENGTH_BASE256));
		
		interfaceCO.setIncludeLength(BigDecimal.ZERO);
		interfaceCO.setPosInHeader(ConstantsCommon.NO);
		interfaceCO.setSkipBitmap(ConstantsCommon.NO);
		interfaceCO.setIsoPresent( ConstantsCommon.NO);
		
		return  IsoConfigParser.createIsoFactory(interfaceCO);
	}
 
    

	public static byte[] hexDecode(String hex) {
		//A null string returns an empty array
		if (hex == null || hex.length() == 0) {
			return new byte[0];
		} else if (hex.length() < 3) {
			char c= (char)(Integer.parseInt(hex, 16));
			System.out.println(c);
			System.out.println((byte)c);
			return new byte[]{ (byte)(Integer.parseInt(hex, 16) & 0xff) };
		}
		//Adjust accordingly for odd-length strings
		int count = hex.length();
		int nibble = 0;
		if (count % 2 != 0) {
			count++;
			nibble = 1;
		}
		byte[] buf = new byte[count / 2];
		char c = 0;
		int holder = 0;
		int pos = 0;
		for (int i = 0; i < buf.length; i++) {
		    for (int z = 0; z < 2 && pos<hex.length(); z++) {
		        c = hex.charAt(pos++);
		        if (c >= 'A' && c <= 'F') {
		            c -= 55;
		        } else if (c >= '0' && c <= '9') {
		            c -= 48;
		        } else if (c >= 'a' && c <= 'f') {
		            c -= 87;
		        }
		        if (nibble == 0) {
		            holder = c << 4;
		        } else {
		            holder |= c;
		            buf[i] = (byte)holder;
		        }
		        nibble = 1 - nibble;
		    }
		}
		return buf;
	}
}
