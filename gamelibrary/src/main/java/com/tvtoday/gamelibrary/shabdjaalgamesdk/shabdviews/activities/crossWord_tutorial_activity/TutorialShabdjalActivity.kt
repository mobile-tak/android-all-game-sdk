package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.crossWord_tutorial_activity


import PrefDataShabdjal
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.VargPaheliGameShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalGameRuleActivity.GameRuleShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.adapter.TutorialShabdAdapter
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity

class TutorialShabdjalActivity : ShabdjalBaseActivity() ,View.OnClickListener{
    //views-------------------------------------------------------

    private var viewFlipper : ViewFlipper?=null
    private var lLayMainTutorialLayout:LinearLayout?=null
    //textView
    private var tvSkip :TextView?=null
    private var tv_next_btn :TextView?=null
    private var tv_start_btn :TextView?=null

    var currentPage = 0

    //viewPager
    private var tutorialAdapter: TutorialShabdAdapter?=null
    private var viewpager:ViewPager2? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_shabd)

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
        tutorialAdapter = TutorialShabdAdapter(this, getImages()!!)
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

                next()
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.NEXT)
            }
            R.id.tvSkip ->{
                CommonUtilsShabdjal.performIntentFinish(this, GameRuleShabdActivity::class.java)
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.SKIP)
            }

            R.id.tv_start_btn ->{
                if(PrefDataShabdjal.getBooleanPrefs(this, PrefDataShabdjal.Key.IsPlayGuestRuleShow)){
                    CommonUtilsShabdjal.performIntentFinish(this, VargPaheliGameShabdActivity::class.java)
                }else{
                    CommonUtilsShabdjal.performIntentFinish(this, GameRuleShabdActivity::class.java)
                }
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.START_GAME)
            }
        }
    }



    private operator fun next() {
        currentPage++
        viewpager?.currentItem = currentPage
    }

    private fun getImages(): List<Int>? {
        val list: ArrayList<Int> = ArrayList()
        list.add(R.drawable.first_img)
        list.add(R.drawable.second_img)
        list.add(R.drawable.third_img)

        return list
    }
}