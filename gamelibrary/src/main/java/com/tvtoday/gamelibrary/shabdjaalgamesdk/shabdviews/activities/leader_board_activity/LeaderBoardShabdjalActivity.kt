package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.leader_board_activity

import PrefDataShabdjal
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
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
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.submitGame.SubmitGameReuquestShabd
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.constants.ConstantsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.constants.IntentConstantsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.enum.ClickedItemShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.VargPaheliGameShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.waitingactivity.WaitingShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.adapter.LeaderBoardResultShabdAdapter
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class LeaderBoardShabdjalActivity : ShabdjalBaseActivity(), View.OnClickListener {

    private var gso: GoogleSignInOptions?=null
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()

    //recyclerView
    private var rv_getLeaderboard_List : RecyclerView?=null

    private var mInterstitialAd: InterstitialAd? = null

    //textView
    private var tvRankForth:TextView?=null
    private var tvOk:TextView?=null
    private var tvRankForthName :TextView?=null
    private var tvRankForthGameTime :TextView?=null
    private var tv_name_one :TextView?=null
    private var tv_name_two :TextView?=null
    private var tv_name_three :TextView?=null
    private var tvGameTime :TextView?=null
    private var tvGSingingBtn :TextView?=null

    private var iv_back_btn :ImageView?=null
    private var ivLangChange :ImageView?=null

    var clicked_Item : ClickedItemShabdjal?=null


    //Strings
    private var minutes: Long = 0
    private  var seconds:Long = 0
    private var minute: String? = ""
    private  var second:String? = ""
    private var gameUserId:String? =""

    private var puzzleGameDate:String? =""

    //relative layout
    private var rl_two :RelativeLayout?=null
    private var rl_one :RelativeLayout?=null
    private var rl_three :RelativeLayout?=null

    private var rl_share_btn:RelativeLayout?=null
    private var lLayForthPosition:LinearLayout?=null

    private var ll_google_sign_in:LinearLayout?=null
    private var rlLeaderBoarMain:RelativeLayout?=null
    private var tvPlayPastPuzzles:TextView?=null

    //arrayList
    private val leaderBoardDataList : ArrayList<LeaderBoardDataList> = ArrayList()
    //leaderBoardResultModel
    private val leaderBoardResultModel : LeaderBoardDataList?=null

    private lateinit var leaderBoardResultAdapter: LeaderBoardResultShabdAdapter

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
        setContentView(R.layout.activity_leader_board_shabd)
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

        gameUserId = PrefDataShabdjal.getStringPrefs(this,PrefDataShabdjal.Key.GAME_USER_ID)
        gameTime = intent.getStringExtra(IntentConstantsShabdjal.GAME_TIME)
        gameStatus = intent.getStringExtra(IntentConstantsShabdjal.GAME_STATUS)
        puzzleGameDate = intent.getStringExtra("GAME_DATE_FOR_LEADER_BOARD")

        //tvPlayPastPuzzles = findViewById(R.id.tvPlayPastPuzzles)

        if(PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID) == null){
            findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.VISIBLE
        }else{
            findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.GONE
        }

        ll_google_sign_in = findViewById(R.id.ll_google_sign_in)

        //forth rank user details textView
        tvRankForth = findViewById(R.id.tvRankForth)
        rlLeaderBoarMain = findViewById(R.id.rlLeaderBoarMain)
        tvRankForthName = findViewById(R.id.tvRankForthName)
        tvRankForthGameTime = findViewById(R.id.tvRankForthGameTime)
        lLayForthPosition = findViewById(R.id.lLayForthPosition)

        iv_back_btn = findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        ivLangChange = findViewById(R.id.ivLangChange)
        ivLangChange?.setOnClickListener(this)

        tvGSingingBtn = findViewById(R.id.tv_google_sign_in)
        tvGSingingBtn?.setOnClickListener(this)

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

            if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                val getLeaderBoardRequest = GetLeaderBoardShabdRequest()
                getLeaderBoardRequest.gameUserId = gameUserId
                getLeaderBoardRequest.game_date = puzzleGameDate
                getLeaderBoardRequest.lang_id = "1"
                getLeaderBoardRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                getLeaderBoardCrossword(getLeaderBoardRequest)
            }else{
                val getLeaderBoardRequest = GetLeaderBoardShabdRequest()
                getLeaderBoardRequest.gameUserId = gameUserId
                getLeaderBoardRequest.game_date = puzzleGameDate
                getLeaderBoardRequest.lang_id = "2"
                getLeaderBoardRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                getLeaderBoardCrossword(getLeaderBoardRequest)
            }

        }

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")){

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

            if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID))) {
                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
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

            if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = "hi"
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID))) {

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){
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

                val signupRequest = SignUpRequest()

                val email = acct.email
                val name  = acct.displayName
                val userId =acct.id?.toString()
                val profile  = Uri.parse(acct.photoUrl.toString())

                signupRequest.email = email
                signupRequest.name = name
                signupRequest.uname = name
               // signupRequest.userId =userId
                // val personPhoto = Uri.parse(acct.photoUrl.toString())
                signupRequest.profileimage = profile.toString()
                PrefDataShabdjal.saveUserDetails(this, name!!,name,email!!, profile.toString())

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
                        ll_google_sign_in?.visibility = View.GONE

                        val gameUserId = response.data?.id
                        PrefDataShabdjal.setStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID,gameUserId.toString() )

                        if(gameTime != null){

                            if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                                PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                                val submitGameRequest = SubmitGameReuquestShabd()
                                submitGameRequest.gameStatus = gameStatus
                                submitGameRequest.gameUserId = gameUserId.toString()
                                submitGameRequest.time = gameTime
                                submitGameRequest.lang_id="1"
                                submitGameRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                                submitGame(submitGameRequest)
                            }else{
                                val submitGameRequest = SubmitGameReuquestShabd()
                                submitGameRequest.gameStatus = gameStatus
                                submitGameRequest.gameUserId = gameUserId.toString()
                                submitGameRequest.time = gameTime
                                submitGameRequest.lang_id="2"
                                submitGameRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                                submitGame(submitGameRequest)
                            }


                        }else{
                            if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                                PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {

                                val getLeaderBoardRequest = GetLeaderBoardShabdRequest()
                                getLeaderBoardRequest.gameUserId = gameUserId.toString()
                                getLeaderBoardRequest.game_date = puzzleGameDate
                                getLeaderBoardRequest.lang_id = "1"
                                getLeaderBoardRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                                getLeaderBoardCrossword(getLeaderBoardRequest)
                            }else{
                                val getLeaderBoardRequest = GetLeaderBoardShabdRequest()
                                getLeaderBoardRequest.gameUserId = gameUserId.toString()
                                getLeaderBoardRequest.game_date = puzzleGameDate
                                getLeaderBoardRequest.lang_id = "2"
                                getLeaderBoardRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                                getLeaderBoardCrossword(getLeaderBoardRequest)
                            }

                        }
                    }
                }) { throwable ->
                    ll_google_sign_in?.visibility = View.GONE
                    Log.d("Error", "" + throwable)
                })
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun getLeaderBoardCrossword(add_game_user_id : GetLeaderBoardShabdRequest) {
        compositeDisposable.add(
            apiService!!.getLeaderBoardCrossWord(add_game_user_id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("response:", response.toString())
                    if (response?.status == "true") {
                        rlLeaderBoarMain?.visibility=View.VISIBLE
                        findViewById<LinearLayout>(R.id.ll_google_sign_in).visibility = View.GONE

                        leaderBoardDataList.addAll(response.data)

                        getLeadBoardData(response.data)

                        Log.d("print",leaderBoardDataList.size.toString())

                        if(leaderBoardDataList.size>3){
                             setDataInViews(leaderBoardDataList[3])
                            leaderBoardResultAdapter = LeaderBoardResultShabdAdapter(this, leaderBoardDataList)
                            rv_getLeaderboard_List?.adapter = leaderBoardResultAdapter
                            leaderBoardResultAdapter.notifyDataSetChanged()
                        }else{
                            leaderBoardResultAdapter = LeaderBoardResultShabdAdapter(this, leaderBoardDataList)
                            rv_getLeaderboard_List?.adapter = leaderBoardResultAdapter
                            leaderBoardResultAdapter.notifyDataSetChanged()
                        }
                    }
                    else{
                        noLeaderBoardFound()
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }

    private fun noLeaderBoardFound() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.no_leader_board_data)
        dialog.setCanceledOnTouchOutside(false);
        // set height and width
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT

        /* val tvQuestionInfo = dialog.findViewById<TextView>(R.id.tvQuestionInfo)
        tvQuestionInfo?.text = tvQuestionInfo.toString()*/
      val tvOk = dialog.findViewById<TextView>(R.id.tvOk)
        tvOk?.setOnClickListener {

            if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))){

                if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                    PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
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
                val intent = Intent(this@LeaderBoardShabdjalActivity, PastPuzzleActivityShabdjaal::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }
        }
        // set to custom layout
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.CENTER
        // find TextView and set Error
        dialog.show()
    }

    private fun submitGame(submitGameRequest: SubmitGameReuquestShabd) {
        compositeDisposable.add(
            apiService!!.gameSubmitCrossWord(submitGameRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("submitGameResponse:", response.toString())
                    if (response?.status == "true") {

                        if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                            PrefDataShabdjal.getAppLanguageStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                            val getLeaderBoardRequest = GetLeaderBoardShabdRequest()
                            getLeaderBoardRequest.gameUserId = PrefDataShabdjal.getStringPrefs(applicationContext, PrefDataShabdjal.Key.GAME_USER_ID)
                            getLeaderBoardRequest.game_date =puzzleGameDate
                            getLeaderBoardRequest.lang_id = "1"
                            getLeaderBoardRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                            getLeaderBoardCrossword(getLeaderBoardRequest)
                        }else{
                            val getLeaderBoardRequest = GetLeaderBoardShabdRequest()
                            getLeaderBoardRequest.gameUserId = PrefDataShabdjal.getStringPrefs(applicationContext, PrefDataShabdjal.Key.GAME_USER_ID)
                            getLeaderBoardRequest.game_date =puzzleGameDate
                            getLeaderBoardRequest.lang_id = "2"
                            getLeaderBoardRequest.app_id = PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
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
    }

    override fun onClick(v: View?) {

      when(v?.id){
          R.id.tv_google_sign_in -> {
              signIn()
              CleverTapEventShabdjal(this).createOnlyEvent(
                  CleverTapShabdjalConstants.LB_GOOGLE_LOGIN)
          }

       R.id.ivLangChange ->{
          // openLanguageBottomSheet()
       }

          R.id.rl_share_btn->{
              clicked_Item = ClickedItemShabdjal.SHARE_BUTTON
             // takeScreenShot(window.decorView)
              loadAdd()

              CleverTapEventShabdjal(this).createOnlyEvent(
                  CleverTapShabdjalConstants.LB_SHARE)
              // takeScreenShot(window.decorView)
          }

          R.id.iv_back_btn -> {

              if(PrefDataShabdjal.getBooleanPrefs(this, PrefDataShabdjal.Key.FOR_HURREY_SCREEN)){
                  CommonUtilsShabdjal.performIntentFinish(this, WaitingShabdActivity::class.java)
              }else{
                  loadAdd()
                  CommonUtilsShabdjal.backPress(this)
              }
          }
      }
    }

    override fun onBackPressed() {
        if(PrefDataShabdjal.getBooleanPrefs(this, PrefDataShabdjal.Key.FOR_HURREY_SCREEN)){
            CommonUtilsShabdjal.performIntentFinish(this, WaitingShabdActivity::class.java)
        }else{
            CommonUtilsShabdjal.backPress(this)
        }
        super.onBackPressed()
    }


    private fun interstitialAdd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_INTERTITIAL_ID)!!, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd

                    if(clicked_Item == ClickedItemShabdjal.SHARE_BUTTON){
                        takeScreenShot(window.decorView)
                    }
                    //Log.i(TAG, "onAdLoaded");
                    //  takeScreenShot(window.decorView)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {

                    if(clicked_Item == ClickedItemShabdjal.SHARE_BUTTON){
                        takeScreenShot(window.decorView)
                    }
                    // Handle the error
                    //Log.i(TAG, loadAdError.getMessage());
                    //  takeScreenShot(window.decorView)
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
                    //takeScreenShot(window.decorView)
                    interstitialAdd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    //takeScreenShot(window.decorView)
                    interstitialAdd()
                }
            })
        } else {
            takeScreenShot(window.decorView)
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
            PrefDataShabdjal.getStringPrefs(this,"applicationId")+".leader_board_activity.provider",
            imageFile
        )

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out my Leader board score for Shabdjaal – word guess game in Hindi, think you can beat this https://onelink.to/4e9bge");
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

        val view = this.layoutInflater.inflate(R.layout.shabdjaal_language_bottom_sheet_layout_, null)

        val lLayHindiLang: LinearLayout? = view.findViewById(R.id.lLayHindiLang)
        val lLayEnglishLang: LinearLayout? = view.findViewById(R.id.lLayEnglishLang)

        val tvHindiLang: TextView? = view.findViewById(R.id.tvHindiLang)
        val tvEngLang: TextView? = view.findViewById(R.id.tvEngLang)
        //  val lLayQuitGame: LinearLayout? = view.findViewById(R.id.lLayQuitGame)

        if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")){
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

            if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
                lLayHindiLang.isClickable = false
            }else{

                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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

            if(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("english")) {
                lLayEnglishLang?.isClickable = false
            }else{
                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()


                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@LeaderBoardShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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