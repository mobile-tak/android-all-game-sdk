package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord

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
import com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`.RecyclerViewOnClick
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.monthlyPastPuzzleActivity.MonthlyPastPuzzleCrossWordActivity

class PastPuzzleOuterAdapter(
    private var activity: Activity,
   private var recyclerViewOnClick: RecyclerViewOnClick
) : RecyclerView.Adapter<PastPuzzleOuterAdapter.ViewHolder>() {

    private val list: ArrayList<CrosswordArrayItem?>? = ArrayList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvMonth: TextView = view.findViewById(R.id.tv_months)
        var rvInnerCategory: RecyclerView = view.findViewById(R.id.rvInnerCategory)
        var lLayMonthlyPuzzle: LinearLayout = view.findViewById(R.id.lLayMonthlyPuzzle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item =
            LayoutInflater.from(activity).inflate(R.layout.var_past_puzzle_outer_rv_layout, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list?.get(position)

        holder.apply {
            val crossWordItemArray : ArrayList<CrosswordItem?> = ArrayList()
            tvMonth.text = model?.month.toString()
            crossWordItemArray.addAll(model?.crossword!!)

            setCategoryRvItemData(holder.rvInnerCategory,crossWordItemArray)

            lLayMonthlyPuzzle.setOnClickListener {
                val intent = Intent(activity, MonthlyPastPuzzleCrossWordActivity::class.java)
                intent.putExtra("DATE_GAME", list?.get(absoluteAdapterPosition)?.crossword?.get(0)?.date)
                activity.startActivityForResult(intent, 11110)

                //recyclerViewOnClick.onItemClicked(position, 44,lLayMonthlyPuzzle)

            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    private fun setCategoryRvItemData(
        recyclerView: RecyclerView,
        categoryItem: ArrayList<CrosswordItem?>?
    ) {

        val pastPuzzleInnerAdapter = PastPuzzleInnerAdapter(activity, categoryItem)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = pastPuzzleInnerAdapter
    }


    fun setListData(item: ArrayList<CrosswordArrayItem?>){

        if(list!=null){
            list.clear()
        }
        list?.addAll(item)
        notifyDataSetChanged()
    }

}