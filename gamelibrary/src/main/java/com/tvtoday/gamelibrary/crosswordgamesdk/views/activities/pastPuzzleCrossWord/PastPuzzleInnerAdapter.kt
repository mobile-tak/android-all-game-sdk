package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.englishVargPaheliGameActivity.EnglishVargPahleiGameActivity
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEvent
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.cleverTap.CleverTapEventConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPahleiGameRuleActivity.GameRuleActivity

class PastPuzzleInnerAdapter(private var activity: Activity,
                             private val list: ArrayList<CrosswordItem?>?
): RecyclerView.Adapter<PastPuzzleInnerAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvMonths: TextView = view.findViewById(R.id.tvMonths)
        var lLayInner: LinearLayout = view.findViewById(R.id.lLayInner)
        var ivIsCompleted: ImageView = view.findViewById(R.id.ivIsCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item= LayoutInflater.from(activity).inflate(R.layout.past_puzzle_rv_inner_layout_crossword,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list!![position]

        holder.apply {
            tvMonths.text= model?.date

            val mothData = model?.date.toString()
            val status =  model?.isComplete == true

            val dateTime: List<String> = mothData.split(" ")
            val date = dateTime[0]
            val time = dateTime[1]
            tvMonths.text= date

            if(model?.isComplete == true){
                ivIsCompleted.visibility= View.VISIBLE
                lLayInner.isClickable= false
            }else{
                ivIsCompleted.visibility= View.GONE
                lLayInner.isClickable= true
            }


            if(model?.isComplete == false){
                lLayInner.setOnClickListener {

                    if(PrefData.getBooleanPrefs(activity, PrefData.Key.ISRuleSHow)){

                        if(!TextUtils.isEmpty(PrefData.getAppLangaugeStringPrefs(activity,PrefData.Key.CROSSWORD_APP_LANGUAGE)) &&
                            PrefData.getAppLangaugeStringPrefs(activity,PrefData.Key.CROSSWORD_APP_LANGUAGE).equals("hindi")) {

                            CleverTapEvent(activity).createOnlyEvent(CleverTapEventConstants.PAST_PUZZLE_VP)

                            val intent = Intent(
                                activity,
                                VargPaheliGameActivity::class.java
                            )

                            intent.putExtra(
                                "DATE_GAME",
                                model?.date
                            )
                            activity.startActivityForResult(intent, 11110)

                        }else{
                            CleverTapEvent(activity).createOnlyEvent(CleverTapEventConstants.PAST_PUZZLE_CW)

                            val intent = Intent(
                                activity,
                                EnglishVargPahleiGameActivity::class.java
                            )

                            intent.putExtra(
                                "DATE_GAME",
                                model?.date
                            )
                            activity.startActivityForResult(intent, 11110)
                        }
                        /////////////////////////////////
                     /*   val intent = Intent(
                            activity,
                            VargPaheliGameActivity::class.java
                        )

                        intent.putExtra(
                            "DATE_GAME",
                            model?.date
                        )
                        activity.startActivityForResult(intent, 11110)*/
                    }else{
                        val intent = Intent(
                            activity,
                            GameRuleActivity::class.java
                        )

                        intent.putExtra(
                            "DATE_GAME",
                            model?.date
                        )
                        activity.startActivityForResult(intent, 11110)
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

}