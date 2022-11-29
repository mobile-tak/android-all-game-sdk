package com.tvtoday.gamelibrary.core.views.activity.homePage

import PrefData
import PrefDataShabdjal
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

import com.tvtoday.crosswordhindi.views.activity.BaseActivityMain
import com.tvtoday.gamelibrary.core.views.activity.landingActivity.SignUpLandingActivity
import com.tvtoday.crosswordhindi.views.controller.MainCommonPref.MainCommonPref
import com.tvtoday.crosswordhindi.views.controller.constants.AppConstantsMain
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.core.controller.utils.localeHelper.LanguagePreferenceMain
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.model.delete_account_api.DeleteAccountRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.network.ApiService
import com.tvtoday.gamelibrary.crosswordgamesdk.network.RetrofitClient
import com.tvtoday.gamelibrary.crosswordgamesdk.utils.VargPaheliLanguagePreference
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.splashScreen.SplashActivity
import com.tvtoday.gamelibrary.shabdamgamesdk.GameDataManager
import com.tvtoday.gamelibrary.shabdamgamesdk.ShabdamSplashActivity
import com.tvtoday.gamelibrary.shabdamgamesdk.model.SignupRequest
import com.tvtoday.gamelibrary.shabdamgamesdk.pref.CommonPreference
import com.tvtoday.gamelibrary.shabdamgamesdk.utils.ShabdamLanguagePreference
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.deleteAccountShabdjaal.DeleteAccShabdjaalRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.splashScreen.SplashShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class HomeActivity : BaseActivityMain(),View.OnClickListener {

    private val compositeDisposable = CompositeDisposable()
    private var apiServices: ApiService? = RetrofitClient.getInstance()
    private var shabdamApiService:com.tvtoday.gamelibrary.shabdamgamesdk.network.ApiService = com.tvtoday.gamelibrary.shabdamgamesdk.network.RetrofitClient.getInstance()
    private var shabdjaalApiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()
    private var isVisibleN = false

    private var apiServiceshabdjal: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()

    private var cvVargPaheli:CardView?=null
    private var cvShabdam:CardView?=null
    private var cvShabdjal:CardView?=null

    private var ivSetting :ImageView?=null
    private var tvLogin :TextView?=null
    private var lLayToolBar:LinearLayout?=null
    private var toolbar:Toolbar?=null

    private var login:TextView?=null
    private var delete_account:TextView?=null
    private var logout:TextView?=null

    private var viewDelete :View?=null
    private var viewLogin :View?=null

    //
    private var iv_vargPahlei : ImageView?=null
    private var ivShabdam : ImageView?=null
    private var ivShabdjaal : ImageView?=null

    private var ivMenuOptions:ImageView ?=null
    private var fLayMain:FrameLayout ?=null

    private var isForceUpgrade = false

    private var email :String = ""
    lateinit var menu: Menu

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null
    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null
    private var lLayMenuOptions:LinearLayout?=null

    var vargPaheliLang :String= ""
    var shabdamLang :String= ""
    var shabdjaalLang :String= ""

    private var pInfo: PackageInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

      //  setTitle(getString(R.string.shabd_pheli_text))
        //for main app language change to hindi
       // LanguagePreferenceMain.getInstance(baseContext).language = ""
        //remoteConfig.setDefaultsAsync(def);

        try{

            initViews()
        }catch (e:Exception){
            e.printStackTrace()
        }
        //callInAppupdate()
    }




    private fun deleteShabjaal_account() {
        val deleteRequest = DeleteAccShabdjaalRequest()
        deleteRequest.gameUserId = PrefDataShabdjal.getStringPrefs(this,PrefDataShabdjal.Key.GAME_USER_ID)
        deleteRequest.app_id = PrefDataShabdjal.getStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)

        compositeDisposable.add(
            shabdjaalApiService?.deleteShabdjaalAccount(deleteRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("delete_acc_shabdjal:", response.toString())
                    if (response?.status == "true") {
                        CommonPreference.getInstance(this).clear()
                        PrefData.clearWholePreference(this)
                        // CommonPreference.getInstance(this).put(CommonPreference.Key.IS_LOGOUT, false)
                        // PrefData.setBooleanPrefs(this, PrefData.Key.IS_LOGOUT, false)
                        val intent = Intent(this, SignUpLandingActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }


    private fun deleteAccount() {
        val deleteRequest = DeleteAccountRequest()
        deleteRequest.gameUserId = PrefData.getStringPrefs(this,PrefData.Key.GAME_USER_ID)
        deleteRequest.app_id = PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_ID)

        compositeDisposable.add(
            apiServices!!.deleteAccount(deleteRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("delete_account:", response.toString())
                    if (response?.status == "true") {
                        CommonPreference.getInstance(this).clear()
                        PrefData.clearWholePreference(this)
                       // CommonPreference.getInstance(this).put(CommonPreference.Key.IS_LOGOUT, false)
                       // PrefData.setBooleanPrefs(this, PrefData.Key.IS_LOGOUT, false)
                        val intent = Intent(this, SignUpLandingActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }

    private fun logoutAlertDialog(){
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id ->
                logout()
            })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
    }


    private fun deleteAccountDialog(){
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Are you sure?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id ->
                logout()
                deleteAccount()
                deleteShabam_account()
                deleteShabjaal_account()
            })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.show()
    }


    private fun logout() {
        CommonPreference.getInstance(this).clear()
        PrefData.clearWholePreference(this)
        PrefDataShabdjal.clearWholePreference(this)

        CommonPreference.getInstance(this).put(CommonPreference.Key.IS_LOGOUT, false)
        PrefData.setBooleanPrefs(this, PrefData.Key.IS_LOGOUT, false)
        PrefDataShabdjal.setBooleanPrefs(this, PrefDataShabdjal.Key.IS_LOGOUT, false)
        MainCommonPref.setBooleanPrefs(this@HomeActivity, MainCommonPref.Key.IS_FIRST_INSTALLED,true)

        //lnguage for hindi -
        MainCommonPref.setStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,
            AppConstantsMain.ENGLISH)

        CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH)
        CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

        PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)
        PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

        PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.ENGLISH)
        PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

        //for main app language change to hindi
        LanguagePreferenceMain.getInstance(baseContext).language = ""

        ShabdamLanguagePreference.getInstance(baseContext).setLanguage("")
        ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
        VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("")


        val intent = Intent(this, SignUpLandingActivity::class.java)
        startActivity(intent)
        this.finish()

    }



    private fun initViews() {
        //initialisation views here

        vargPaheliLang = PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).toString()
        shabdamLang = CommonPreference.getInstance(this@HomeActivity).getString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).toString()
        shabdjaalLang = PrefDataShabdjal.getStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).toString()

        val mainAppLang  = MainCommonPref.getStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE)
        if (!mainAppLang.equals(vargPaheliLang)){

            changeAllLanguage(vargPaheliLang.toString())
            startActivity(intent)
            finish()
            return
        }else if(!mainAppLang.equals(shabdjaalLang)){
            changeAllLanguage(shabdjaalLang.toString())
            startActivity(intent)
            finish()
            return
        }else if(!mainAppLang.equals(shabdamLang)){
            changeAllLanguage(shabdamLang.toString())
            startActivity(intent)
            finish()
            return
        }

        logout = findViewById(R.id.logout)
        logout?.setOnClickListener(this)
        login = findViewById(R.id.login)
        login?.setOnClickListener(this)
        delete_account = findViewById(R.id.delete_account)
        delete_account?.setOnClickListener(this)

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)
        viewLogin = findViewById(R.id.viewLogin)
        viewDelete = findViewById(R.id.viewDelete)

        cvVargPaheli = findViewById(R.id.cvVargPaheli)
        cvVargPaheli?.setOnClickListener(this)
        cvShabdam = findViewById(R.id.cvShabdam)
        cvShabdam?.setOnClickListener(this)
        cvShabdjal = findViewById(R.id.cc_shabd_jal)
        cvShabdjal?.setOnClickListener(this)

        ivMenuOptions = findViewById(R.id.ivMenuOptions)
        ivMenuOptions?.setOnClickListener(this)
        lLayMenuOptions = findViewById(R.id.lLayMenuOptions)
        fLayMain = findViewById(R.id.fLayMain)
        fLayMain?.setOnClickListener(this)



        ivSetting = findViewById(R.id.ivSetting)
        ivSetting?.setOnClickListener(this)

        iv_vargPahlei= findViewById(R.id.iv_vargPahlei)
        ivShabdjaal= findViewById(R.id.ivShabdjaal)
        ivShabdam= findViewById(R.id.ivShabdam)

        if(!TextUtils.isEmpty(MainCommonPref.getLangaugeStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE)) && MainCommonPref.getLangaugeStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE).equals("english")){
            llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llEng?.setBackgroundColor(Color.BLACK);
            llHindi?.setBackgroundColor(Color.WHITE);

            tvHindi?.setTextColor(Color.parseColor("#000000"));
            tvEng?.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

           // llHindi?.setBackgroundColor(Color.BLACK);
            llEng?.setBackgroundColor(Color.WHITE);

            tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));
            tvEng?.setTextColor(Color.parseColor("#000000"));
        }

        llEng?.setOnClickListener {
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_ENGLISH)

            if(PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{
                GameDataManager.getInstance().dataList.clear()

                llEng?.setBackgroundColor(Color.WHITE)
                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

              //  llHindi?.setBackgroundColor(Color.BLACK)
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEng?.setTextColor(Color.parseColor("#000000"));


                MainCommonPref.setStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,AppConstantsMain.ENGLISH)

                CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH)
                CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.ENGLISH)
                PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                ShabdamLanguagePreference.getInstance(baseContext).language = ""
                ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                VargPaheliLanguagePreference.getInstance(baseContext).language = ""

                //for main app language change to english --
                LanguagePreferenceMain.getInstance(baseContext).language = ""

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

                finish()
            }

        }

        llHindi?.setOnClickListener {
            CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_HINDI)

            if(PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE)!=null &&
                PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{
                GameDataManager.getInstance().dataList.clear()
                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)
                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));

                MainCommonPref.setStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,AppConstantsMain.HINDI)

                CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI)
                CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI)

                PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.HINDI)

                PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.HINDI)
                PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.HINDI)

                //for main app language change to hindi
                LanguagePreferenceMain.getInstance(baseContext).language = "hi"

                ShabdamLanguagePreference.getInstance(baseContext).setLanguage("hi")
                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                VargPaheliLanguagePreference.getInstance(baseContext).setLanguage("hi")

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }

        if(!TextUtils.isEmpty(MainCommonPref.getLangaugeStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE)) &&
            MainCommonPref.getLangaugeStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE).equals("english")) {

            iv_vargPahlei?.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, R.drawable.crossword_eng));
            ivShabdam?.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, R.drawable.shabdam_eng));
            ivShabdjaal?.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, R.drawable.shabdjaal_eng));

        }else{
            iv_vargPahlei?.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, R.drawable.varg_paheli_img));
            ivShabdam?.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, R.drawable.shabdam_));
            ivShabdjaal?.setImageDrawable(ContextCompat.getDrawable(this@HomeActivity, R.drawable.shabdjaal_app_logo));
        }
        }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.cvVargPaheli ->{

                if(TextUtils.isEmpty(PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE))){
                    CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.ENGLISH_CROSSWORD)
                    PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

                    val intent = Intent(this, SplashActivity::class.java);
                    // intent.putExtra("Language",langauge)
                    startActivity(intent)
                }else{
                    CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.HINDI_VARGPAHELI)
                    //val langauge = MainCommonPref.getLangaugeStringPrefs(this@HomeActivity, MainCommonPref.Key.MAIN_APP_LANGUAGE)
                    val intent = Intent(this, SplashActivity::class.java);
                    // intent.putExtra("Language",langauge)
                    startActivity(intent)
                }
            }

            R.id.ivMenuOptions ->{

                CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.BUTTON_SETTING)

                lLayMenuOptions?.visibility=View.VISIBLE
                viewDelete?.visibility=View.VISIBLE
                viewLogin?.visibility=View.VISIBLE


                if(PrefData.getBooleanPrefs(this@HomeActivity,PrefData.Key.ENTERFROMGUEST)){

                    login?.visibility=View.VISIBLE

                }else{
                    logout?.visibility=View.VISIBLE
                    delete_account?.visibility=View.VISIBLE
                }

            }
            R.id.fLayMain -> {
                lLayMenuOptions?.visibility = View.GONE

                viewDelete?.visibility=View.GONE
                viewLogin?.visibility=View.GONE
            }

            R.id.cvShabdam ->{
                if(TextUtils.isEmpty(CommonPreference.getInstance(this@HomeActivity).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE))){
                    CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.ENGLISH_WORDGUESS)

                    CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH)
                    val intent = Intent(this, ShabdamSplashActivity::class.java)
                    //    intent.putExtra("Language",langauge)
                    intent.putExtra("applicationId", "com.tvtoday.crosswordhindi")
                    intent.putExtra("appUniqueId", "")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }else{
                    CleverTapEvent(this).createOnlyEvent(CleverTapEventConstants.HINDI_SHABDAM)
                    //  val langauge = MainCommonPref.getLangaugeStringPrefs(this@HomeActivity, MainCommonPref.Key.MAIN_APP_LANGUAGE)
                    //  ShabdamSplashActivity.startShabdam(this@HomeActivity,"com.tvToday.mergeApp","")
                    val intent = Intent(this, ShabdamSplashActivity::class.java)
                    //    intent.putExtra("Language",langauge)
                    intent.putExtra("applicationId", "com.tvtoday.crosswordhindi")
                    intent.putExtra("appUniqueId", "")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }

            }
            R.id.login ->{
                val intent = Intent(this, SignUpLandingActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.logout ->{
                logoutAlertDialog()
            }

            R.id.delete_account -> {
                deleteAccountDialog()
            }

            R.id.ivSetting ->{
                val intent = Intent(this, SignUpLandingActivity::class.java)
                startActivity(intent)
            }

            R.id.cc_shabd_jal ->{

                if(TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE))){
                    PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)

                    CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.ENGLISH_WORDSEARCH)

                    val intent = Intent(this, SplashShabdjalActivity::class.java)
                    //intent.putExtra("Language",langauge)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                }else{
                    CleverTapEventShabdjal(this).createOnlyEvent(CleverTapShabdjalConstants.HINDI_SHABDJAAL)
                    // val langauge = MainCommonPref.getLangaugeStringPrefs(this@HomeActivity, MainCommonPref.Key.MAIN_APP_LANGUAGE)
                    val intent = Intent(this, SplashShabdjalActivity::class.java)
                    //intent.putExtra("Language",langauge)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }
        }
    }

    private fun isShabdamLoggedIn() = !TextUtils.isEmpty(CommonPreference.getInstance(this).getString(CommonPreference.Key.GAME_USER_ID))

    private fun isCrossWordLoggedIn() = !TextUtils.isEmpty(PrefData.getStringPrefs(this, PrefData.Key.GAME_USER_ID))

    private fun isShabdjalLoggedIn() = !TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID))


    override fun onResume() {
        super.onResume()

        val vargPaheliLang1 = PrefData.getStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE)
        val shabdamLang2  = CommonPreference.getInstance(this@HomeActivity).getString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)
        val shabdjaalLang3 =PrefDataShabdjal.getStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)

        if (!vargPaheliLang1.equals(vargPaheliLang) ){

            changeAllLanguage(vargPaheliLang1.toString())
            startActivity(intent)
            finish()
            return
        }else if(!shabdjaalLang3.equals(shabdjaalLang)){

            changeAllLanguage(shabdjaalLang3.toString())
            startActivity(intent)
            finish()
            return
        }else if(!shabdamLang2.equals(shabdamLang)){
            changeAllLanguage(shabdamLang2.toString())
            startActivity(intent)
            finish()
            return
        }


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
            }catch (e:Exception){

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

            }

        }else if(isShabdjalLoggedIn() && (!isCrossWordLoggedIn() || !isShabdamLoggedIn())){
            var email = PrefDataShabdjal.getStringPrefs(this,PrefDataShabdjal.Key.EMAIL)
            var name = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.NAME)
            var uname = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.UNAME)
            var profileimage  = PrefDataShabdjal.getStringPrefs(this, PrefDataShabdjal.Key.PROFILE_IMAGE)

            try{
                loginCrossword(email!!, name!!, uname!!, profileimage!!)
                loginShabdam(email!!, name!!, uname!!, profileimage!!)
            }catch (ex:Exception){

            }

        }
    }

    private fun changeAllLanguage(isHindi: String){
        if(PrefData.Key.HINDI.equals(isHindi)){
            MainCommonPref.setStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,AppConstantsMain.HINDI)

            CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI)
            CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI)

            PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.HINDI)
            PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.HINDI)

            PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.HINDI)
            PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.HINDI)

            ShabdamLanguagePreference.getInstance(baseContext).language = "hi"
            ShabdjalLanguagePreference.getInstance(baseContext).language = "hi"
            VargPaheliLanguagePreference.getInstance(baseContext).language = "hi"
            //for main app language change to english -------------------------
            LanguagePreferenceMain.getInstance(baseContext).language = "hi"

        }else{

            MainCommonPref.setStringPrefs(this@HomeActivity,MainCommonPref.Key.MAIN_APP_LANGUAGE,AppConstantsMain.ENGLISH)

            CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH)
            CommonPreference.getInstance(this@HomeActivity).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

            PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,CommonPreference.Key.ENGLISH)
            PrefDataShabdjal.setStringPrefs(this@HomeActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

            PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_LANGAUGE,CommonPreference.Key.ENGLISH)
            PrefData.setStringPrefs(this@HomeActivity,PrefData.Key.CROSSWORD_APP_LANGUAGE,CommonPreference.Key.ENGLISH)

            ShabdamLanguagePreference.getInstance(baseContext).language = ""
            ShabdjalLanguagePreference.getInstance(baseContext).language = ""
            VargPaheliLanguagePreference.getInstance(baseContext).language = ""
            //for main app language change to english -------------------------
            LanguagePreferenceMain.getInstance(baseContext).language = ""
        }
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
                    onCreateOptionsMenu(this.menu)
                    if (userResponse?.status == "true") {
                        //openGameSelectionScreen()
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
                    if (response?.status == "true" && response.data !=null) {

                        PrefData.setStringPrefs(this, PrefData.Key.GAME_USER_ID,response.data?.id.toString())
                        PrefData.saveUserDetails(this, response?.data?.name!!,response?.data?.name!!,response?.data?.email!!, response?.data?.profileimage!!)
                       // openGameSelectionScreen()
                        PrefData.setBooleanPrefs(this,PrefData.Key.LOGIN_FROM_SIGNUP, true)
                        onCreateOptionsMenu(this.menu)
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
                    if (response?.status == "true" && response.data !=null) {

                        Log.e("user_id:", response.data?.id.toString())
                        PrefDataShabdjal.setStringPrefs(this, PrefDataShabdjal.Key.GAME_USER_ID,response.data?.id.toString())
                        PrefDataShabdjal.saveUserDetails(this, response?.data?.name!!,response?.data?.name!!,response?.data?.email!!, response?.data?.profileimage!!)
                        // openGameSelectionScreen()
                        onCreateOptionsMenu(this.menu)
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
    }

    private fun callInAppupdate() {
        val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(this)

// Returns an intent object that you use to check for an update.
        val appUpdateInfoTask: Task<AppUpdateInfo> =
            appUpdateManager.getAppUpdateInfo()

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() === UpdateAvailability.UPDATE_AVAILABLE // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        101
                    )
                } catch (exception: IntentSender.SendIntentException) {
                    Log.d("Error_Message", "" + exception.message)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
                if(isForceUpgrade){
                    finish()
                }

            }
        }
    }



    private fun deleteShabam_account() {
        val deleteAccountRequest = com.tvtoday.gamelibrary.shabdamgamesdk.model.deleteAccount.DeleteAccountRequest()
        deleteAccountRequest.gameUserId = CommonPreference.getInstance(this).getString(CommonPreference.Key.GAME_USER_ID)
        deleteAccountRequest.app_id= CommonPreference.getInstance(this@HomeActivity).getString(CommonPreference.Key.SHABDAM_APP_ID)
        compositeDisposable.add(
            shabdamApiService.deleteAccount(deleteAccountRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ userResponse ->

                    CommonPreference.getInstance(this).clear()
                    PrefData.clearWholePreference(this)

                    Log.e("shabdam_signup_response", userResponse.toString())

                }) { throwable ->
                    Log.d("Error", "" + throwable)
                })
    }



    fun getVersionCode(): Int {
        pInfo = null
        try {
            pInfo = packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.i("MYLOG", "NameNotFoundException: " + e.message)
        }
        Log.i("check_version", pInfo!!.versionCode.toString())
        return pInfo!!.versionCode
    }


}