package com.path.simulator.core.dataprovider;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.path.simulator.core.vo.Account;
import com.path.simulator.core.vo.UUIDCo;

/**
 * @author MohammadAliMezzawi
 *
 */
public class MessageGenerator {
	
	private HashMap<String,Object> providedObject = new HashMap<>();
	private DataProviderManager manager;
	
	
	/**
	 * Message generator
	 */
	public MessageGenerator( DataProviderManager manager ){
		this.manager = manager;
	}
	
	
	/**
	 * @param template
	 * @return
	 */
	public String generate(String template){
		
		Pattern tagPattern = Pattern.compile("\\{\\{[a-zA-Z0-9-\\[-\\]-\\.]*\\}\\}");
		Matcher m = tagPattern.matcher(template);
		
		LinkedHashSet<String> placeHolders = new LinkedHashSet();
		while(m.find()){
			String placeHolder = m.group(0);
			placeHolder = placeHolder.replace("{{", "").replace("}}", "");
			//String value = getPlaceHolderValue(placeHolder);
			placeHolders.add(placeHolder);
			//System.out.println(placeHolder);
		}
		
		for( String placeH : placeHolders){
			String value = getPlaceHolderValue(placeH);
			template = template.replace("{{"+placeH+"}}", value);
		}
		
		return template;
	}
	
	
	/**
	 * @param placeHolder
	 * @return
	 */
	private String getPlaceHolderValue(String placeHolder) {
		
		String[] parts = placeHolder.split("\\.");
		Pattern tagPattern = Pattern.compile("([a-z]*)?(\\[[0-9]*\\])?");
		Matcher m = tagPattern.matcher(parts[0]);
		m.find();
		String providerName = m.group(1);
		String providerIndex = m.group(2);
		String method = parts[1];
		
		switch( providerName ){
			case "account":
				return returnAccountInfo(method,providerIndex);
			case "uuid":
				return returnUUIDInfo(method,providerIndex);
		}
		
		return "";
	}
	
	/**
	 * @param method
	 * @param providerIndex
	 * @return
	 */
	private String returnUUIDInfo(String method, String providerIndex) {
		
		String key = "uuid"+providerIndex;
		
		if(!providedObject.containsKey(key)){
			providedObject.put(key, manager.getUniqueIdProvider().getOne());
		}
		
		Pattern tagPattern = Pattern.compile("([a-z]*)?\\[([0-9]*)\\]?");
		Matcher m = tagPattern.matcher(method);
		m.find();
		String methodName = m.group(1);
		String digitCount = m.group(2);
		
		return ((UUIDCo)providedObject.get(key)).returnValue(methodName,digitCount);
	}

	/**
	 * @param method
	 * @param providerIndex
	 * @return
	 */
	private String returnAccountInfo(String method, String providerIndex) {
		
		String key = "account"+providerIndex;
		
		if(!providedObject.containsKey(key)){
			providedObject.put(key, manager.getAccountProvider().getOne());
		}
		
		return ((Account)providedObject.get(key)).getAdditionalRef();
	}

}
