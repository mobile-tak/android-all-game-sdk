package com.tvtoday.gamelibrary.core.controller.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.core.CrossWordHindiApplication
import com.tvtoday.gamelibrary.core.controller.notification.Constants.Companion.NOTIFICATION_CHANNEL_ID
import com.tvtoday.gamelibrary.core.controller.notification.Constants.Companion.NOTIFICATION_CHANNEL_NAME

class SampleNotificationFactory {

    companion object{

        private val notify_id = 1

        @SuppressLint("NotificationTrampoline")
        fun onGenerateNotification(pushMessage: PushMessage) {
// Toast.makeText(NewsTakApplication.getInstance(), "This is notification", Toast.LENGTH_LONG).show();
            Log.d("batman-call", "Notification Received")

            /*   Intent intent = null;
            intent = new Intent(GameApplication.getInstance(), NotificationReceiver.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //intent.setData(Uri.parse(pushMessage.getDeepLink()));
            //intent.putExtras(pushMessage.getBundle());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(GameApplication.getInstance(), notify_id,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
            val newIntent = Intent(
                CrossWordHindiApplication.getInstance(),
                NotificationReceiver::class.java
            )
            val mBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(CrossWordHindiApplication.getInstance()!!, NOTIFICATION_CHANNEL_ID)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                //mBuilder.setColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
               //the last one icons-- mBuilder.setSmallIcon(com.tvtoday.crosswordhindi.R.drawable.varg_icon_notification)
                mBuilder.setSmallIcon(com.tvtoday.gamelibrary.R.drawable.crossword_notification)
            } else {
                //mBuilder.setSmallIcon(com.tvtoday.crosswordhindi.R.drawable.varg_transparent)
                mBuilder.setSmallIcon(R.drawable.crossword_notification)
                mBuilder.color = ContextCompat.getColor(CrossWordHindiApplication.getInstance()!!, R.color.white)
            }

            mBuilder.setContentIntent(
                PendingIntent.getBroadcast(
                    CrossWordHindiApplication.getInstance(),
                    notify_id,
                    newIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )

            mBuilder.setContentTitle(pushMessage.getHeader())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText(pushMessage.getMessage())
                .setAutoCancel(true)
            mBuilder.build().flags = mBuilder.build().flags or Notification.FLAG_ONGOING_EVENT
            mBuilder.setWhen(System.currentTimeMillis())
            val mNotificationManager = CrossWordHindiApplication.getInstance()!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val notificationChannel =
                    NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
                notificationChannel.description = "no sound"
                notificationChannel.setSound(null, null)
                notificationChannel.enableLights(false)
                notificationChannel.enableVibration(false)
                mBuilder.setGroup(System.currentTimeMillis().toString())
                    .setChannelId(NOTIFICATION_CHANNEL_ID)
                mNotificationManager.createNotificationChannel(notificationChannel)
            }

            // if (notifyMgr != null)
            //notify_id=notify_id+1;
            mNotificationManager.notify(
                (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                mBuilder.build()
            )
        }
    }

}