package com.tvtoday.crosswordhindi.views.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tvtoday.gamelibrary.core.controller.utils.localeHelper.LanguagePreferenceMain
import com.tvtoday.gamelibrary.core.controller.utils.localeHelper.LocaleHelperMain


open class BaseActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguagePreferenceMain.getInstance(this@BaseActivityMain)

        LocaleHelperMain.setLocale(this, LanguagePreferenceMain.getInstance(this@BaseActivityMain).getLanguage())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelperMain.onAttach(base))
    }
}