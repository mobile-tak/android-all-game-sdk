package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.nav_drawer_adapter

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
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.`interface`.RecyclerViewOnClickShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.utils.CommonUtilsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.nav_drawer_tutorial.NavDrawerTutorialShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpShabdjalActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.shabdjaal_settingActivity.ShabdjalSettingActivity


class NavDrawerShabdjalAdapter(private var activity: Activity,
                               private var list: ArrayList<NavDrawerShabdjalModel>, private val onItemClicked: RecyclerViewOnClickShabdjal
):RecyclerView.Adapter<NavDrawerShabdjalAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var nav_title : TextView = view.findViewById(R.id.nav_title)
        var lLayMain : LinearLayout = view.findViewById(R.id.lLayMain)
    }


    var mGoogleSignInClient: GoogleSignInClient? = null

    val userName = PrefDataShabdjal.getStringPrefs(activity, PrefDataShabdjal.Key.NAME)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.nav_drawer_shabd_layout, parent, false)

        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.apply {
            lLayMain.visibility = View.VISIBLE

            if(position == 0){

                if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(activity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                    PrefDataShabdjal.getAppLanguageStringPrefs(activity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {
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

            }

            if(position == 1){
                lLayMain.setOnClickListener {

                    CleverTapEventShabdjal(activity).createOnlyEvent(
                        CleverTapShabdjalConstants.OTHER_GAMES_TVTN)

                    val url = "https://play.google.com/store/apps/developer?id=TV+Today+Network"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    activity.startActivity(browserIntent)
                }
                nav_title.text = model.navString.toString()
            }
            if(position == 2){
                lLayMain.setOnClickListener {
                    CommonUtilsShabdjal.performIntent(activity, NavDrawerTutorialShabdjalActivity::class.java)
                    CleverTapEventShabdjal(activity).createOnlyEvent(
                        CleverTapShabdjalConstants.TUTORIAL)
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

                    CleverTapEventShabdjal(activity).createOnlyEvent(
                        CleverTapShabdjalConstants.SHARE_APP)
                }
                nav_title.text = model.navString.toString()
            }

            if(position == 4){
                lLayMain.setOnClickListener {

                    onItemClicked.onItemClicked(position, 11, holder.lLayMain)
                    val intent = Intent(activity, ShabdjalSettingActivity::class.java)
                    activity.startActivity(intent)
                    CleverTapEventShabdjal(activity).createOnlyEvent(
                        CleverTapShabdjalConstants.SETTINGS)
                    //CommonUtils.performIntent(activity,VargPaheliSettingActivity::class.java )
                }
                nav_title.text = model.navString.toString()
            }

            if(position == 5){
                lLayMain.setOnClickListener {
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
                if(PrefDataShabdjal.getStringPrefs(activity, PrefDataShabdjal.Key.GAME_USER_ID) == null){
                    lLayMain.visibility = View.GONE
                }else{
                    lLayMain.visibility = View.GONE
                }
                lLayMain.setOnClickListener {
                    PrefDataShabdjal.clearWholePreference(activity)
                    val intent = Intent(activity, SignUpShabdjalActivity::class.java)
                    activity.startActivity(intent)
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