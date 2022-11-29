package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.nav_drawer_tutorial

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity

class NavDrawerTutorialActivity : VargPaheliBaseActivity(), View.OnClickListener {
    //views
    private var backBtn:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer_tutorial)

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
                CommonUtils.backPress(this)
            }
        }
    }
}