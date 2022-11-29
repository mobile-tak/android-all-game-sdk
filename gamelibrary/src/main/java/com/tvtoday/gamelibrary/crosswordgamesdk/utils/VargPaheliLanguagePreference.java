package com.tvtoday.gamelibrary.crosswordgamesdk.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class VargPaheliLanguagePreference {

    private static VargPaheliLanguagePreference instance;
    private static SharedPreferences pref;

    private VargPaheliLanguagePreference(Context context)
    {
        if (context != null)
        {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

    }

    public static VargPaheliLanguagePreference getInstance(Context context)
    {
        if (instance == null || pref == null)
        {
            instance = new VargPaheliLanguagePreference(context);
        }
        return instance;
    }


    public String getLanguage()
    {
        return pref.getString("appLanguagevargpaheli", "");
    }

    public void setLanguage(String b)
    {
        pref.edit().putString("appLanguagevargpaheli", b).apply();
    }
}

