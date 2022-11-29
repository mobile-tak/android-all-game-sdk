package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.nav_drawer_adapter

import PrefData

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.tvtoday.crosswordhindi.controller.*

import com.tvtoday.crosswordhindi.controller.utils.CommonUtils
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`.RecyclerViewOnClick
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.nav_drawer_tutorial.NavDrawerTutorialActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.varg_paheli_settingActivity.VargPaheliSettingActivity


class NavDrawerAdapter(private var activity: Activity,
                       private var list: ArrayList<NavDrawerModel>, private val onItemClicked: RecyclerViewOnClick
):RecyclerView.Adapter<NavDrawerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var nav_title : TextView = view.findViewById(R.id.nav_title)
        var lLayMain : LinearLayout = view.findViewById(R.id.lLayMain)
    }

    var mGoogleSignInClient: GoogleSignInClient? = null

    val userName = PrefData.getStringPrefs(activity, PrefData.Key.NAME)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.nav_drawer_rv_inner_layout, parent, false)

        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.apply {
            lLayMain.visibility = View.VISIBLE

            if(position == 0){

                if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(activity,PrefData.Key.CROSSWORD_LANGAUGE)) &&
                    PrefData.getAppLangaugeStringPrefs(activity,PrefData.Key.CROSSWORD_LANGAUGE).equals("hindi")) {
                    if(userName!=null){
                        nav_title.text = "नमस्ते, $userName"
                    }else{
                        nav_title.text = "नमस्ते,"
                    }
                }else{
                    if(userName!=null){
                        nav_title.text = "Hi, $userName"
                    }else{
                        nav_title.text = "Hi,"
                    }
                }

              /*
                if(userName!=null){

                    //val url: String = Resources.getSystem().getString(com.tvtoday.vargpaheli_sdk.R.string.song)
                    nav_title.text = "नमस्ते, $userName"

                }else{
                    nav_title.text = "नमस्ते,"
                }*/
            }

            if(position == 1){
                lLayMain.setOnClickListener {

                    CleverTapEvent(activity).createOnlyEvent(
                        CleverTapEventConstants.OTHER_GAMES_TVTN)

                    val url = "https://play.google.com/store/apps/developer?id=TV+Today+Network"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(browserIntent)
                }
                nav_title.text = model.navString.toString()
            }
            if(position == 2){
                lLayMain.setOnClickListener {
                    CommonUtils.performIntent(activity, NavDrawerTutorialActivity::class.java)
                    CleverTapEvent(activity).createOnlyEvent(
                        CleverTapEventConstants.TUTORIAL)
                }
                nav_title.text = model.navString.toString()
            }
            if(position == 3){
                lLayMain.setOnClickListener {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Love playing crossword, check out the new Hindi crossword on Android https://play.google.com/store/apps/details?id=com.tvtoday.crosswordhindi" +  "\n" +
                                "An iPhone version is also available: https://apps.apple.com/us/app/vargpaheli/id1622360590"
                    )
                    sendIntent.type = "text/plain"
                    activity.startActivity(sendIntent)

                    CleverTapEvent(activity).createOnlyEvent(
                        CleverTapEventConstants.SHARE_APP)
                }
                nav_title.text = model.navString.toString()
            }

            if(position == 4){
                lLayMain.setOnClickListener {

                    onItemClicked.onItemClicked(position, 11, holder.lLayMain)

                    val intent = Intent(activity, VargPaheliSettingActivity::class.java)
                    activity.startActivity(intent)

                    CleverTapEvent(activity).createOnlyEvent(
                        CleverTapEventConstants.SETTINGS)

                    //CommonUtils.performIntent(activity,VargPaheliSettingActivity::class.java )
                }
                nav_title.text = model.navString.toString()
            }

            if(position == 5){
                lLayMain.setOnClickListener {

                   /* val file =
                        File(Environment.getExternalStorageDirectory().absolutePath + "/" + R.raw.game_privacy_policy)
                    val target = Intent(Intent.ACTION_VIEW)
                    target.setDataAndType(Uri.fromFile(file), "application/pdf")
                    target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY

                    val intent = Intent.createChooser(target, "Open File")
                    try {
                        activity.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        // Instruct the user to install a PDF reader here, or something
                    }*/

                  val url = "https://docs.google.com/document/d/1efJN1iZrt9r_hd4hK8_Kct0tUsnVNptSXSm_26DcE6Q/edit?usp=sharing"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(browserIntent)
                }
                nav_title.text = model.navString.toString()
            }


            if(position == 6){
                lLayMain.setOnClickListener {
                    val url = "https://docs.google.com/document/d/1efJN1iZrt9r_hd4hK8_Kct0tUsnVNptSXSm_26DcE6Q/edit?usp=sharing"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(browserIntent)
                }
                nav_title.text = model.navString.toString()
            }

            if(position == 7) {
                if(PrefData.getStringPrefs(activity, PrefData.Key.GAME_USER_ID) == null){
                    lLayMain.visibility = View.GONE
                }else{
                    lLayMain.visibility = View.VISIBLE
                }
                lLayMain.setOnClickListener {
                    PrefData.clearWholePreference(activity)
                    PrefData.setBooleanPrefs(activity, PrefData.Key.IS_LOGOUT, true)

                    activity.finish()
                }
                nav_title.setTextColor(Color.parseColor("#ff0000"));
                nav_title.text = model.navString.toString()
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}