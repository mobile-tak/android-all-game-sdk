package com.tvtoday.gamelibrary.core

import android.app.Application
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI



class CrossWordHindiApplication : Application() {

    companion object{

        var application: CrossWordHindiApplication? = null

        fun getInstance(): CrossWordHindiApplication? {
            return application
        }
    }


    override fun onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate()
        application = applicationContext as CrossWordHindiApplication
        val clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
            applicationContext
        )
    }
}
