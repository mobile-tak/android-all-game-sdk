package com.tvtoday.gamelibrary.core.views.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.MonthlyPuzzleModel

class MonthlyPuzzleAdapter(
    private var activity: Activity,
    private val list: List<MonthlyPuzzleModel>
): RecyclerView.Adapter<MonthlyPuzzleAdapter.ViewHolder>(){


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvMonths: TextView = view.findViewById(R.id.tvMonths)
        var tvNewGame: TextView = view.findViewById(R.id.tvNewGame)
        var tvpuzzleStatus: TextView = view.findViewById(R.id.tvpuzzleStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item= LayoutInflater.from(activity).inflate(R.layout.monthly_puzzle_layout,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.apply {
            tvMonths.text= model.month
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}