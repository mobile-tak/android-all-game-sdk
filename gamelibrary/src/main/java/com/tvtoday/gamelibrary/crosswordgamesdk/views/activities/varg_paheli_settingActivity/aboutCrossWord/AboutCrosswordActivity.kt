package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.varg_paheli_settingActivity.aboutCrossWord

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.tvtoday.gamelibrary.R


class AboutCrosswordActivity : AppCompatActivity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_crossword)

        try{
            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun initViews() {
        webView =  findViewById(R.id.faqWebView);
        webView!!.settings.javaScriptEnabled = true;
        webView!!.loadUrl("https://docs.google.com/document/d/1OcIcWLqs4iIxtkElyYJ3SG7ZF4i8xfXN/edit?usp=sharing&ouid=113620526461677736080&rtpof=true&sd=true");
    }


}