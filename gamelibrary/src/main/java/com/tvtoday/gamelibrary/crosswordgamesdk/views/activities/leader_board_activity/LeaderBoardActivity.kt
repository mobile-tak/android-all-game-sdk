package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity

import PrefData
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.clevertap.android.sdk.CleverTapAPI
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tvtoday.crosswordhindi.controller.*
import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.Constants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.IntentConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.enum.ClickedItem
import com.tvtoday.gamelibrary.crosswordgamesdk.model.submitGame.SubmitGameReuquest
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.waitingactivity.WaitingActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.adapter.LeaderBoardResultAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class LeaderBoardActivity : VargPaheliBaseActivity(), View.OnClickListener {

    private var puzzleGameDate: String? = ""
    private var gso: GoogleSignInOptions?=null
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()

    var clicked_Item : ClickedItem?=null

    //recyclerView
    private var rv_getLeaderboard_List : RecyclerView?=null

    private var mInterstitialAd: InterstitialAd? = null

    //textView
    private var tvRankForth:TextView?=null
    private var tvRankForthName :TextView?=null
    private var tvRankForthGameTime :TextView?=null
    private var tv_name_one :TextView?=null
    private var tv_name_two :TextView?=null
    private var tv_name_three :TextView?=null
    private var tvGameTime :TextView?=null
    private var tvGSingingBtn :TextView?=null

    private var rlLeaderBoardMain:RelativeLayout?=null

    private var iv_back_btn :ImageView?=null
    private var ivChangeLanguage :ImageView?=null

    //Strings
    private var minutes: Long = 0
    private  var seconds:Long = 0
    private var minute: String? = ""
    private  var second:String? = ""
    private var gameUserId:String? =""
    private var languageId:String? =""

    private var type :String = ""
    //relative layout
    private var rl_two :RelativeLayout?=null
    private var rl_one :RelativeLayout?=null
    private var rl_three :RelativeLayout?=null
    private var tvPlayPastPuzzles :TextView?=null

    private var rl_share_btn:RelativeLayout?=null
    private var lLayForthPosition:LinearLayout?=null


    //arrayList
    private val leaderBoardDataList : ArrayList<LeaderBoardDataList> = ArrayList()
    //leaderBoardResultModel
    private val leaderBoardResultModel : LeaderBoardDataList?=null

    private lateinit var leaderBoardResultAdapter: LeaderBoardResultAdapter

    private val RC_SIGN_IN = 1
    var mGoogleSignInClient: GoogleSignInClient? = null

    var gameTime:String? = null
    var gameStatus:String? = null

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        interstitialAdd()

        try{
            gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build()
            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso!!)

            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun initViews() {
        gameUserId = PrefData.getStringPrefs(this,PrefData.Key.GAME_USER_ID)
        Log.e("userIdLe",gameUserId.toString())
        gameTime = intent.getStringExtra(IntentConstants.GAME_TIME)
        gameStatus = intent.getStringExtra(IntentConstants.GAME_STATUS)
        puzzleGameDate = intent.getStringExtra("GAME_DATE_FOR_LEADER_BOARD")
        type= intent.getStringExtra("type_for_back_button").toString()

       // tvPlayPastPuzzles= findViewById(R.id.tvPlayPastPuzzles)
       languageId = intent.getStringExtra("lanuageID")

        if(PrefData.getStringPrefs(this, PrefData.Key.GAME_USER_ID) == null){
            findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.VISIBLE
        }else{
            findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.GONE
        }

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){

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
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH)

            if(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.GAME_USER_ID))) {
                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID))){
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

            if(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                VargPaheliLanguagePreference.getInstance(baseContext).language = "hi"
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID))){
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


        tvPlayPastPuzzles?.setOnClickListener{
            val intent = Intent(this@LeaderBoardActivity, PastPuzzleCrosswordActivity::class.java)
            startActivity(intent)
            finish()
        }


        //forth rank user details textView
        tvRankForth = findViewById(R.id.tvRankForth)
        tvRankForthName = findViewById(R.id.tvRankForthName)
        tvRankForthGameTime = findViewById(R.id.tvRankForthGameTime)
        lLayForthPosition = findViewById(R.id.lLayForthPosition)

        iv_back_btn = findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        ivChangeLanguage = findViewById(R.id.ivChangeLanguage)
        ivChangeLanguage?.setOnClickListener(this)

        tvGSingingBtn = findViewById(R.id.tv_google_sign_in)
        tvGSingingBtn?.setOnClickListener(this)

        rlLeaderBoardMain = findViewById(R.id.rlLeaderBoardMain)

        //rank pillar user name
        tv_name_one = findViewById(R.id.tv_name_one)
        tv_name_two = findViewById(R.id.tv_name_two)
        tv_name_three = findViewById(R.id.tv_name_three)

        //relativeLayout
        rl_three = findViewById(R.id.rl_three)
        rl_two= findViewById(R.id.rl_two)
        rl_one = findViewById(R.id.rl_one)

        rl_share_btn = findViewById(R.id.rl_share_btn)
        rl_share_btn?.setOnClickListener(this)

        //recyclerView
        rv_getLeaderboard_List = findViewById(R.id.rv_getLeaderboard_List)

        if(gameUserId != null){
            val getLeaderBoardRequest = GetLeaderBoardRequest()
            getLeaderBoardRequest.gameUserId = gameUserId
            getLeaderBoardRequest.game_date = puzzleGameDate
            getLeaderBoardRequest.lang_id =languageId
            getLeaderBoardRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
            getLeaderBoardCrossword(getLeaderBoardRequest)
        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(
            signInIntent,
            101
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 101) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val acct = completedTask.getResult(ApiException::class.java)
          //  val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {

                PrefData.setBooleanPrefs(this@LeaderBoardActivity,PrefData.Key.ENTERFROMGUEST, false)
                val signupRequest = SignUpRequest()

                val email = acct.email
                val name  = acct.displayName
                val userId =acct.id?.toString()
                val profile  = Uri.parse(acct.photoUrl.toString())

                signupRequest.email = email
                signupRequest.name = name
                signupRequest.uname = name
                signupRequest.userId =userId
                // val personPhoto = Uri.parse(acct.photoUrl.toString())
                signupRequest.profileimage = profile.toString()
                PrefData.saveUserDetails(this, name!!,name,email!!, profile.toString())

                signUpUser(signupRequest)
            }

        } catch (e: ApiException) {
            Log.e("exceptionn", "$e.statusCode")
        }
    }

    private fun signUpUser(addUserRequest: SignUpRequest?) {
        compositeDisposable.add(
            apiService!!.signUpUser(addUserRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("response:", response.toString())
                    if (response?.status == "true") {
                        findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.GONE

                        val gameUserId = response.data?.id
                        PrefData.setStringPrefs(this, PrefData.Key.GAME_USER_ID,gameUserId.toString())

                        try{
                            var id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                            val  profileUpdate =  HashMap<String, Any>()
                            profileUpdate["Identity"] = id
                            profileUpdate["Email"] = response?.data?.email.toString()
                            profileUpdate["Name"] = response?.data?.name.toString()
                            profileUpdate["MSG-push"] = true
                            profileUpdate["MSG-email"] = true

                            val cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext);
                            cleverTapAPI?.pushProfile(profileUpdate);
                        }catch (ex:Exception){
                        }

                        if(gameTime != null){

                            if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                                PrefData.getAppLangaugeStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                                val submitGameRequest = SubmitGameReuquest()
                                submitGameRequest.gameStatus = gameStatus
                                submitGameRequest.gameUserId = gameUserId.toString()
                                submitGameRequest.time = gameTime
                                submitGameRequest.lang_id="1"
                                submitGameRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
                                submitGame(submitGameRequest)

                            }else{
                                val submitGameRequest = SubmitGameReuquest()
                                submitGameRequest.gameStatus = gameStatus
                                submitGameRequest.gameUserId = gameUserId.toString()
                                submitGameRequest.time = gameTime
                                submitGameRequest.lang_id = "2"
                                submitGameRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
                                submitGame(submitGameRequest)
                            }

                        }else{
                            if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                                PrefData.getAppLangaugeStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                                val getLeaderBoardRequest = GetLeaderBoardRequest()
                                getLeaderBoardRequest.gameUserId = gameUserId.toString()
                                getLeaderBoardRequest.game_date = puzzleGameDate
                                getLeaderBoardRequest.lang_id = "1"
                                getLeaderBoardRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
                                getLeaderBoardCrossword(getLeaderBoardRequest)

                            }else{

                                val getLeaderBoardRequest = GetLeaderBoardRequest()
                                getLeaderBoardRequest.gameUserId = gameUserId.toString()
                                getLeaderBoardRequest.game_date = puzzleGameDate
                                getLeaderBoardRequest.lang_id = "2"
                                getLeaderBoardRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
                                getLeaderBoardCrossword(getLeaderBoardRequest)

                            }
                        }
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getLeaderBoardCrossword(add_game_user_id : GetLeaderBoardRequest) {
        compositeDisposable.add(
            apiService!!.getLeaderBoardCrossWord(add_game_user_id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("response:", response!!.data.toString())
                    if (response.status == "true") {
                        rlLeaderBoardMain?.visibility=View.VISIBLE
                        findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.GONE

                        leaderBoardDataList.addAll(response.data)

                        getLeadBoardData(response.data)

                        Log.d("print",leaderBoardDataList.size.toString())

                        if(leaderBoardDataList.size>3){
                             setDataInViews(leaderBoardDataList[3])
                            leaderBoardResultAdapter = LeaderBoardResultAdapter(this, leaderBoardDataList)
                            rv_getLeaderboard_List?.adapter = leaderBoardResultAdapter
                            leaderBoardResultAdapter.notifyDataSetChanged()
                        }else{
                            leaderBoardResultAdapter = LeaderBoardResultAdapter(this, leaderBoardDataList)
                            rv_getLeaderboard_List?.adapter = leaderBoardResultAdapter
                            leaderBoardResultAdapter.notifyDataSetChanged()
                        }
                    }
                    else if(response.status == "false"){

                        Toast.makeText(applicationContext, "Data not found.", Toast.LENGTH_LONG).show()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }

    private fun submitGame(submitGameRequest: SubmitGameReuquest) {
        compositeDisposable.add(
            apiService!!.gameSubmitCrossWord(submitGameRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("submitGameResponse:", response.toString())
                    if (response?.status == "true") {

                        if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                            PrefData.getAppLangaugeStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                            val getLeaderBoardRequest = GetLeaderBoardRequest()
                            getLeaderBoardRequest.gameUserId = PrefData.getStringPrefs(applicationContext, PrefData.Key.GAME_USER_ID)
                            getLeaderBoardRequest.game_date = puzzleGameDate
                            getLeaderBoardRequest.lang_id = "1"
                            getLeaderBoardRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
                            getLeaderBoardCrossword(getLeaderBoardRequest)
                        }else{
                                val getLeaderBoardRequest = GetLeaderBoardRequest()
                                getLeaderBoardRequest.gameUserId = PrefData.getStringPrefs(applicationContext, PrefData.Key.GAME_USER_ID)
                                getLeaderBoardRequest.game_date = puzzleGameDate
                                getLeaderBoardRequest.lang_id = "2"
                            getLeaderBoardRequest.app_id = PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_ID)
                            getLeaderBoardCrossword(getLeaderBoardRequest)
                        }
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }


    private fun getLeadBoardData(leadBoardData: ArrayList<LeaderBoardDataList>){
        if(leaderBoardDataList.size == 0){
            rl_one?.visibility = View.GONE
            rl_two?.visibility = View.GONE
            rl_three?.visibility = View.GONE
            lLayForthPosition?.visibility = View.GONE
        }

        if(leadBoardData.size == 1){
            rl_one?.visibility = View.VISIBLE
            rl_two?.visibility = View.INVISIBLE
            rl_three?.visibility = View.INVISIBLE
            tv_name_one?.text = leadBoardData[0].name
        }else if(leadBoardData.size == 2){
            rl_one?.visibility = View.VISIBLE
            rl_two?.visibility = View.VISIBLE
            rl_three?.visibility = View.INVISIBLE
            tv_name_one?.text = leadBoardData[0].name
            tv_name_two?.text = leadBoardData[1].name
        }else if(leadBoardData.size ==3){
            rl_one?.visibility = View.VISIBLE
            rl_two?.visibility = View.VISIBLE
            rl_three?.visibility = View.VISIBLE
            tv_name_one?.text = leadBoardData[0].name
            tv_name_two?.text = leadBoardData[1].name
            tv_name_three?.text = leadBoardData[2].name
        }else if(leadBoardData.size == 4){
            rl_one?.visibility = View.VISIBLE
            rl_two?.visibility = View.VISIBLE
            rl_three?.visibility = View.VISIBLE
            tv_name_one?.text = leadBoardData[0].name
            tv_name_two?.text = leadBoardData[1].name
            tv_name_three?.text = leadBoardData[2].name
            lLayForthPosition?.visibility=View.VISIBLE
        }
    }

    private fun setDataInViews(resultList : LeaderBoardDataList) {
        minutes = resultList.time?.toLong()!! / 60
        seconds = (resultList.time.toInt() % 60).toLong()
        minute = String.format("%02d", minutes)
        second = String.format("%02d", seconds)
        tvRankForthGameTime?.text = "$minute : $second"
        tvRankForth?.text = resultList.rank.toString()
        tvRankForthName?.text = resultList.name.toString()
        tvRankForthGameTime?.text = resultList.time.toString()
    }

    override fun onClick(v: View?) {

      when(v?.id){
          R.id.tv_google_sign_in -> {
              signIn()
              CleverTapEvent(this).createOnlyEvent(
                  CleverTapEventConstants.LB_GOOGLE_LOGIN)
          }

          R.id.rl_share_btn->{

              clicked_Item = ClickedItem.SHARE_BUTTON
              loadAdd()

              CleverTapEvent(this).createOnlyEvent(
                  CleverTapEventConstants.LB_SHARE)
              // takeScreenShot(window.decorView)
          }

          R.id.ivChangeLanguage ->{

       //  openLanguageBottomSheet()
              /*  val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.putExtra("called_game_id", 3)
                    startActivity(intent)
                } catch (ignored: ClassNotFoundException) {
                }*/


              /*  val activityToStart =
                    "com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity"
                try {
                    val c = Class.forName(activityToStart)
                    val intent = Intent(this, c)
                    intent.putExtra("called_game_id", 3)
                    startActivity(intent)
                } catch (ignored: ClassNotFoundException) {
                }*/
          }

          R.id.iv_back_btn -> {
              if(PrefData.getBooleanPrefs(this, PrefData.Key.FOR_HURREY_SCREEN)){
                  CommonUtils.performIntentFinish(this, WaitingActivity::class.java)
              }else{
                  if(!TextUtils.isEmpty(type) && type == "2"){
                      loadAdd()
                      intent = Intent(this@LeaderBoardActivity, VargPaheliGameActivity::class.java)
                      startActivity(intent)
                      finish()
                  }else{
                      loadAdd()
                      CommonUtils.backPress(this)
                  }

              }
          }
      }
    }


    override fun onBackPressed() {
        if(PrefData.getBooleanPrefs(this, PrefData.Key.FOR_HURREY_SCREEN)){
            CommonUtils.performIntentFinish(this, WaitingActivity::class.java)
        }else{
            CommonUtils.backPress(this)
        }
        super.onBackPressed()
    }


    private fun interstitialAdd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_INTERTITIALS_AD_ID)!!, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    //Log.i(TAG, "onAdLoaded");

                    if(clicked_Item == ClickedItem.SHARE_BUTTON){
                        takeScreenShot(window.decorView)
                    }

                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    //Log.i(TAG, loadAdError.getMessage());
                    if(clicked_Item == ClickedItem.SHARE_BUTTON){
                        takeScreenShot(window.decorView)
                    }
                    mInterstitialAd = null
                }
            })
    }

    private fun loadAdd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
            mInterstitialAd?.setFullScreenContentCallback(object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    interstitialAdd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    interstitialAdd()
                }
            })
        } else {

            if(clicked_Item == ClickedItem.SHARE_BUTTON){
                takeScreenShot(window.decorView)
            }

            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }



    private fun takeScreenShot(view: View) {
        val date = Date()
        val format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date)
        try {
            val mainDir = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare"
            )
            if (!mainDir.exists()) {
                val mkdir = mainDir.mkdir()
            }
            val path = "$mainDir/TrendOceans-$format.jpeg"
            view.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false
            val imageFile = File(path)
            val fileOutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            shareScreenShot(imageFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //Share ScreenShot
    private fun shareScreenShot(imageFile: File) {
        val uri = FileProvider.getUriForFile(
            this,
            PrefData.getStringPrefs(this,"applicationId")+".leader_board_activity.provider",
            imageFile
        )

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out my Leader board score for Crossword – word guess game in Hindi, think you can beat this \n https://onelink.to/4e9bge");
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show()
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

        if(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
            PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")){
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

            if(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                lLayHindiLang.isClickable =false

            }else{

                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.HINDI)
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.HINDI)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.GAME_USER_ID))) {
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

            if(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {

                lLayEnglishLang?.isClickable =false

            }else{

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()

                VargPaheliLanguagePreference.getInstance(baseContext).language = ""
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_LANGAUGE,PrefData.Key.ENGLISH)
                PrefData.setStringPrefs(this@LeaderBoardActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,PrefData.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@LeaderBoardActivity,PrefData.Key.GAME_USER_ID))) {
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