package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.adapter

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.`interface`.RecyclerViewOnClickShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.response.ShabdjaalArrayItem
import java.util.*


class MonthlyPuzzleAdapterShabdjaal(
    private var activity: Activity,
    private val list: ArrayList<ShabdjaalArrayItem?>?,
    private val recyclerViewOnClickShabdjal: RecyclerViewOnClickShabdjal
) : RecyclerView.Adapter<MonthlyPuzzleAdapterShabdjaal.ViewHolder>() {

    var isCompleteStatus = false

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvMonths: TextView = view.findViewById(R.id.tvMonths)
        var ivTick: ImageView = view.findViewById(R.id.ivTick)
        var lLayMain: LinearLayout = view.findViewById(R.id.lLayMain)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            LayoutInflater.from(activity)
                .inflate(R.layout.monthly_puzzle_layout_rv_shbdjaal, parent, false)
        return ViewHolder(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list!![0]

        holder.apply {
            tvMonths.text = model?.month
            val monthData = model?.shabdjaal?.get(position)?.date.toString()

            val dateTime: List<String> = monthData.split(" ")
            val date = dateTime[0]
            val time = dateTime[1]
            /*val inputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val outputFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
            val date: LocalDate = LocalDate.parse(monthData, inputFormatter)
            val formattedDate: String = outputFormatter.format(date)
            Log.e("DDD",formattedDate.toString())*/



           // tvMonths.text= date.toString()
            tvMonths.text=date.toString()

         /*   if(isCompleteStatus){
                ivTick.visibility=View.VISIBLE
            }*/

            if(model?.shabdjaal?.get(position)!!.isComplete==true){
                ivTick.visibility=View.VISIBLE
                lLayMain.isClickable = false
            }else{
                ivTick.visibility=View.GONE
                lLayMain.isClickable = true
            }
            if(model?.shabdjaal?.get(position)!!.isComplete==false){
                lLayMain.setOnClickListener {
                    recyclerViewOnClickShabdjal.onItemClicked(absoluteAdapterPosition, 12, holder.lLayMain)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list!![0]!!.shabdjaal!!.size
    }

}