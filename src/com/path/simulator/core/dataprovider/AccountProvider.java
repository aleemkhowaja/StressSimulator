package com.path.simulator.core.dataprovider;

import java.util.ArrayList;
import java.util.Iterator;

import com.path.simulator.core.vo.Account;

public class AccountProvider {
	
	/**
	 * Hold the data
	 */
	private ArrayList<Account> data;
	
	/**
	 * Iterator
	 */
	private Iterator<Account> iterator;
	
	public AccountProvider( ArrayList<Account> data){
		this.data = data;
		iterator = this.data.iterator();
	}
	
	/**
	 * @return
	 */
	public Account getOne(){
		
		if(iterator.hasNext())
			iterator = data.iterator();
		
		Account account = iterator.next();
		return account;
	}
	
}
