package com.tvtoday.gamelibrary.core.controller.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tvtoday.gamelibrary.core.CrossWordHindiApplication
import com.tvtoday.gamelibrary.core.views.activity.splashActivity.SplashActivityMerger

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        var intent1: Intent? = null
        intent1 = Intent(CrossWordHindiApplication.getInstance(), SplashActivityMerger::class.java)
        intent1!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent1)

        /* if (String.valueOf(intent.getData()).contains(GameApplication.getInstance().getString(R.string.app_name))) {
            intent1 = new Intent(GameApplication.getInstance(), ShabdamSplashActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.setData(intent.getData());
            intent1.putExtras(intent.getExtras());
            context.startActivity(intent1);
        }*/
    }
}