package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.uttarDekhoAnswerActivity

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.constants.IntentConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.VargPaheliBaseActivity


class UttarDekhoAnswerActivity : VargPaheliBaseActivity(), View.OnClickListener {
    //views----------------------------------------------------------------

    private var answerImage :String = ""
    private var rLayBackBtn :RelativeLayout?=null
    private var progressBar:ProgressBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uttar_dekho_answer)

        initViews()

    }

    private fun initViews() {
        //initialisation views----------------------------------
        var iv_answer = findViewById<ImageView>(R.id.iv_answer)
        progressBar = findViewById(R.id.progressBar)
        rLayBackBtn = findViewById(R.id.rLayBackBtn)
        rLayBackBtn?.setOnClickListener(this)

        answerImage = intent.getStringExtra(IntentConstants.UTTAR_DEKHO_IMAGE_URL).toString()


       /* Glide.with(this)
            .load(answerImage)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    iv_answer?.visibility = View.VISIBLE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    iv_answer?.visibility = View.GONE
                    return false
                }
            })
            .into(iv_answer)*/

        Glide.with(this).load(Uri.parse(answerImage)).into(iv_answer)

    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.rLayBackBtn -> {
                CommonUtils.backPress(this)
            }
        }

    }
}