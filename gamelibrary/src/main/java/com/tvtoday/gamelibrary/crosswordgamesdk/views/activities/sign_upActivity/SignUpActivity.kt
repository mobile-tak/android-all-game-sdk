package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.clevertap.android.sdk.CleverTapAPI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


import com.tvtoday.crosswordhindi.controller.utils.CommonUtils

import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.IntentConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.model.gameUserUpdateToken.GameUserUpdateTokenRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.crossWord_tutorial_activity.TutorialActivity

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap


class SignUpActivity : VargPaheliBaseActivity() , View.OnClickListener{
    //deceleration views-----------------------------------------
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService? = RetrofitClient.getInstance()
    //google SignupButton
    private var lLyaGoogleSignUpBtn: LinearLayout?=null
    private var lLayGuestBtn: LinearLayout?=null

    private val RC_SIGN_IN = 89
    var gso: GoogleSignInOptions? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var device_token:String?=null
    var device_type:String?=null

    companion object{
        private var TAG = SignUpActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val email = PrefData.getStringPrefs(this, PrefData.Key.EMAIL)

         device_token = PrefData.getStringPrefs(this@SignUpActivity,PrefData.Key.DEVICE_TOKEN)
         device_type= PrefData.getStringPrefs(this@SignUpActivity,PrefData.Key.DEVICE_TYPE)

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


    /**
     * this method is used to initialization views
     */
    private fun initViews() {
        lLyaGoogleSignUpBtn= findViewById(R.id.lLyaGoogleSignUpBtn)
        lLyaGoogleSignUpBtn?.setOnClickListener(this)

        lLayGuestBtn = findViewById(R.id.lLayGuestBtn)
        lLayGuestBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.lLyaGoogleSignUpBtn ->{
                signIn()
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.GOOGLE_SIGNUP)
            }

            R.id.lLayGuestBtn ->{
                PrefData.setBooleanPrefs(this,PrefData.Key.GUEST_USER, true)
                CommonUtils.performIntentFinish(this@SignUpActivity, TutorialActivity::class.java)
                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.PLAY_GUEST)
                /*if(PrefData.getBooleanPrefs(this, PrefData.Key.ISRuleHow)){
                    CommonUtils.performIntentFinish(this@SignUpActivity, VargPaheliGameActivity::class.java)
                }else{

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


                val signupRequest = SignUpRequest()
                val email = acct.email
                val name  = acct.displayName
                val userId =acct.id?.toString()
                val profile  = Uri.parse(acct.photoUrl.toString())

                signupRequest.email = email
                signupRequest.name = name
                signupRequest.uname = name
                signupRequest.userId =userId
                signupRequest.device_type = IntentConstants.DEVICE_TYPE
                signupRequest.device_token = PrefData.getStringPrefs(this,PrefData.Key.DEVICE_TOKEN)

               // val personPhoto = Uri.parse(acct.photoUrl.toString())
                signupRequest.profileimage = profile.toString()
                PrefData.saveUserDetails(this, name!!,name,email!!, profile.toString())

                signUpUser(signupRequest)
            }

            // Signed in successfully
         //   CommonUtils.performIntent(this,GameRuleActivity::class.java)
            CommonUtils.performIntent(this, TutorialActivity::class.java)
         // CommonPreference.getInstance(UserDetailActivity.this).put(CommonPreference.Key.EMAIL, personEmail);



        /*
            val familyName = account.familyName
            val email = account.email
            val name = account.displayName
            val id = account.id
            val photo = account.photoUrl
            val firstName = account.givenName

            Log.e("email", email.toString())
            Log.e("name", name.toString())
            Log.e("id", id.toString())
            Log.e("photo", photo.toString())
            Log.e("firstName", firstName.toString())
            Log.e("familyName", familyName.toString())
            Log.e("account", account.toString())
*/
            // this method is for social register call rest api


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
                    Log.e("signup_response:", response.toString())
                    if (response?.status == "true") {

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
                        val gameUserTokenUpdateRequest = GameUserUpdateTokenRequest()
                        gameUserTokenUpdateRequest.deviceToken = PrefData.getStringPrefs(this,PrefData.Key.DEVICE_TOKEN)
                        gameUserTokenUpdateRequest.gameUserId = gameUserId.toString()
                        gameUserTokenUpdateRequest.deviceType = IntentConstants.DEVICE_TYPE
                        gameUserTokenUpdateRequest.app_id = PrefData.getStringPrefs(this@SignUpActivity,PrefData.Key.CROSSWORD_APP_ID)
                        Log.i("params:" ,gameUserTokenUpdateRequest.toString())

                        gameUserUpdateTokenCrossword(gameUserTokenUpdateRequest)

                        }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }


    private fun gameUserUpdateTokenCrossword(gameUserTokenUpdateRequest:GameUserUpdateTokenRequest) {
        compositeDisposable.add(
            apiService!!.gameUserUpdateToken(gameUserTokenUpdateRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("game_user___:", response.toString())
                    if (response?.status == "true") {
                        Log.e("game_user:", response.toString())

                        val deviceToken = response.data?.deviceToken.toString()
                        //val deviceType =  response.data?.deviceType.toString()

                        PrefData.setStringPrefs(this@SignUpActivity,PrefData.Key.DEVICE_TOKEN,deviceToken)
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })

    }


}