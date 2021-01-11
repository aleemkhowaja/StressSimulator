package com.path.simulator.core.vo;

import java.util.UUID;

/**
 * @author MohammadAliMezzawi
 *
 */
public class UUIDCo {

	private String value;
	
	/**
	 * 
	 */
	public UUIDCo(){
		String uuid = UUID.randomUUID().toString();
		value = uuid.replace("-","").replaceAll("[a-z]", "1");
		
		System.out.println("Value => " + value);
	}

	/**
	 * @param methodName
	 * @param digitCount
	 * @return
	 */
	public String returnValue(String methodName, String digitCount) {
		
		String returnValue = "";
		
		switch(methodName){
			case "last":
				returnValue = value.substring(32 - Integer.valueOf(digitCount), 32);
				break;
			case "first":
				returnValue = value.substring(0, Integer.valueOf(digitCount));
				break;
		}
		
		return returnValue;
	}
}
