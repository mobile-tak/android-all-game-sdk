package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.waitingactivity

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity

class WaitingShabdActivity : ShabdjalBaseActivity(){
    var timer:CountDownTimer? = null
    private var ivHome:ImageView?=null
    private var tvForPastPuzzleLogin:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_shabd)

        initViews()
    }

    private fun initViews() {
        ivHome = findViewById(R.id.ivHome)
        tvForPastPuzzleLogin = findViewById(R.id.tvForPastPuzzleLogin)
        ivHome?.setOnClickListener {
          finish()
        }


        if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@WaitingShabdActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
            tvForPastPuzzleLogin?.visibility= View.GONE
        }

    }

    override fun onBackPressed() {
    super.onBackPressed();
   // Not calling **super**, disables back button in current screen.
    }
}