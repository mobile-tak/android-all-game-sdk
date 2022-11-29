package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

import com.tvtoday.crosswordhindi.controller.utils.CommonUtils

import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity.LeaderBoardActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.nav_drawer_tutorial.NavDrawerTutorialActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.CorsswordBoradItem
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.CrosswordPlayItem
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`.RecyclerViewOnClick
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.Constants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.IntentConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.enum.ClickedItem
import com.tvtoday.gamelibrary.crosswordgamesdk.model.submitGame.SubmitGameReuquest
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.nav_drawer_adapter.NavDrawerAdapter
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.nav_drawer_adapter.NavDrawerModel
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.varg_paheli_settingActivity.VargPaheliSettingActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.waitingactivity.WaitingActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EnglishVargPahleiGameActivity : VargPaheliBaseActivity(), View.OnClickListener{

    private var mRewardedAd: RewardedAd? = null
    lateinit var mAdView : AdView
    private var mediaPlayer : MediaPlayer? = null
    private var mediaPlayerError : MediaPlayer? = null
    private var mediaPlayerCorrectWord : MediaPlayer? = null
    private var mediaPlayerGameComplete : MediaPlayer? = null

    //api services-------------------------------------------------------

    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()
    //linearLayout ------------------------------------------------------
    private var lLayNextQuestion: LinearLayout? = null
    private var lLayPreviousQuestion: LinearLayout? = null
    private var rl_hint: LinearLayout?=null
    private var lLayBackBtn : LinearLayout? =null
    private var lLayQuestionBtn : LinearLayout?=null
    private var lLayShowAnswer : LinearLayout?=null
    private var lLayProgressBar : LinearLayout?=null

    private var mInterstitialAd: InterstitialAd? = null
    private var cursorIndex: Int = 0

    private var isHintPressed  = false

    //addView
    private var adView: AdView?=null

    //navigation Drawer
    private lateinit var my_drawer_layout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView

    private var rvNavDrawer: RecyclerView?=null

    //imageView-----------------------------
    private var ivNext: ImageView?=null
    private var ivBack: ImageView?=null
    private var ivHamburger: ImageView?=null
    private var ivHomeBtn: ImageView?=null
    private var ivChangeLanguage: ImageView?=null

    private var ivSettingGame : ImageView?=null

    private var ivLeaderBoard : ImageView?=null

    private var ivUserName: TextView?=null

    private var date_for_puzzle :String=""

    //answer Array
    var gson = Gson()
    var questionIndex = 0

    var playModel : CrosswordPlayItem? =null
    var clicked_Item : ClickedItem?=null

    //arrayList
    private var crosswordPlayItem: java.util.ArrayList<CrosswordPlayItem> = java.util.ArrayList()
    private var crossWordBoardItem: java.util.ArrayList<CorsswordBoradItem> = java.util.ArrayList()

    //strings
    private var minute: String? = ""
    private  var second:String? = ""
    private var mTimingRunning = false
    private var pauseOffset: Long = 0
    private  var minutes:Long = 0
    private  var seconds:Long = 0
    private var answerImage :String = ""
    private var gameUserId :String = ""
    private var mGameUsrId:String = ""
    private var mGameEndTime : String = ""
    private var mGameStatus:String = ""
    private var hintCount = 0
    //textView-----------------------------
    private var question: AppCompatTextView? = null
    private var tv_timer_text : Chronometer?=null


    private var tvQ: TextView? = null
    private var tvW: TextView? = null
    private var tv_E: TextView? = null
    private var tv_R: TextView? = null
    private var tv_T: TextView? = null
    private var tv_Y: TextView? = null
    private var tv_U: TextView? = null
    private var tv_I: TextView? = null
    private var tv_O: TextView? = null
    private var tv_P: TextView? = null
    private var tv_A: TextView? = null
    private var tvS: TextView? = null
    private var tv_D: TextView? = null
    private var tv_F: TextView? = null
    private var tv_G: TextView? = null
    private var tv_H: TextView? = null
    private var tv_J: TextView? = null
    private var tv_K: TextView? = null
    private var tv_L: TextView? = null
    private var tv_Z: TextView? = null
    private var tv_X: TextView? = null
    private var tv_C: TextView? = null
    private var tv_V: TextView? = null
    private var tv_B: TextView? = null
    private var tv_N: TextView? = null
    private var tv_M: TextView? = null

    private var gameDate:String?=null

    private var vargPahleiAdapter : EnglishVargPhaeliAdapter?=null

    lateinit var gridView: GridView

    private var keyArray = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

    private var buttonsId = intArrayOf(
        R.id.tvQ,
        R.id.tvW,
        R.id.tv_E,
        R.id.tv_R,
        R.id.tv_T,
        R.id.tv_Y,
        R.id.tv_U,
        R.id.tv_I,
        R.id.tv_O,
        R.id.tv_P,
        R.id.tv_A,
        R.id.tvS,
        R.id.tv_D,
        R.id.tv_F,
        R.id.tv_L,
        R.id.tv_H,
        R.id.tv_J,
        R.id.tv_K,
        R.id.tv_Z,
        R.id.tv_X,
        R.id.tv_C,
        R.id.tv_V,
        R.id.tv_B,
        R.id.tv_N,
        R.id.tv_M,
    )

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    private var tvGameDate:TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_english_varg_pahlei_game)
        }catch (e:Exception){

        }

        PrefData.setBooleanPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.IS_GUEST_USER,false)

        mediaPlayer = MediaPlayer.create(this,R.raw.peaceful_garden_healing)
        mediaPlayerError  = MediaPlayer.create(this,R.raw.for_error_music)
        mediaPlayerCorrectWord  = MediaPlayer.create(this,R.raw.small_applause)
        mediaPlayerGameComplete  = MediaPlayer.create(this,R.raw.game_complete)

        interstitialAdd()
        initRewardAdd()

        if(PrefData.getSoundState(this@EnglishVargPahleiGameActivity)){
            playSound()
        }else{
            mediaPlayer?.pause()
        }
        /*if(PrefData.getStringMusicPrefs(this@VargPaheliGameActivity,PrefData.Key.IS_MUSIC_PLAYING) == null || PrefData.getStringMusicPrefs(this@VargPaheliGameActivity,PrefData.Key.IS_MUSIC_PLAYING).equals("1")) {
        }else{
            mediaPlayer?.pause()
            mediaPlayerGameComplete?.pause()
            mediaPlayerCorrectWord?.pause()
            mediaPlayerError?.pause()
        }*/

        try {
            date_for_puzzle =intent.getStringExtra("DATE_GAME").toString()
            Log.e("eng_date_check", date_for_puzzle)
            initViews()

            getCrossWord()
            navDrawerSetup()
        } catch (e: Exception) {
            Log.e("get_cross_word", e.message.toString())
            //e.printStackTrace()
        }
    }

    private fun gameTimer() {
        tv_timer_text?.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            if (SystemClock.elapsedRealtime() - tv_timer_text!!.base >= getTimeTillMidnight()) {
                tv_timer_text?.base = SystemClock.elapsedRealtime()
                // endGame(Constants.LOSS, "10000", )
                endGame(gameUserId, (pauseOffset / 1000).toString(), Constants.LOSS)
                Toast.makeText(this, "Game Finished!", Toast.LENGTH_SHORT).show()
            }
        }
        startTimer()
    }

    private fun startTimer() {
        if (!mTimingRunning) {
            tv_timer_text!!.base = SystemClock.elapsedRealtime() - pauseOffset
            tv_timer_text!!.start()
            mTimingRunning = true
        }
    }

    override fun onStop() {
        super.onStop()
        /* if (mediaPlayer != null) {
            // mediaPlayer!!.release()
             mediaPlayer!!.stop()
            // mediaPlayer = null
         }*/
    }

    private fun getTimeTillMidnight():Long{
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        c.add(Calendar.DATE,1)
        val howMany: Long = c.getTimeInMillis() - System.currentTimeMillis()
        return howMany
    }

    /* override fun onStart() {
         super.onStart()

     }*/

    override fun onResume() {
        super.onResume()
        if(PrefData.getSoundState(this@EnglishVargPahleiGameActivity)){
            mediaPlayer?.start()
        }else{
            mediaPlayer?.pause()
        }

        if(gameDate != null){
            gameTimer()
        }
    }

    override fun onPause() {
        super.onPause()
        try{
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }

            if(clicked_Item != ClickedItem.HINT){
                if (mTimingRunning) {
                    tv_timer_text!!.stop()
                    pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                    mTimingRunning = false
                    minutes = TimeUnit.MILLISECONDS.toMinutes(pauseOffset)
                    seconds = TimeUnit.MILLISECONDS.toSeconds(pauseOffset) % 60
                    minute = String.format("%02d", minutes)
                    second = String.format("%02d", seconds)
                }
            }
        }catch (e:Exception){

        }


    }


    override fun onDestroy() {
        try{
            mediaPlayer?.stop();
            mediaPlayer?.release();
            if(gameDate != null){
                CommonUtils.saveGame(this,crossWordBoardItem, PrefData.Key.ENGLISH+gameDate.toString())
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.QUESTION_NO_ENGLISH_GAME+gameDate.toString(),questionIndex.toString())

                if(tv_timer_text != null){
                    pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                    PrefData.setLongPrefs(applicationContext, PrefData.Key.ENGLISH+PrefData.Key.PAUSE_OFF_SET+gameDate, pauseOffset)
                }
            }
            tv_timer_text = null
            super.onDestroy()
        }catch (e:Exception){

        }

    }

    override fun onUserLeaveHint() {
        if(gameDate != null){
            CommonUtils.saveGame(this,crossWordBoardItem, PrefData.Key.ENGLISH+gameDate.toString())
            PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.QUESTION_NO_ENGLISH_GAME+gameDate.toString(),questionIndex.toString())

            if(tv_timer_text != null){
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                PrefData.setLongPrefs(applicationContext, PrefData.Key.ENGLISH+PrefData.Key.PAUSE_OFF_SET+gameDate, pauseOffset)
            }
        }
        super.onUserLeaveHint()
    }


    fun  playSound() {
        if(mediaPlayer != null){
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }
    }


    private fun getCrossWord() {

        val map = HashMap<String, String>()
        var date:String? = ""
        var userId:String? = ""
        if(!date_for_puzzle.equals("null") && !TextUtils.isEmpty(date_for_puzzle) ){
            date = date_for_puzzle
        }

        if(TextUtils.isEmpty(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID).toString())){
            userId = PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID).toString()
        }

        map["game_user_id"] = PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID).toString()
        map["game_date"] = date.toString()
        map["lang_id"] = "2"
        if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_ID).toString().equals("null")){
            map["app_id"] =""
        }else{
            map["app_id"] = PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_ID).toString()
        }
        Log.e("params",map.toString())
        compositeDisposable.add(
            apiService?.getCrossWord(map)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe(
                    { responseModel ->
                        try{
                            Log.e("Response", responseModel?.data.toString())
                            if (responseModel?.status == "true" && responseModel.data !=null) {

                                date_for_puzzle = responseModel.data.gameDate.toString()
                                if(TextUtils.isEmpty(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_ID))) { try{
                                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                    val formatter = SimpleDateFormat("dd-MM-yyyy")
                                    val dateFormated = formatter.format(parser.parse(date_for_puzzle))
                                    tvGameDate?.text = dateFormated
                                }catch (e: ParseException){
                                }
                                }

                                if(responseModel?.data?.gameDate != null){
                                    if(date_for_puzzle.equals("null") || TextUtils.isEmpty(date_for_puzzle) ){

                                        date_for_puzzle = responseModel.data.gameDate

                                        lLayPreviousQuestion?.isClickable = true
                                        lLayNextQuestion?.isClickable = true

                                        /*  var dateFormated =""
                                         // dateFormated = date_for_puzzle.toString()

                                              val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                              val formatter = SimpleDateFormat("dd-MM-yyyy")
                                              try {
                                                  dateFormated = formatter.format(parser.parse(date_for_puzzle))
                                              } catch (e: ParseException) {

                                              }
                                              tvGameDate?.text = dateFormated*/
                                    }
                                    gameDate = responseModel.data.gameDate


                                    if(PrefData.getStringPrefs(applicationContext, PrefData.Key.ENGLISH+gameDate.toString()+PrefData.Key.IS_DONE) != null){
                                        val intent = Intent(this, WaitingActivity::class.java);
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        lLayProgressBar?.visibility = View.GONE
                                        pauseOffset = PrefData.getLongPrefs(applicationContext, PrefData.Key.ENGLISH+PrefData.Key.PAUSE_OFF_SET+gameDate, 0)
                                        startTimer()
                                        answerImage = responseModel.data.imageUrl.toString()
                                        Log.i("img:", answerImage.toString())

                                        crossWordBoardItem.addAll(responseModel.data.corsswordBoard)
                                        crosswordPlayItem.addAll(responseModel.data.crosswordPlay)
                                        question?.text = crosswordPlayItem[0].hint

                                        Log.i("crossBoard:", crossWordBoardItem.toString())
                                        Log.i("crossBoardPlayItem:", crosswordPlayItem.toString())

                                        //playGameSetup(crosswordPlayItem?.get(0)!!)
                                        if(PrefData.getStringPrefs(applicationContext,PrefData.Key.ENGLISH+gameDate.toString())  != null){
                                            crossWordBoardItem.clear()
                                            crossWordBoardItem.addAll(CommonUtils.getGame(PrefData.getStringPrefs(this,PrefData.Key.ENGLISH+gameDate.toString()).toString()))
                                            vargPahleiAdapter = EnglishVargPhaeliAdapter(this, crossWordBoardItem)
                                            gridView.adapter = vargPahleiAdapter
                                            vargPahleiAdapter!!.notifyDataSetChanged()

                                            gridView.onItemClickListener =
                                                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                                                    run {
                                                        Log.d("batme", i.toString())
                                                        higlightSelectedHint(i)
                                                    }
                                                }
                                        } else {
                                            vargPahleiAdapter = EnglishVargPhaeliAdapter(this, crossWordBoardItem)
                                            gridView.adapter = vargPahleiAdapter
                                            vargPahleiAdapter!!.notifyDataSetChanged()

                                            gridView.onItemClickListener =
                                                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                                                    run {
                                                        Log.d("batme", i.toString())
                                                        higlightSelectedHint(i)
                                                    }
                                                }
                                        }
                                        try {
                                            arrOfLtterDo()
                                        }catch (e:Exception){
                                            e.printStackTrace()
                                        }
                                        if( !TextUtils.isEmpty(PrefData.getStringPrefs(this, PrefData.Key.QUESTION_NO_ENGLISH_GAME+gameDate.toString()))){
                                            questionIndex = PrefData.getStringPrefs(this, PrefData.Key.QUESTION_NO_ENGLISH_GAME+gameDate.toString())!!.toInt();
                                            Log.e("QUESTIO_NO",questionIndex.toString())
                                            setQuestionOnRestart()

                                        }else{
                                            playGameSetup(crosswordPlayItem.first())
                                        }
                                    }
                                }
                                else{
                                    noCrosswordFound()
                                    //No Crossword found
                                    // Toast.makeText(this,"Crossword Not Found.", Toast.LENGTH_LONG).show()
                                }
                            }
                            else if(responseModel?.status == "false"){
                                // noCrosswordFound()
                                val intent = Intent(this, PastPuzzleCrosswordActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                ) { throwable ->
                    Log.e("HomeActivity", throwable.message ?: "onError")
                }
        )
    }

    fun  setQuestionOnRestart(){
        ivBack?.setColorFilter(resources.getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        // if(questionIndex < crosswordPlayItem.size-1){
        if(questionIndex < crosswordPlayItem.size){
            question?.text = crosswordPlayItem[questionIndex].hint.toString()
            playGameSetup(crosswordPlayItem[questionIndex])
        }

        if(questionIndex == 0){
            lLayPreviousQuestion?.isClickable = false
        }

        if(questionIndex == crosswordPlayItem.size -1){
            lLayNextQuestion?.isClickable  = false
            DrawableCompat.setTint(ivNext?.drawable!!, ContextCompat.getColor(this, R.color.color_4c4c4c));
            ivNext?.background?.setColorFilter(Color.parseColor("#4c4c4c"),PorterDuff.Mode.SRC_OVER)
            lLayNextQuestion?.background = ContextCompat.getDrawable(this, R.drawable.curve_radius);
        }
        vargPahleiAdapter!!.notifyDataSetChanged()
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



    private fun initViews() {
        gameUserId = PrefData.getStringPrefs(this, PrefData.Key.GAME_USER_ID).toString()
        Log.d("email", gameUserId)

        my_drawer_layout = findViewById(R.id.my_drawer_layout)
        navView = findViewById(R.id.navView)
        navView.bringToFront();
        question = findViewById(R.id.question)
        lLayShowAnswer = findViewById(R.id.lLayShowAnswer)
        lLayShowAnswer?.setOnClickListener(this)

        rvNavDrawer = findViewById(R.id.rvNavDrawer)

        lLayProgressBar = findViewById(R.id.lLayProgressBar)

        ivHamburger = findViewById(R.id.ivHamburger)
        ivHamburger?.setOnClickListener(this)

        rl_hint= findViewById(R.id.rl_hint)
        rl_hint?.setOnClickListener(this)
        tv_timer_text =findViewById(R.id.tv_timer_text)

        ivChangeLanguage =findViewById(R.id.ivChangeLanguage)
        ivChangeLanguage?.setOnClickListener(this)

        ivHomeBtn =findViewById(R.id.ivHomeBtn)
        ivHomeBtn?.setOnClickListener(this)

        ivNext = findViewById(R.id.ivNext)
        ivBack = findViewById(R.id.ivBack)
        tvGameDate = findViewById(R.id.tvGameDate)

        ivSettingGame = findViewById(R.id.ivSettingGame)
        ivSettingGame?.setOnClickListener(this)

        ivLeaderBoard = findViewById(R.id.ivLeaderBoard)
        ivLeaderBoard?.setOnClickListener(this)

        ivUserName = findViewById(R.id.ivUserName)
        ivUserName?.text =  PrefData.getStringPrefs(this, PrefData.Key.NAME)
        //linearLayout button for back button--------------
        lLayBackBtn = findViewById(R.id.lLayBackBtn)
        lLayBackBtn?.setOnClickListener(this)

        //linearLayout button for question btn--------------
        lLayQuestionBtn = findViewById(R.id.lLayQuestionBtn)
        lLayQuestionBtn?.setOnClickListener(this)

        //linearLayout button for next question
        lLayNextQuestion = findViewById(R.id.lLayNextQuestion)
        lLayNextQuestion?.setOnClickListener(this)

        //linearLayout button for previous question
        lLayPreviousQuestion = findViewById(R.id.lLayPreviousQuestion)
        lLayPreviousQuestion?.setOnClickListener(this)

        rvNavDrawer = findViewById(R.id.rvNavDrawer)

        tvQ = findViewById(R.id.tvQ)
        tvQ?.setOnClickListener(this)

        tvW = findViewById(R.id.tvW)
        tvW?.setOnClickListener(this)

        tv_E = findViewById(R.id.tv_E)
        tv_E?.setOnClickListener(this)

        tv_R = findViewById(R.id.tv_R)
        tv_R?.setOnClickListener(this)

        tv_T = findViewById(R.id.tv_T)
        tv_T?.setOnClickListener(this)

        tv_Y = findViewById(R.id.tv_Y)
        tv_Y?.setOnClickListener(this)

        tv_U = findViewById(R.id.tv_U)
        tv_U?.setOnClickListener(this)

        tv_I = findViewById(R.id.tv_I)
        tv_I?.setOnClickListener(this)

        tv_O = findViewById(R.id.tv_O)
        tv_O?.setOnClickListener(this)

        tv_P = findViewById(R.id.tv_P)
        tv_P?.setOnClickListener(this)

        tv_A = findViewById(R.id.tv_A)
        tv_A?.setOnClickListener(this)

        tvS = findViewById(R.id.tvS)
        tvS?.setOnClickListener(this)

        tv_D = findViewById(R.id.tv_D)
        tv_D?.setOnClickListener(this)

        tv_F = findViewById(R.id.tv_F)
        tv_F?.setOnClickListener(this)

        tv_G = findViewById(R.id.tv_G)
        tv_G?.setOnClickListener(this)

        tv_H = findViewById(R.id.tv_H)
        tv_H?.setOnClickListener(this)

        tv_J = findViewById(R.id.tv_J)
        tv_J?.setOnClickListener(this)

        tv_K = findViewById(R.id.tv_K)
        tv_K?.setOnClickListener(this)

        tv_L = findViewById(R.id.tv_L)
        tv_L?.setOnClickListener(this)

        tv_Z = findViewById(R.id.tv_Z)
        tv_Z?.setOnClickListener(this)

        tv_X = findViewById(R.id.tv_X)
        tv_X?.setOnClickListener(this)

        tv_C = findViewById(R.id.tv_C)
        tv_C?.setOnClickListener(this)

        tv_V = findViewById(R.id.tv_V)
        tv_V?.setOnClickListener(this)

        tv_B = findViewById(R.id.tv_B)
        tv_B?.setOnClickListener(this)

        tv_N = findViewById(R.id.tv_N)
        tv_N?.setOnClickListener(this)

        tv_M = findViewById(R.id.tv_M)
        tv_M?.setOnClickListener(this)


        gridView = findViewById(R.id.gridView)


       /* adView = findViewById(R.id.adView)
        adView!!.setAdSize(AdSize.BANNER)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)*/

        var adView = AdView(this@EnglishVargPahleiGameActivity)
        adView.adUnitId = PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_BANNER_AD_ID)!!.toString()
        adView.setAdSize(AdSize.BANNER)
        val adRequest = AdRequest.Builder().build()
        findViewById<LinearLayout?>(R.id.lLAyBannerAd).addView(adView)
        adView.loadAd(adRequest)


        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

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

            if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));
                //  llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID))) {
                    val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                    intent.putExtra("DATE_GAME",date_for_puzzle)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                    intent.putExtra("DATE_GAME",date_for_puzzle)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }


        llHindi?.setOnClickListener {
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_HINDI)

            if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else {

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));
                // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));

                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefData.setStringPrefs(
                    this@EnglishVargPahleiGameActivity,
                    PrefData.Key.CROSSWORD_LANGAUGE,
                    PrefData.Key.HINDI
                )
                PrefData.setStringPrefs(
                    this@EnglishVargPahleiGameActivity,
                    PrefData.Key.CROSSWORD_APP_LANGUAGE,
                    PrefData.Key.HINDI
                )

                if (!TextUtils.isEmpty(
                        PrefData.getStringPrefs(
                            this@EnglishVargPahleiGameActivity,
                            PrefData.Key.GAME_USER_ID
                        )
                    )
                ) {
                    val intent = Intent(this, VargPaheliGameActivity::class.java)
                    intent.putExtra("DATE_GAME", date_for_puzzle)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, VargPaheliGameActivity::class.java)
                    intent.putExtra("DATE_GAME", date_for_puzzle)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }

      /*  mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adUnitId = PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_BANNER_AD_ID)!!
        mAdView.loadAd(adRequest)*/
    }

    private fun navDrawerSetup() {
        val nav_drawer_list = ArrayList<NavDrawerModel>()
        nav_drawer_list.add(NavDrawerModel("Hi,"))
        nav_drawer_list.add(NavDrawerModel("New games from TV Today"))
        nav_drawer_list.add(NavDrawerModel("Tutorial"))
        nav_drawer_list.add(NavDrawerModel("Share – Message with Share"))
        nav_drawer_list.add(NavDrawerModel("Settings"))
        nav_drawer_list.add(NavDrawerModel("Privacy policy"))
        nav_drawer_list.add(NavDrawerModel("Terms & uses"))
        // nav_drawer_list.add(NavDrawerModel("LogOut"))

        rvNavDrawer?.adapter = NavDrawerAdapter(this, nav_drawer_list, object :
            RecyclerViewOnClick {
            override fun onItemClicked(position: Int, viewType: Int, view: View) {
                when (viewType) {

                    11 ->{
                        my_drawer_layout.closeDrawer(navView)
                    }
                }
            }
        })
    }

    private fun playGameSetup( crossWordPlayModel : CrosswordPlayItem) {

        try {
            playModel= crossWordPlayModel
            cursorIndex = 0

            vargPahleiAdapter!!.currentAnswerIndexes.clear()
            val noOfCell :Int
            if(crossWordPlayModel.numberOfCell!!.isNotEmpty()){
                noOfCell =   crossWordPlayModel.numberOfCell.toInt()
            }else{
                noOfCell = 0
            }

            val cellRow: Int = crossWordPlayModel.cellRow!!.toInt() ?:0
            val cellColumn: Int = crossWordPlayModel.cellColumn!!.toInt() ?:0

            if (cellRow > 0 && cellColumn > 0) {
                vargPahleiAdapter!!.currentCellIndex = ((cellColumn - 1) + ((cellRow - 1) * 10))

                if(vargPahleiAdapter!!.currentCellIndex < crossWordBoardItem.size){
                    if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex] != null
                        && crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value == arrOfLetter[vargPahleiAdapter!!.currentCellIndex]){
                        if(crossWordPlayModel.hintType == "across"){
                            while (cursorIndex < playModel?.numberOfCell!!.toInt().minus(1) && crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value
                                == arrOfLetter[vargPahleiAdapter!!.currentCellIndex]){
                                vargPahleiAdapter!!.currentCellIndex = vargPahleiAdapter!!.currentCellIndex +1
                                cursorIndex+=1
                            }
                        }else{
                            while (cursorIndex < playModel?.numberOfCell!!.toInt().minus(1) && crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value
                                == arrOfLetter[vargPahleiAdapter!!.currentCellIndex]){
                                vargPahleiAdapter!!.currentCellIndex = vargPahleiAdapter!!.currentCellIndex +10
                                cursorIndex+=1
                            }
                        }
                    }
                }
            }

            if (crossWordPlayModel.hintType == "across") {
                /* val  answer = crosswordPlayItem[questionIndex].splitAcross!!

                 val  answerList = answer.split(",").toTypedArray()
                 Log.i("answer:" , answerList.toString())
                 //val finalKeyArray = getSubArray(keyArray, 1, answerList.size)

                 val finalKeyArray = keyArray.toList()
                     .subList(0, keyArray.size - answerList.size)
                     .toTypedArray()
                 Log.i("finakKeyArray1" , finalKeyArray.contentToString())

                 var newArr =  finalKeyArray.toMutableList()
                 newArr.addAll(answerList)
                 newArr.shuffle()

                 for(i in 0..buttonsId.size.minus(1)){

                     (findViewById(buttonsId.get(i)) as TextView).text = newArr.get(i)

                 }*/

                //  Log.i("finakKeyArray2" , newArr.toString())
                for (i in 0 until noOfCell) {
                    vargPahleiAdapter!!.currentAnswerIndexes.add(((cellColumn - 1) + ((cellRow - 1) * 10)) + i)
                }
            }else{

                val  answer = crosswordPlayItem[questionIndex].splitDown!!

                /* val  answerList = answer.split(",").toTypedArray()
                 Log.i("answer:" , answerList.toString())
                 //val finalKeyArray = getSubArray(keyArray, 1, answerList.size)

                 val finalKeyArray = keyArray.toList()
                     .subList(0, keyArray.size - answerList.size)
                     .toTypedArray()
                 Log.i("finakKeyArray1" , finalKeyArray.contentToString())

                 var newArr =  finalKeyArray.toMutableList()
                 newArr.addAll(answerList)
                 newArr.shuffle()

                 for(i in 0..buttonsId.size.minus(1)){
                     (findViewById(buttonsId.get(i)) as TextView).text = newArr.get(i)
                 }*/

                //  Log.i("finakKeyArray2" , newArr.toString())
                for (i in 0 until noOfCell){
                    vargPahleiAdapter!!.currentAnswerIndexes.add(((cellColumn - 1) + ((cellRow - 1) * 10)) + (i * 10))
                }
            }

            gridView.smoothScrollToPosition(vargPahleiAdapter!!.currentCellIndex)

            Log.e("current_cell_index", vargPahleiAdapter!!.currentCellIndex.toString())
            Log.e("current_answer_index", vargPahleiAdapter!!.currentAnswerIndexes.toString())
        }catch (e:Exception){
            e.printStackTrace()
        }
    }




    @SuppressLint("ResourceAsColor", "Range")
    override fun onClick(v: View?) {
        //val iid = v?.id
        // val textView : TextView? = v as TextView?

        when (v?.id) {

            R.id.lLayShowAnswer ->{
                clicked_Item = ClickedItem.UTTAR_DEKHO
                loadAdd()
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.ASK_FRIEND)

                //loadAdd()
                // endGame(gameUserId, (pauseOffset / 1000).toString(), Constants.LOSS)

                /*val bundle = Bundle()
                bundle.putString(IntentConstants.UTTAR_DEKHO_IMAGE_URL, answerImage.toString())

                CommonUtils.performIntentWithBundle(
                    this@VargPaheliGameActivity,
                    UttarDekhoAnswerActivity::class.java,
                    bundle
                )*/
                //showAnswerDialog(answerImage)
                //CommonUtils.performIntent(this@VargPaheliGameActivity, LeaderBoardActivity::class.java)
            }
            R.id.lLayBackBtn ->{
                removeTextOnBox()
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.KEYBOARD_BACKSPACE)
            }

            R.id.rl_hint ->{
                if(!isAllDone()){
                    clicked_Item = ClickedItem.HINT
                    // loadA
                    // setHint()
                    //showRewardAdd()
                    // fillCompleteWord()
                    hintOptionBottomSheet()
                    CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.HINT_BUTTON)
                }
            }

            R.id.lLayQuestionBtn ->{
                CommonUtils.performIntent(this, NavDrawerTutorialActivity::class.java)
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.QUESTION_MARK)
            }

            R.id.lLayPreviousQuestion -> {
                if(!crosswordPlayItem.isNullOrEmpty()){
                    CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BACK_ARROW_CLUE)
                    if(questionIndex > 0){
                        questionIndex--
                        question?.text = crosswordPlayItem[questionIndex].hint.toString()
                        playGameSetup(crosswordPlayItem[questionIndex])
                    }

                    if(questionIndex == crosswordPlayItem.indexOfFirst {
                            true
                        }){
                        lLayPreviousQuestion?.isClickable  = false
                        //ivBack?.setColorFilter(resources.getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
                        lLayPreviousQuestion?.background = ContextCompat.getDrawable(this, R.drawable.curve_radius);
                    }

                    lLayNextQuestion?.isClickable  = true
                    ivNext?.setColorFilter(resources.getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                    lLayNextQuestion?.background = ContextCompat.getDrawable(this, R.drawable.silver_curve_rectangular_box_bg);
                    // question?.text = crosswordPlayItem[questionIndex].hint.toString()
                    vargPahleiAdapter!!.notifyDataSetChanged()
                }
            }


            R.id.lLayNextQuestion -> {
                if(!crosswordPlayItem.isNullOrEmpty()){
                    try{
                        CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.FORWARDARROW_CLUE)
                        lLayPreviousQuestion?.isClickable  = true
                        ivBack?.setColorFilter(resources.getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
                        lLayPreviousQuestion?.background = ContextCompat.getDrawable(this, R.drawable.silver_curve_rectangular_box_bg);
                        if(questionIndex < crosswordPlayItem.size-1){
                            questionIndex++
                            question?.text = crosswordPlayItem[questionIndex].hint.toString()

                            playGameSetup(crosswordPlayItem[questionIndex])
                        }

                        if(questionIndex == crosswordPlayItem.size -1){
                            lLayNextQuestion?.isClickable  = false
                            DrawableCompat.setTint(ivNext?.drawable!!, ContextCompat.getColor(this, R.color.color_4c4c4c));
                            ivNext?.background?.setColorFilter(Color.parseColor("#4c4c4c"),PorterDuff.Mode.SRC_OVER)
                            lLayNextQuestion?.background = ContextCompat.getDrawable(this, R.drawable.curve_radius);
                        }
                        vargPahleiAdapter!!.notifyDataSetChanged()
                    }catch (e:Exception){

                    }
                }
            }

            R.id.tvQ -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tvW -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tv_E -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tv_R -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_T -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_Y -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_U -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_I -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_O -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_P -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_A -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tvS -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_D -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tv_F -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tv_G -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tv_H -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.tv_J -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_K -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_L -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_Z -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_X -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_C -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_V -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_B -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_N -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }
            R.id.tv_M -> {
                setTextOnBox((findViewById<TextView>(v.id)).text.toString())
            }

            R.id.ivHamburger ->{
                my_drawer_layout.openDrawer(navView)

                CleverTapEvent(this).createOnlyEvent(
                    CleverTapEventConstants.HAMBUGER_MENU)
            }

            R.id.ivLeaderBoard ->{
                val bundle = Bundle()
                bundle.putString(IntentConstants.GAME_USER_ID_,gameUserId)
                bundle.putString("GAME_DATE_FOR_LEADER_BOARD", date_for_puzzle)
                bundle.putString("lanuageID","2")
                CommonUtils.performIntentWithBundle(this, LeaderBoardActivity::class.java, bundle)
            }

            R.id.ivSettingGame ->{
                val intent = Intent(this, VargPaheliSettingActivity::class.java)
                startActivity(intent)
            }

            R.id.ivHomeBtn -> {
                /*  setResult(11110)
                  this.finish()*/
                val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.homePage.HomeActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } catch (ignored: ClassNotFoundException) {
                }
            }
            R.id.ivChangeLanguage ->{

                // openLanguageBottomSheet()
            }
        }
    }

    private fun hintOptionBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(
            this,
            R.style.AppBottomSheetDialogTheme
        )

        val view = this.layoutInflater.inflate(R.layout.hint_options_bottom_sheet, null)

        val lLayRevealLetter: LinearLayout? = view.findViewById(R.id.lLayRevealLetter)
        val lLayRevealWord: LinearLayout? = view.findViewById(R.id.lLayRevealWord)
        //  val lLayQuitGame: LinearLayout? = view.findViewById(R.id.lLayQuitGame)

        Log.d("count", hintCount.toString());
        lLayRevealLetter?.setOnClickListener {
            clicked_Item = ClickedItem.REVEAL_LETTER
            hintCount++
            Log.d("countIncremented", hintCount.toString());

            /*if(hintCount > 3){
                mediaPlayerCorrectWord?.pause()
            }*/

            if(hintCount == 4){
                hintCount = 0

                loadAdd()
            }else{
                setHint()
            }
            bottomSheetDialog.dismiss()
        }

        lLayRevealWord?.setOnClickListener{
            CleverTapEvent(this@EnglishVargPahleiGameActivity).createOnlyEvent(CleverTapEventConstants.REVEAL_WORD)
            showRewardAdd()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun setTextOnBox(keyVal: String){
        try{
            val keyValue = keyVal.trim()
            if(playModel?.hintType == "across"){
                if(vargPahleiAdapter!!.currentCellIndex < ((playModel?.cellColumn?.toInt()!!.plus(playModel?.cellRow!!.toInt().minus(1)*10).plus(playModel?.numberOfCell!!.toInt()))).minus(1)){

                    var isLastLetter = false
                    var isWrongAnswer = false
                    if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value != arrOfLetter[vargPahleiAdapter!!.currentCellIndex]){
                        crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value = keyValue
                    }

                    if(isWordDone()){
                        // Toast.makeText(this, "LastCell", Toast.LENGTH_LONG ).show()
                        var startIndex = (playModel?.cellRow!!.toInt().minus(1)*10) + playModel?.cellColumn!!.toInt().minus(1)
                        var builder = StringBuilder()
                        for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                            builder.append(crossWordBoardItem[startIndex+i].value)
                        }

                        if(builder.toString() != playModel?.answer?.trim()){
                            Log.d("fixed", "correct");

                            for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                                crossWordBoardItem[startIndex+i].isWronAnswer = true
                            }

                            if(PrefData.getSoundState(this@EnglishVargPahleiGameActivity)){
                                mediaPlayerError?.start()
                            }else{
                                mediaPlayerError?.pause()
                            }
                            showWrongAnswerDialog()

                            isWrongAnswer = true

                        }else{
                            if(isHintPressed){
                                isHintPressed = false
                            }else{
                                if(PrefData.getSoundState(this@EnglishVargPahleiGameActivity)){
                                    mediaPlayerCorrectWord?.start()
                                }else{
                                    mediaPlayerCorrectWord?.pause()
                                }
                            }
                            for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                                crossWordBoardItem[startIndex+i].isWronAnswer = false
                                crossWordBoardItem[startIndex+i].isFreezed = true
                            }
                            isLastLetter = true
                        }
                    }

                    vargPahleiAdapter!!.notifyDataSetChanged()
                    vargPahleiAdapter!!.currentCellIndex = vargPahleiAdapter!!.currentCellIndex +1

                    cursorIndex += 1 //this is used for hint

                    if(!isWrongAnswer && isLastLetter){
                        onClick(lLayNextQuestion)
                    }

                    if(isWrongAnswer){//Reset Word
                        for (i in  1..playModel!!.numberOfCell!!.toInt()){
                            if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex -i].isFreezed != true){
                                crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex -i].isWronAnswer = false
                                crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex -i].value = ""
                            }
                        }

                        playGameSetup(playModel!!)
                    }

                    if(isAllDone()){
                        // Toast.makeText(this,"All Done. Hurray", Toast.LENGTH_LONG).show()
                        showWinGif()
                        PrefData.setStringPrefs(this,PrefData.Key.ENGLISH+gameDate.toString()+PrefData.Key.IS_DONE, "Done");
                        pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base

                        Handler(Looper.myLooper()!!).postDelayed(Runnable {

                            endGame(gameUserId, (pauseOffset / 1000).toString(), Constants.WIN)
                        }, 2000)
                    }
                }
            }else{
                var wordArray = playModel!!.splitDown?.split(",")
                if(wordArray != null && cursorIndex < wordArray.size){
                }else{
                    return
                }

                if(vargPahleiAdapter!!.currentCellIndex < playModel?.cellRow?.toInt()!!.minus(1).plus(playModel?.numberOfCell!!.toInt())*10){
                    var isLastLetter = false
                    var isWrongAnswer = false
                    if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value != arrOfLetter[vargPahleiAdapter!!.currentCellIndex]){
                        crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value = keyValue
                        if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value == arrOfLetter[vargPahleiAdapter!!.currentCellIndex]){
                            crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].isWronAnswer = false
                        }
                    }


                    if(isWordDone()){
                        // Toast.makeText(this, "LastCell", Toast.LENGTH_LONG ).show()
                        var startIndex = (playModel?.cellRow!!.toInt().minus(1)*10) + playModel?.cellColumn!!.toInt().minus(1)

                        var builder = StringBuilder()
                        for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                            builder.append(crossWordBoardItem[startIndex+i*10].value)
                        }


                        if(builder.toString() != playModel?.answer?.trim()){
                            Log.d("fixed", "not correct");
                            isWrongAnswer = true

                            for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                                crossWordBoardItem[startIndex+i*10].isWronAnswer = true
                            }
                            if(PrefData.getSoundState(this)){
                                mediaPlayerError?.start()
                            }else{
                                mediaPlayerError?.pause()
                            }
                            showWrongAnswerDialog()

                        }else{

                            if(isHintPressed){
                                isHintPressed = false
                            }else{
                                if(PrefData.getSoundState(this@EnglishVargPahleiGameActivity)){
                                    mediaPlayerCorrectWord?.start()
                                }else{
                                    mediaPlayerCorrectWord?.pause()
                                }
                            }

                            for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                                crossWordBoardItem[startIndex+i*10].isWronAnswer = false
                                crossWordBoardItem[startIndex+i*10].isFreezed = true
                            }
                        }
                        isLastLetter = true
                    }

                    vargPahleiAdapter!!.notifyDataSetChanged()
                    vargPahleiAdapter!!.currentCellIndex = vargPahleiAdapter!!.currentCellIndex +10

                    cursorIndex += 1// this is used for hit

                    if(!isWrongAnswer && isLastLetter){
                        onClick(lLayNextQuestion)
                    }


                    if(isWrongAnswer){//Reset Word
                        for (i in  1..playModel!!.numberOfCell!!.toInt()){
                            if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex -i*10].isFreezed != true){
                                crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex -i*10].isWronAnswer = false
                                crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex -i*10].value = ""
                            }
                        }

                        playGameSetup(playModel!!)
                    }

                    if(isAllDone()){
                        // Toast.makeText(this,"All Done. Hurray", Toast.LENGTH_LONG).show()
                        showWinGif()
                        PrefData.setStringPrefs(this,PrefData.Key.ENGLISH+gameDate.toString()+PrefData.Key.IS_DONE, "Done");
                        Handler(Looper.myLooper()!!).postDelayed(Runnable {
                            endGame(gameUserId, (pauseOffset / 1000).toString(), Constants.WIN)
                        }, 2000)
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
                finish()
            }
        }
    }


    private fun removeTextOnBox(){
        if(playModel?.hintType == "across"){
            if(crossWordBoardItem.isNotEmpty()){
                if(vargPahleiAdapter!!.currentCellIndex > playModel?.cellColumn?.toInt()!!.plus(playModel?.cellRow!!.toInt().minus(1)*10).minus(1)){
                    vargPahleiAdapter!!.currentCellIndex = vargPahleiAdapter!!.currentCellIndex -1

                    if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].isFreezed == false){
                        crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value = ""
                        crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].isWronAnswer = false
                    }
                    vargPahleiAdapter!!.notifyDataSetChanged()
                    cursorIndex -= 1
                }

            }else{
                if(vargPahleiAdapter!!.currentCellIndex >=  playModel?.cellRow?.toInt()!!*10){
                    vargPahleiAdapter!!.currentCellIndex = vargPahleiAdapter!!.currentCellIndex -10

                    if(crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].isFreezed == false){
                        crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].value = ""
                        crossWordBoardItem[vargPahleiAdapter!!.currentCellIndex].isWronAnswer = false
                    }

                    vargPahleiAdapter!!.notifyDataSetChanged()
                    cursorIndex -= 1
                }
            }
            }

    }
    var arrOfLetter = arrayOfNulls<String>(100)

    private fun arrOfLtterDo(){
        for (i in 0..crosswordPlayItem.size.minus(1)){
            if(crosswordPlayItem[i].hintType == "across"){
                var playIt = crosswordPlayItem[i]
                var splitArr = playIt.splitAcross?.split(",")
                var count = 0
                for (j in 0..playIt.numberOfCell?.toInt()
                !!.minus(1)){
                    arrOfLetter[playIt.cellColumn!!.toInt().minus(1).plus(playIt.cellRow!!.toInt().minus(1)*10).plus(j)] = splitArr?.get(count).toString()
                    count = count.plus(1)
                }
            }else{
                var playIt = crosswordPlayItem[i]
                var splitArr = playIt.splitDown?.split(",")
                var count = 0
                for (j in 0..playIt.numberOfCell?.toInt()
                !!.minus(1)){
                    arrOfLetter[playIt.cellColumn!!.toInt().minus(1).plus(j*10).plus(playIt.cellRow!!.toInt().minus(1)*10)] = splitArr?.get(count).toString()
                    count = count.plus(1)

                }
            }
        }
        Log.d("bee-man", arrOfLetter.toString())

    }

    private fun isWordDone():Boolean{
        if(playModel != null){
            if(playModel?.hintType == "across"){

                var startIndex = (playModel?.cellRow!!.toInt().minus(1)*10) + playModel?.cellColumn!!.toInt().minus(1)
                var builder = StringBuilder()
                for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                    if(crossWordBoardItem[startIndex+i].value.isNullOrEmpty()){
                        return false
                    }
                    builder.append(crossWordBoardItem[startIndex+i].value)
                }
                return true

            }else{
                var startIndex = (playModel?.cellRow!!.toInt().minus(1)*10) + playModel?.cellColumn!!.toInt().minus(1)

                var builder = StringBuilder()
                for (i in 0..playModel?.numberOfCell!!.toInt().minus(1)){
                    if(crossWordBoardItem[startIndex+i*10].value.isNullOrEmpty()){
                        return false
                    }
                    builder.append(crossWordBoardItem[startIndex+i*10].value)
                }
                return true

            }
        }

        return false
    }

    private fun isAllDone():Boolean{
        var isDone = true
        var value = 0;
        for(i in 0..99){
            if(arrOfLetter[i] != null && arrOfLetter[i]?.trim() != crossWordBoardItem[i].value?.trim() ){
                isDone = false
                value =i
                break
            }
        }
        //  Log.d("bat-mens",value.toString() +"-"+ crossWordBoardItem[value].value + " - " + arrOfLetter[value])
        return isDone
    }

    private fun endGame(gameUserId: String, gameEndTime:String, gameStatus: String) {

        CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.GAME_PLAY)

        if(PrefData.getStringPrefs(applicationContext, PrefData.Key.GAME_USER_ID) != null){
            mGameUsrId = PrefData.getStringPrefs(applicationContext, PrefData.Key.GAME_USER_ID).toString()
            mGameEndTime = gameEndTime
            mGameStatus = gameStatus

            val submitGameRequest = SubmitGameReuquest()
            submitGameRequest.gameStatus = mGameStatus
            submitGameRequest.gameUserId = mGameUsrId
            submitGameRequest.time = mGameEndTime
            submitGameRequest.game_date = date_for_puzzle
            submitGameRequest.lang_id= "2"
            submitGameRequest.app_id = PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_ID)
            Log.e("submit", submitGameRequest.toString())
            submitGame(submitGameRequest)
        }else{
            val bundle = Bundle()
            bundle.putString(IntentConstants.GAME_TIME,gameEndTime)
            bundle.putString(IntentConstants.GAME_STATUS,Constants.WIN)
            bundle.putString("GAME_DATE_FOR_LEADER_BOARD", date_for_puzzle)
            bundle.putString("lanuageID","2")
            CommonUtils.performIntentWithBundle(this, LeaderBoardActivity::class.java, bundle)
            finish()
        }
    }

    private fun submitGame(submitGameRequest: SubmitGameReuquest) {
        compositeDisposable.add(
            apiService!!.gameSubmitCrossWord(submitGameRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("submitGameResponse:", response.toString())
                    if (response?.status == "true") {
                        val bundle = Bundle()
                        bundle.putString(IntentConstants.GAME_USER_ID_,gameUserId)
                        bundle.putString("GAME_DATE_FOR_LEADER_BOARD", date_for_puzzle)
                        bundle.putString("lanuageID","2")
                        CommonUtils.performIntentWithBundle(this, LeaderBoardActivity::class.java, bundle)
                        finish()

                        PrefData.setBooleanPrefs(this, PrefData.Key.FOR_HURREY_SCREEN, true)
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }

    private fun showWrongAnswerDialog() {
        val dialog =  Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.wrong_answer_layout)
        /*dialog.findViewById<LinearLayout>(R.id.ll_dia).setOnClickListener {
            dialog.dismiss()
        }*/
        // set height and width
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        // set to custom layout
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.CENTER
        // find TextView and set Error
        dialog.show()
    }


    private fun setHint(){
        if(  playModel != null){
            isHintPressed = true
            if(playModel?.hintType == "across"){
                var wordArray = playModel!!.splitAcross?.split(",")
                if(wordArray != null && cursorIndex < wordArray.size){
                    setTextOnBox(wordArray?.get(cursorIndex).toString())
                }
            }else{
                var wordArray = playModel!!.splitDown?.split(",")
                if(wordArray != null && cursorIndex < wordArray.size ){
                    setTextOnBox(wordArray?.get(cursorIndex).toString())
                }
            }
        }
    }

    private fun loadAdd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
            mInterstitialAd?.setFullScreenContentCallback(object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    if (clicked_Item == ClickedItem.HINT) {
                        //  showHint()
                    } else if (clicked_Item == ClickedItem.UTTAR_DEKHO) {
                        // CommonUtils.performIntent(this@VargPaheliGameActivity, LeaderBoardActivity::class.java)
                        askQuestionShare()
                    }else if(clicked_Item == ClickedItem.REVEAL_LETTER){
                        CleverTapEvent(this@EnglishVargPahleiGameActivity).createOnlyEvent(CleverTapEventConstants.REVEAL_LETTER)
                        setHint()
                    }
                    interstitialAdd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    if (clicked_Item == ClickedItem.HINT) {
                        //showHint()
                    } else if (clicked_Item == ClickedItem.UTTAR_DEKHO) {
                        askQuestionShare()
                    }
                    interstitialAdd()
                }
            })
        } else {
            if (clicked_Item == ClickedItem.HINT) {
                //showHint()
            } else if (clicked_Item == ClickedItem.UTTAR_DEKHO) {
                askQuestionShare()
            }
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askQuestionShare(){
        if(playModel != null && !TextUtils.isEmpty(playModel?.hint)){
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = "Can you help me, it’s a "+ playModel?.numberOfCell+" letter word and clue is "+ playModel?.hint +"https://onelink.to/4e9bge"
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "दोस्त से पूछो"
            )
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(
                Intent.createChooser(
                    intent,
                    "दोस्त से पूछो"
                )
            )
        }
    }

    private fun interstitialAdd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_INTERTITIALS_AD_ID)!!, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    //Log.i(TAG, "onAdLoaded");
                    // loadAdd();
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    //Log.i(TAG, loadAdError.getMessage());
                    mInterstitialAd = null
                }
            })
    }

    private fun initRewardAdd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_REWARDED_AD_ID)!!,
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error.
                    // Log.d(TAG, loadAdError.getMessage());
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    // Log.d(TAG, "Ad was loaded.");
                }
            })
    }

    private fun showRewardAdd() {
        if (mRewardedAd != null) {
            mRewardedAd?.show(this, OnUserEarnedRewardListener { rewardItem -> // Handle the reward.
                //  Log.d(TAG, "The user earned the reward.");
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type

                // setHint()
                initRewardAdd()
                fillCompleteWord()
            })
            mRewardedAd?.setFullScreenContentCallback(object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    // Log.d(TAG, "Ad was shown.");
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when ad fails to show.
                    // Log.d(TAG, "Ad failed to show.");
                    initRewardAdd()

                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    // Log.d(TAG, "Ad was dismissed.");
                    mRewardedAd = null
                    initRewardAdd()
                }
            })
        } else {
            // Log.d(TAG, "The rewarded ad wasn't ready yet.");
            Toast.makeText(this,"The rewarded ad wasn't ready yet.", Toast.LENGTH_LONG).show()
            initRewardAdd()
        }
    }

    override fun onBackPressed() {
        if(gameDate != null){
            CommonUtils.saveGame(this,crossWordBoardItem, PrefData.Key.ENGLISH+gameDate.toString())
            PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.QUESTION_NO_ENGLISH_GAME+gameDate.toString(),questionIndex.toString())


            if(tv_timer_text != null){
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                PrefData.setLongPrefs(applicationContext, PrefData.Key.ENGLISH+PrefData.Key.PAUSE_OFF_SET+gameDate, pauseOffset)
            }
        }
        finish()
        super.onBackPressed()
    }

    fun showWinGif(){
        if (PrefData.getSoundState(this@EnglishVargPahleiGameActivity)){
            mediaPlayerGameComplete?.start()
        }else{
            mediaPlayerGameComplete?.pause()
        }

        findViewById<FrameLayout>(R.id.fl_gif).visibility = View.VISIBLE

        Glide.with(this)
            .asGif()
            .load(R.drawable.giphy)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<GifDrawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resource.setLoopCount(4)
                    return false
                }
            })
            .into(findViewById(R.id.gif_icon))
    }

    fun higlightSelectedHint(pos:Int){
        var index = -1
        for (i in 0..crosswordPlayItem.size.minus(1)){
            var playItem = crosswordPlayItem[i]
            if(playItem.hintType == "across"){
                var value1 = playItem.cellColumn?.toInt()!!.minus(1).plus(
                    playItem.cellRow?.toInt()!!.minus(1)
                        ?.times(10)!!
                )

                var value2 = playItem.cellColumn?.toInt()!!.minus(1).plus(playItem.numberOfCell!!.toInt()).plus(
                    playItem.cellRow?.toInt()!!.minus(1)
                        ?.times(10)!!
                )
                if(pos in value1 until value2){

                    index = i
                    if(vargPahleiAdapter != null){
                        questionIndex = index
                        question?.text = crosswordPlayItem[questionIndex].hint.toString()
                        playGameSetup(playItem)
                        vargPahleiAdapter!!.notifyDataSetChanged()
                    }
                    break
                }
            }else{
                // var value1 = playItem.cellRow?.toInt()!!.minus(1).times(10).plus(playItem.cellColumn?.toInt()!!.minus(1))
                var value1 = pos.mod(10).plus(1) == playItem.cellColumn!!.toInt()
                //var value2 = playItem.cellRow?.toInt()!!.plus(playItem.numberOfCell!!.toInt()).times(10).plus(playItem.cellColumn?.toInt()!!.minus(1))
                var value2 = (pos/10).plus(1) >= playItem.cellRow!!.toInt() && (pos/10).plus(1) < playItem.cellRow!!.toInt().plus(playItem.numberOfCell!!.toInt())
                if(value1 && value2){
                    index = i
                    if(vargPahleiAdapter != null){
                        questionIndex = index
                        question?.text = crosswordPlayItem[questionIndex].hint.toString()
                        playGameSetup(playItem)
                        vargPahleiAdapter!!.notifyDataSetChanged()
                    }
                    break
                }
            }
        }
    }

    fun fillCompleteWord(){
        if(playModel != null){
            if(playModel!!.hintType == "across"){
                for (i in 0..playModel!!.numberOfCell!!.toInt().minus(1)){
                    var value = (playModel?.cellRow!!.toInt().minus(1).times(10)).plus(i).plus(playModel?.cellColumn!!.toInt().minus(1))
                    crossWordBoardItem[value].value = arrOfLetter[value]
                    crossWordBoardItem[value].isFreezed = true
                    crossWordBoardItem[value].isWronAnswer = false
                }

                if(questionIndex.plus(1) < crosswordPlayItem.size){
                    questionIndex += 1
                    question?.text = crosswordPlayItem[questionIndex].hint.toString()
                    playGameSetup(crosswordPlayItem[questionIndex])
                }
                vargPahleiAdapter!!.notifyDataSetChanged()


            }else{
                for (i in 0 until playModel!!.numberOfCell!!.toInt()){
                    var value = ((playModel?.cellColumn!!.toInt() - 1) + ((playModel?.cellRow!!.toInt() - 1) * 10)) + (i * 10)
                    crossWordBoardItem[value].value = arrOfLetter[value]
                    crossWordBoardItem[value].isFreezed = true
                    crossWordBoardItem[value].isWronAnswer = false
                }


                if(questionIndex.plus(1) < crosswordPlayItem.size){
                    questionIndex += 1
                    question?.text = crosswordPlayItem[questionIndex].hint.toString()
                    playGameSetup(crosswordPlayItem[questionIndex])
                }
                vargPahleiAdapter!!.notifyDataSetChanged()
            }

            if(isAllDone()){
                // Toast.makeText(this,"All Done. Hurray", Toast.LENGTH_LONG).show()
                showWinGif()
                PrefData.setStringPrefs(this,gameDate.toString()+PrefData.Key.IS_DONE, "Done");
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base

                Handler(Looper.myLooper()!!).postDelayed(Runnable {

                    endGame(gameUserId, (pauseOffset / 1000).toString(), Constants.WIN)
                }, 2000)
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

        if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){
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

            if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                lLayHindiLang?.isClickable = false
            }else{

                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                bottomSheetDialog.dismiss()
                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID))) {
                    val intent = Intent(this, VargPaheliGameActivity::class.java)
                    intent.putExtra("DATE_GAME",date_for_puzzle)
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

            lLayHindiLang?.setBackgroundColor(Color.BLACK);
            lLayEnglishLang?.setBackgroundColor(Color.WHITE);

            tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
            tvEngLang?.setTextColor(Color.parseColor("#000000"));

            bottomSheetDialog.dismiss()
            VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
            PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
            PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

            if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID))) {
                val intent = Intent(this, VargPaheliGameActivity::class.java)
                intent.putExtra("DATE_GAME",date_for_puzzle)
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

        lLayEnglishLang?.setOnClickListener{

            if(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                lLayEnglishLang.isClickable = true
            }else{

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@EnglishVargPahleiGameActivity,PrefData.Key.GAME_USER_ID))) {
                    val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                    intent.putExtra("DATE_GAME",date_for_puzzle)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{

                    val intent = Intent(this, EnglishVargPahleiGameActivity::class.java)
                    intent.putExtra("DATE_GAME",date_for_puzzle)
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
