package com.path.simulator.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;


/**
 * Hold all String manipulation methods.
 * @author MohammadAliMezzawi
 *
 */
public class StringUtil {

	/**
	 * Check if string isn't empty
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * Check if string is empty
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Replaces the null or empty string with given Value
	 */
	public static String nullEmptyToValue(Object obj, Object toValue) {
		if (obj == null) {
			if (toValue == null) {
				return "";
			} else {
				return toValue.toString();
			}
		} else {
			if ("".equals(obj.toString())) {
				if (toValue == null) {
					return "";
				} else {
					return toValue.toString();
				}
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * returns json object from json string
	 * 
	 * @param objClass
	 * @param jsonStr
	 * @return
	 */
	public static Object returnJsonObjectFromStr(Class<?> objClass, String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonStr, objClass);
		} catch (Exception e) {
			//LoggerUtil.error("Failed to returnJsonObjectFromStr");
		}
		return null;
	}
	
	
	/**
	 * returns json object from json string
	 * 
	 * @param objClass
	 * @param jsonStr
	 * @return
	 * @throws JsonProcessingException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static String convertObjToJsonStr(Object object ) throws JsonProcessingException  {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
	/**
	 * Convert Xml to HashMap
	 * @param xml
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static HashMap<String,Object> convertXmlToHashMap(String xml) throws JsonParseException, JsonMappingException, IOException{
	    
		JSONObject jObject = XML.toJSONObject(xml);
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    
	    HashMap<String,Object> json = mapper.readValue(
	    		jObject.toString(), HashMap.class);
		return json;
	}
}
