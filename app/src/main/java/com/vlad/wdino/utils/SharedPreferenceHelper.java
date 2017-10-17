package com.vlad.wdino.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by braincollaboration on 24/09/2017.
 */

public class SharedPreferenceHelper {
    private final static String PREF_FILE = "PREF";

    public static final String KEY_LOGIN_USER = "SavedLogin";

    /**
     * Set a string shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Set a integer shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void putInteger(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Set a Boolean shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Set a Object shared preference converted into Gson
     *
     * @param key - Key to set shared preference
     * @param o   - Value for the key
     */
    public static void putObject(Context context, String key, Object o) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(o);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * Get a string shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }

    /**
     * Get a integer shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static int getInteger(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }

    /**
     * Get a boolean shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }

    /**
     * Get a boolean shared preference
     *
     * @param key - Key to look up in shared preferences.
     * @param classType - Restored class type.
     */
    public static <GenericClass> GenericClass getBoolean(Context context, String key, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, 0);
        if (sharedPreferences.contains(key)) {
            final Gson gson = new Gson();
            String json = sharedPreferences.getString(key, "");
            return gson.fromJson(json, classType);
        }
        return null;
    }
}
