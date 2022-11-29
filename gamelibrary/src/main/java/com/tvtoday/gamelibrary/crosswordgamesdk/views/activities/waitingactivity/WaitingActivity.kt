package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.waitingactivity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity

class WaitingActivity : VargPaheliBaseActivity(), View.OnClickListener {
    var timer:CountDownTimer? = null
    private var ivHome: ImageView?=null
    private var tvForPastPuzzleLogin: TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)

        initViews()

    }

    private fun initViews() {
        ivHome = findViewById(R.id.ivHome)
        ivHome?.setOnClickListener(this)

        tvForPastPuzzleLogin = findViewById(R.id.tvForPastPuzzleLogin)

        if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@WaitingActivity,PrefData.Key.GAME_USER_ID))){
            tvForPastPuzzleLogin?.visibility= View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivHome -> {
              //  this.finish()
                val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.homePage.HomeActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } catch (ignored: ClassNotFoundException) {
                }
            }
        }
    }
}