package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.crossWord_tutorial_activity


import PrefData
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPahleiGameRuleActivity.GameRuleActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.adapter.TutorialAdapter


class TutorialActivity : VargPaheliBaseActivity() ,View.OnClickListener{
    //views-------------------------------------------------------

    private var viewFlipper : ViewFlipper?=null
    private var lLayMainTutorialLayout:LinearLayout?=null
    //textView
    private var tvSkip :TextView?=null
    private var tv_next_btn :TextView?=null
    private var tv_start_btn :TextView?=null

    var currentPage = 0

    //viewPager
    private var tutorialAdapter: TutorialAdapter?=null
    private var viewpager:ViewPager2? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        initViews()
    }

    private fun initViews() {
        //initialisation views-----------------------------------------
        lLayMainTutorialLayout=findViewById(R.id.lLayMainTutorialLayout)
        lLayMainTutorialLayout?.setOnClickListener(this)
        //viewFlipper =findViewById(R.id.viewFlipper)

        //textView
        tvSkip = findViewById(R.id.tvSkip)
        tvSkip?.setOnClickListener(this)

        tv_next_btn = findViewById(R.id.tv_next_btn)
        tv_next_btn?.setOnClickListener(this)

        tv_start_btn = findViewById(R.id.tv_start_btn)
        tv_start_btn?.setOnClickListener(this)

        viewpager = findViewById(R.id.viewpager)
        tutorialAdapter = TutorialAdapter(this, getImages()!!)
        viewpager!!.adapter = tutorialAdapter

        viewpager!!.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                invalidateButton()
            }
        })
    }

    private fun invalidateButton() {
        if (currentPage == tutorialAdapter!!.itemCount - 1) {
            tvSkip?.visibility = View.GONE
            tv_next_btn?.visibility = View.GONE
            tv_start_btn?.visibility = View.VISIBLE
        } else {
            tvSkip?.visibility = View.VISIBLE
            tv_next_btn?.visibility = View.VISIBLE
            tv_start_btn?.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_next_btn ->{
                if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                    PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                    if (currentPage == 0) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.NEXT_VP)
                    } else if (currentPage == 1) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.NEXT_VP_2)
                    }
                }else{
                    if (currentPage == 0) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.NEXT_CW_1)
                    } else if (currentPage == 1) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.NEXT_CW_2)
                    }
                }

                next()

            }

            R.id.tvSkip ->{
                if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                    PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                    if (currentPage == 0) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.SKIP_VP)
                    } else if (currentPage == 1) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.SKIP_VP_2)
                    }

                }else{
                    if (currentPage == 0) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.SKIP_CW_1)
                    } else if (currentPage == 1) {
                        CleverTapEvent(this).createOnlyEvent(
                            CleverTapEventConstants.SKIP_CW_2)
                    }
                }


                if(PrefData.getStringPrefs(this@TutorialActivity,PrefData.Key.GAME_USER_ID) !=null){

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_ID))){

                        CommonUtils.performIntentFinish(this@TutorialActivity, GameRuleActivity::class.java)

                    }else{
                        val intent = Intent(
                            this@TutorialActivity,
                            PastPuzzleCrosswordActivity::class.java
                        )
                        startActivity(intent)
                        finish()
                        //CommonUtils.performIntentFinish(this@TutorialActivity, PastPuzzleCrosswordActivity::class.java)

                    }


                }else{
                    val intent = Intent(
                        this@TutorialActivity,
                        GameRuleActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }


            }

            R.id.tv_start_btn -> {

                if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                    PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                    CleverTapEvent(this).createOnlyEvent(
                        CleverTapEventConstants.START_GAME_VP)

                }else{
                    CleverTapEvent(this).createOnlyEvent(
                        CleverTapEventConstants.START_GAME_CW)
                }



                if(PrefData.getStringPrefs(this@TutorialActivity,PrefData.Key.GAME_USER_ID) !=null){

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_ID))){

                        CommonUtils.performIntentFinish(this@TutorialActivity, GameRuleActivity::class.java)

                    }else{
                        CommonUtils.performIntentFinish(this@TutorialActivity, PastPuzzleCrosswordActivity::class.java)

                    }
                  //  CommonUtils.performIntentFinish(this@TutorialActivity, PastPuzzleCrosswordActivity::class.java)

                }else{
                    CommonUtils.performIntentFinish(this@TutorialActivity, GameRuleActivity::class.java)
                }

               /* if(PrefData.getBooleanPrefs(this@TutorialActivity,PrefData.Key.IS_GUEST_USER)){

                }*/

              /*  if(PrefData.getBooleanPrefs(this@TutorialActivity, PrefData.Key.LOGIN_FROM_SIGNUP)){
                    CommonUtils.performIntentFinish(this@TutorialActivity, PastPuzzleCrosswordActivity::class.java)
                }else{
                    CommonUtils.performIntentFinish(this@TutorialActivity, GameRuleActivity::class.java)
                }*/

              /*  if(PrefData.getBooleanPrefs(this, PrefData.Key.IsPlayGuestRuleShow)){
                    CommonUtils.performIntentFinish(this, PastPuzzleCrosswordActivity::class.java)
                }else{
                    CommonUtils.performIntentFinish(this, GameRuleActivity::class.java)
                }*/

            }
        }
    }


    private operator fun next() {
        currentPage++
        viewpager?.currentItem = currentPage
    }

    private fun getImages(): List<Int>? {

        if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
            PrefData.getAppLangaugeStringPrefs(this@TutorialActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

            val list: ArrayList<Int> = ArrayList()
            list.add(R.drawable.first_img)
            list.add(R.drawable.second_img)
            list.add(R.drawable.third_img)

            return list
        }else{
            val list: ArrayList<Int> = ArrayList()
            list.add(R.drawable.eng_1)
            list.add(R.drawable.eng_2)
            list.add(R.drawable.eng_3)

            return list
        }


    }
}