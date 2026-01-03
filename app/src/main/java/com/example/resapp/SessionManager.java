package com.example.resapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF = "session";
    private static final String KEY_EMAIL = "email";

    public static void login(Context c, String email) {
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_EMAIL, email)
                .apply();
    }

    public static String getUserEmail(Context c) {
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .getString(KEY_EMAIL, null);
    }

    public static void logout(Context c) {
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().clear().apply();
    }
}
