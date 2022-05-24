package com.qts.gopik_loan.Activity;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPreferences mPreferences;
    private static String DBNAME = "yoyorydes";
    private static SharedPreferences.Editor editor;

    public static SharedPreferences getPreferences(Context context){

        if(mPreferences==null)
            mPreferences=context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        return mPreferences;
    }

    public static void saveStringInSharedPref(String key, String value, Context context){
        editor=getPreferences(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getStringFromSharedPref(String key, Context context){
        return getPreferences(context).getString(key,"NA");
    }

    public static void saveBooleanInSharedPref(String key, boolean value, Context context){
        editor=getPreferences(context).edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static boolean getBooleanFromSharedPref(String key, Context context){
        return getPreferences(context).getBoolean(key,false);
    }


}

