package com.example.hp_pc.serverrajeev;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Hp-Pc on 10/11/2017.
 */
public class QueryPreferences {

    private  static final String PREF_USER_NAME = "current_login_user_name";

    public static String getStoredUserName(Context context) {
        return  PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USER_NAME, null);
    }

    public static void setStoredUserName(Context context, String userName) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USER_NAME, userName)
                .apply();
    }

    public static void removeStoredUserName(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(PREF_USER_NAME)
                .apply();
    }
}
