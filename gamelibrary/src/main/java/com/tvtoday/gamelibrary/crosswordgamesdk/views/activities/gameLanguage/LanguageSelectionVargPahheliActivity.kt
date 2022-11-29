package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.gameLanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.tvtoday.gamelibrary.R


class LanguageSelectionVargPahheliActivity : AppCompatActivity(), View.OnClickListener {

    private var lLayEnglishBtn:LinearLayout?=null
    private var lLayHindiBtn:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection_varg_pahheli)

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
            R.id.lLayEnglishBtn ->{

            }
            R.id.lLayHindiBtn ->{

            }
        }
    }
}