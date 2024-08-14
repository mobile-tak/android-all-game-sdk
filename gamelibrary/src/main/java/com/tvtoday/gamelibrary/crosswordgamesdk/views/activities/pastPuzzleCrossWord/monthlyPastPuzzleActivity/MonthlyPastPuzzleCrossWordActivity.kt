package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.monthlyPastPuzzleActivity

import PrefData
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`.RecyclerViewOnClick
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MonthlyPastPuzzleCrossWordActivity : VargPaheliBaseActivity(), View.OnClickListener {
    //views----------------------------------------------------------
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()

    private var rvmonthlyPuzzle: RecyclerView? = null
    private var monthlyPuzzleAdapter: MonthlyPuzzleCrossWordAdapter? = null


    private val crosswordMonthlyPastPuzzle: ArrayList<CrosswordArrayItem?> = ArrayList()

    private var date_from_puzzle: String = ""

    private var iv_back_btn: ImageView? = null
    private var ivChangeLanguage: ImageView? = null
    private var tvMonthName: TextView? = null

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_past_puzzle_cross_word)

        date_from_puzzle = intent.getStringExtra("DATE_GAME").toString()

        initViews()

        if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
            PrefData.getAppLangaugeStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
            val pastMonthlyPuzzleRequest = PastPuzzleMonthlyCrossWordRequest()
            pastMonthlyPuzzleRequest.gameUserId = PrefData.getStringPrefs(
                this@MonthlyPastPuzzleCrossWordActivity,
                PrefData.Key.GAME_USER_ID)
            pastMonthlyPuzzleRequest.gameDate = date_from_puzzle
            pastMonthlyPuzzleRequest.lang_id = "1"
            pastMonthlyPuzzleRequest.app_id = PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_ID)
            Log.e("Past_monthly_param",pastMonthlyPuzzleRequest.toString())
            getMonthlyPastPuzzle(pastMonthlyPuzzleRequest)
        }else{
            val pastMonthlyPuzzleRequest = PastPuzzleMonthlyCrossWordRequest()
            pastMonthlyPuzzleRequest.gameUserId = PrefData.getStringPrefs(
                this@MonthlyPastPuzzleCrossWordActivity,
                PrefData.Key.GAME_USER_ID
            )
            pastMonthlyPuzzleRequest.gameDate = date_from_puzzle
            pastMonthlyPuzzleRequest.lang_id = "2"
            pastMonthlyPuzzleRequest.app_id = PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_ID)
            Log.e("Past_monthly_param",pastMonthlyPuzzleRequest.toString())
            getMonthlyPastPuzzle(pastMonthlyPuzzleRequest)
        }
    }
    private fun initViews() {
        //initialisation views here--------------------------
        rvmonthlyPuzzle = findViewById(R.id.rvmonthlyPuzzle)
        iv_back_btn = findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        tvMonthName =findViewById(R.id.tvMonthName)
        ivChangeLanguage = findViewById(R.id.ivChangeLanguage)
        ivChangeLanguage?.setOnClickListener(this)


        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

          //  llHindi?.setBackgroundColor(Color.BLACK)
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
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH)

            if(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

              //  llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.GAME_USER_ID))) {
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

        llHindi?.setOnClickListener {
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_HINDI)

            if(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

             //   llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.GAME_USER_ID))) {
                    val intent = Intent(this, PastPuzzleCrosswordActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }else{
                    val intent = Intent(this, VargPaheliGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }

            }

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back_btn -> {

                finish()
            }
            R.id.ivChangeLanguage ->{
               // openLanguageBottomSheet()
            }
            /*R.id.ivChangeLanguage ->{
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View = inflater.inflate(R.layout.language_selection_filter, null)
                // create the popup window
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true // lets taps outside the popup also dismiss it

                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAtLocation(ivChangeLanguage, 0, 160, 150)

                val ivCancel: ImageView = popupView.findViewById(R.id.ivCancel)
                val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)

                // radioGroup.setOnCheckedChangeListener(radioListener)
                radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.radioBtnEnglish -> {

                            VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                            PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                            PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                            if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.GAME_USER_ID))) {
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
                        R.id.radioBtnHindi ->{

                            VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                            PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                            PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                            if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.GAME_USER_ID))) {
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




                *//*  val activityToStart =
                      "com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity"
                  try {
                      val c = Class.forName(activityToStart)
                      val intent = Intent(this, c)
                      intent.putExtra("called_game_id", 3)
                      startActivity(intent)
                  } catch (ignored: ClassNotFoundException) {
                  }*//*
            }


              *//*  val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.putExtra("called_game_id", 3)
                    startActivity(intent)
                } catch (ignored: ClassNotFoundException) {
                }*//*
            }*/
        }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        if (requestCode == 11110) {
            setResult(1111)
            // Make sure the request was successful
            finish()
        }
    }


    private fun getMonthlyPastPuzzle(monthlyPuzzleRequest: PastPuzzleMonthlyCrossWordRequest?) {
        compositeDisposable.add(
            apiService!!.getMonthlyPastPuzzle(monthlyPuzzleRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("get_past_montlhy:", response.toString())
                    if (response?.status == "true") {
                        if (response.data != null) {
                            crosswordMonthlyPastPuzzle.clear()
                            crosswordMonthlyPastPuzzle.addAll(response.data.crosswordArray!!)

                            tvMonthName?.text = response.data.crosswordArray[0]!!.month

                            monthlyPuzzleAdapter =
                                MonthlyPuzzleCrossWordAdapter(this, crosswordMonthlyPastPuzzle,
                                    object : RecyclerViewOnClick {
                                        override fun onItemClicked(
                                            position: Int,
                                            viewType: Int,
                                            view: View
                                        ) {

                                         /*   when (viewType) {
                                                76 -> {

                                                }
                                            }*/

                                        }
                                    })

                            val layoutManager = GridLayoutManager(this, 3)
                            rvmonthlyPuzzle?.layoutManager = layoutManager
                            rvmonthlyPuzzle?.adapter = monthlyPuzzleAdapter

                        }
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
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

        if(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){
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
            if(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){
                lLayHindiLang.isClickable = false
            }else{

                lLayHindiLang.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.GAME_USER_ID))) {
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

        lLayEnglishLang?.setOnClickListener{

            if(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")){
                lLayEnglishLang.isClickable = false
            }else{

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@MonthlyPastPuzzleCrossWordActivity,PrefData.Key.GAME_USER_ID))) {
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