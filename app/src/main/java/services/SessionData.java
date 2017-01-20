package services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.infomanav.astaguru.Country;

import java.util.ArrayList;

public class SessionData
{
	private Editor editor;
	private SharedPreferences pref;

	public SessionData(Context context)
	{
		pref = context.getSharedPreferences(context.getPackageName(), 0);
		editor = pref.edit();
	}

	public void setString(String key, String value)
	{
		editor.putString(key, value);
		editor.commit();
	}

	public void setInt(String key, int value)
	{
		editor.putInt(key, value);
		editor.commit();
	}

	public void setBoolean(String key, boolean value)
	{
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void setFloat(String key, float value)
	{
		editor.putFloat(key, value);
		editor.commit();
	}

	public void setDouble(String key, double value)
	{
		editor.putLong(key, Double.doubleToLongBits(value));
		editor.commit();
	}

	public void setLong(String key, long value)
	{
		editor.putLong(key, Double.doubleToLongBits(value));
		editor.commit();
	}
	public void setJson(String key, String srrJson)
	{
		editor.putString(key,srrJson);
		editor.commit();
	}

	public void removeKey(String key)
	{
		editor.remove(key);
		editor.commit();
	}

	public void clearSession()
	{
		editor.clear();
		editor.commit();
	}

	public String getString(String key)
	{
		return pref.getString(key, null);
	}
	public String getJson(String key)
	{
		return pref.getString(key, null);
	}

	public int getInt(String key)
	{
		return pref.getInt(key, 0);
	}

	public boolean getBoolean(String key)
	{
		return pref.getBoolean(key, false);
	}

	public float getFloat(String key)
	{
		return pref.getFloat(key, 0.0000f);
	}

	public double getDouble(String key)
	{
		return Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(0.0000)));
	}

	public double getLong(String key)
	{
		return pref.getLong(key, 0);
	}

	/**
	 * This is used to add Object as JSON String into shared preferences
	 * 
	 * @param key
	 * @param value
	 */
	public void setObjectAsString(String key, String value)
	{
		Log.d(key, value);
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * This is used to get Object as JSON String from preferences
	 * 
	 * @param key
	 * @return
	 */
	public String getObjectAsString(String key)
	{
		return pref.getString(key, "Empty");
	}
}
