package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLocaleHelper

open class VargPaheliBaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VargPaheliLanguagePreference.getInstance(this@VargPaheliBaseActivity)
        VargPaheliLocaleHelper.setLocale(this, VargPaheliLanguagePreference.getInstance(this@VargPaheliBaseActivity).getLanguage())

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(VargPaheliLocaleHelper.onAttach(base))
    }
}