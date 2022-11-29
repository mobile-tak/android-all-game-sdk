package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal

import PrefDataShabdjal
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapEventShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.cleverTap.CleverTapShabdjalConstants
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShabdjalGameRuleActivity.GameRuleShabdActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalPlayGameActivity
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.englishShabdjaalPlayGame.EnglishShabdjaalPlayGameActivity
import java.util.*

class PastPuzzleInnerAdapterShabdjaal(
    private var activity: Activity,
    private val list: ArrayList<ShabdjaalItem?>?
): RecyclerView.Adapter<PastPuzzleInnerAdapterShabdjaal.ViewHolder>(){


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvMonths: TextView = view.findViewById(R.id.tvMonths)
        var ivIsCompleted: ImageView = view.findViewById(R.id.ivIsCompleted)
        var lLayMain: LinearLayout = view.findViewById(R.id.lLayMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item= LayoutInflater.from(activity).inflate(R.layout.past_puzzle_rv_inner_layout_shabdjaal,parent,false)
        return ViewHolder(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list?.get(position)

        holder.apply {

            val mothData = model?.date.toString()

            val dateTime: List<String> = mothData.split(" ")
            val date = dateTime[0]
            val time = dateTime[1]


         /*   val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
            val date: LocalDate = LocalDate.parse(mothData, inputFormatter)
            val formattedDate: String = outputFormatter.format(date)

            Log.e("DDD",formattedDate.toString())*/
            tvMonths.text= date.toString()

            if(model?.isComplete==true){
                lLayMain.isClickable = false
                ivIsCompleted.visibility= View.VISIBLE
            }else{
                lLayMain.isClickable = true
                ivIsCompleted.visibility= View.GONE
            }

            if(model?.isComplete==false){
                lLayMain.setOnClickListener {
                    ivIsCompleted.visibility= View.GONE
                    if(PrefDataShabdjal.getBooleanPrefs(activity, PrefDataShabdjal.Key.ISRuleSHow)) {

                        if(!TextUtils.isEmpty(PrefDataShabdjal.getAppLanguageStringPrefs(activity,PrefDataShabdjal.Key.SHABDJAAL_APP_LANGUAGE)) &&
                            PrefDataShabdjal.getAppLanguageStringPrefs(activity,PrefDataShabdjal.Key.SHABDJAAL_LANGUAGE).equals("hindi")) {

                            CleverTapEventShabdjal(activity).createOnlyEvent(
                                CleverTapShabdjalConstants.PAST_PUZZLE_SHABJAAL)

                            PrefDataShabdjal.setStringPrefs(activity,PrefDataShabdjal.Key.DATE_FOR_LANGUAGE_CANGE_PART,mothData)
                            val intent = Intent(activity, ShabdjaalPlayGameActivity::class.java)
                            intent.putExtra("Date",mothData)
                            activity.startActivityForResult(intent, 220)

                        }else{
                            CleverTapEventShabdjal(activity).createOnlyEvent(
                                CleverTapShabdjalConstants.PAST_PUZZLE_WORD_SEARCH)
                            PrefDataShabdjal.setStringPrefs(activity,PrefDataShabdjal.Key.DATE_FOR_LANGUAGE_CANGE_PART,mothData)
                            val intent = Intent(activity, EnglishShabdjaalPlayGameActivity::class.java)
                            intent.putExtra("Date",mothData)
                            activity.startActivityForResult(intent, 220)
                        }

                    }else{
                        val intent = Intent(activity, GameRuleShabdActivity::class.java)
                        intent.putExtra("Date",mothData)
                        activity.startActivityForResult(intent, 220)
                    }


                }
            }


        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

}