package com.tvtoday.gamelibrary.core.controller.notification

import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tvtoday.gamelibrary.core.controller.notification.SampleNotificationFactory.Companion.onGenerateNotification


class MyFirebaseMessagingService : FirebaseMessagingService() {

   override fun onNewToken(token: String) {
        Log.e("NEW_TOKEN", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("batman", message.toString() + "")

        message.data.apply {
            try {
                if (size > 0) {
                    val extras = Bundle()
                    for ((key, value) in this) {
                        extras.putString(key, value)
                    }

                    val pushMessage = PushMessage()
                    pushMessage.setHeader(extras.getString("nt"))
                    pushMessage.setMessage(extras.getString("nm"))
                    pushMessage.setBigPictureUrl(extras.getString("wzrk_bp"))
                    pushMessage.setLargeIconUrl(extras.getString("ico"))
                    pushMessage.setBundle(extras)

                    onGenerateNotification(pushMessage)
                   /* val info = CleverTapAPI.getNotificationInfo(extras)
                    if (info.fromCleverTap) {
                        CleverTapAPI.createNotification(applicationContext, extras)
                    } else {
                        // not from CleverTap handle yourself or pass to another provider
                    }*/
                }
            } catch (t: Throwable) {
                Log.d("MYFCMLIST", "Error parsing FCM message", t)
            }
        }

    }
}