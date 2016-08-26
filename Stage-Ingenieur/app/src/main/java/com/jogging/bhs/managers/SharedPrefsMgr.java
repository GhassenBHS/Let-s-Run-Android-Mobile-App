package com.jogging.bhs.managers;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefsMgr {

    private static final String APP_SETTINGS = "UserData";



    private static final String SOME_STRING_VALUE = "UserData";



    public SharedPrefsMgr() {

    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public  String getStringValue(Context context,String value) {
        return getSharedPreferences(context).getString(value, null);
    }

    public  void setStringValue(Context context,String key,String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key , newValue);
        editor.commit();
    }
    public int getIntValue(Context context,String value) {
        return getSharedPreferences(context).getInt(value, 13);
    }

    public  void setIntValue(Context context,String key,int newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, newValue);
        editor.commit();
    }
    public  boolean getBoolValue(Context context,String value) {
        return getSharedPreferences(context).getBoolean(value, false);
    }

    public  void setBoolValue(Context context,String key,boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, newValue);
        editor.commit();
    }
    public  Long getLongValue(Context context,String value) {
        return getSharedPreferences(context).getLong(value, 0);
    }

    public  void setLongValue(Context context,String key,Long newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(key, newValue);
        editor.commit();
    }

    public boolean exists (String key,Context context)
    {
        final SharedPreferences prefs = getSharedPreferences(context) ;
        return prefs.contains(key) ;



    }

}
