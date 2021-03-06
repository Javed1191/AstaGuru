package com.custom.baselibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SystemSession {
	
	public static final String DELETEALEARTBOX = "DELETE_ALERT_BOX";
	public static final String Swip = "SWIP";
	private Editor editor;
	
	private SharedPreferences preference;
	
	public SystemSession(Context context) 
	{
		preference = context.getSharedPreferences("ILIA_Session",Context.MODE_PRIVATE);
		editor = preference.edit();
	}

	public void addData(String key,String value)
	{
		editor.putString(key, value);
		editor.commit();
	}
	
	public void clearAll()
	{
		editor.clear();
	}
	
	/**
	 * This is used to get Drop pin value
	 * @return
	 */
	 public boolean getAleartDelete()
	 {
	 return preference.getBoolean(DELETEALEARTBOX, false);
	 }
	
	
	
	public String  getcheckingflagsession(String key) 
	{
		return  preference.getString(key, "");
	}
	
	public String getData(String key)
	{
		return preference.getString(key, "");
	}
	
	/**
	 * This is used to get Drop pin value
	 * @return
	 */
	 public boolean getSwip()
	 {
	 return preference.getBoolean(Swip, false);
	 }
	
	 
	 public void removeData(String key)
	{
		editor.remove(key);
		editor.commit();
	}

	 public void setAleartDelete(boolean value)
	 {
	 editor.putBoolean(DELETEALEARTBOX, value);
	 editor.commit();
	 }
	 
	 public void  setcheckingflagsession(String key,String value) 
	{
		editor.putString(key, value);
		editor.commit();
	}

	 public void setSwip(boolean value)
	 {
	 editor.putBoolean(Swip, value);
	 editor.commit();
	 }

	
}
