package com.qts.gopik_loan.Activity;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qts.gopik_loan.Supply_Chain.PO_Product;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
    public static void saveArrayListInSharedPref(String key, ArrayList<PO_Product> mPoModelArrayList, Context context){
        editor=getPreferences(context).edit();
        Gson gson = new Gson();
        String json = gson.toJson(mPoModelArrayList);
        editor.putString(key, json);
        editor.apply();
    }

    public static ArrayList<PO_Product> getArrayListFromSharedPref(String key, Context context){
        editor=getPreferences(context).edit();
        Gson gson = new Gson();
        ArrayList<PO_Product> mPoModelArrayList;
        String json = getPreferences(context).getString(key, null);
        Type type = new TypeToken<ArrayList<PO_Product>>() {}.getType();
        mPoModelArrayList = gson.fromJson(json, type);
        if (mPoModelArrayList == null) {
            mPoModelArrayList = new ArrayList<>();
        }
        return mPoModelArrayList;
    }

}

