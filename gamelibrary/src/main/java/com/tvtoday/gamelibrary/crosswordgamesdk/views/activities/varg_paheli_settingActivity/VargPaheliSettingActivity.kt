package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.varg_paheli_settingActivity

import PrefData
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.*

import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference

class VargPaheliSettingActivity : VargPaheliBaseActivity(), View.OnClickListener{
    //
    private var iv_back_btn:ImageView?=null
    private var rl_feedback_btn:RelativeLayout?=null
    private var rlFaq:RelativeLayout?=null
    private var rl_logout_btn:RelativeLayout?=null
    private var rlGame_language_selection:RelativeLayout?=null
    private var webView: WebView? = null

    private var switch_notification :Switch?=null
    private var switch_music :Switch?=null

    private var view_log_out :View?=null

    private var radioGLangSelection : RadioGroup?=null

    private var llHindiLan:LinearLayout?=null
    private var llEngLang:LinearLayout?=null

    private var tvHindi:TextView?=null
    private var tvEng:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varg_paheli_setting)

        try{
            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {
        view_log_out = findViewById(R.id.view_log_out)
        iv_back_btn= findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        rl_feedback_btn = findViewById(R.id.rl_feedback_btn)
        rl_feedback_btn?.setOnClickListener(this)

        rlFaq = findViewById(R.id.rlFaq)
        rlFaq?.setOnClickListener(this)

        rl_logout_btn = findViewById(R.id.rl_logout_btn)
        rl_logout_btn?.setOnClickListener(this)

        switch_music = findViewById(R.id.switch_music)

        rlGame_language_selection= findViewById(R.id.rlGame_language_selection)

        tvHindi = findViewById(R.id.tvHindi)
        tvEng = findViewById(R.id.tvEng)
        llHindiLan = findViewById(R.id.llHindiLan)
        llEngLang = findViewById(R.id.llEngLang)

        if(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null && PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

            llHindiLan?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llHindiLan?.setBackgroundColor(Color.BLACK);
            llEngLang?.setBackgroundColor(Color.WHITE);

            tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));
            tvEng?.setTextColor(Color.parseColor("#000000"));

        }else{

            llEngLang?.setBackgroundColor(Color.parseColor("#4267B2"));

          //  llEngLang?.setBackgroundColor(Color.BLACK);
            llHindiLan?.setBackgroundColor(Color.WHITE);

            tvHindi?.setTextColor(Color.parseColor("#000000"));
            tvEng?.setTextColor(Color.parseColor("#FFFFFF"));
        }


        llHindiLan?.setOnClickListener{
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_HINDI)

            if(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindiLan?.isClickable = false
            }else{

                llHindiLan?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindiLan?.setBackgroundColor(Color.BLACK);
                llEngLang?.setBackgroundColor(Color.WHITE);

                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEng?.setTextColor(Color.parseColor("#000000"));


                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefData.setStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_ID))){
                        val intent = Intent(this, VargPaheliGameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }else{
                        val intent = Intent(this, PastPuzzleCrosswordActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }

                }else{
                    val intent = Intent(this, VargPaheliGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }

        }


        llEngLang?.setOnClickListener{
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH)

            if(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEngLang?.isClickable = false
            }else{

                llEngLang?.setBackgroundColor(Color.parseColor("#4267B2"));
               // llEngLang?.setBackgroundColor(Color.BLACK);
                llHindiLan?.setBackgroundColor(Color.WHITE);

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_ID))){
                        val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }else{
                        val intent = Intent(this, PastPuzzleCrosswordActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }


                }else{
                    val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
            }

        }



       /* if(PrefData.getStringMusicPrefs(this@VargPaheliSettingActivity, PrefData.Key.IS_MUSIC_PLAYING)==null){

            switch_music?.isChecked

        }else{
            switch_music?.isChecked = false
        }*/

       /* if (PrefData.getStringMusicPrefs(this, PrefData.Key.IS_MUSIC_PLAYING) ==null || PrefData.getStringMusicPrefs(this, PrefData.Key.IS_MUSIC_PLAYING).equals("1"))
        {
            switch_music?.isChecked=true
        }else{
            switch_music?.isChecked
        }*/

        if(PrefData.getStringPrefs(this, PrefData.Key.GAME_USER_ID) == null){
            rl_logout_btn?.visibility = View.GONE
            view_log_out?.visibility = View.GONE
        }else{
          /*  rl_logout_btn?.visibility = View.VISIBLE
            view_log_out?.visibility = View.VISIBLE*/
        }

        switch_music?.isChecked = PrefData.getSoundState(this@VargPaheliSettingActivity)
        switch_music?.setOnCheckedChangeListener{ _, isChecked ->

            PrefData.saveSoundState(this@VargPaheliSettingActivity,isChecked)
        }

        switch_notification = findViewById(R.id.switch_notification)

        switch_notification?.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) "Switch1:ON" else
                    CleverTapEvent(this).createOnlyEvent(
                        CleverTapEventConstants.NOTIFICATION_OF
                    )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back_btn -> {
                CommonUtils.backPress(this)
            }

            R.id.rl_feedback_btn -> {

               /* val intent = Intent(Intent.ACTION_SEND)
                intent.type = "plain/text"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("shabdam.puzzle@gmail.com"))
                startActivity(Intent.createChooser(intent, ""))*/

                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "shabdam.puzzle@gmail.com", null
                    )
                )

                this.startActivity(Intent.createChooser(emailIntent, null))

                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.FEEDBACK)
            }

            R.id.rlFaq -> {

                if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                    PrefData.getAppLangaugeStringPrefs(this@VargPaheliSettingActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                }else{
                    val url = "https://docs.google.com/document/d/1OcIcWLqs4iIxtkElyYJ3SG7ZF4i8xfXN/edit?usp=sharing&ouid=113620526461677736080&rtpof=true&sd=true"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                    CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.FAQ)
            }

            R.id.rl_logout_btn -> {
                PrefData.clearWholePreference(this)
                PrefData.setBooleanPrefs(this, PrefData.Key.IS_LOGOUT, true)
                this.finish()

                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.LOGOUT_ICON)
            }

            R.id.rlGame_language_selection ->{
               /* val intent = Intent(this, LanguageSelectionVargPahheliActivity::class.java)
                // start your next activity
                startActivity(intent)*/

                /* Intent intent = new Intent(this, LanguageSelectionShabdamActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
                val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.putExtra("called_game_id", 3)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } catch (ignored: ClassNotFoundException) {
                }
            }
        }
    }

}