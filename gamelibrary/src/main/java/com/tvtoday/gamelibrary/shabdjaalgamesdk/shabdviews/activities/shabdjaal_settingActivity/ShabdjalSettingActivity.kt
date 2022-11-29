package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.shabdjaal_settingActivity

import PrefDataShabdjal
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference

class ShabdjalSettingActivity : ShabdjalBaseActivity(), View.OnClickListener{
    //
    private var iv_back_btn:ImageView?=null
    private var rl_feedback_btn:RelativeLayout?=null
    private var rlFaq:RelativeLayout?=null
    private var rl_logout_btn:RelativeLayout?=null
    private var rlLanguageSelection:RelativeLayout?=null

    private var radioGLangSelection : RadioGroup?=null

    private var switch_notification :Switch?=null
    private var switch_music :Switch?=null

    private var view_log_out :View?=null

    private var tvEng:TextView?=null
    private var tvHindi:TextView?=null

    private var llHindiLan:LinearLayout?=null
    private var llEngLang:LinearLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varg_paheli_setting_shabd)

        try{
            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {
        iv_back_btn= findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        rl_feedback_btn = findViewById(R.id.rl_feedback_btn)
        rl_feedback_btn?.setOnClickListener(this)

        tvHindi = findViewById(R.id.tvHindi)
        tvEng = findViewById(R.id.tvEng)
        llEngLang = findViewById(R.id.llEngLang)

        llHindiLan = findViewById(R.id.llHindiLan)

        if(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null && PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")){
            llHindiLan?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llHindiLan?.setBackgroundColor(Color.BLACK);
            llEngLang?.setBackgroundColor(Color.WHITE);

            tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));
            tvEng?.setTextColor(Color.parseColor("#000000"));

        }else{

            llEngLang?.setBackgroundColor(Color.parseColor("#4267B2"));
           // llEngLang?.setBackgroundColor(Color.BLACK);
            llHindiLan?.setBackgroundColor(Color.WHITE);

            tvHindi?.setTextColor(Color.parseColor("#000000"));
            tvEng?.setTextColor(Color.parseColor("#FFFFFF"));
        }

        llHindiLan?.setOnClickListener {
            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_HINDI)

            if(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                llHindiLan?.isClickable = false
            }else{


                llHindiLan?.setBackgroundColor(Color.parseColor("#4267B2"));

              //  llHindiLan?.setBackgroundColor(Color.BLACK);
                llEngLang?.setBackgroundColor(Color.WHITE);

                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEng?.setTextColor(Color.parseColor("#000000"));

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)



                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.GAME_USER_ID))){

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
                        val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }

                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }

        }

        llEngLang = findViewById(R.id.llEngLang)
        llEngLang?.setOnClickListener {
            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_ENGLISH)

            llEngLang?.setOnClickListener {
                if(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                    PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {
                    llEngLang?.isClickable = false
                }else {
                    llEngLang?.setBackgroundColor(Color.parseColor("#4267B2"));

                   // llEngLang?.setBackgroundColor(Color.BLACK);
                    llHindiLan?.setBackgroundColor(Color.WHITE);

                    tvHindi?.setTextColor(Color.parseColor("#000000"));
                    tvEng?.setTextColor(Color.parseColor("#FFFFFF"));

                    ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                    PrefDataShabdjal.setStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                    PrefDataShabdjal.setStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.GAME_USER_ID))){

                        if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
                            val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }

                    }else{
                        val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }
                }
                }
        }


        rlFaq = findViewById(R.id.rlFaq)
        rlFaq?.setOnClickListener(this)


        rl_logout_btn = findViewById(R.id.rl_logout_btn)
        rl_logout_btn?.setOnClickListener(this)

        switch_music = findViewById(R.id.switch_music)

        view_log_out = findViewById(R.id.view_log_out)

        rlLanguageSelection = findViewById(R.id.rlLanguageSelection)
        rlLanguageSelection?.setOnClickListener {

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

       /* if(PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID) == null){
            rl_logout_btn?.visibility = View.GONE
            view_log_out?.visibility = View.GONE
        }else{
            rl_logout_btn?.visibility = View.VISIBLE
            view_log_out?.visibility = View.VISIBLE
        }*/

        switch_music?.isChecked = PrefDataShabdjal.getSoundState(this@ShabdjalSettingActivity)
        switch_music?.setOnCheckedChangeListener{ _, isChecked ->

            PrefDataShabdjal.saveSoundState(this@ShabdjalSettingActivity,isChecked)
        }

        switch_notification = findViewById(R.id.switch_notification)

        switch_notification?.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) "Switch1:ON" else
                    CleverTapEventShabdjal(this).createOnlyEvent(
                        CleverTapShabdjalConstants.NOTIFICATION_OF
                    )
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back_btn -> {
                CommonUtilsShabdjal.backPress(this)
            }

        /*    R.id.lLayHindiLang ->{





                *//*lLayEngLan?.setBackgroundColor(resources.getColor(R.color.white))
                tvEnglish?.setBackgroundColor(resources.getColor(R.color.black))*//*
*//*
                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@ShabdjalSettingActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
                    val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }*//*
            }*/




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

                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.FEEDBACK)
            }

            R.id.rlFaq -> {
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.FAQ)
            }

            R.id.rl_logout_btn -> {
                PrefDataShabdjal.clearWholePreference(this)
                val intent = Intent(this, SignUpShabdjalActivity::class.java)
                this.startActivity(intent)
                this.finish()
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.LOGOUT_ICON)
            }
            R.id.rlLanguageSelection ->{
                /*val intent = Intent(this, ShabdjaalGameLanguageActivity::class.java)
                this.startActivity(intent)
                this.finish()*/


                /* Intent intent = new Intent(this, LanguageSelectionShabdamActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
                val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.putExtra("called_game_id", 2)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } catch (ignored: ClassNotFoundException) {
                }
            }
        }
    }

}