package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity

import PrefDataShabdjal
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.`interface`.RecyclerViewOnClickShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.ApiServiceShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork.RetrofitClientShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalBaseActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalGameRuleActivity.GameRuleShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.VargPaheliGameShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleActivityShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.ShabdjaalItem
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.response.PastMonthlyPuzzleShabdjaalRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.response.ShabdjaalArrayItem
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.adapter.MonthlyPuzzleAdapterShabdjaal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.utils.ShabdjalLanguagePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MonthlyPuzzleActivity : ShabdjalBaseActivity(), View.OnClickListener {
    //views---------------------------------------------------------------

    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiServiceShabdjal? = RetrofitClientShabdjal.getInstance()

    private val pastMonthlyPuzzle: ArrayList<ShabdjaalArrayItem?>? = ArrayList()

    private var rvmonthlyPuzzle: RecyclerView? = null
    private var monthlyPuzzleAdapter: MonthlyPuzzleAdapterShabdjaal? = null

    private var monthlyDataList: ArrayList<ShabdjaalItem>?=ArrayList()

    private var iv_back_btn: ImageView? = null
    private var ivLangChange: ImageView? = null
    private var tvMonthName :TextView?=null

    private var tvHindi :TextView?=null
    private var tvEng :TextView?=null

    private var llHindi:LinearLayout?=null
    private var llEng:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_past_puzzle_)

        val gameDate=intent.getStringExtra("gameDate")

        //monthlyDataList = intent!!.getParcelableArrayListExtra("MonthlyList")
        /*val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        val listMontly = args!!.getSerializable("ARRAYLIST") as ArrayList<Any>?
        Log.e("monthlyList",listMontly.toString())*/


        initViews()

        if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
            PrefDataShabdjal.getAppLanguageStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {

            val pastPuzzleRequest = PastMonthlyPuzzleShabdjaalRequest()
            pastPuzzleRequest.gameUserId= PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.GAME_USER_ID)
            pastPuzzleRequest.gameDate = gameDate.toString()
            pastPuzzleRequest.lang_id = "1"
            pastPuzzleRequest.app_id = PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
            getMonthlyPastPuzzle(pastPuzzleRequest)

        }else{
            val pastPuzzleRequest = PastMonthlyPuzzleShabdjaalRequest()
            pastPuzzleRequest.gameUserId= PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.GAME_USER_ID)
            pastPuzzleRequest.gameDate = gameDate.toString()
            pastPuzzleRequest.lang_id = "2"
            pastPuzzleRequest.app_id = PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_ID)
            getMonthlyPastPuzzle(pastPuzzleRequest)
        }
    }

    private fun initViews() {
        //initialisation views here--------------------------
        rvmonthlyPuzzle = findViewById(R.id.rvmonthlyPuzzle)
        iv_back_btn = findViewById(R.id.iv_back_btn)
        iv_back_btn?.setOnClickListener(this)

        tvMonthName = findViewById(R.id.tvMonthName)
        ivLangChange = findViewById(R.id.ivLangChange)
        ivLangChange?.setOnClickListener(this)

        llEng = findViewById(R.id.llEng)
        llHindi = findViewById(R.id.llHindi)
        tvEng = findViewById(R.id.tvEng)
        tvHindi = findViewById(R.id.tvHindi)

        if(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")){

            llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));
            //llHindi?.setBackgroundColor(Color.BLACK)
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

            if(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("english")) {
                llEng?.isClickable = false
            }else{

                llEng?.setBackgroundColor(Color.parseColor("#4267B2"));

                //llEng?.setBackgroundColor(Color.BLACK)
                llHindi?.setBackgroundColor(Color.WHITE)

                tvHindi?.setTextColor(Color.parseColor("#000000"));
                tvEng?.setTextColor(Color.parseColor("#FFFFFF"));

                ShabdjalLanguagePreference.getInstance(baseContext).language = ""
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.GAME_USER_ID))) {
                    val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

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

            if(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE).equals("hindi")) {
                llHindi?.isClickable = false
            }else{

                llHindi?.setBackgroundColor(Color.parseColor("#4267B2"));

               // llHindi?.setBackgroundColor(Color.BLACK)
                llEng?.setBackgroundColor(Color.WHITE)

                tvEng?.setTextColor(Color.parseColor("#000000"));
                tvHindi?.setTextColor(Color.parseColor("#FFFFFF"));


                ShabdjalLanguagePreference.getInstance(baseContext).language = "hi"
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.GAME_USER_ID))) {
                    val intent = Intent(this, PastPuzzleActivityShabdjaal::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }else{
                    val intent = Intent(this, ShabdjaalPlayGameActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                }

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == 22) {
            // Make sure the request was successful
            setResult(220)
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back_btn -> {
                finish()
            }
          R.id.ivLangChange ->{
            //  openLanguageBottomSheet()
          }
        }
    }



    private fun getMonthlyPastPuzzle(pastMonthlyRequest: PastMonthlyPuzzleShabdjaalRequest?) {
        compositeDisposable.add(
            apiService!!.getPastMonthlyPuzzle(pastMonthlyRequest)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
            !!.subscribe({ response ->
                    Log.e("get_monthly_puzzle:", response.toString())
                    if (response?.status == "true") {
                        if (response.data != null) {

                            pastMonthlyPuzzle?.addAll(response.data.shabdjaalArray!!)

                            tvMonthName?.text= response.data.shabdjaalArray!![0]?.month

                            monthlyPuzzleAdapter = MonthlyPuzzleAdapterShabdjaal(
                                this,
                                pastMonthlyPuzzle,
                                object : RecyclerViewOnClickShabdjal {
                                    override fun onItemClicked(
                                        position: Int,
                                        viewType: Int,
                                        view: View
                                    ) {
                                        when (viewType) {
                                            12 -> {

                                                val gameDate = pastMonthlyPuzzle?.get(0)?.shabdjaal?.get(position)?.date.toString()

                                                if(PrefDataShabdjal.getBooleanPrefs(this@MonthlyPuzzleActivity, PrefDataShabdjal.Key.ISRuleSHow)) {
                                                    val intent = Intent(
                                                        this@MonthlyPuzzleActivity,
                                                        ShabdjaalPlayGameActivity::class.java)
                                                    intent.putExtra("Date",gameDate)
                                                    startActivityForResult(intent, 22)
                                                }else{
                                                    val intent = Intent(
                                                        this@MonthlyPuzzleActivity,
                                                        GameRuleShabdActivity::class.java)
                                                    intent.putExtra("Date",gameDate)
                                                    startActivityForResult(intent, 22)
                                                }


                                            }
                                        }
                                    }
                                })

                            val layoutManager = GridLayoutManager(this, 3)
                            rvmonthlyPuzzle?.layoutManager = layoutManager
                            rvmonthlyPuzzle?.adapter = monthlyPuzzleAdapter
                        }
                    }
                }) { throwable ->

                    Log.d("Error", "" + throwable)
                })
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

        if(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
            PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")){
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

            if(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")){
                lLayHindiLang.isClickable = false
            }else{

                lLayHindiLang?.setBackgroundColor(Color.BLACK);
                lLayEnglishLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#FFFFFF"));
                tvEngLang?.setTextColor(Color.parseColor("#000000"));

                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("hi")
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.HINDI)
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.HINDI)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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

            if(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE)!=null &&
                PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("english")){
                lLayEnglishLang.isClickable = false
            }else{

                lLayEnglishLang?.setBackgroundColor(Color.BLACK);
                lLayHindiLang?.setBackgroundColor(Color.WHITE);

                tvHindiLang?.setTextColor(Color.parseColor("#000000"));
                tvEngLang?.setTextColor(Color.parseColor("#FFFFFF"));

                bottomSheetDialog.dismiss()


                ShabdjalLanguagePreference.getInstance(baseContext).setLanguage("")
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)
                PrefDataShabdjal.setStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE,PrefDataShabdjal.Key.ENGLISH)

                if(!TextUtils.isEmpty(PrefDataShabdjal.getStringPrefs(this@MonthlyPuzzleActivity,PrefDataShabdjal.Key.GAME_USER_ID))){
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