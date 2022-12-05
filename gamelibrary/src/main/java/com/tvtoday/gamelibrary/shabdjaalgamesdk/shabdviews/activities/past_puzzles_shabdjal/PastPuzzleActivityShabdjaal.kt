package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal

import PrefDataShabdjal
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
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.`interface`.RecyclerViewOnClickShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalGameRuleActivity.GameRuleShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalGameRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalGameResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.VargPaheliGameShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import java.lang.String
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Int
import kotlin.toString


class PastPuzzleActivityShabdjaal : ShabdjalBaseActivity(), View.OnClickListener {
    //deceleration views-------------------------------------------------------
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()

    private var rvPastPuzzle: RecyclerView? = null
    private var outerPastPuzzleMainAdapter: PastPuzzleMainAdapterShabdaajl? = null

    private val shabdjaalPastPuzzle: ArrayList<ShabdjaalArrayItem?> = ArrayList()

    private var ivCalendar: ImageView? = null
    private var ivFilter: ImageView? = null
    private var ivHome: ImageView? = null
    private var ivLangChange: ImageView? = null
    //

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try{
            setContentView(R.layout.activity_past_puzzle_shabdjaal)

            intiViews()

            if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                PrefDataShabdjal.getAppLanguageStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                val pastPuzzle = PastPuzzleShabdjaalRequest()
                pastPuzzle.gameUserId = PrefDataShabdjal.getStringPrefs(
                    this@PastPuzzleActivityShabdjaal,
                    PrefDataShabdjal.Key.GAME_USER_ID)
                pastPuzzle.lang_id = "1"
                pastPuzzle.app_id = PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                getPastPuzzle(pastPuzzle)
            }else{
                val pastPuzzle = PastPuzzleShabdjaalRequest()
                pastPuzzle.gameUserId = PrefDataShabdjal.getStringPrefs(
                    this@PastPuzzleActivityShabdjaal,
                    PrefDataShabdjal.Key.GAME_USER_ID)
                pastPuzzle.lang_id = "2"
                pastPuzzle.app_id = PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                getPastPuzzle(pastPuzzle)
            }
        }catch (e:Exception){

        }


    }

    override fun onResume() {
        super.onResume()

        /*  shabdjaalPastPuzzle.clear()
          val pastPuzzle = PastPuzzleShabdjaalRequest()
          pastPuzzle.gameUserId = PrefDataShabdjal.getStringPrefs(
              this@PastPuzzleActivityShabdjaal,
              PrefDataShabdjal.Key.GAME_USER_ID)
          getPastPuzzle(pastPuzzle)*/
    }

    private fun intiViews() {
        //initialisation views here------------------
        rvPastPuzzle = findViewById(R.id.rvPastPuzzle)

        ivFilter = findViewById(R.id.ivFilter)
        ivFilter?.setOnClickListener(this)

        ivCalendar = findViewById(R.id.ivCalendar)
        ivCalendar?.setOnClickListener(this@PastPuzzleActivityShabdjaal)

        ivLangChange = findViewById(R.id.ivLangChange)
        ivLangChange?.setOnClickListener(this)

        ivHome = findViewById(R.id.ivHome)
        ivHome?.setOnClickListener {
            finish()
        }

        /*val outerPastPuzzleModel = ArrayList<PastPuzzleOuterModelShabdjaal>()
        outerPastPuzzleMainAdapter = PastPuzzleMainAdapterShabdaajl(this, outerPastPuzzleModel, object :RecyclerViewOnClickShabdjal{
            override fun onItemClicked(position: Int, viewType: Int, view: View) {

                val intent = Intent(this@PastPuzzleActivityShabdjaal, MonthlyPuzzleActivity::class.java)
                startActivityForResult(intent, 220)
            }
        })*/

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")){

            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llHindi?.setBackgroundColor(Color.BLACK)
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

            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_ENGLISH)

            if(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));

                ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))) {
                    val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

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

            if(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = "hi"
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))) {
                    val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == 220) {
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
                var selectedDate: Int
                var calendarView: CalendarView = dialog.findViewById(R.id.calendarView)
                var calendar: Calendar? = null
                calendar = Calendar.getInstance()
                val cyear: Int = calendar.get(Calendar.YEAR)
                val cmonth: Int = calendar.get(Calendar.MONTH)
                val cday: Int = calendar.get(Calendar.DAY_OF_MONTH)

                calendarView.maxDate = System.currentTimeMillis()

                calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                    // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
                    // val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
                    dialog.dismiss()
                    //val initDate: Date = SimpleDateFormat("dd/MM/yyyy").parse("10/12/2016")
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

                        val shabdjaalRequest = ShabdjaalGameRequest()

                        if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                            PrefDataShabdjal.getAppLanguageStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                            // shabdjaalRequest.appId = "1"
                            shabdjaalRequest.game_user_id =
                                PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID)
                                    .toString()
                            shabdjaalRequest.game_date = finalD
                            shabdjaalRequest.lang_id= "1"
                            shabdjaalRequest.app_id = PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                            Log.e("params:", shabdjaalRequest.toString())
                        }else{
                            // shabdjaalRequest.appId = "1"
                            shabdjaalRequest.game_user_id =
                                PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID)
                                    .toString()
                            shabdjaalRequest.game_date = finalD
                            shabdjaalRequest.lang_id = "2"
                            shabdjaalRequest.app_id = PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                            Log.e("params:", shabdjaalRequest.toString())
                        }

                        compositeDisposable.add(
                            apiService!!.getShabdjaalGame(shabdjaalRequest)
                                ?.subscribeOn(Schedulers.io())
                                ?.observeOn(AndroidSchedulers.mainThread())
                            !!.subscribe(fun(response: ShabdjaalGameResponse?) {
                                    Log.e("game_response:", response.toString())
                                    if (response?.status == "true" && response.data != null) {

                                        if (response.data == null) {
                                            noShabdjaalFound()
                                            //  Toast.makeText(this,"No Crossword Found", Toast.LENGTH_LONG).show()
                                        } else if (response.data != null) {
                                            if (response.data.today_leaderboard_status) {
                                                answerCompletedShabdjaal()
                                            } else {
                                                if (PrefDataShabdjal.getBooleanPrefs(
                                                        this@PastPuzzleActivityShabdjaal,
                                                        PrefDataShabdjal.Key.ISRuleSHow
                                                    )
                                                ) {
                                                    val intent = Intent(
                                                        this@PastPuzzleActivityShabdjaal,
                                                        ShabdjaalPlayGameActivity::class.java
                                                    )
                                                    intent.putExtra("Date", finalD)
                                                    startActivity(intent)
                                                    finish()
                                                } else {
                                                    val intent = Intent(
                                                        this@PastPuzzleActivityShabdjaal,
                                                        GameRuleShabdActivity::class.java
                                                    )
                                                    intent.putExtra("Date", finalD)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                            }
                                        } else {

                                        }

                                        Log.e(
                                            "leaderBoardStatus::",
                                            response.data.today_leaderboard_status.toString()
                                        )

                                        /*if(response.data.today_leaderboard_status){
                                            CommonUtilsShabdjal.performIntentFinish(this, WaitingShabdActivity::class.java)
                                            return
                                        }*/

                                    } else if (response?.status == "false") {
                                        noShabdjaalFound()
                                    }
                                }) { throwable ->
                                    Log.d("Error", "" + throwable)
                                })

                        //Toast.makeText(applicationContext,finalD,Toast.LENGTH_SHORT).show()
                    } catch (ex: ParseException) {
                        // Handle Exception.
                    }
                }
            }


            R.id.ivLangChange ->{
               // openLanguageBottomSheet()
            }

            R.id.ivFilter -> {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View =
                    inflater.inflate(R.layout.past_puzzle_filter_popup_shabdjaal, null)
                // create the popup window
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true // lets taps outside the popup also dismiss it

                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAtLocation(ivFilter, 0, 160, 150)

                val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)
                radioGroup.setOnCheckedChangeListener(radioListener)

                val ivCancel: ImageView = popupView.findViewById(R.id.ivCancel)
                ivCancel.setOnClickListener {
                    popupWindow.dismiss()
                }
                /* popupView.setOnTouchListener { v, event ->
                     popupWindow.dismiss()
                     true
                 }*/
            }
        }
    }

    private fun answerCompletedShabdjaal() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.answer_completed_shabdjaal)
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

    private fun noShabdjaalFound() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.no_shabdjaal_found)

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

    var ratdioLang = fun(radioGroup1: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.radioBtnEnglish -> {
                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))){
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

            R.id.radioBtnHindi -> {
                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))){
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
    }


    var radioListener = fun(radioGroup1: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.radioBtnAll -> {

                if(shabdjaalPastPuzzle != null && !shabdjaalPastPuzzle.isEmpty()){
                    if(outerPastPuzzleMainAdapter != null){
                        outerPastPuzzleMainAdapter!!.setListData(shabdjaalPastPuzzle)
                        outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                    }
                }
            }

            R.id.radioBtnCompleted -> {

                val completedList: ArrayList<ShabdjaalArrayItem?> = ArrayList()

                if(shabdjaalPastPuzzle !=null && !shabdjaalPastPuzzle.isEmpty()){
                    for (i in 0 until shabdjaalPastPuzzle.size) {
                        var data = shabdjaalPastPuzzle[i]
                        if (data?.shabdjaal != null && !data.shabdjaal!!.isEmpty()) {
                            var data1 = ShabdjaalArrayItem()
                            data1.month = data.month

                            var listPuzzle: ArrayList<ShabdjaalItem?>? = ArrayList()

                            for (j in 0 until data.shabdjaal!!.size) {
                                if (data.shabdjaal?.get(j)!!.isComplete == true) {
                                    listPuzzle?.add(data.shabdjaal?.get(j)!!)
                                }
                            }

                            data1.shabdjaal = listPuzzle
                            if (data1.shabdjaal?.size!! > 0) {
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
            R.id.radioBtnPending -> {

                val pendingList: ArrayList<ShabdjaalArrayItem?> = ArrayList()

                if(shabdjaalPastPuzzle !=null && !shabdjaalPastPuzzle.isEmpty()){
                    for (i in 0 until shabdjaalPastPuzzle.size) {
                        var data = shabdjaalPastPuzzle[i]
                        if (data?.shabdjaal != null && !data.shabdjaal!!.isEmpty()) {
                            var data1 = ShabdjaalArrayItem()
                            data1.month = data.month

                            var listPuzzle: ArrayList<ShabdjaalItem?>? = ArrayList()

                            for (j in 0 until data.shabdjaal!!.size) {
                                if (data.shabdjaal?.get(j)!!.isComplete == false) {
                                    listPuzzle?.add(data.shabdjaal?.get(j)!!)
                                }
                            }

                            data1.shabdjaal = listPuzzle
                            if (data1.shabdjaal?.size!! > 0) {
                                pendingList.add(data1)
                            }
                        }

                        if (outerPastPuzzleMainAdapter != null) {
                            outerPastPuzzleMainAdapter!!.setListData(pendingList)
                            outerPastPuzzleMainAdapter!!.notifyDataSetChanged()
                        }
                    }
                }

            }
        }
    }

    var radioListenerLanguage = fun(radioGroup1: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.radioBtnHindi -> {

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))){
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

            R.id.radioBtnEnglish -> {

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))){
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
    }


    private fun getPastPuzzle(addUserRequest: PastPuzzleShabdjaalRequest?) {
        compositeDisposable.add(
            apiService!!.getPastPuzzle(addUserRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("get_past_puzzle:", response.toString())
                    if (response?.status == "true") {
                        if (response.data != null) {

                            shabdjaalPastPuzzle.addAll(response.data.shabdjaalArray!!)

                            outerPastPuzzleMainAdapter = PastPuzzleMainAdapterShabdaajl(
                                this,
                                object : RecyclerViewOnClickShabdjal {
                                    override fun onItemClicked(
                                        position: Int,
                                        viewType: Int,
                                        view: View
                                    ) {
                                        /* val intent = Intent(this@PastPuzzleActivityShabdjaal, MonthlyPuzzleActivity::class.java)
                                         startActivityForResult(intent, 220)*/
                                    }
                                })

                            outerPastPuzzleMainAdapter?.setListData(shabdjaalPastPuzzle)

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

        if(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")){
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

            if(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                lLayHindiLang.isClickable = false
            }else{
                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))){
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

            if(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("english")) {
                lLayEnglishLang.isClickable = false
            }else{
                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()


                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@PastPuzzleActivityShabdjaal,PrefDataShabdjal.Key.GAME_USER_ID))){
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