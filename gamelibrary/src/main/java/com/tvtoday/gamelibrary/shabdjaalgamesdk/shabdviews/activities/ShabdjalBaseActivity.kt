package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLocaleHelper

open class ShabdjalBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ShabdjalLanguagePreference.getInstance(this@ShabdjalBaseActivity)

        ShabdjalLocaleHelper.setLocale(this, ShabdjalLanguagePreference.getInstance(this@ShabdjalBaseActivity).getLanguage())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(ShabdjalLocaleHelper.onAttach(base))
    }
}