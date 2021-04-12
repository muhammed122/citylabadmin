package com.muhammed.citylabadmin.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.muhammed.citylabadmin.data.model.login.UserData;

public class MyPreference {

    public static final String SHARED_USER_TOKEN = "token";
    public static final String SHARED_USER_NAME = "token";
    public static final String SHARED_USER_PHONE = "token";


    static private Context appContext;

    public static void init(Context context) {
        appContext = context;
    }

    private static SharedPreferences getSharedPreference() {
        return appContext.getSharedPreferences("shared_file", Context.MODE_PRIVATE);
    }

    public static void saveUser(UserData user) {
        getSharedPreference().edit().putString(SHARED_USER_TOKEN, user.getToken()).apply();
    }

    public static String getSharedString(String key) {

        return getSharedPreference().getString(key, "");
    }


}
