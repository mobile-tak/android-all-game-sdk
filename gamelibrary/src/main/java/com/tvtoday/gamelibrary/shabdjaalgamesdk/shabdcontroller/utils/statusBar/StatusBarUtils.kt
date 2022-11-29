package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.statusBar

import android.app.Activity
import android.view.WindowManager

class StatusBarUtils(private val activity: Activity) {

    fun statusBarHidden() {
        // Hide status bar
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun statusBarShow() {
        // Show status bar
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}