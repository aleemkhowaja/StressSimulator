package com.path.simulator.core.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.path.simulator.core.vo.Account;
import com.path.simulator.core.vo.UUIDCo;

public class Test {

	private static HashMap<String,Object> providedObject = new HashMap<>();
	private static  AccountProvider accountProvider;
	private static  UniqueIdProvider uniqueIdProvider;
	public static void main(String[] args) {
		
		prepareProviders();
		StringBuilder sb = new StringBuilder();
		for(int i =0 ; i < 100 ; i++){
			sb.append("hello my is {{account[0].additionalRef}} {{account[0].additionalRef}} my unique id is   {{uuid[0].first[12]}} {{uuid[0].last[12]}}"
					+ " {{uuid[1].first[12]}} {{uuid[1].last[12]}}");
		}
		//String s = "hello my is {{account[0].additionalRef}} {{account[0].additionalRef}} my unique id is {{uiid[0].last[12]}}";
		String s = sb.toString();
		Pattern tagPattern = Pattern.compile("\\{\\{[a-zA-Z0-9-\\[-\\]-\\.]*\\}\\}");
		Matcher m = tagPattern.matcher(sb.toString());
		
		LinkedHashSet<String> placeHolders = new LinkedHashSet();
		while(m.find()){
			String placeHolder = m.group(0);
			placeHolder = placeHolder.replace("{{", "").replace("}}", "");
			//String value = getPlaceHolderValue(placeHolder);
			placeHolders.add(placeHolder);
			//System.out.println(placeHolder);
		}
		
		for( String x : placeHolders){
			String value = getPlaceHolderValue(x);
			s = s.replace(x, value);
		}
		
		System.out.println(s);
	}

	private static String getPlaceHolderValue(String placeHolder) {
		
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
	
	private static String returnUUIDInfo(String method, String providerIndex) {
		
		String key = "uuid"+providerIndex;
		
		if(!providedObject.containsKey(key)){
			providedObject.put(key, uniqueIdProvider.getOne());
		}
		
		Pattern tagPattern = Pattern.compile("([a-z]*)?\\[([0-9]*)\\]?");
		Matcher m = tagPattern.matcher(method);
		m.find();
		String methodName = m.group(1);
		String digitCount = m.group(2);
		
		return ((UUIDCo)providedObject.get(key)).returnValue(methodName,digitCount);
	}

	private static String returnAccountInfo(String method, String providerIndex) {
		
		String key = "account"+providerIndex;
		
		if(!providedObject.containsKey(key)){
			providedObject.put(key, accountProvider.getOne());
		}
		
		return ((Account)providedObject.get(key)).getAdditionalRef();
	}
	
	
	private static void prepareProviders() {
		
		ArrayList<Account> data = new ArrayList<>();
		Account a = new Account();
		a.setAdditionalRef("1234567890");
		data.add(a);
		
		Account b = new Account();
		b.setAdditionalRef("2222222222222");
		data.add(b);
		
		Account c = new Account();
		c.setAdditionalRef("333333333333");
		data.add(c);
		
		accountProvider = new AccountProvider(data);
		uniqueIdProvider = new UniqueIdProvider();
	}

}
