package com.tvtoday.gamelibrary.core.views.activity.landingActivity

import PrefData
import PrefDataShabdjal
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.clevertap.android.sdk.CleverTapAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

import com.tvtoday.gamelibrary.core.views.activity.homePage.HomeActivity
import com.tvtoday.gamelibrary.core.views.activity.languageSelection.LanguageSelectionActivity
import com.tvtoday.crosswordhindi.views.controller.MainCommonPref.MainCommonPref
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.core.controller.utils.ClikedEnums
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.IntentConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpActivity
import com.tvtoday.gamelibrary.shabdamgamesdk.model.SignupRequest
import com.tvtoday.gamelibrary.shabdamgamesdk.pref.CommonPreference
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignUpLandingActivity : AppCompatActivity() , View.OnClickListener{
    //deceleration views------------------------------------------------s
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()
    private var shabdamApiService:com.tvtoday.gamelibrary.shabdamgamesdk.network.ApiService = com.tvtoday.gamelibrary.shabdamgamesdk.network.RetrofitClient.getInstance()
    private var apiServiceshabdjal: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()
    //google SignupButton
    private var lLyaGoogleSignUpBtn: LinearLayout?=null
    private var lLayGuestBtn: LinearLayout?=null

    private var  clikedEnum : ClikedEnums?=null

    private val RC_SIGN_IN = 89
    var gso: GoogleSignInOptions? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var device_token:String?=null
    var device_type:String?=null
    var iShabdamLoggedIn = false
    var isCrosswordLoggedIn = false
    var isShabdjalLoggedIn = false
    var isGameLaunched = false
    var isGuestLogin = false

    private var tvForTest: AppCompatTextView?=null

    companion object{
        private var TAG = SignUpActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_sign_up_landing)

            var id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

            PrefData.setStringPrefs(this@SignUpLandingActivity, PrefData.Key.DEVICE_ID,id)
            PrefDataShabdjal.setStringPrefs(this@SignUpLandingActivity, PrefDataShabdjal.Key.DEVICE_ID,id.toString())
            CommonPreference.getInstance(this@SignUpLandingActivity).put(CommonPreference.Key.DEVICE_ID,id.toString())


            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                try {
                    var token = task.result
                    PrefData.setStringPrefs(this,PrefData.Key.DEVICE_TOKEN, token.toString())
                    if (token != null) {
                        Log.d("beemen", token!!)
                    }
                    if (token != null) {
                        CleverTapAPI.getDefaultInstance(this@SignUpLandingActivity)!!
                            .pushFcmRegistrationId(token, true)
                    }

                    val userId =  PrefData.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.GAME_USER_ID)

                    /*val gameUserTokenUpdateRequest = GameUserUpdateTokenRequest()
                    gameUserTokenUpdateRequest.deviceToken = token.toString()
                    gameUserTokenUpdateRequest.gameUserId = userId.toString()
                    gameUserTokenUpdateRequest.deviceType = IntentConstants.DEVICE_TYPE
                    Log.i("params:" ,gameUserTokenUpdateRequest.toString())

                    gameUserUpdateTokenCrossword(gameUserTokenUpdateRequest)*/

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val email = PrefData.getStringPrefs(this, PrefData.Key.EMAIL)

        device_token = PrefData.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.DEVICE_TOKEN)
        device_type= PrefData.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.DEVICE_TYPE)

        Log.d("email", email.toString())


        /*if(PrefData.getBooleanPrefs(this, PrefData.Key.ISRuleHow)){
                CommonUtils.performIntentFinish(this@SignUpActivity, VargPaheliGameActivity::class.java)
            }else{
            CommonUtils.performIntent(this,GameRuleActivity::class.java)
            }*/

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestProfile()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso!!)

        if (mGoogleSignInClient!=null){
            mGoogleSignInClient?.signOut()

            //  PrefData.clearWholePreference(this)
        }
        initViews()
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
    }

    private fun initViews() {
        //initialisation views--------------------------------------

        lLyaGoogleSignUpBtn = findViewById(R.id.lLyaGoogleSignUpBtn)
        lLyaGoogleSignUpBtn?.setOnClickListener(this)

        lLayGuestBtn = findViewById(R.id.lLayGuestBtn)
        lLayGuestBtn?.setOnClickListener(this)

        tvForTest = findViewById(R.id.tvTest)
        tvForTest?.setOnClickListener {

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.lLyaGoogleSignUpBtn ->{
                CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.GOOGLE_SIGNUP)
                isGuestLogin = false
                PrefData.setBooleanPrefs(this@SignUpLandingActivity,PrefData.Key.ENTERFROMGUEST,false)
                signIn()
            }

            R.id.lLayGuestBtn ->{
                isGuestLogin = true
                clikedEnum = ClikedEnums.GUESTBUTTONCLICKED
                CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.PLAY_GUEST)


              PrefData.setBooleanPrefs(this@SignUpLandingActivity,PrefData.Key.ENTERFROMGUEST,true)

                if(isGuestLogin){
                    try{
                        val signupRequest = com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest()
                        val shabdamSignUpRequest = SignupRequest()

                        signupRequest.email = ""
                        signupRequest.name = ""
                        signupRequest.uname = ""
                        signupRequest.device_type = IntentConstants.DEVICE_TYPE
                        signupRequest.device_token = ""

                        // val personPhoto = Uri.parse(acct.photoUrl.toString())

                        signupRequest.device_id = PrefData.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.DEVICE_ID)

                            signupRequest.is_guest = true

                        signUpUser(signupRequest)

                        //for shabdham ------------------
                        shabdamSignUpRequest.email = ""
                        shabdamSignUpRequest.name = ""
                        shabdamSignUpRequest.uname = ""
                        shabdamSignUpRequest.device_type = IntentConstants.DEVICE_TYPE
                        shabdamSignUpRequest.device_token = ""
                        shabdamSignUpRequest.device_id = CommonPreference.getInstance(this).getString(CommonPreference.Key.DEVICE_ID)

                            shabdamSignUpRequest.is_guest = true

                        Log.e("shabdam_sign_param",shabdamSignUpRequest.toString())
                        loginShabdam(shabdamSignUpRequest)

                        //for shabdjal
                        var shabdjalRequest = com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpRequest()
                        shabdjalRequest.email = ""
                        shabdjalRequest.name = ""
                        shabdjalRequest.uname = ""
                        shabdjalRequest.device_type = IntentConstants.DEVICE_TYPE
                        shabdjalRequest.device_token = ""
                        shabdjalRequest.device_id = PrefDataShabdjal.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.DEVICE_ID)

                            shabdjalRequest.is_guest = true

                        Log.e("shabdjaal_sign_param",shabdjalRequest.toString())

                        loginShabdjal(shabdjalRequest)
                    }catch (e:Exception){

                    }

                }


               /* PrefData.setBooleanPrefs(this@SignUpLandingActivity,PrefData.Key.IS_GUEST_USER,true)

                if(!MainCommonPref.getBooleanPrefs(this@SignUpLandingActivity,MainCommonPref.Key.IS_FIRST_INSTALLED)){
                    MainCommonPref.setBooleanPrefs(this@SignUpLandingActivity, MainCommonPref.Key.IS_FIRST_INSTALLED,true)
                    val intent = Intent(this, LanguageSelectionActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }*/
            }
        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account = completedTask.getResult(ApiException::class.java)
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {

                val signupRequest = com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest()
                val shabdamSignUpRequest = SignupRequest()
                val email = acct.email
                val name  = acct.displayName
                val userId =acct.id?.toString()
                val profile  = Uri.parse(acct.photoUrl.toString())

                signupRequest.email = email
                signupRequest.name = name
                signupRequest.uname = name
                signupRequest.device_type = IntentConstants.DEVICE_TYPE
                signupRequest.device_token = PrefData.getStringPrefs(this,PrefData.Key.DEVICE_TOKEN)

                // val personPhoto = Uri.parse(acct.photoUrl.toString())

                if(profile !=null){
                    signupRequest.profileimage = profile.toString()
                }
                signupRequest.device_id = PrefData.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.DEVICE_ID)

                if(clikedEnum == ClikedEnums.GUESTBUTTONCLICKED){
                    signupRequest.is_guest = true
                }else{
                    signupRequest.is_guest = false
                }


                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) ){

                    PrefData.saveUserDetails(this, name!!,name,email!!, profile.toString())
                }
                signUpUser(signupRequest)

                //for shabdham -------------------
                shabdamSignUpRequest.email = email
                shabdamSignUpRequest.name = name
                shabdamSignUpRequest.uname = name
                shabdamSignUpRequest.device_type = IntentConstants.DEVICE_TYPE
                shabdamSignUpRequest.device_token = PrefData.getStringPrefs(this,PrefData.Key.DEVICE_TOKEN)
                shabdamSignUpRequest.device_id = CommonPreference.getInstance(this).getString(CommonPreference.Key.DEVICE_ID)

                if(clikedEnum == ClikedEnums.GUESTBUTTONCLICKED){
                    shabdamSignUpRequest.is_guest = true
                }else{
                    shabdamSignUpRequest.is_guest = false
                }

                Log.e("shabdam_sign_param",shabdamSignUpRequest.toString())
                loginShabdam(shabdamSignUpRequest)

                //for shabdjal
                var shabdjalRequest = com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpRequest()
                shabdjalRequest.email = email
                shabdjalRequest.name = name
                shabdjalRequest.uname = name
                shabdjalRequest.device_type = IntentConstants.DEVICE_TYPE
                shabdjalRequest.device_token = PrefData.getStringPrefs(this,PrefData.Key.DEVICE_TOKEN)
                shabdjalRequest.device_id = PrefDataShabdjal.getStringPrefs(this@SignUpLandingActivity,PrefData.Key.DEVICE_ID)

                if(clikedEnum == ClikedEnums.GUESTBUTTONCLICKED){
                    shabdjalRequest.is_guest = true
                }else{
                    shabdjalRequest.is_guest = false
                }

                Log.e("shabdjaal_sign_param",shabdjalRequest.toString())

                loginShabdjal(shabdjalRequest)
            }
            // CommonPreference.getInstance(UserDetailActivity.this).put(CommonPreference.Key.EMAIL, personEmail);

        } catch (e: ApiException) {
            Log.e("exceptionn", "$e.statusCode")
        }
    }

    private fun signUpUser(addUserRequest: com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest?) {
        compositeDisposable.add(
            apiService!!.signUpUser(addUserRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("signup_response:", response?.data.toString())

                    if (response?.data != null && response.status == "true") {

                        try{
                            var id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                            val  profileUpdate =  HashMap<String, Any>()
                            profileUpdate["Identity"] = id
                            profileUpdate["Email"] = response?.data?.email.toString()
                            profileUpdate["Name"] = response?.data?.name.toString()
                            profileUpdate["MSG-push"] = true
                            profileUpdate["MSG-email"] = true
                            val cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext);
                            cleverTapAPI?.onUserLogin(profileUpdate);
                        }catch (e:Exception){

                        }

                        val gameUserId = response.data?.id
                        PrefData.setStringPrefs(this, PrefData.Key.GAME_USER_ID,gameUserId.toString())

                        PrefData.saveUserDetails(this, response.data!!.name!!,response.data!!.name!!,response.data!!.email!!, "")

                        // Signed in successfully
                        isCrosswordLoggedIn = true;
                        if(iShabdamLoggedIn && isCrosswordLoggedIn && isShabdjalLoggedIn && !isGameLaunched){
                            Log.e("superman","asahs")
                            isGameLaunched = true

                          /*  val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()*/
                            if(!MainCommonPref.getBooleanPrefs(this@SignUpLandingActivity,MainCommonPref.Key.IS_FIRST_INSTALLED)){
                                MainCommonPref.setBooleanPrefs(this@SignUpLandingActivity, MainCommonPref.Key.IS_FIRST_INSTALLED,true)
                                val intent = Intent(this, LanguageSelectionActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable.message)
                })
    }


    private fun loginShabdam(shabdamSignUpRequest: SignupRequest) {
        compositeDisposable.add(
            shabdamApiService.signUpUser(shabdamSignUpRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ userResponse ->
                    Log.e("signup_response_shabd", userResponse.data.toString())

                    if ( userResponse!=null && userResponse.data !=null && userResponse.status == "true") {
                        CommonPreference.getInstance(this).put(CommonPreference.Key.GAME_USER_ID,userResponse.data.id.toString())
                        CommonPreference.getInstance(this).put(CommonPreference.Key.NAME,userResponse.data.name)
                        CommonPreference.getInstance(this).put(CommonPreference.Key.UNAME,userResponse.data.uname)
                        CommonPreference.getInstance(this).put(CommonPreference.Key.PROFILE_IMAGE,userResponse.data.profileimage)
                        CommonPreference.getInstance(this).put(CommonPreference.Key.EMAIL,userResponse.data.email)
                    }

                    iShabdamLoggedIn = true;
                    if(iShabdamLoggedIn && isCrosswordLoggedIn && !isGameLaunched){
                        Log.e("superman","shabd")
                        isGameLaunched = true

                    /*    val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()*/
                      /*  val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()*/
                        if(!MainCommonPref.getBooleanPrefs(this@SignUpLandingActivity,MainCommonPref.Key.IS_FIRST_INSTALLED)){
                            MainCommonPref.setBooleanPrefs(this@SignUpLandingActivity, MainCommonPref.Key.IS_FIRST_INSTALLED,true)
                            val intent = Intent(this, LanguageSelectionActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }

    private fun loginShabdjal(addUserRequest: com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpRequest?) {
        compositeDisposable.add(
            apiServiceshabdjal!!.signUpUser(addUserRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("shbdh_response:", response.toString())
                    if (response?.status == "true") {
                        val gameUserId = response.data?.id
                        PrefDataShabdjal.setStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID,gameUserId.toString() )
                        PrefDataShabdjal.saveUserDetails(this, response.data?.name!!,response.data?.name!!,response.data?.email!!, response.data?.profileimage.toString())
                        isShabdjalLoggedIn = true
                        if(iShabdamLoggedIn && isCrosswordLoggedIn && isShabdjalLoggedIn && !isGameLaunched){
                            Log.e("superman","asahs")
                            isGameLaunched = true

                          /*  val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()*/

                           /* val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()*/
                            if(!MainCommonPref.getBooleanPrefs(this@SignUpLandingActivity,MainCommonPref.Key.IS_FIRST_INSTALLED)){
                                MainCommonPref.setBooleanPrefs(this@SignUpLandingActivity, MainCommonPref.Key.IS_FIRST_INSTALLED,true)
                                val intent = Intent(this, LanguageSelectionActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }


}