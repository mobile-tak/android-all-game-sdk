package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord

import PrefData
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`.RecyclerViewOnClick
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPahleiGameRuleActivity.GameRuleActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.String
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PastPuzzleCrosswordActivity : VargPaheliBaseActivity(), View.OnClickListener {

    //views--------------------------------------------------------------------
    private var rvPastPuzzle: RecyclerView? = null
    private var outerPastPuzzleMainAdapter: PastPuzzleOuterAdapter? = null

    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()

    private var ivCalendar: ImageView? = null
    private var ivFilter: ImageView? = null
    private var ivHomeIcon: ImageView? = null
    private var ivChangeLanguage: ImageView? = null

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    private var progressLayout:LinearLayout? = null

    private val crosswordPastPuzzle: ArrayList<CrosswordArrayItem?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try{
            setContentView(R.layout.activity_past_puzzle_crossword)
            intiViews()
        }catch (e:Exception){

        }
    }


    override fun onResume() {
        super.onResume()

        crosswordPastPuzzle.clear()
        if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
            PrefData.getAppLangaugeStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

            VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")

            val pastPuzzleVargPahleiRequest = PastPuzzleCrossWordRequest()
            pastPuzzleVargPahleiRequest.gameUserId= PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity, PrefData.Key.GAME_USER_ID)
            pastPuzzleVargPahleiRequest.lang_id = "1"
            pastPuzzleVargPahleiRequest.app_id = PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_ID)
            getPastPuzzle(pastPuzzleVargPahleiRequest)
            Log.e("Params_past",pastPuzzleVargPahleiRequest.toString())

        }else{
            VargPaheliLanguagePreference.getInstance(baseContext).language = ""

            val pastPuzzleVargPahleiRequest = PastPuzzleCrossWordRequest()
            pastPuzzleVargPahleiRequest.gameUserId= PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity, PrefData.Key.GAME_USER_ID)
            pastPuzzleVargPahleiRequest.lang_id = "2"
            pastPuzzleVargPahleiRequest.app_id = PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_ID)
            getPastPuzzle(pastPuzzleVargPahleiRequest)
            Log.e("eng_params_",pastPuzzleVargPahleiRequest.toString())
        }
    }

    private fun intiViews() {
        //initialisation views here--------------------
        rvPastPuzzle = findViewById(R.id.rvPastPuzzle)
        progressLayout = findViewById(R.id.progressBar)
        ivChangeLanguage = findViewById(R.id.ivChangeLanguage)
        ivChangeLanguage?.setOnClickListener(this)

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

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
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH)

            if(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.GAME_USER_ID))) {
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

            if(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

                //llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = "hi"
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.GAME_USER_ID))) {
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

        ivFilter = findViewById(R.id.ivFilter)
        ivFilter?.setOnClickListener(this)
        ivHomeIcon = findViewById(R.id.ivHomeIcon)
        ivHomeIcon?.setOnClickListener{
            val activityToStart =
                "com.tvtoday.gamelibrary.core.views.activity.homePage.HomeActivity"
            try {
                val c = Class.forName(activityToStart)
                val intent = Intent(this, c)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } catch (ignored: ClassNotFoundException) {
            }
        }

        ivCalendar = findViewById(R.id.ivCalendar)
        ivCalendar?.setOnClickListener(this@PastPuzzleCrosswordActivity)
    }

    @Deprecated("Deprecated in Java")
    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)

        if (requestCode == 1111) {
            // Make sure the request was successful
            finish()
        }
    }

    @SuppressLint("ResourceType")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivCalendar -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.calendar_open_layout_shabdjaal)
                dialog.show()

                var calendarView: CalendarView = dialog.findViewById(R.id.calendarView)
                var calendar: Calendar? = null
                calendar = Calendar.getInstance()
                val cyear: Int = calendar.get(Calendar.YEAR)
                val cmonth: Int = calendar.get(Calendar.MONTH)
                val cday: Int = calendar.get(Calendar.DAY_OF_MONTH)

                calendarView.maxDate = System.currentTimeMillis()

                calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                    // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
                    //val initDate: Date = SimpleDateFormat("dd/MM/yyyy").parse("10/12/2016")
                    dialog.dismiss()
                    progressLayout?.visibility = View.VISIBLE
                    val msg = "" + dayOfMonth + "-" + month + "-" + year
                    val curDate = String.valueOf(dayOfMonth)
                    val Year = String.valueOf(year)
                    val month = String.valueOf(month+1)

                    val selectedD = curDate + "-" + month + "-" + Year

                    val originalFormat = SimpleDateFormat("dd-MM-yyyy")
                    val targetFormat = SimpleDateFormat("yyyy-MM-dd")
                    val date: Date
                    try {
                        date = originalFormat.parse(selectedD)
                        val finalD = targetFormat.format(date)

                        val map = HashMap<kotlin.String, kotlin.String>()
                        if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                            PrefData.getAppLangaugeStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                            map["game_user_id"] = PrefData.getStringPrefs(this,PrefData.Key.GAME_USER_ID).toString()
                            map["game_date"] = finalD
                            map["lang_id"] = "1"
                            map["app_id"] = PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_ID).toString()
                            Log.e("params",map.toString())

                        }else{
                            map["game_user_id"] = PrefData.getStringPrefs(this,PrefData.Key.GAME_USER_ID).toString()
                            map["game_date"] = finalD
                            map["lang_id"] = "2"
                            map["app_id"] = PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_ID).toString()
                            Log.e("params",map.toString())
                        }


                        compositeDisposable.add(
                            apiService?.getCrossWord(map)
                                ?.subscribeOn(Schedulers.io())
                                ?.observeOn(AndroidSchedulers.mainThread())
                            !!.subscribe(
                                    { responseModel ->
                                        //  progressLayout?.visibility = View.GONE
                                        if(responseModel!!.data == null){
                                            noCrosswordFound()
                                            //  Toast.makeText(this,"No Crossword Found", Toast.LENGTH_LONG).show()
                                        }else if (responseModel?.status == "true" && responseModel.data !=null) {
                                            if(responseModel.data != null){
                                                if(responseModel?.data.today_leaderboard_status == false){

                                                    if(PrefData.getBooleanPrefs(this, PrefData.Key.ISRuleSHow)){
                                                        val intent = Intent(this@PastPuzzleCrosswordActivity, VargPaheliGameActivity::class.java)
                                                        intent.putExtra("DATE_GAME",finalD)
                                                        startActivity(intent)
                                                        finish()
                                                    }else{
                                                        val intent = Intent(this@PastPuzzleCrosswordActivity, GameRuleActivity::class.java)
                                                        intent.putExtra("DATE_GAME",finalD)
                                                        startActivity(intent)
                                                        finish()
                                                    }

                                                }else{
                                                    gameCompleted()
                                                    // Toast.makeText(this,"Game Already Completed", Toast.LENGTH_LONG).show()
                                                    //Already completed
                                                }
                                            }else{
                                                noCrosswordFound()

                                            }
                                        }
                                    }
                                ) { throwable ->
                                    progressLayout?.visibility = View.GONE

                                    Log.e("HomeActivity", throwable.message ?: "onError")
                                }
                        )

                        //Toast.makeText(applicationContext,finalD,Toast.LENGTH_SHORT).show()
                    } catch (ex: ParseException) {
                        // Handle Exception.
                    }
                }
            }

            R.id.ivFilter -> {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View = inflater.inflate(R.layout.filter_layout, null)
                // create the popup window
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true // lets taps outside the popup also dismiss it

                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAtLocation(ivFilter, 0, 160, 150)

                val ivCancel: ImageView = popupView.findViewById(R.id.ivCancel)
                val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)

               // radioGroup.setOnCheckedChangeListener(radioListener)
                radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.radioBtnAll -> {
                            if(crosswordPastPuzzle !=null && !crosswordPastPuzzle.isEmpty() && outerPastPuzzleMainAdapter !=null){

                                outerPastPuzzleMainAdapter!!.setListData(crosswordPastPuzzle)
                                outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                                popupWindow.dismiss();
                            }
                            //Toast.makeText(applicationContext,"this is toast message",Toast.LENGTH_SHORT).show()
                        }

                        R.id.radioBtnCompleted -> {
                            val completedList:ArrayList<CrosswordArrayItem?> = ArrayList()

                            if(crosswordPastPuzzle !=null && !crosswordPastPuzzle.isEmpty()){
                                for( i in 0 until crosswordPastPuzzle.size ){
                                    var data = crosswordPastPuzzle[i]
                                    if (data?.crossword != null && !data.crossword!!.isEmpty()) {
                                        var data1 = CrosswordArrayItem()
                                        data1.month = data.month

                                        var listPuzzle : ArrayList<CrosswordItem?>?= ArrayList()

                                        for (j in 0 until data.crossword!!.size){
                                            if(data.crossword?.get(j)!!.isComplete == true){
                                                listPuzzle?.add(data.crossword?.get(j)!!)
                                            }
                                        }

                                        data1.crossword = listPuzzle
                                        if(data1.crossword?.size!! > 0){
                                            completedList.add(data1)
                                        }
                                    }

                                    if(outerPastPuzzleMainAdapter !=null){
                                        outerPastPuzzleMainAdapter!!.setListData(completedList)
                                        outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                                    }
                                }

                            }
                            popupWindow.dismiss();
                            //  Toast.makeText(applicationContext,"this is toast message",Toast.LENGTH_SHORT).show()
                        }
                        R.id.radioBtnPending -> {

                            val completedList:ArrayList<CrosswordArrayItem?> = ArrayList()

                            for( i in 0 until crosswordPastPuzzle.size ){
                                var data = crosswordPastPuzzle[i]
                                if (data?.crossword != null && !data.crossword!!.isEmpty()) {
                                    var data1 = CrosswordArrayItem()
                                    data1.month = data.month

                                    var listPuzzle : ArrayList<CrosswordItem?>?= ArrayList()

                                    for (j in 0 until data.crossword!!.size){
                                        if(data.crossword?.get(j)!!.isComplete == false){
                                            listPuzzle?.add(data.crossword?.get(j)!!)
                                        }
                                    }

                                    data1.crossword = listPuzzle
                                    if(data1.crossword?.size!! > 0){
                                        completedList.add(data1)
                                    }
                                }

                                if(outerPastPuzzleMainAdapter !=null){
                                    outerPastPuzzleMainAdapter!!.setListData(completedList)
                                    outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                                }
                            }
                            popupWindow.dismiss();
                        }
                    }
                }


                ivCancel.setOnClickListener {
                    popupWindow.dismiss();
                }

                /* popupView.setOnTouchListener { v, event ->
                     popupWindow.dismiss()
                     true
                 }*/
            }
            R.id.ivChangeLanguage ->{
              //  openLanguageBottomSheet()
            }
            /*R.id.ivChangeLanguage -> {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View = inflater.inflate(R.layout.language_selection_filter, null)
                // create the popup window
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true // lets taps outside the popup also dismiss it

                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAtLocation(ivFilter, 0, 160, 150)

                val ivCancel: ImageView = popupView.findViewById(R.id.ivCancel)
                val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)

                // radioGroup.setOnCheckedChangeListener(radioListener)
                radioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.radioBtnEnglish -> {

                            VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                            PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                            PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                            if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.GAME_USER_ID))) {
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
                            PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                            PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                            if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.GAME_USER_ID))) {
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

    private fun gameCompleted() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.already_completed_game)
        dialog.setCanceledOnTouchOutside(false);

        // set height and width
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT

        /* val tvQuestionInfo = dialog.findViewById<TextView>(R.id.tvQuestionInfo)
        tvQuestionInfo?.text = tvQuestionInfo.toString()*/
        // set to custom layout
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.CENTER
        // find TextView and set Error
        dialog.show()
    }


 /*   var radioListener = fun(radioGroup1: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.radioBtnAll -> {
                outerPastPuzzleMainAdapter!!.setListData(crosswordPastPuzzle)
                outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
            }

            R.id.radioBtnCompleted -> {

                val completedList: java.util.ArrayList<CrosswordArrayItem?> = ArrayList()

                for (i in 0 until crosswordPastPuzzle.size) {
                    var data = crosswordPastPuzzle[i]
                    if (data?.crossword != null && !data.crossword!!.isEmpty()) {
                        var data1 = CrosswordArrayItem()
                        data1.month = data.month

                        var listPuzzle: java.util.ArrayList<CrosswordItem?>? = ArrayList()

                        for (j in 0 until data.crossword!!.size) {
                            if (data.crossword?.get(j)!!.isComplete == true) {
                                listPuzzle?.add(data.crossword?.get(j)!!)
                            }
                        }

                        data1.crossword = listPuzzle
                        if (data1.crossword?.size!! > 0) {
                            completedList.add(data1)
                        }
                    }

                    if (outerPastPuzzleMainAdapter != null) {
                        outerPastPuzzleMainAdapter!!.setListData(completedList)
                        outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                    }
                }
            }

            R.id.radioBtnPending -> {

                val completedList: java.util.ArrayList<CrosswordArrayItem?> = ArrayList()

                for (i in 0 until crosswordPastPuzzle.size) {
                    var data = crosswordPastPuzzle[i]
                    if (data?.crossword != null && !data.crossword!!.isEmpty()) {
                        var data1 = CrosswordArrayItem()
                        data1.month = data.month

                        var listPuzzle: java.util.ArrayList<CrosswordItem?>? = ArrayList()

                        for (j in 0 until data.crossword!!.size) {
                            if (data.crossword?.get(j)!!.isComplete == false) {
                                listPuzzle?.add(data.crossword?.get(j)!!)
                            }
                        }

                        data1.crossword = listPuzzle
                        if (data1.crossword?.size!! > 0) {
                            completedList.add(data1)
                        }
                    }

                    if (outerPastPuzzleMainAdapter != null) {
                        outerPastPuzzleMainAdapter!!.setListData(completedList)
                        outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }*/



    private fun getPastPuzzle(pastPuzzleRequest: PastPuzzleCrossWordRequest?) {
        compositeDisposable.add(
            apiService!!.getPastPuzzle(pastPuzzleRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("get_past_puzzle:", response.toString())
                    if (response?.status == "true") {
                        if(response.data !=null){

                            crosswordPastPuzzle.clear()
                            crosswordPastPuzzle.addAll(response.data.crosswordArray!!)

                            outerPastPuzzleMainAdapter = PastPuzzleOuterAdapter(this,object :
                                RecyclerViewOnClick {
                                override fun onItemClicked(position: Int, viewType: Int, view: View) {

                                    /*   val intent = Intent(this@PastPuzzleCrosswordActivity, MonthlyPastPuzzleCrossWordActivity::class.java)
                                       startActivityForResult(intent, 11110)*/
                                }
                            })
                            outerPastPuzzleMainAdapter!!.setListData(crosswordPastPuzzle)

                            val layoutManager = LinearLayoutManager(this)
                            rvPastPuzzle?.layoutManager = layoutManager
                            rvPastPuzzle?.adapter = outerPastPuzzleMainAdapter
                            outerPastPuzzleMainAdapter?.notifyDataSetChanged()
                        }
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }


    private fun noCrosswordFound() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.no_crossword_found)
        dialog.setCanceledOnTouchOutside(false);

        // set height and width
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT

        /* val tvQuestionInfo = dialog.findViewById<TextView>(R.id.tvQuestionInfo)
        tvQuestionInfo?.text = tvQuestionInfo.toString()*/
        // set to custom layout
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.CENTER
        // find TextView and set Error
        dialog.show()
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

        if(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){
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

            if(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                lLayHindiLang?.isClickable = false

            }else{

                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.GAME_USER_ID))) {
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

            if(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {

                lLayEnglishLang?.isClickable = false

            }else{

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@PastPuzzleCrosswordActivity,PrefData.Key.GAME_USER_ID))) {
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