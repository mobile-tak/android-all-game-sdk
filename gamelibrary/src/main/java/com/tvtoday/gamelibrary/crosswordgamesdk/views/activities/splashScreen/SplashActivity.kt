package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.splashScreen

import PrefData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.messaging.FirebaseMessaging
import com.tvtoday.crosswordhindi.controller.utils.CommonUtils

import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.crossWord_tutorial_activity.TutorialActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrosswordActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import io.reactivex.disposables.CompositeDisposable

class SplashActivity : VargPaheliBaseActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()

    private var token :String?=null

    private var language :String?= ""

    companion object{
        fun startGame(context: Context){
            val intent = Intent(context, SplashActivity::class.java);
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       /* language = intent.getStringExtra("Language").toString()
        PrefData.setStringPrefs(this,PrefData.Key.CROSSWORD_APP_LANGUAGE, language!!)*/

        var id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val  profileUpdate =  HashMap<String, Any>()
        profileUpdate["Identity"] = id
        Log.d("beeman", id)

        profileUpdate["MSG-push"] = true
        profileUpdate["MSG-email"] = true

        val cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext);
        cleverTapAPI?.onUserLogin(profileUpdate);

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                try {
                     token = task.result
                    PrefData.setStringPrefs(this,PrefData.Key.DEVICE_TOKEN, token.toString())
                    if (token != null) {
                        Log.d("beemen", token!!)
                    }
                    if (token != null) {
                        CleverTapAPI.getDefaultInstance(this@SplashActivity)!!
                            .pushFcmRegistrationId(token, true)
                    }


                val userId =  PrefData.getStringPrefs(this@SplashActivity,PrefData.Key.GAME_USER_ID)

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


        // for splash screen
        Handler(Looper.myLooper()!!).postDelayed({

            if(PrefData.getBooleanPrefs(this, PrefData.Key.ISRuleSHow)){
                if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@SplashActivity, PrefData.Key.GAME_USER_ID))){

                    if(!TextUtils.isEmpty(PrefData.getStringPrefs(this@SplashActivity,PrefData.Key.CROSSWORD_APP_ID))) {

                        if(PrefData.getStringPrefs(this@SplashActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                            PrefData.getStringPrefs(this@SplashActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {

                            CommonUtils.performIntentFinish(this, EnglishVargPahleiGameActivity::class.java)
                        }else{
                            CommonUtils.performIntentFinish(this, VargPaheliGameActivity::class.java)
                        }
                    }else{
                        CommonUtils.performIntentFinish(this, PastPuzzleCrosswordActivity::class.java)
                    }
                }else{
                    CommonUtils.performIntentFinish(this, VargPaheliGameActivity::class.java)
                }
            }else{
                CommonUtils.performIntentFinish(this, TutorialActivity::class.java)
            }
        }, 3000)
    }

    /*private fun gameUserUpdateTokenCrossword(gameUserTokenUpdateRequest:GameUserUpdateTokenRequest) {
        compositeDisposable.add(
            apiService!!.gameUserUpdateToken(gameUserTokenUpdateRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                   // Log.e("game_user:", response.toString())
                    if (response?.status == "true") {
                        Log.e("game_user:", response.toString())

                        val deviceToken = response.data?.deviceToken.toString()
                        val deviceType =  response.data?.deviceType.toString()

                        PrefData.setStringPrefs(this@SplashActivity,PrefData.Key.DEVICE_TOKEN,deviceToken)
                        PrefData.setStringPrefs(this@SplashActivity,PrefData.Key.DEVICE_TYPE,deviceType)
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })

    }*/


}