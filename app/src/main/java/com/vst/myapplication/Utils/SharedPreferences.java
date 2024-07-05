package com.vst.myapplication.Utils;

import android.content.Context;

public class SharedPreferences {
    private static final String PREF_NAME = "my_app_preferences";
    private static final String KEY_USERNAME = "userId";
    private static final String KEY_STORAGE = "KEY_STORAGE";
    private static android.content.SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static void saveUsername(Context context, String username) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }
    public static String getUsername(Context context) {
        return getSharedPreferences(context).getString(KEY_USERNAME, "");
    }
    public static void saveStorage(Context context, String STORAGE) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_STORAGE, STORAGE);
        editor.apply();
    }
    public static String getStorage(Context context) {
        return getSharedPreferences(context).getString(KEY_STORAGE, "");
    }
}
