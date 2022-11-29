package com.tvtoday.crosswordhindi.views.controller.MainCommonPref

import android.content.Context
import com.tvtoday.crosswordhindi.views.controller.constants.AppConstantsMain

object MainCommonPref {
    private const val PREFERENCE = "MainAppPref"

    object Key {
        const val   MAIN_APP_LANGUAGE = "main_app_language"
        const val IS_FIRST_INSTALLED = "is_first_installed"

    }


    fun setBooleanPrefs(context: Context, prefKey: String, value: Boolean) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putBoolean(prefKey, value).apply()
    }


    fun getBooleanPrefs(context: Context, prefKey: String): Boolean {

        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getBoolean(prefKey, false)
    }


    fun getBoolean(context: Context, key: String?, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getBoolean(key, defaultValue)
    }


    fun getBooleanPrefs(context: Context, prefKey: String, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getBoolean(prefKey, defaultValue)
    }

    fun setStringPrefs(context: Context, prefKey: String, Value: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putString(prefKey, Value).apply()
    }


    fun getStringPrefs(context: Context, prefKey: String): String? {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, null)
    }

    fun getLangaugeStringPrefs(context: Context, prefKey: String): String? {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, AppConstantsMain.ENGLISH)
    }



    fun getStringPrefs(context: Context, prefKey: String, defaultValue: String): String {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, defaultValue)
            .toString()
    }

    fun setIntPrefs(context: Context, prefKey: String, value: Int) {
        return context.getSharedPreferences(PREFERENCE, 0).edit().putInt(prefKey, value)
            .apply()
    }

    fun getIntPrefs(context: Context, prefKey: String): Int {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getInt(prefKey, 0)
    }

    fun getIntPrefss(context: Context, prefKey: String, defaultValue: Int): Int {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getInt(prefKey, defaultValue)
    }

    fun setLongPrefs(context: Context, prefKey: String, value: Long) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putLong(prefKey, value).apply()
    }

    fun getLongPrefs(context: Context, prefKey: String): Long {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getLong(prefKey, 0)
    }

    fun getLongPrefs(context: Context, prefKey: String, defaultValue: Long): Long {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getLong(prefKey, defaultValue)
    }

    fun setFloatPrefs(context: Context, prefKey: String, value: Float) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putFloat(prefKey, value).apply()
    }

    fun getFloatPrefs(context: Context, prefKey: String): Float {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getFloat(prefKey, 0f)
    }

    fun getFloatPrefs(context: Context, prefKey: String, defaultValue: Long): Float {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getFloat(prefKey, defaultValue.toFloat())
    }

    /**
     * Clear all data in SharedPreference
     *
     * @param context
     */
    fun clearWholePreference(context: Context): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit().clear()
            .commit()
    }

    /**
     * Clear single key value
     *
     * @param prefKey
     * @param context
     */
    fun remove(context: Context, prefKey: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .remove(prefKey).apply()
    }


    fun setStringMusicPrefs(context: Context, prefKey: String, Value: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putString(prefKey, Value).apply()
    }

    fun getStringMusicPrefs(context: Context, prefKey: String): String? {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, null)
    }


}