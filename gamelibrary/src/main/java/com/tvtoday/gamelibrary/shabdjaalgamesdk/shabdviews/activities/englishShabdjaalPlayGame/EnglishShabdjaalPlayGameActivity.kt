package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame

import PrefDataShabdjal
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
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
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabd_word_search.Word
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabd_word_search.WordSearchView
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.`interface`.RecyclerViewOnClickShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.submitGame.SubmitGameReuquestShabd
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.constants.ConstantsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.constants.IntentConstantsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.GameGridShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.AnswerModelShabd
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.ShabdjalUtils
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalBoradItem
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalGameRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalGameResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.gameAdapter.ShabdjaalGameAdapter
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.nav_drawer_adapter.NavDrawerShabdjalAdapter
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.nav_drawer_adapter.NavDrawerShabdjalModel
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.leader_board_activity.LeaderBoardShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.shabdjaal_settingActivity.ShabdjalSettingActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.waitingactivity.WaitingShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.adapter.AnswerShabdAdapter
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EnglishShabdjaalPlayGameActivity : ShabdjalBaseActivity(), View.OnClickListener{


    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()
    //arrayList----------------------------------------------------------
    private val shabdjaalBoradItem: ArrayList<ShabdjaalBoradItem> = ArrayList()
    private val answerListt: ArrayList<AnswerModelShabd> = ArrayList()

    private val splitedList : ArrayList<String> = ArrayList()
    //private var answerList :ArrayList<AnswerModel> = ArrayList()

    //adapter-----------------------------------------------------------
    private var answerAdapter: AnswerShabdAdapter?=null
    private var shabdjaalGameAdapter: ShabdjaalGameAdapter? = null

    //navigation Drawer------------------------------------------------
    private var rvNavDrawer: RecyclerView? = null
    private var rvAnswers: RecyclerView? = null
    private lateinit var my_drawer_layout: DrawerLayout
    private lateinit var navView: NavigationView

    //imageViews---------------------------------------
    private var ivHamburger: ImageView? = null
    private var ivLangChange: ImageView? = null
    private var ivSettingGame: ImageView? = null
    private var ivLeaderBoard: ImageView? = null
    private var ivHome: ImageView? = null
    private var rlHint: LinearLayout? = null

    private var tvQuestionIn : String?=null

    //ad-----------------------------------------------
    private var mInterstitialAd: InterstitialAd? = null
    //adView

    private var paint: Paint? = null

    //textViews-------------------------------------------
    private var ivUserName: TextView? = null
    private var tvQuestion: TextView? = null
    private var tvSubQuestionText: TextView? = null
    private var tvAnswerCount: TextView? = null
    private var tvTotalQuestion: TextView? = null

    //linearLayout
    private var lLayHowToPlay: LinearLayout? = null
    private var lLayInfoQuestion: LinearLayout? = null
    private var lLayAnswers: LinearLayout? = null

    //mediaPlayer-----------------------------------------
    private var mediaPlayer: MediaPlayer? = null
    private var mediaPlayerError : MediaPlayer? = null
    private var mediaPlayerCorrectWord : MediaPlayer? = null
    private var mediaPlayerGameComplete : MediaPlayer? = null

    //chronometer for show time
    private var tv_timer_text: Chronometer? = null

    lateinit var gameGrid: GameGridShabdjal

    //booleans
    private var isHintPressed = false

    //strings------------------------------------------------
    private var mTimingRunning = false
    private var pauseOffset: Long = 0
    private var minute: String? = ""
    private var second: String? = ""
    private var minutes: Long = 0
    private var gameUserId :String = ""
    private var seconds: Long = 0
    private var mGameUsrId:String = ""
    private var mGameEndTime : String = ""
    private var mGameStatus:String = ""
    private var gameDate:String?=null
    private var puzzleGameDate:String?=null

    var wordsGrid: WordSearchView? = null;
    //counter for hint button-----------------
    var hintCount = 0

    private var date :String? = null

    lateinit var spinner: Spinner

    protected var context: Context? = null
    private var mRewardedAd: RewardedAd? = null

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null
    private var tvGameDate :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_english_shabdjaal_play_game)

        //  wordsGrid.setTypeface(FontManager.getTypeface(this, FontManager.POYNTER))
        mediaPlayerError  = MediaPlayer.create(this,R.raw.for_error_music)
        mediaPlayerCorrectWord  = MediaPlayer.create(this,R.raw.small_applause)
        mediaPlayerGameComplete  = MediaPlayer.create(this,R.raw.game_complete)

        /*  wordsGrid.setLetters(
              arrayOf(
                  "ASCDEFGHIJK".toCharArray(),
                  "AECDEFGHIJK".toCharArray(),
                  "AACDEFGHIJK".toCharArray(),
                  "ARCWEFGHIJK".toCharArray(),
                  "ACCDOFGHIJK".toCharArray(),
                  "AHCGERGHIJK".toCharArray(),
                  "AICDEFDHIJK".toCharArray(),
                  "ANCDEFGHIJK".toCharArray(),
                  "AGCSOMEHIJK".toCharArray(),
                  "ABCDEFGHIJK".toCharArray(),
                  "ABCDEFGHIJK".toCharArray()
              )
          )*/

        /*  wordsGrid?.setWords(
              Word("WORD", false, 3, 3, 6, 6),
              Word("SOME", false, 8, 3, 8, 6),
              Word("SEARCHING", false, 0, 1, 8, 1),
              Word("FOG", false, 3, 5, 5, 3)
          )*/

        /* wordsGrid.setOnWordSearchedListener(object : WordSearchView.OnWordSearchedListener() {
             fun wordFound(word: String) {
                 Toast.makeText(this@Shabdja, "$word found", Toast.LENGTH_SHORT).show()
             }
         })*/
        puzzleGameDate=intent.getStringExtra("Date")
      /*  if(puzzleGameDate.equals("null")){
            puzzleGameDate = PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.DATE_FOR_LANGUAGE_CANGE_PART)
        }*/
        // Toast.makeText(this, puzzleGameDate, Toast.LENGTH_SHORT).show()
        interstitialAdd()
        try {
            initViews()
            navDrawerSetup()


            getShabdjaalGame()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (PrefDataShabdjal.getSoundState(this@EnglishShabdjaalPlayGameActivity)) {
            playSound()
        } else {
            mediaPlayer?.pause()
        }

    }


    override fun onResume() {
        super.onResume()

        if (PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.NAME) != null) {
            ivUserName?.text = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.NAME)
        }

        if (PrefDataShabdjal.getSoundState(this@EnglishShabdjaalPlayGameActivity)) {
            mediaPlayer?.start()
        } else {
            mediaPlayer?.pause()
        }
        if(gameDate != null){
            gameTimer()
        }
    }

    private fun gameTimer() {
        tv_timer_text?.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            if (SystemClock.elapsedRealtime() - tv_timer_text!!.base >= getTimeTillMidnight()) {
                tv_timer_text?.base = SystemClock.elapsedRealtime()
                endGame(gameUserId, (pauseOffset / 1000).toString(), ConstantsShabdjal.LOSS)
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

    override fun onDestroy() {
        if(gameDate != null && wordsGrid != null && !wordsGrid?.answeredWordList!!.isEmpty()  ){
            ShabdjalUtils.saveGame(this, wordsGrid?.answeredWordList!!, PrefDataShabdjal.Key.ENGLISH+gameDate.toString())
            if(tv_timer_text != null){
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                PrefDataShabdjal.setLongPrefs(applicationContext, PrefDataShabdjal.Key.ENGLISH+PrefDataShabdjal.Key.PAUSE_OFF_SET+gameDate, pauseOffset)
            }
        }
        tv_timer_text = null
        super.onDestroy()
    }

    override fun onUserLeaveHint() {
        if(gameDate != null && wordsGrid != null && !wordsGrid?.answeredWordList!!.isEmpty()  ){
            ShabdjalUtils.saveGame(this, wordsGrid?.answeredWordList!!, PrefDataShabdjal.Key.ENGLISH+gameDate.toString())
            if(tv_timer_text != null){
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                PrefDataShabdjal.setLongPrefs(applicationContext, PrefDataShabdjal.Key.ENGLISH+PrefDataShabdjal.Key.PAUSE_OFF_SET+gameDate, pauseOffset)
            }
        }
        super.onUserLeaveHint()
    }

    override fun onPause() {
        super.onPause()

        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }

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

    fun playSound() {
        if (mediaPlayer != null) {
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }
    }

    private fun initViews() {

        gameUserId = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID).toString()

        mediaPlayer = MediaPlayer.create(this, R.raw.peaceful_garden_healing)

        tv_timer_text = findViewById(R.id.tv_timer_text)

        var list = mutableListOf<Int>()

        rvNavDrawer = findViewById(R.id.rvNavDrawer)
        rvAnswers = findViewById(R.id.rvAnswers)
        navView = findViewById(R.id.navView)
        navView.bringToFront()
        ivHamburger = findViewById(R.id.ivHamburger)
        lLayInfoQuestion = findViewById(R.id.lLayInfoQuestion)
        lLayAnswers = findViewById(R.id.lLayAnswers)
        lLayInfoQuestion?.setOnClickListener(this)
        ivHamburger?.setOnClickListener(this)

        ivHome = findViewById(R.id.ivHome)
        tvGameDate = findViewById(R.id.tvGameDate)
        ivHome?.setOnClickListener(this)

        ivLangChange = findViewById(R.id.ivLangChange)
        ivLangChange?.setOnClickListener(this)

        wordsGrid = findViewById(R.id.wordsGrid) as WordSearchView

        my_drawer_layout = findViewById(R.id.my_drawer_layout)

        ivSettingGame = findViewById(R.id.ivSettingGame)
        ivSettingGame?.setOnClickListener(this)

        ivLeaderBoard = findViewById(R.id.ivLeaderBoard)
        ivLeaderBoard?.setOnClickListener(this)

        lLayHowToPlay = findViewById(R.id.lLayHowToPlay)
        lLayHowToPlay?.setOnClickListener(this)

        tvAnswerCount = findViewById(R.id.tvAnswerCount)

        ivUserName = findViewById(R.id.ivUserName)
        ivUserName?.text = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.NAME)

        rlHint = findViewById(R.id.rl_hint)

        var adView = AdView(this@EnglishShabdjaalPlayGameActivity)
        adView.adUnitId = PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_BANNER_AD_ID)!!
       adView.setAdSize(AdSize.BANNER)
       val adRequestt = AdRequest.Builder().build()
       findViewById<LinearLayout?>(R.id.lLAyBannerAd).addView(adView)
       adView.loadAd(adRequestt)

        tvTotalQuestion = findViewById(R.id.tvTotalQuestion)

        rlHint?.setOnClickListener(this)

        tvQuestion = findViewById(R.id.tvQuestion)
        // for splash screen
        Handler(Looper.myLooper()!!).postDelayed({
            tvQuestion?.visibility = View.GONE
            tvSubQuestionText?.visibility = View.GONE
            lLayAnswers?.visibility =View.VISIBLE
        }, 5000)


        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")){
            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llHindi?.setBackgroundColor(Color.BLACK)
            llEng?.setBackgroundColor(Color.WHITE)

            tvEng?.setTextColor(Color.parseColor("#000000"));
            tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));

        }else{
            llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

            //llEng?.setBackgroundColor(Color.BLACK)
            llHindi?.setBackgroundColor(Color.WHITE)

            tvHindi?.setTextColor(Color.parseColor("#000000"));
            tvEng?.setTextColor(Color.parseColor("#FFFFFF"));
        }


        llEng?.setOnClickListener {
            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_ENGLISH)

            if(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{
                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

                //llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
                    val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }

            }

        }

        llHindi?.setOnClickListener {
            CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.BUTTON_HINDI)

            if(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

             //   llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = "hi"
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }





        /*mAdView = findViewById(R.id.adView)
        val adRequestt = AdRequest.Builder().build()
        mAdView.setAdSize(AdSize.BANNER)
        mAdView.adUnitId = PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_BANNER_AD_ID)!!
        mAdView.loadAd(adRequestt)*/

      /*  mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.setAdSize(AdSize.BANNER)
        mAdView.adUnitId = PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_BANNER_AD_ID)!!
        mAdView.loadAd(adRequest)*/
    }

    private fun forHintBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(
            this,
            R.style.AppBottomSheetDialogTheme
        )

        val view = this.layoutInflater.inflate(R.layout.hint_bottom_sheet, null)

        val lLayRevealLetter: LinearLayout? = view.findViewById(R.id.lLayRevealLetter)
        val lLayRevealWord: LinearLayout? = view.findViewById(R.id.lLayRevealWord)
        //  val lLayQuitGame: LinearLayout? = view.findViewById(R.id.lLayQuitGame)

        Log.d("count", hintCount.toString());
        lLayRevealLetter?.setOnClickListener {
            if(wordsGrid != null){
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.HINT_BUTTON)

                isHintPressed = true
                hintCount ++
                if(hintCount == 4){
                    loadAdd()
                    hintCount = 0
                }else{
                    wordsGrid?.setHint()
                }
            }
            bottomSheetDialog.dismiss()
        }

        lLayRevealWord?.setOnClickListener{
            CleverTapEventShabdjal(this).createOnlyEvent(
                CleverTapShabdjalConstants.REVEAL_LETTER)

            /* if(wordsGrid != null){
             wordsGrid?.revelWord()
             }*/
            isHintPressed = true
            if(wordsGrid != null){
                showRewardAdd()
            }
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun initRewardAdd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_REWARDED_AD_ID)!!,
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
                wordsGrid?.revelWord()
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

    private fun getShabdjaalGame() {
        // https://mocki.io/v1/abe31b40-38e7-402b-bc91-99a2e94aefa7

        val shabdjaalRequest = ShabdjaalGameRequest()
        // shabdjaalRequest.appId = "1"
        shabdjaalRequest.game_user_id = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID).toString()
        shabdjaalRequest.game_date= puzzleGameDate
        shabdjaalRequest.lang_id="2"
        shabdjaalRequest.app_id = PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)

        Log.e("params:", shabdjaalRequest.toString())

        compositeDisposable.add(
            apiService!!.getShabdjaalGame(shabdjaalRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe(@SuppressLint("SimpleDateFormat")
                fun(response: ShabdjaalGameResponse?) {
                   try {
                       Log.e("game_response:", response.toString())
                       if (response?.status == "true" && response.data != null) {
                           puzzleGameDate = response.data.gameDate
                           if(TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
                               try{
                                   val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                   val formatter = SimpleDateFormat("dd-MM-yyyy")
                                   val dateFormated = formatter.format(parser.parse(puzzleGameDate))
                                   tvGameDate?.text = dateFormated
                               }catch (e:Exception){
                               }
                           }

                          /* if(puzzleGameDate.equals("null") || TextUtils.isEmpty(puzzleGameDate) ) {
                               puzzleGameDate = response.data.gameDate

                               var dateFormated = ""
                               val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                               val formatter = SimpleDateFormat("dd-MM-yyyy")
                               try {
                                   dateFormated = formatter.format(parser.parse(puzzleGameDate))
                               } catch (e: ParseException) {

                               }
                               tvGameDate?.text = dateFormated
                           }*/

                           /*var dateFormated = response.data.gameDate.toString()

                           val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                           val formatter = SimpleDateFormat("dd-MM-yyyy")
                           try{
                                dateFormated = formatter.format(parser.parse(puzzleGameDate))
                           } catch (e: ParseException) {

                           }
                           tvGameDate?.text = dateFormated*/
                           Log.e("leaderBoardStatus::", response.data.today_leaderboard_status.toString())

                           if(response.data.today_leaderboard_status){
                               CommonUtilsShabdjal.performIntentFinish(this, WaitingShabdActivity::class.java)
                               return
                           }

                           tvQuestionIn = response.data.question.toString()
                           tvQuestion?.text = tvQuestionIn.toString()
                           Log.e("question:", response.data.question.toString())
                           gameDate = response.data.gameDate

                           shabdjaalBoradItem.addAll(response.data.shabdjaalBorad!!)

                           wordsGrid?.setLetters(shabdjaalBoradItem)
                           //   wordsGrid?.setWords(response.data.shabdjaalPlay.word.t)
                           wordsGrid?.setWordsList(response.data.shabdjaalPlay?.word)
                           tvTotalQuestion?.text = response.data.shabdjaalPlay?.word?.size.toString()
                           Log.e("word_", response.data.shabdjaalPlay?.word.toString())
                           //start timer after showing response
                           pauseOffset = PrefDataShabdjal.getLongPrefs(applicationContext, PrefDataShabdjal.Key.ENGLISH+PrefDataShabdjal.Key.PAUSE_OFF_SET+gameDate, 0)
                           startTimer()

                           if(response.data.shabdjaalPlay?.split!!.isNotEmpty()){
                               for(i in 0 until response.data.shabdjaalPlay?.split!!.size){
                                   splitedList.add(response.data.shabdjaalPlay.split[i]!!)
                                   wordsGrid?.setSplitetdList(splitedList)
                                   Log.e("splited_list",splitedList.toString())
                               }
                           }

                           ////  startTimer()
                           wordsGrid?.setOnWordSearchedListener(object :
                               WordSearchView.OnWordSearchedListener {
                               @SuppressLint("NotifyDataSetChanged")
                               override fun wordFound(word: String) {
                                   /* Toast.makeText(
                                        this@ShabdjaalPlayGameActivity,
                                        word + " found",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()*/

                                   if(isHintPressed){
                                       isHintPressed = false
                                   }else{
                                       if(PrefDataShabdjal.getSoundState(this@EnglishShabdjaalPlayGameActivity)){
                                           mediaPlayerCorrectWord?.start()
                                       }else{
                                           mediaPlayerCorrectWord?.pause()
                                       }
                                   }

                                   for (answerModel in answerListt) {
                                       if (word.equals(answerModel.answer)) {
                                           Log.e("selected_answer", answerModel.toString())
                                           answerModel.isAnswered = true
                                           break
                                       }
                                   }

                                   answerAdapter?.notifyDataSetChanged()

                                   var answerCount = 0
                                   for (i in 0 until answerListt.size) {

                                       if (answerListt[i].isAnswered) {
                                           answerCount++
                                           Log.e("isAns", "")
                                       }
                                   }
                                   tvAnswerCount?.text = answerCount.toString()

                               }

                               override fun onAllWordFound() {


                                   showWinGif()
                                   /*    Toast.makeText(
                                           this@ShabdjaalPlayGameActivity,
                                           " All Word " + " found",
                                           Toast.LENGTH_SHORT
                                       )
                                           .show()*/
                                   PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.ENGLISH+gameDate.toString()+PrefDataShabdjal.Key.IS_DONE, "Done");
                                   pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base


                                   Handler(Looper.myLooper()!!).postDelayed(Runnable {

                                       //  response.data.today_leaderboard_status = true

                                       endGame(
                                           gameUserId,
                                           (pauseOffset / 1000).toString(),
                                           ConstantsShabdjal.WIN
                                       )
                                   }, 2000)
                               }

                               override fun wordNotFound() {
                                   if (PrefDataShabdjal.getSoundState(this@EnglishShabdjaalPlayGameActivity)) {
                                       mediaPlayerError?.start()
                                   } else {
                                       mediaPlayerError?.pause()
                                   }
                               }
                           })

                           shabdjaalGameAdapter = ShabdjaalGameAdapter(this, shabdjaalBoradItem)
                           shabdjaalGameAdapter?.notifyDataSetChanged()

                           //answerListt.clear()

                           for (i in 0 until response.data.shabdjaalPlay?.word!!.size) {
                               answerListt.add(
                                   AnswerModelShabd(
                                       response.data.shabdjaalPlay.word[i].toString(),
                                       false
                                   )
                               )
                               Log.e("answerList:", answerListt.toString())
                           }

                           answerAdapter = AnswerShabdAdapter(this, answerListt)
                           val layoutManager = GridLayoutManager(this, 4)
                           Log.d("betmm", answerListt.size.toString())
                           rvAnswers?.layoutManager = layoutManager
                           rvAnswers?.adapter = answerAdapter

                           if (PrefDataShabdjal.getStringPrefs(
                                   applicationContext,
                                   PrefDataShabdjal.Key.ENGLISH+
                                   gameDate.toString()
                               ) != null
                           ) {
                               var dataSet = ShabdjalUtils.getGame(
                                   PrefDataShabdjal.getStringPrefs(
                                       this,
                                       PrefDataShabdjal.Key.ENGLISH+
                                       gameDate.toString()
                                   ).toString()
                               )
                               wordsGrid?.setAnsweredList(
                                   dataSet
                               )

                               if(dataSet != null && !dataSet.isEmpty()){
                                   val it: Iterator<Word> = dataSet.iterator()
                                   while (it.hasNext()){
                                       var answer  = it.next().word
                                       for (i in 0 until answerListt.size){
                                           if( answer == answerListt[i].answer){
                                               answerListt[i].isAnswered = true
                                           }
                                       }
                                   }

                                   var answerCount = 0
                                   for (i in 0 until answerListt.size) {

                                       if (answerListt[i].isAnswered) {
                                           answerCount++
                                           Log.e("isAns", "")
                                       }
                                   }
                                   tvAnswerCount?.text = answerCount.toString()
                               }
                           }
                           answerAdapter?.notifyDataSetChanged()

                           if(wordsGrid != null && wordsGrid!!.isAllDone){
                               endGame(
                                   gameUserId,
                                   (pauseOffset / 1000).toString(),
                                   ConstantsShabdjal.WIN
                               )
                           }

                       }
                       else if(response?.status == "false"){
                           val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                           intent.flags =
                               Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                           startActivity(intent)
                           finish()
                       }
                   }catch (e:Exception){

                   }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }

    private fun goToLeaderBoardScreen() {
        val intent = Intent(this@EnglishShabdjaalPlayGameActivity, LeaderBoardShabdjalActivity::class.java)
        startActivity(intent)
    }

    private fun navDrawerSetup() {
        val nav_drawer_list = java.util.ArrayList<NavDrawerShabdjalModel>()
        nav_drawer_list.add(NavDrawerShabdjalModel(getString(R.string.hi_hindi)))
        nav_drawer_list.add(NavDrawerShabdjalModel(getString(R.string.new_game_tv_today_hindi)))
        nav_drawer_list.add(NavDrawerShabdjalModel(getString(R.string.tutorial_hindi)))
        nav_drawer_list.add(NavDrawerShabdjalModel(getString(R.string.share_app_hindi)))
        nav_drawer_list.add(NavDrawerShabdjalModel(getString(R.string.setting_hindi)))
        nav_drawer_list.add(NavDrawerShabdjalModel(getString(R.string.priviacy_hindi)))
        //nav_drawer_list.add(NavDrawerShabdjalModel("Terms & uses"))
        //nav_drawer_list.add(NavDrawerShabdjalModel("LogOut"))

        rvNavDrawer?.adapter = NavDrawerShabdjalAdapter(this, nav_drawer_list, object :
            RecyclerViewOnClickShabdjal {
            override fun onItemClicked(position: Int, viewType: Int, view: View) {
                when (viewType) {
                    11 -> {
                        my_drawer_layout.closeDrawer(navView)
                    }
                }
            }
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_hint ->{

                forHintBottomSheet()
                Log.e("hint-press","hint-press")
            }

            R.id.ivHome ->{

                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.HOME_CLICK)

                /*setResult(22);
                finish()*/

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

            R.id.ivHamburger -> {
                CleverTapEventShabdjal(this).createOnlyEvent(
                    CleverTapShabdjalConstants.HAMBUGER_MENU)

                my_drawer_layout.openDrawer(navView)
                Log.e("hint-press","hint-press")

            }

            R.id.ivLeaderBoard -> {
                val bundle = Bundle()
                bundle.putString(IntentConstantsShabdjal.GAME_USER_ID_,gameUserId)
                bundle.putString("GAME_DATE_FOR_LEADER_BOARD", puzzleGameDate)
                CommonUtilsShabdjal.performIntentWithBundle(this, LeaderBoardShabdjalActivity::class.java, bundle)
            }

            R.id.ivSettingGame -> {
                val intent = Intent(this, ShabdjalSettingActivity::class.java)
                startActivity(intent)
            }

            R.id.lLayHowToPlay -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.how_to_play_dialog_shabd)

                val ivCancel = dialog.findViewById<ImageView>(R.id.ivCancel)
                ivCancel?.setOnClickListener {
                    dialog.cancel()
                }
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(dialog.window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                lp.gravity = Gravity.BOTTOM
                lp.windowAnimations = R.style.DialogAnimation
                dialog.window?.attributes = lp
                dialog.show()
            }
            R.id.lLayInfoQuestion -> {
                showQuestion()
            }

            R.id.ivLangChange ->{
               // openLanguageBottomSheet()
            }
        }
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


    private fun submitGame(submitGameRequest: SubmitGameReuquestShabd) {
        compositeDisposable.add(
            apiService!!.gameSubmitShabdjaal(submitGameRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("submitGameResponse:", response.toString())
                    if (response?.status == "true") {

                        date = response.data?.game_date
                        Log.e("date:", date.toString())

                        val bundle = Bundle()
                        bundle.putString(IntentConstantsShabdjal.GAME_USER_ID_,gameUserId)
                        bundle.putString("GAME_DATE_FOR_LEADER_BOARD", puzzleGameDate)

                        PrefDataShabdjal.setBooleanPrefs(this, PrefDataShabdjal.Key.FOR_HURREY_SCREEN, true)

                        CommonUtilsShabdjal.performIntentWithBundle(this, LeaderBoardShabdjalActivity::class.java, bundle)
                        finish()
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable.message)
                })
    }

    private fun endGame(gameUserId: String, gameEndTime:String, gameStatus: String) {

        if(PrefDataShabdjal.getStringPrefs(applicationContext, PrefDataShabdjal.Key.GAME_USER_ID) != null){
            mGameUsrId = PrefDataShabdjal.getStringPrefs(applicationContext, PrefDataShabdjal.Key.GAME_USER_ID).toString()
            mGameEndTime = gameEndTime
            mGameStatus = gameStatus

            val submitGameRequest = SubmitGameReuquestShabd()
            submitGameRequest.gameStatus = mGameStatus
            submitGameRequest.gameUserId = mGameUsrId.toString()
            submitGameRequest.time = mGameEndTime
            submitGameRequest.game_date = puzzleGameDate.toString()
            submitGameRequest.lang_id= "2"
            submitGameRequest.app_id = PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
            Log.e("submit_param",submitGameRequest.toString())
            submitGame(submitGameRequest)
        }else{
            val bundle = Bundle()
            bundle.putString(IntentConstantsShabdjal.GAME_TIME,gameEndTime)
            bundle.putString(IntentConstantsShabdjal.GAME_STATUS, ConstantsShabdjal.WIN)
            bundle.putString("GAME_DATE_FOR_LEADER_BOARD", puzzleGameDate)
            CommonUtilsShabdjal.performIntentWithBundle(this, LeaderBoardShabdjalActivity::class.java, bundle)
            finish()
        }
    }


    private fun noShabdjaalPuzzleFound() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.no_shabdjaal_found)
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


    private fun showQuestion() {
        val dialog =  Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.question_info_shabd_layout)

        // set height and width
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT

        val tvQuestionInfo = dialog.findViewById<TextView>(R.id.tvQues)
        tvQuestionInfo?.text = tvQuestionIn.toString()
        // set to custom layout
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.CENTER
        // find TextView and set Error
        dialog.show()
    }


    private fun loadAdd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
            mInterstitialAd?.setFullScreenContentCallback(object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()

                    wordsGrid?.setHint()
                    interstitialAdd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)

                    interstitialAdd()
                }
            })
        } else {

            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }

    private fun interstitialAdd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_INTERTITIAL_ID)!!, adRequest,
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


    fun showWinGif(){
        if (PrefDataShabdjal.getSoundState(this@EnglishShabdjaalPlayGameActivity)){
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

    override fun onBackPressed() {
        if(gameDate != null && wordsGrid != null && !wordsGrid?.answeredWordList!!.isEmpty()  ){
            ShabdjalUtils.saveGame(this, wordsGrid?.answeredWordList!!, PrefDataShabdjal.Key.ENGLISH+gameDate.toString())

            if(tv_timer_text != null){
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text!!.base
                PrefDataShabdjal.setLongPrefs(applicationContext, PrefDataShabdjal.Key.ENGLISH+PrefDataShabdjal.Key.PAUSE_OFF_SET+gameDate, pauseOffset)
            }
        }
        //new comment
        //comment
        super.onBackPressed()
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

        if(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")){
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

            if(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                lLayHindiLang.isClickable = false
            }else{

                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }

        lLayEnglishLang?.setOnClickListener{

            if(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("english")) {
                lLayEnglishLang?.isClickable = false
            }else {

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()


                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@EnglishShabdjaalPlayGameActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
                    val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, EnglishShabdjaalPlayGameActivity::class.java)
                    intent.putExtra("Date", puzzleGameDate)
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