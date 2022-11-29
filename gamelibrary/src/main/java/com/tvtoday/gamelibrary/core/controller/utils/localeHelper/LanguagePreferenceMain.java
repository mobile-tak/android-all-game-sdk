package com.tvtoday.gamelibrary.core.controller.utils.localeHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class LanguagePreferenceMain {

    private static LanguagePreferenceMain instance;
    private static SharedPreferences pref;

    private LanguagePreferenceMain(Context context)
    {
        if (context != null)
        {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }

    }

    public static LanguagePreferenceMain getInstance(Context context)
    {
        if (instance == null || pref == null)
        {
            instance = new LanguagePreferenceMain(context);
        }
        return instance;
    }


    public String getLanguage()
    {
        return pref.getString("appLanguageMain", "");
    }

    public void setLanguage(String b)
    {
        pref.edit().putString("appLanguageMain", b).apply();
    }
}

