package com.vst.myapplication.Utils;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import java.util.HashMap;

public class MyApplicationNew extends MultiDexApplication{
    public static String MyLock = "Lock";
    public static Context mContext;
    public static HashMap<String, Object> hashMap;

    private static Object dataObject = null;
    public static boolean IsLargeData = true;

    public static boolean RoomDB = false;
    public static MyApplicationNew instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null)
            mContext = this;


    }


    public static void setData(Object data) {
        dataObject = data;
    }

    public static void setData(String key, Object data) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(key, data);
    }

    public static Object getData(String key) {
        if (hashMap != null && hashMap.containsKey(key))
            return hashMap.get(key);
        else
            return null;
    }

    public static Object getData() {
        return dataObject;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static MyApplicationNew getInstance() {
        return instance;
    }
}
