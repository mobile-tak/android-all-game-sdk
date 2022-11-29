package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalGameRuleActivity

import PrefDataShabdjal
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.leader_board_activity.LeaderBoardShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.shabdjaal_settingActivity.ShabdjalSettingActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.VargPaheliGameShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference


class GameRuleShabdActivity : ShabdjalBaseActivity(), View.OnClickListener {

    private var rlNext: RelativeLayout? = null
    private var ivSetting: AppCompatImageView? = null
    private var ivLeaderBoard: AppCompatImageView? = null
    private var ivLangChange: AppCompatImageView? = null

    private var iv_back_btn: ImageView? = null

    private var checkBox: CheckBox? = null
    private var puzzleGameDate = ""

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rule_shabd)

        try {
            puzzleGameDate = intent.getStringExtra("Date").toString()
            initViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initViews() {
        rlNext = findViewById(R.id.rlNext)
        rlNext?.setOnClickListener(this)

        ivSetting = findViewById(R.id.ivSetting)
        ivSetting?.setOnClickListener(this)

        ivLangChange = findViewById(R.id.ivLangChange)
        ivLangChange?.setOnClickListener(this)

        ivLeaderBoard = findViewById(R.id.ivLeaderBoard)
        ivLeaderBoard?.setOnClickListener(this)

        iv_back_btn = findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        checkBox = findViewById(R.id.checkBox)
        checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->

            CleverTapEventShabdjal(this).createOnlyEvent(
                CleverTapShabdjalConstants.DO_NOT_SHOW
            )

            if (buttonView.isChecked) {
                PrefDataShabdjal.setBooleanPrefs(this, PrefDataShabdjal.Key.ISRuleSHow, true)
                // perform logic
            } else {
                PrefDataShabdjal.setBooleanPrefs(this, PrefDataShabdjal.Key.ISRuleSHow, false)
            }
        }
        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")){

            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llHindi?.setBackgroundColor(Color.BLACK)
            llEng?.setBackgroundColor(Color.WHITE)

            tvEng?.setTextColor(Color.parseColor("#000000"));
            tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));

        }else{

            llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llEng?.setBackgroundColor(Color.BLACK)
            llHindi?.setBackgroundColor(Color.WHITE)

            tvHindi?.setTextColor(Color.parseColor("#000000"));
            tvEng?.setTextColor(Color.parseColor("#FFFFFF"));
        }

        llEng?.setOnClickListener {
            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_ENGLISH)

            if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.GAME_USER_ID))) {
                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
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
                    }


                }else{
                    val intent = Intent(this, VargPaheliGameShabdActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }

            }

        }

        llHindi?.setOnClickListener {
            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_HINDI)

            if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = "hi"
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
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
                    }


                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }

            }

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rlNext -> {
                PrefDataShabdjal.setBooleanPrefs(
                    this,
                    PrefDataShabdjal.Key.IsPlayGuestRuleShow,
                    true
                )
                //  CommonUtilsShabdjal.performIntentFinish(this@GameRuleShabdActivity, ShabdjaalPlayGameActivity::class.java)

                if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.GAME_USER_ID) !=null){

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){

                        if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                            PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                            val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
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

                    }else{
                        if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                            PrefDataShabdjal.getAppLanguageStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {

                            val intent = Intent(this@GameRuleShabdActivity, ShabdjaalPlayGameActivity::class.java)
                            intent.putExtra("Date",puzzleGameDate)
                            startActivity(intent)
                            finish()

                        }else{
                            val intent = Intent(this@GameRuleShabdActivity, EnglishShabdjaalPlayGameActivity::class.java)
                            intent.putExtra("Date",puzzleGameDate)
                            startActivity(intent)
                            finish()

                        }
                      /*  val intent = Intent(this@GameRuleShabdActivity, PastPuzzleActivityShabdjaal::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        this.finish()*/
                    }




                /*    CommonUtilsShabdjal.performIntentFinish(
                        this@GameRuleShabdActivity,
                        PastPuzzleActivityShabdjaal::class.java
                    )*/
                }else{

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                        PrefDataShabdjal.getAppLanguageStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {

                        CommonUtilsShabdjal.performIntentFinish(
                            this@GameRuleShabdActivity,
                            ShabdjaalPlayGameActivity::class.java
                        )
                    }else{
                        CommonUtilsShabdjal.performIntentFinish(
                            this@GameRuleShabdActivity,
                            EnglishShabdjaalPlayGameActivity::class.java
                        )
                    }

                }

                //cleverTap event
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.GO_FORWARD
                )
            }

            R.id.ivSetting -> {
                CommonUtilsShabdjal.performIntent(
                    this@GameRuleShabdActivity,
                    ShabdjalSettingActivity::class.java
                )
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.SETTING
                )
            }

            R.id.iv_back_btn -> {

                // CommonUtils.backPress(this)

                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.RULE_BACK_BUTTON
                )

                if (PrefDataShabdjal.getBooleanPrefs(this, PrefDataShabdjal.Key.GUEST_USER)) {
                    val intent = Intent(this, SignUpShabdjalActivity::class.java)
                    startActivity(intent)
                    PrefDataShabdjal.clearWholePreference(this)
                } else {
                    CommonUtilsShabdjal.backPress(this)
                }
            }

            R.id.ivLeaderBoard -> {
                CommonUtilsShabdjal.performIntent(
                    this@GameRuleShabdActivity,
                    LeaderBoardShabdjalActivity::class.java
                )
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.LEADER_BOARD
                )
            }

            R.id.ivLangChange-> {
                openLanguageBottomSheet()
            }
        }
    }


    private fun openLanguageBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(
            this,
            R.style.AppBottomSheetDialogTheme
        )

        val view = this.layoutInflater.inflate(R.layout.shabdjaal_language_bottom_sheet_layout_, null)

        val lLayHindiLang: LinearLayout? = view.findViewById(R.id.lLayHindiLang)
        val lLayEnglishLang: LinearLayout? = view.findViewById(R.id.lLayEnglishLang)

        val tvHindiLang: TextView? = view.findViewById(R.id.tvHindiLang)
        val tvEngLang: TextView? = view.findViewById(R.id.tvEngLang)
        //  val lLayQuitGame: LinearLayout? = view.findViewById(R.id.lLayQuitGame)

        if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")){
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

            if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
            }else{
                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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
        }

        lLayEnglishLang?.setOnClickListener{

            if(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("english")) {
                lLayEnglishLang.isClickable = false
            }else {

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()


                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@GameRuleShabdActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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

        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

}
