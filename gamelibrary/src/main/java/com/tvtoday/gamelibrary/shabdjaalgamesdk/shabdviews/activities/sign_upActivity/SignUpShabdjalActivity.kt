package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.constants.IntentConstantsShabdjal

import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.gameUserUpdateToken.TokenRequestShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.crossWord_tutorial_activity.TutorialShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SignUpShabdjalActivity : ShabdjalBaseActivity() , View.OnClickListener{
    //deceleration views-------------------------------
    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()
    //google SignupButton
    private var lLyaGoogleSignUpBtn: LinearLayout?=null
    private var lLayGuestBtn: LinearLayout?=null

    private val RC_SIGN_IN = 89
    var gso: GoogleSignInOptions? = null
    var mGoogleSignInClient: GoogleSignInClient? = null
    var device_token:String?=null
    var device_type:String?=null

    companion object{
        private var TAG = SignUpShabdjalActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_shabd)

        val email = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.EMAIL)

         device_token = PrefDataShabdjal.getStringPrefs(this@SignUpShabdjalActivity,PrefDataShabdjal.Key.DEVICE_TOKEN)
         device_type= PrefDataShabdjal.getStringPrefs(this@SignUpShabdjalActivity,PrefDataShabdjal.Key.DEVICE_TYPE)

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
                CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.GOOGLE_SIGNUP)
            }

            R.id.lLayGuestBtn ->{
                PrefDataShabdjal.setBooleanPrefs(this,PrefDataShabdjal.Key.GUEST_USER, true)
                CommonUtilsShabdjal.performIntentFinish(this@SignUpShabdjalActivity, TutorialShabdjalActivity::class.java)
                CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.PLAY_GUEST)
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
                signupRequest.device_type = IntentConstantsShabdjal.DEVICE_TYPE
                signupRequest.device_token = PrefDataShabdjal.getStringPrefs(this,PrefDataShabdjal.Key.DEVICE_TOKEN)

               // val personPhoto = Uri.parse(acct.photoUrl.toString())
                signupRequest.profileimage = profile.toString()
                PrefDataShabdjal.saveUserDetails(this, name!!,name,email!!, profile.toString())

                signUpUser(signupRequest)
            }

            // Signed in successfully
         //   CommonUtils.performIntent(this,GameRuleActivity::class.java)
            CommonUtilsShabdjal.performIntent(this, TutorialShabdjalActivity::class.java)
         // CommonPreference.getInstance(UserDetailActivity.this).put(CommonPreference.Key.EMAIL, personEmail);

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
                        PrefDataShabdjal.setStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID,gameUserId.toString())

                        val gameUserTokenUpdateRequest = TokenRequestShabdjal()
                        gameUserTokenUpdateRequest.deviceToken = PrefDataShabdjal.getStringPrefs(this,PrefDataShabdjal.Key.DEVICE_TOKEN)
                        gameUserTokenUpdateRequest.gameUserId = gameUserId.toString()
                        gameUserTokenUpdateRequest.deviceType = IntentConstantsShabdjal.DEVICE_TYPE
                        gameUserTokenUpdateRequest.app_id = PrefDataShabdjal.getStringPrefs(this@SignUpShabdjalActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
                        Log.i("params:" ,gameUserTokenUpdateRequest.toString())

                        gameUserUpdateTokenCrossword(gameUserTokenUpdateRequest)


                        }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }


    private fun gameUserUpdateTokenCrossword(gameUserTokenUpdateRequest: TokenRequestShabdjal) {
        compositeDisposable.add(
            apiService!!.gameUserUpdateShabdToken(gameUserTokenUpdateRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("game_user___:", response.toString())
                    if (response?.status == "true") {
                        Log.e("game_user:", response.toString())

                        val deviceToken = response.data?.deviceToken.toString()
                        //val deviceType =  response.data?.deviceType.toString()

                        PrefDataShabdjal.setStringPrefs(this@SignUpShabdjalActivity,PrefDataShabdjal.Key.DEVICE_TOKEN,deviceToken)

                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })

    }


}