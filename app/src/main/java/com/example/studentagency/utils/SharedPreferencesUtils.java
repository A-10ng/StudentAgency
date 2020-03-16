package com.example.studentagency.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/15
 * desc: 该文件保存四个值：isFirstInstall,cookie,userId,phoneNum
 */
public class SharedPreferencesUtils {
    private static final String fileName = "StudentAgency";
    private static final String TAG = "SharedPreferencesUtils";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context) {
        preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean getBoolean(String key,boolean defValue){
        return preferences.getBoolean(key,defValue);
    }

    public String getString(String key,String defValue){
        return preferences.getString(key,defValue);
    }

    public void putBoolean(String key,boolean value){
        boolean result = editor.putBoolean(key,value).commit();
        Log.i(TAG, "putBoolean: result>>>>>"+result);
    }

    public void putString(String key,String value){
        boolean result = editor.putString(key,value).commit();
        Log.i(TAG, "putString: result>>>>>"+result);
    }

    public int getInt(String key,int defValue){
        return preferences.getInt(key,defValue);
    }

    public void putInt(String key,int value){
        boolean result = editor.putInt(key,value).commit();
        Log.i(TAG, "putInt: result>>>>>"+result);
    }
}
