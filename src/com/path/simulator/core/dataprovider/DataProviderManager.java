package com.path.simulator.core.dataprovider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import com.path.simulator.core.vo.Account;
import com.path.simulator.util.StringUtil;

public class DataProviderManager {
	
	private static Object monitor = new Object();
	private static DataProviderManager instance;
	
	private AccountProvider accountProvider;
	private UniqueIdProvider uniqueIdProvider;
	
	
	/**
	 * @return
	 */
	public static DataProviderManager getInstance() {
		
		if (instance == null) {
			synchronized (monitor) {
				if (instance == null) 
					instance = new DataProviderManager();
			}
		}
		
		return instance;
	}
	
	
	/**
	 * Regenerate Data provider
	 */
	public void regenerate( String xmlPath ) {
		
		accountProvider = new AccountProvider(returnAccountList(xmlPath));
		uniqueIdProvider = new UniqueIdProvider();
	}
    
	
	public ArrayList<Account> returnAccountList(String xmlPath) {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try{
			/**
			 * red xml file
			 */
			xmlPath = "./messageProvider/"+xmlPath+"/accounts.xml";
			String xml = Files.lines(Paths.get(xmlPath)).collect(Collectors.joining("\n"));

			/**
			 * convert XML to Hashmap
			 */
			HashMap<String, Object> map = StringUtil.convertXmlToHashMap(xml);

			/**
			 * get additional Reference Values from hashmap and add in arrayList
			 */
			ArrayList<String> additionalRefList = new ArrayList<String>();
			for (Object ob : map.values()) {
				additionalRefList.addAll((ArrayList<String>) ob);
			}
			
			for (int i = 0; i < additionalRefList.size(); i++) {
				Account account = new Account();
				account.setAdditionalRef(String.valueOf(additionalRefList.get(i)));
				accounts.add(account);
			}
			
		}catch(Exception e ){
			System.out.println("Parse account list");
		}
		
		return accounts;
	}
    
	/**
	 * @return the accountProvider
	 */
	public AccountProvider getAccountProvider() {
		return accountProvider;
	}

	/**
	 * @param accountProvider the accountProvider to set
	 */
	public void setAccountProvider(AccountProvider accountProvider) {
		this.accountProvider = accountProvider;
	}

	/**
	 * @return the uniqueIdProvider
	 */
	public UniqueIdProvider getUniqueIdProvider() {
		return uniqueIdProvider;
	}

	/**
	 * @param uniqueIdProvider the uniqueIdProvider to set
	 */
	public void setUniqueIdProvider(UniqueIdProvider uniqueIdProvider) {
		this.uniqueIdProvider = uniqueIdProvider;
	}

}
