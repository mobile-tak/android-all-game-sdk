package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.`interface`.RecyclerViewOnClickShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.MonthlyPuzzleActivity


class PastPuzzleMainAdapterShabdaajl(
    private var activity: Activity,
    private var recyclerViewOnClickShabdjal: RecyclerViewOnClickShabdjal
) : RecyclerView.Adapter<PastPuzzleMainAdapterShabdaajl.ViewHolder>() {

    private val list : ArrayList<ShabdjaalArrayItem?> = ArrayList()
    private var monthlyList : ShabdjaalArrayItem?=null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvMonth: TextView = view.findViewById(R.id.tv_months)
        var rvInnerCategory: RecyclerView = view.findViewById(R.id.rvInnerCategory)
        var lLayMonthlyPuzzle: LinearLayout = view.findViewById(R.id.lLayMonthlyPuzzle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            LayoutInflater.from(activity).inflate(R.layout.past_puzzle_outer_rv_item_shabdjaal, parent, false)
        return ViewHolder(item)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list?.get(position)


        holder.apply {

             val shabdjaalItemArray : ArrayList<ShabdjaalItem?> = ArrayList()
            tvMonth.text = model?.month.toString()
            shabdjaalItemArray.addAll(model?.shabdjaal!!)

            setCategoryRvItemData(holder.rvInnerCategory,shabdjaalItemArray)

            lLayMonthlyPuzzle.setOnClickListener {
/*
                if(DataSingleton.itemList != null){
                    DataSingleton.itemList.clear()
                    DataSingleton.itemList.addAll(list?.get(position)!!.shabdjaal!!)
                }*/

                val intent = Intent(activity, MonthlyPuzzleActivity::class.java)
                intent.putExtra("gameDate",  list?.get(absoluteAdapterPosition)?.shabdjaal?.get(0)?.date)
               // bundle.putParcelableArrayList("MonthlyList", list?.get(position)!!.shabdjaal)
                activity.startActivity(intent)

                /* intent.putExtra("MonthlyList", list)
                 activity.startActivity(intent)*/
                //recyclerViewOnClickShabdjal.onItemClicked(position, 90, holder.lLayMonthlyPuzzle)
            }



        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    private fun setCategoryRvItemData(
        recyclerView: RecyclerView,
        categoryItem: ArrayList<ShabdjaalItem?>?
    ) {

        val pastPuzzleInnerAdapter = PastPuzzleInnerAdapterShabdjaal(activity, categoryItem)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = pastPuzzleInnerAdapter
    }

    fun setListData(item: ArrayList<ShabdjaalArrayItem?>){
        if(list!=null){
            list.clear()
        }
        list?.addAll(item)
    }

}