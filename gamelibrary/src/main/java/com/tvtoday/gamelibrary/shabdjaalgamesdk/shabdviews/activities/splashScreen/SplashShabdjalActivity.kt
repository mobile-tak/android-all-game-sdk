package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.splashScreen

import PrefDataShabdjal
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.messaging.FirebaseMessaging
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalGameRuleActivity.GameRuleShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import io.reactivex.disposables.CompositeDisposable

class SplashShabdjalActivity : ShabdjalBaseActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()

    private var token :String?=null

    private var language :String = ""
    companion object{

        fun startGame(context: Context){
            val intent = Intent(context, SplashShabdjalActivity::class.java);
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*   language = intent.getStringExtra("Language").toString()
           PrefDataShabdjal.setStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,language)
   */
        try {
            setContentView(R.layout.activity_splash_shabd)
            //Firebase Messaging ---------------------------------
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                try {
                    token = task.result
                    PrefDataShabdjal.setStringPrefs(this,PrefDataShabdjal.Key.DEVICE_TOKEN, token.toString())
                    if (token != null) {
                        Log.d("beemen", token!!)
                    }
                    if (token != null) {
                        CleverTapAPI.getDefaultInstance(this@SplashShabdjalActivity)!!
                            .pushFcmRegistrationId(token, true)
                    }


                    val userId =  PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID)

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

        val  profileUpdate =  HashMap<String, Any>();
        profileUpdate["email"] = "ashwin@gmail.com";
        val cleverTapAPI = CleverTapAPI.getDefaultInstance(applicationContext);
        cleverTapAPI?.pushProfile(profileUpdate);

        val email = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.EMAIL)
        Log.d("email", email.toString())

        // for splash screen
        Handler(Looper.myLooper()!!).postDelayed({

            if(PrefDataShabdjal.getBooleanPrefs(this@SplashShabdjalActivity, PrefDataShabdjal.Key.ISRuleSHow)) {
                if(PrefDataShabdjal?.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID) !=null){

                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))) {

                        if(PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                            PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {

                            CommonUtilsShabdjal.performIntentFinish(this,
                                EnglishShabdjaalPlayGameActivity::class.java)
                        }else{
                            CommonUtilsShabdjal.performIntentFinish(this,
                                ShabdjaalPlayGameActivity::class.java)
                        }
                    }else{
                        CommonUtilsShabdjal.performIntentFinish(this, PastPuzzleActivityShabdjaal::class.java)
                    }
                }else{
                    CommonUtilsShabdjal.performIntentFinish(this, GameRuleShabdActivity::class.java)
                }
            }else{
                if(PrefDataShabdjal?.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.GAME_USER_ID) !=null){
                    if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID))) {

                        if(PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                            PrefDataShabdjal.getStringPrefs(this@SplashShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {

                            CommonUtilsShabdjal.performIntentFinish(this,
                                EnglishShabdjaalPlayGameActivity::class.java)
                        }else{
                            CommonUtilsShabdjal.performIntentFinish(this,
                                ShabdjaalPlayGameActivity::class.java)
                        }
                    }else{
                        CommonUtilsShabdjal.performIntentFinish(this, PastPuzzleActivityShabdjaal::class.java)
                    }
                }else{
                    CommonUtilsShabdjal.performIntentFinish(this, GameRuleShabdActivity::class.java)
                }

            }
        }, 3000)
    }



}