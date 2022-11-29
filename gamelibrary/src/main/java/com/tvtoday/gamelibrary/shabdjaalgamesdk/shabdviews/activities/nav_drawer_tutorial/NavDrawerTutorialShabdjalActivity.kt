package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.nav_drawer_tutorial

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity

class NavDrawerTutorialShabdjalActivity : ShabdjalBaseActivity(), View.OnClickListener {
    //views
    private var backBtn:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer_tutorial_shabd)

        initViews()
    }

    private fun initViews() {
        //initViews----------
        backBtn = findViewById(R.id.backBtn)
        backBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backBtn -> {
                CommonUtilsShabdjal.backPress(this)
            }
        }
    }
}