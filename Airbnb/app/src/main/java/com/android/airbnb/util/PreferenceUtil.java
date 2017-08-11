package com.android.airbnb.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MDY on 2017-08-09.
 */

public class PreferenceUtil {
    private static SharedPreferences sharedPreferences = null;

    private static void setSharedPreferences(Context context){
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE); // MODE_PRIVATE - 다른 앱이 접근하지 못하게
    }

    private static void setString(Context context, String key, String value){
        setSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(Context context, String key){
        setSharedPreferences(context);
        return sharedPreferences.getString(key, "해당 데이터 없음");
    }

    // token
    public static void setToken(Context context, String token){
        setString(context,"userToken", token);
    }
    public static String getToken(Context context) {
        return getString(context, "userToken");
    }

    // pk
    public static void setPrimaryKey(Context context, String primaryKey){
        setString(context,"userPrimaryKey", primaryKey);
    }
    public static String getPrimaryKey(Context context) {
        return getString(context, "userPrimaryKey");
    }

    // email
    public static void setEmail(Context context, String email){
        setString(context,"userEmail", email);
    }
    public static String getEmail(Context context) {
        return getString(context, "userEmail");
    }

}
