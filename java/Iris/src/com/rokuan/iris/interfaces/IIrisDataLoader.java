package com.rokuan.iris.interfaces;

public interface IIrisDataLoader {
	public void loadData(Object ... args);
	public void saveData(Object ... args);
	
	public int getPlayerIntVariable(String tag);
	public void setPlayerIntVariable(String tag, int value);

	public String getPlayerStringVariable(String tag);
	public void setPlayerStringVariable(String tag, String value);
	
	public int getMoney();
	public boolean addMoney(int qty);
	public boolean removeMoney(int qty);
	
	public int countItem(int itemId);
	public boolean addItem(int itemId, int qty);
	public boolean deleteItem(int itemId, int qty);
	
	public int getHour();
	public int getMinutes();
	public int getSeconds();
	
	/**
	 * 
	 * @return The current time in hh:mm:ss format (0h-23h)
	 */
	public String getTime();	

	
	public int getDay();
	public int getMonth();
	public int getYear();
	
	/**
	 * 
	 * @return The current date in YYYY-MM-DD format
	 */
	public String getDate();
	
	
	/**
	 * 
	 * @return The current data in ISO format (YYYY-MM-DDThh:mm:ss). For example, "2014-12-31T23h59h59" 
	 */
	public String getISO();
	
	/*public boolean getBooleanData(String varName);
	public int getIntData(String varName);
	public String getStringData(String varName);
	public Object getData(String varName);	
	
	public void setBooleanData(String varName);
	public void setIntData(String varName);
	public void setStringData(String varName);
	public void setData(String varName, Object varValue);*/
}
