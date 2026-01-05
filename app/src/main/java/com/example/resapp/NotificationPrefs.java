package com.example.resapp;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPrefs {

    private static final String PREFS = "reservation_noti_prefs";
    private static final String KEY_CREATE = "noti_create";
    private static final String KEY_EDIT = "noti_edit";
    private static final String KEY_DELETE = "noti_delete";

    public static boolean isCreateEnabled(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_CREATE, true);
    }

    public static boolean isEditEnabled(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_EDIT, true);
    }

    public static boolean isDeleteEnabled(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_DELETE, true);
    }

    public static void setCreateEnabled(Context c, boolean enabled) {
        prefs(c).edit().putBoolean(KEY_CREATE, enabled).apply();
    }

    public static void setEditEnabled(Context c, boolean enabled) {
        prefs(c).edit().putBoolean(KEY_EDIT, enabled).apply();
    }

    public static void setDeleteEnabled(Context c, boolean enabled) {
        prefs(c).edit().putBoolean(KEY_DELETE, enabled).apply();
    }

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
