package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.analytics.FirebaseAnalytics


class CleverTapEventShabdjal(context: Context){

    private var clevertapDefaultInstance: CleverTapAPI? = null
    private var firebaseAnalytics: FirebaseAnalytics? = null

    private val cleverTapEvent: CleverTapEventShabdjal? = null

    init {
        initializedCleverTap(context)
        initializedFirebase(context)
    }

    fun initializedCleverTap(context: Context) {
        if (clevertapDefaultInstance == null) {
            clevertapDefaultInstance =
                CleverTapAPI.getDefaultInstance(context.applicationContext)
        }
    }



    fun initializedFirebase(context: Context) {
        if (firebaseAnalytics == null) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context.applicationContext)
        }
    }



    fun createOnlyEvent(event_name: String?) {
        if (clevertapDefaultInstance != null) {
            val prodViewedAction = HashMap<String, Any>()
            prodViewedAction[CleverTapShabdjalConstants.DEVICE_TYPE] =
                CleverTapShabdjalConstants.ANDROID
            clevertapDefaultInstance!!.pushEvent(event_name, prodViewedAction)
            Log.d("CLEVERTAP_EVENTS", event_name.toString())
        }

        if (firebaseAnalytics != null) {
            val bundle = Bundle()
            bundle.putString(
                CleverTapShabdjalConstants.DEVICE_TYPE,
                CleverTapShabdjalConstants.ANDROID
            )
            firebaseAnalytics!!.logEvent(event_name!!, bundle)
        }
    }
}