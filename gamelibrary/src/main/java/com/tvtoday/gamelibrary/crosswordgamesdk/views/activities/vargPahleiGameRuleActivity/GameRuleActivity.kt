package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPahleiGameRuleActivity

import PrefData
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity.LeaderBoardActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.varg_paheli_settingActivity.VargPaheliSettingActivity


class GameRuleActivity : VargPaheliBaseActivity(), View.OnClickListener {

    private var rlNext:RelativeLayout? = null
    private var ivSetting: AppCompatImageView?=null
    private var ivLeaderBoard: AppCompatImageView?=null

    private var iv_back_btn:ImageView?=null
    private var ivChangeLangauge:ImageView?=null

    private var checkBox :CheckBox?=null
    private var date_for_puzzle = ""


    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rule)

        try{
            date_for_puzzle =intent.getStringExtra("DATE_GAME").toString()
            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {

        rlNext = findViewById(R.id.rlNext)
        rlNext?.setOnClickListener(this)

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

            //llHindi?.setBackgroundColor(Color.BLACK)
            llEng?.setBackgroundColor(Color.WHITE)

            tvEng?.setTextColor(Color.parseColor("#000000"));
            tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));

        }else{
            llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

          //  llEng?.setBackgroundColor(Color.BLACK)
            llHindi?.setBackgroundColor(Color.WHITE)

            tvHindi?.setTextColor(Color.parseColor("#000000"));
            tvEng?.setTextColor(Color.parseColor("#FFFFFF"));
        }


        llEng?.setOnClickListener {
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH)

            if(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{


                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_ID))){
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

        llHindi?.setOnClickListener {
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_HINDI)

            if(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_ID))){
                        val intent = Intent(this, VargPaheliGameActivity::class.java)
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
                    val intent = Intent(this, VargPaheliGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }

            }

        }


        /*ivChangeLangauge = findViewById(R.id.ivChangeLangauge)
        ivChangeLangauge?.setOnClickListener(this)*/

        ivSetting = findViewById(R.id.ivSetting)
        ivSetting?.setOnClickListener(this)

        ivLeaderBoard = findViewById(R.id.ivLeaderBoard)
        ivLeaderBoard?.setOnClickListener(this)

        iv_back_btn = findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        checkBox = findViewById(R.id.checkBox)
        checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->

            CleverTapEvent(this).createOnlyEvent(
                CleverTapEventConstants.DO_NOT_SHOW)

            if (buttonView.isChecked) {
                PrefData.setBooleanPrefs(this, PrefData.Key.ISRuleSHow, true)
                // perform logic
            }else{
                PrefData.setBooleanPrefs(this, PrefData.Key.ISRuleSHow, false)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rlNext -> {

                if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                    PrefData.getAppLangaugeStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                    val intent = Intent(
                        this@GameRuleActivity,
                        VargPaheliGameActivity::class.java
                    )

                    intent.putExtra(
                        "DATE_GAME",
                        date_for_puzzle
                    )
                    startActivity(intent)
                    finish()

                }else{

                    val intent = Intent(
                        this@GameRuleActivity,
                        EnglishVargPahleiGameActivity::class.java
                    )

                    intent.putExtra(
                        "DATE_GAME",
                        date_for_puzzle
                    )
                    startActivity(intent)
                    finish()
                }



               /*  if(PrefData.getBooleanPrefs(this@GameRuleActivity,PrefData.Key.IS_GUEST_USER)){
                     CommonUtils.performIntentFinish(this@GameRuleActivity, VargPaheliGameActivity::class.java)
               }else{
                     CommonUtils.performIntentFinish(this@GameRuleActivity, PastPuzzleCrosswordActivity::class.java)
                 }*/

               // PrefData.setBooleanPrefs(this, PrefData.Key.IsPlayGuestRuleShow, true)
               /* if(PrefData.getBooleanPrefs(this@GameRuleActivity, PrefData.Key.LOGIN_FROM_SIGNUP)){
                    CommonUtils.performIntentFinish(this@GameRuleActivity, PastPuzzleCrosswordActivity::class.java)
                }else{
                    CommonUtils.performIntentFinish(this@GameRuleActivity, VargPaheliGameActivity::class.java)
                }*/

               // CommonUtils.performIntentFinish(this@GameRuleActivity, VargPaheliGameActivity::class.java)
                //cleverTap event
                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.GO_FORWARD)
            }

            R.id.ivSetting ->{
                CommonUtils.performIntent(this@GameRuleActivity, VargPaheliSettingActivity::class.java)
                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.SETTING)
            }



        /*  R.id.ivChangeLangauge ->{
              openLanguageBottomSheet()
          }*/


            R.id.iv_back_btn -> {
               // CommonUtils.backPress(this)

                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.RULE_BACK_BUTTON)

                if(PrefData.getBooleanPrefs(this, PrefData.Key.GUEST_USER)){
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                    PrefData.clearWholePreference(this)
                }else{
                    CommonUtils.backPress(this)
                }
            }

            R.id.ivLeaderBoard ->{
                CommonUtils.performIntent(this@GameRuleActivity, LeaderBoardActivity::class.java)
                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.LEADER_BOARD)
            }
        }
    }

    private fun openLanguageBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(
            this,
            R.style.AppBottomSheetDialogTheme
        )

        val view = this.layoutInflater.inflate(R.layout.language_bottom_sheet_layout, null)

        val lLayHindiLang: LinearLayout? = view.findViewById(R.id.lLayHindiLang)
        val lLayEnglishLang: LinearLayout? = view.findViewById(R.id.lLayEnglishLang)

        val tvHindiLang: TextView? = view.findViewById(R.id.tvHindiLang)
        val tvEngLang: TextView? = view.findViewById(R.id.tvEngLang)
        //  val lLayQuitGame: LinearLayout? = view.findViewById(R.id.lLayQuitGame)

        if(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

            lLayHindiLang?.setBackgroundColor(Color.BLACK);
            lLayEnglishLang?.setBackgroundColor(Color.WHITE);

            tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
            tvEngLang?.setTextColor(Color.parseColor("#000000"));
        }else{

            lLayEnglishLang?.setBackgroundColor(Color.BLACK);
            lLayHindiLang?.setBackgroundColor(Color.WHITE);

            tvHindiLang?.setTextColor(Color.parseColor("#000000"));
            tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));
        }


        lLayHindiLang?.setOnClickListener {



        }

        lLayEnglishLang?.setOnClickListener{

            if(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                lLayEnglishLang.isClickable = false
            }else{

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@GameRuleActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@GameRuleActivity,PrefData.Key.GAME_USER_ID))) {
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

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}