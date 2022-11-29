package com.tvtoday.gamelibrary.core.views.activity.languageSelection

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

import com.tvtoday.gamelibrary.core.views.activity.homePage.HomeActivity
import com.tvtoday.crosswordhindi.views.controller.MainCommonPref.MainCommonPref
import com.tvtoday.crosswordhindi.views.controller.constants.AppConstantsMain
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.core.controller.utils.localeHelper.LanguagePreferenceMain
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.shabdamgamesdk.GameActivity
import com.tvtoday.gamelibrary.shabdamgamesdk.GameDataManager
import com.tvtoday.gamelibrary.shabdamgamesdk.pref.CommonPreference
import com.tvtoday.gamelibrary.shabdamgamesdk.ui.englishShabdam.englishGameActivity.EnglishGameActivity
import com.tvtoday.gamelibrary.shabdamgamesdk.utils.ShabdamLanguagePreference
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference


class LanguageSelectionActivity : AppCompatActivity(), View.OnClickListener {
    //views--------------------------------------------

    private var lLayHindiBtn :LinearLayout?=null
    private var lLayEnglishBtn :LinearLayout?=null

    private var context: Context?=null
    private var aa : Resources?=null

    private val SHABDAM_ID = 1
    private val SHABDJAL_ID = 2
    private val VARGPAHELI_ID = 3
    private var calledGameId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        try{
            setContentView(R.layout.activity_language_selection)

            if(intent != null && intent.hasExtra("called_game_id")){
                calledGameId = intent.getIntExtra("called_game_id", 0)
            }

            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {
        //initialisation views ----------------------
        lLayHindiBtn = findViewById(R.id.lLayHindiBtn)
        lLayHindiBtn?.setOnClickListener(this)

        lLayEnglishBtn = findViewById(R.id.lLayEnglishBtn)
        lLayEnglishBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.lLayEnglishBtn -> {
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.LANGUAGE_ENGLISH)

                GameDataManager.getInstance().dataList.clear()
                when(calledGameId){
                    0 ->{

                        MainCommonPref.setStringPrefs(this@LanguageSelectionActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,AppConstantsMain.ENGLISH)

                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH)
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)
                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.ENGLISH)
                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                        ShabdamLanguagePreference.getInstance(baseContext).language = ""
                        ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                        VargPaheliLanguagePreference.getInstance(baseContext).language = ""

                        //for main app language change to english ---
                        LanguagePreferenceMain.getInstance(baseContext).language = ""

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                        finish()
                    }

                    1 ->{
                        ShabdamLanguagePreference.getInstance(baseContext).setLanguage("")
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH)
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                        val intent = Intent(this, EnglishGameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }

                    2 ->{
                        ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                        /*CommonPreference.getInstance(this@LanguageSelectionActivity).put(PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.ENGLISH)
*/
                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)
                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                        if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
                            val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }
                    }

                    3 ->{
                        VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.ENGLISH)
                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                        if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LanguageSelectionActivity,PrefData.Key.GAME_USER_ID))) {
                            val intent = Intent(this, PastPuzzleCrosswordActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)

                        }else{
                            val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)

                        }
                    }
                }
            }

            R.id.lLayHindiBtn -> {

                /* context = LocaleHelper.setLocale(this@LanguageSelectionActivity, "")
                 aa = context!!.getResources()
                 recreate()*/
                GameDataManager.getInstance().dataList.clear()
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.LANGUAGE_HINDI)


                when(calledGameId){
                    0 ->{
                        MainCommonPref.setStringPrefs(this@LanguageSelectionActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,AppConstantsMain.HINDI)

                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI)
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI)

                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.HINDI)
                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.HINDI)

                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.HINDI)
                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.HINDI)

                        //for main app language change to hindi
                        LanguagePreferenceMain.getInstance(baseContext).language = "hi"

                        ShabdamLanguagePreference.getInstance(baseContext).setLanguage("hi")
                        ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                        VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }

                    1 ->{
                        ShabdamLanguagePreference.getInstance(baseContext).setLanguage("hi")
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI)
                        CommonPreference.getInstance(this@LanguageSelectionActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI)

                        val intent = Intent(this, GameActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }

                    2 ->{
                        ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.HINDI)
                        PrefDataShabdjal.setStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.HINDI)

                        if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LanguageSelectionActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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
                        }
                    }

                    3 ->{
                        VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.HINDI)
                        PrefData.setStringPrefs(this@LanguageSelectionActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.HINDI)

                        if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LanguageSelectionActivity,PrefData.Key.GAME_USER_ID))) {
                            val intent = Intent(this, PastPuzzleCrosswordActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, VargPaheliGameActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        }

                    }
                }
            }
        }
    }
}