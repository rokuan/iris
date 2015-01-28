package com.rokuan.iris.implementation;

import java.util.HashMap;

import com.rokuan.iris.interfaces.IIrisDataLoader;

public class HashMapDataLoader implements IIrisDataLoader {
	private HashMap<String, Integer> intVariables = new HashMap<String, Integer>();
	private HashMap<String, String> stringVariables = new HashMap<String, String>();
	private int money = 10000;

	@Override
	public void loadData(Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData(Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPlayerIntVariable(String tag) {
		try{
			return intVariables.get(tag);
		}catch(Exception e){
			return 0;			
		}
	}

	@Override
	public void setPlayerIntVariable(String tag, int value) {
		intVariables.put(tag, value);
	}

	@Override
	public String getPlayerStringVariable(String tag) {
		try{
			return stringVariables.get(tag);
		}catch(Exception e){
			return "";
		}
	}

	@Override
	public void setPlayerStringVariable(String tag, String value) {
		stringVariables.put(tag, value);
	}

	@Override
	public int getMoney() {
		return this.money;
	}

	@Override
	public boolean addMoney(int qty) {
		if(qty > 0){
			money += qty;
			return true;
		}
		
		return false;
	}

	@Override
	public boolean removeMoney(int qty) {
		if(qty > 0 && money >= qty){
			money -= qty;
			return true;
		}
		
		return false;
	}

	@Override
	public int countItem(int itemId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addItem(int itemId, int qty) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean deleteItem(int itemId, int qty) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getHour() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMonth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYear() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getISO() {
		// TODO Auto-generated method stub
		return null;
	}

}
