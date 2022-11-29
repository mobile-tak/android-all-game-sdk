package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.gameLanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.tvtoday.gamelibrary.R

class ShabdjaalGameLanguageActivity : AppCompatActivity() , View.OnClickListener{

    private var lLayHindiBtn:LinearLayout?=null

    private var lLayEnglishBtn:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shabdjaal_game_language)

        try{
            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {
        lLayHindiBtn = findViewById(R.id.lLayHindiBtn)
        lLayHindiBtn?.setOnClickListener(this)

        lLayEnglishBtn = findViewById(R.id.lLayEnglishBtn)
        lLayEnglishBtn?.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
       when(v?.id){
           R.id.lLayEnglishBtn -> {

           }

           R.id.lLayHindiBtn -> {

           }
       }
    }
}