package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.monthlyPastPuzzleActivity

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.VargPaheliGameActivity
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`.RecyclerViewOnClick
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPahleiGameRuleActivity.GameRuleActivity

class MonthlyPuzzleCrossWordAdapter(
    private var activity: Activity,
    private val list: ArrayList<CrosswordArrayItem?>?,
    private var recyclerViewOnClick: RecyclerViewOnClick
) : RecyclerView.Adapter<MonthlyPuzzleCrossWordAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvMonths: TextView = view.findViewById(R.id.tvMonths)
        var lLayMain: LinearLayout = view.findViewById(R.id.lLayMain)
        var ivTick: ImageView = view.findViewById(R.id.ivTick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            LayoutInflater.from(activity).inflate(R.layout.monthly_puzzle_lay, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list?.get(0)!!

        holder.apply {
            tvMonths.text = model?.crossword?.get(position)?.date.toString()

            val monthData =model?.crossword?.get(position)?.date.toString()

            //isCompleteStatus= model?.shabdjaal!![position]?.isComplete!!

            val dateTime: List<String> = monthData.split(" ")
            val date = dateTime[0]
            val time = dateTime[1]

            tvMonths.text=  date

           /* if(model.crossword?.get(position)!!.isComplete==true){
                lLayMain.isClickable = false
                ivTick.visibility=View.VISIBLE
                lLayMain.isClickable = false
            }*/

            if(model.crossword?.get(position)!!.isComplete==true){
                ivTick.visibility=View.VISIBLE
                lLayMain.isClickable = false
            }else{
                ivTick.visibility=View.GONE
                lLayMain.isClickable = true
            }

            if(model.crossword.get(position)!!.isComplete==false){
                lLayMain.setOnClickListener {
                    ivTick.visibility=View.GONE

                    if(PrefData.getBooleanPrefs(activity, PrefData.Key.ISRuleSHow)){
                        val intent = Intent(activity, VargPaheliGameActivity::class.java)
                        intent.putExtra("DATE_GAME", model?.crossword?.get(position)?.date)
                        activity.startActivity(intent)
                        activity.finish()
                    }else{
                        val intent = Intent(
                            activity,
                            GameRuleActivity::class.java
                        )
                        intent.putExtra("DATE_GAME", model?.crossword?.get(position)?.date)
                        activity.startActivityForResult(intent, 11110)
                        activity.finish()
                    }


                    recyclerViewOnClick.onItemClicked(absoluteAdapterPosition, 76, lLayMain)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return list?.get(0)!!.crossword!!.size!!
    }


}