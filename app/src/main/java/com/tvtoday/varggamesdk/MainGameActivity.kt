package com.tvtoday.varggamesdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.core.views.activity.splashActivity.SplashActivityMerger

class MainGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  startActivity(Intent(this, SplashActivityMerger::class.java))
        SplashActivityMerger.startGameStart("com.tvtoday.crosswordhindi","","ca-app-pub-3793720534573472/2377754271","ca-app-pub-3940256099942544/1033173712","ca-app-pub-3793720534573472/9326306002",this)
    }
}