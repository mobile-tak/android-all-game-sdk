package com.tvtoday.gamelibrary.shabdjaalgamesdk.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ShabdjalLanguagePreference {

    private static ShabdjalLanguagePreference instance;
    private static SharedPreferences pref;

    private ShabdjalLanguagePreference(Context context)
    {
        if (context != null)
        {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

    }

    public static ShabdjalLanguagePreference getInstance(Context context)
    {
        if (instance == null || pref == null)
        {
            instance = new ShabdjalLanguagePreference(context);
        }
        return instance;
    }


    public String getLanguage()
    {
        return pref.getString("appLanguageshabjal", "");
    }

    public void setLanguage(String b)
    {
        pref.edit().putString("appLanguageshabjal", b).apply();
    }
}

