package com.tvtoday.gamelibrary.core.views.activity.splashActivity

import PrefData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.gif.GifImageView
import com.google.firebase.messaging.FirebaseMessaging

import com.tvtoday.gamelibrary.core.views.activity.homePage.HomeActivity
import com.tvtoday.gamelibrary.core.views.activity.landingActivity.SignUpLandingActivity

import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest
import com.tvtoday.gamelibrary.shabdamgamesdk.model.SignupRequest
import com.tvtoday.gamelibrary.shabdamgamesdk.pref.CommonPreference
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class SplashActivityMerger : AppCompatActivity() {

   private val compositeDisposable = CompositeDisposable()
    private var apiServices: ApiService? = RetrofitClient.getInstance()
    private var shabdamApiService:com.tvtoday.gamelibrary.shabdamgamesdk.network.ApiService = com.tvtoday.gamelibrary.shabdamgamesdk.network.RetrofitClient.getInstance()
    private var apiServiceshabdjal: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()

    private var giv_image : GifImageView?=null
    private var handler = Handler(Looper.getMainLooper())

    companion object{
        fun startGameStart(applicationPackage:String, gameAppId: String,
                           rewardAdsIdentifier: String, interstitialAdsIdentifier: String,
                           bannerAdsIdentifier: String, context: Context
        ){
            val intent = Intent(context, SplashActivityMerger::class.java)
            intent.putExtra("applicationPackage", applicationPackage)
            intent.putExtra("gameAppId", gameAppId)
            intent.putExtra("rewardAdsIdentifier", rewardAdsIdentifier)
            intent.putExtra("interstitialAdsIdentifier", interstitialAdsIdentifier)
            intent.putExtra("bannerAdsIdentifier", bannerAdsIdentifier)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_merger)


        var id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        saveAppId("")
        saveDataFromIntent()
       /* PrefData.setStringPrefs(this@SplashActivityMerger, PrefData.Key.DEVICE_ID,id)
        PrefDataShabdjal.setStringPrefs(this@SplashActivityMerger, PrefDataShabdjal.Key.DEVICE_ID,id.toString())
        CommonPreference.getInstance(this@SplashActivityMerger).put(CommonPreference.Key.DEVICE_ID,id.toString())*/

        val  profileUpdate =  HashMap<String, Any>()
        profileUpdate["Identity"] = id
        profileUpdate["MSG-push"] = true
        profileUpdate["MSG-email"] = true
        val cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext);
        cleverTapAPI?.onUserLogin(profileUpdate);

        //giv_image = findViewById(R.id.giv_image)

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                try {
                    var token = task.result
                    PrefData.setStringPrefs(this,PrefData.Key.DEVICE_TOKEN, token.toString())
                    if (token != null) {
                        Log.d("beemen", token!!)
                    }
                    if (token != null) {
                        CleverTapAPI.getDefaultInstance(this@SplashActivityMerger)!!
                            .pushFcmRegistrationId(token, true)
                    }

                    val userId =  PrefData.getStringPrefs(this@SplashActivityMerger,PrefData.Key.GAME_USER_ID)

                    CleverTapEvent(this).createOnlyEvent(
                        CleverTapEventConstants.FIRST_OPEN)

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

        //checking conditions to handel google Login
        handler.postDelayed({
            if(CommonPreference.getInstance(this).getBoolean(CommonPreference.Key.IS_LOGOUT)
                || PrefData.getBooleanPrefs(this,PrefData.Key.IS_LOGOUT, false)){
                CommonPreference.getInstance(this).clear()
                PrefData.clearWholePreference(this)
                PrefDataShabdjal.clearWholePreference(this)

                CommonPreference.getInstance(this).put(CommonPreference.Key.IS_LOGOUT, false)
                PrefData.setBooleanPrefs(this, PrefData.Key.IS_LOGOUT, false)
                PrefDataShabdjal.setBooleanPrefs(this, PrefData.Key.IS_LOGOUT, false)
            }

            if(isCrossWordLoggedIn() && (!isShabdamLoggedIn() || !isShabdjalLoggedIn())){
                var email = PrefData.getStringPrefs(this,PrefData.Key.EMAIL)
                var name = PrefData.getStringPrefs(this, PrefData.Key.NAME)
                var uname = PrefData.getStringPrefs(this, PrefData.Key.UNAME)
                var profileimage  = PrefData.getStringPrefs(this, PrefData.Key.PROFILE_IMAGE)

                try{
                    loginShabdam(email!!, name!!, uname!!, profileimage!!)
                    loginShabdjal(email!!, name!!, uname!!, profileimage!!)
                }catch ( e: Exception){
                    e.printStackTrace()
                }

            }else if(isShabdamLoggedIn() && (!isCrossWordLoggedIn() || !isShabdjalLoggedIn())){
                var email = CommonPreference.getInstance(this).getString(CommonPreference.Key.EMAIL)
                var name = CommonPreference.getInstance(this).getString(CommonPreference.Key.NAME)
                var uname = CommonPreference.getInstance(this).getString(CommonPreference.Key.UNAME)
                var profileimage = CommonPreference.getInstance(this).getString(CommonPreference.Key.PROFILE_IMAGE)

                try{
                    loginCrossword(email, name, uname, profileimage)
                    loginShabdjal(email, name, uname, profileimage)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }else if(isShabdjalLoggedIn() && (!isCrossWordLoggedIn() || !isShabdamLoggedIn())){
                var email = PrefDataShabdjal.getStringPrefs(this,PrefDataShabdjal.Key.EMAIL)
                var name = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.NAME)
                var uname = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.UNAME)
                var profileimage  = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.PROFILE_IMAGE)

                try{
                    loginCrossword(email!!, name!!, uname!!, profileimage!!)
                    loginShabdam(email!!, name!!, uname!!, profileimage!!)
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }else if(isShabdamLoggedIn() && isCrossWordLoggedIn() && isShabdjalLoggedIn()){
                openGameSelectionScreen()
            }else{
                openLoginScreen()
            }
        }, 3000)
    }

    private fun saveDataFromIntent() {
        val applicationPackage = intent.getStringExtra("applicationPackage")
        val gameAppId = intent.getStringExtra("gameAppId")
        val rewardAdsIdentifier = intent.getStringExtra("rewardAdsIdentifier")
        val interstitialAdsIdentifier = intent.getStringExtra("interstitialAdsIdentifier")
        val bannerAdsIdentifier = intent.getStringExtra("bannerAdsIdentifier")

        if(!gameAppId.isNullOrEmpty()){
            saveAppId(appId = gameAppId )
        }

        if(!applicationPackage.isNullOrEmpty()){
            CommonPreference.getInstance(this).put("applicationId", applicationPackage);
            PrefData.setStringPrefs(this@SplashActivityMerger,"applicationId", applicationPackage)
            PrefDataShabdjal.setStringPrefs(this@SplashActivityMerger,"applicationId", applicationPackage)
        }

        if(!rewardAdsIdentifier.isNullOrEmpty() && !interstitialAdsIdentifier.isNullOrEmpty() && !bannerAdsIdentifier.isNullOrEmpty()){
            //shabdam ad id;s saving here-----------------------------------------------------------
            CommonPreference.getInstance(this).put(CommonPreference.Key.SHABDAM_BANNER_APP_ID, bannerAdsIdentifier);
            CommonPreference.getInstance(this).put(CommonPreference.Key.SHABDAM_INTERTETIAL_APP_ID, interstitialAdsIdentifier);
            CommonPreference.getInstance(this).put(CommonPreference.Key.SHABDAM_REWARDED_APP_ID, rewardAdsIdentifier);

            //Crossword ad id;s saving here-----------------------------------------------------------
            PrefData.setStringPrefs(this@SplashActivityMerger,PrefData.Key.CROSSWORD_BANNER_AD_ID, bannerAdsIdentifier)
            PrefData.setStringPrefs(this@SplashActivityMerger,PrefData.Key.CROSSWORD_INTERTITIALS_AD_ID, interstitialAdsIdentifier)
            PrefData.setStringPrefs(this@SplashActivityMerger,PrefData.Key.CROSSWORD_REWARDED_AD_ID, rewardAdsIdentifier)

            //shabdjaal ad id;s saving here-----------------------------------------------------------
            PrefDataShabdjal.setStringPrefs(this@SplashActivityMerger,PrefDataShabdjal.Key.SHABDJAAL_BANNER_AD_ID, bannerAdsIdentifier)
            PrefDataShabdjal.setStringPrefs(this@SplashActivityMerger,PrefDataShabdjal.Key.SHABDJAAL_REWARDED_AD_ID, rewardAdsIdentifier)
            PrefDataShabdjal.setStringPrefs(this@SplashActivityMerger,PrefDataShabdjal.Key.SHABDJAAL_INTERTITIAL_ID, interstitialAdsIdentifier)

        }


    }


    fun saveAppId(appId:String){
        PrefData.setStringPrefs(this@SplashActivityMerger,PrefData.Key.CROSSWORD_APP_ID,appId)
        PrefDataShabdjal.setStringPrefs(this@SplashActivityMerger,PrefDataShabdjal.Key.SHABDJAAL_APP_ID,appId)
        CommonPreference.getInstance(this@SplashActivityMerger).put(CommonPreference.Key.SHABDAM_APP_ID,appId)
    }

    private fun loginShabdam(email:String, name:String,uname:String, profileImage:String) {
        var signupRequest = SignupRequest()
        /*signupRequest.email = PrefData.getStringPrefs(this,PrefData.Key.EMAIL)
        signupRequest.name = PrefData.getStringPrefs(this, PrefData.Key.NAME)
        signupRequest.uname = PrefData.getStringPrefs(this, PrefData.Key.UNAME)
        signupRequest.profileimage  = PrefData.getStringPrefs(this, PrefData.Key.PROFILE_IMAGE)*/

        signupRequest.email = email
        signupRequest.name = name
        signupRequest.uname = uname
        signupRequest.profileimage  = profileImage

        compositeDisposable.add(
            shabdamApiService.signUpUser(signupRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ userResponse ->
                    Log.e("shabdam_signup_response", userResponse.toString())
                    if ( userResponse!=null && userResponse.data !=null && userResponse.status == "true") {
                        CommonPreference.getInstance(this).put(CommonPreference.Key.GAME_USER_ID,userResponse.data.id.toString())
                        CommonPreference.getInstance(this).put(CommonPreference.Key.NAME,userResponse.data.name)
                        CommonPreference.getInstance(this).put(CommonPreference.Key.UNAME,userResponse.data.uname)
                        CommonPreference.getInstance(this).put(CommonPreference.Key.PROFILE_IMAGE,userResponse.data.profileimage)
                        CommonPreference.getInstance(this).put(CommonPreference.Key.EMAIL,userResponse.data.email)
                    }
                    if (userResponse?.status == "true") {
                        openGameSelectionScreen()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }


    private fun loginCrossword(email:String, name:String,uname:String, profileImage:String) {
        var userRequest = SignUpRequest()
        /*userRequest.email = CommonPreference.getInstance(this).getString(CommonPreference.Key.EMAIL)
        userRequest.name = CommonPreference.getInstance(this).getString(CommonPreference.Key.NAME)
        userRequest.uname = CommonPreference.getInstance(this).getString(CommonPreference.Key.UNAME)
        userRequest.profileimage = CommonPreference.getInstance(this).getString(CommonPreference.Key.PROFILE_IMAGE)
*/

        userRequest.email = email
        userRequest.name = name
        userRequest.uname = uname
        userRequest.profileimage = profileImage

        compositeDisposable.add(
            apiServices!!.signUpUser(userRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("signup_response:", response.toString())
                    if (response?.status == "true") {
                        PrefData.setStringPrefs(this, PrefData.Key.GAME_USER_ID,response.data?.id.toString())
                        PrefData.saveUserDetails(this, response?.data?.name!!,response?.data?.name!!,response?.data?.email!!, response?.data?.profileimage!!)
                         openGameSelectionScreen()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }

    private fun loginShabdjal(email:String, name:String,uname:String, profileImage:String) {
        var signupRequest = com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpRequest()
        signupRequest.email = email
        signupRequest.name = name
        signupRequest.uname = uname
        signupRequest.profileimage = profileImage

        compositeDisposable.add(
            apiServiceshabdjal!!.signUpUser(signupRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("response:", response.toString())
                    if (response?.status == "true") {

                        PrefDataShabdjal.setStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID,response.data?.id.toString())
                        PrefDataShabdjal.saveUserDetails(this, response?.data?.name!!,response?.data?.name!!,response?.data?.email!!, response?.data?.profileimage!!)
                         openGameSelectionScreen()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }

    private fun isShabdamLoggedIn() = !TextUtils.isEmpty(CommonPreference.getInstance(this).getString(CommonPreference.Key.GAME_USER_ID))

    private fun isCrossWordLoggedIn() = !TextUtils.isEmpty(PrefData.getStringPrefs(this, PrefData.Key.GAME_USER_ID))

    private fun isShabdjalLoggedIn() = !TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID))

    private fun openLoginScreen() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, SignUpLandingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }


    private fun openGameSelectionScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginCrossword() {

        var userRequest = SignUpRequest()

        userRequest.email = CommonPreference.getInstance(this).getString(CommonPreference.Key.EMAIL)
        userRequest.name = CommonPreference.getInstance(this).getString(CommonPreference.Key.NAME)
        userRequest.uname = CommonPreference.getInstance(this).getString(CommonPreference.Key.UNAME)
        userRequest.profileimage = CommonPreference.getInstance(this).getString(CommonPreference.Key.PROFILE_IMAGE)

        compositeDisposable.add(
            apiServices!!.signUpUser(userRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("signup_response:", response.toString())
                    if (response?.status == "true") {
                        PrefData.setStringPrefs(this, PrefData.Key.GAME_USER_ID,response.data?.id.toString())
                        PrefData.saveUserDetails(this, response?.data?.name!!,response?.data?.name!!,response?.data?.email!!, response?.data?.profileimage!!)
                        openGameSelectionScreen()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }
}