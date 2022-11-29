package com.tvtoday.gamelibrary.crosswordgamesdk.views.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity.LeaderBoardDataList

class LeaderBoardResultAdapter(
    private val activity: Activity, private val list: ArrayList<LeaderBoardDataList>
) : RecyclerView.Adapter<LeaderBoardResultAdapter.ViewHolder>() {

    private var minutes: Long = 0
    private  var seconds:Long = 0
    private var minute: String? = ""
    private  var second:String? = ""

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvPosition: TextView = view.findViewById(R.id.tvPosition)
        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvTime: TextView = view.findViewById(R.id.tvTime)
        var lLayResult: LinearLayout = view.findViewById(R.id.lLayResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.leader_board_result_rv_inner_layout, parent, false)

        return ViewHolder(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]



        holder.apply {

            if(position == 0){
                tvPosition.text = model.rank.toString()
                tvName.text = model.name.toString()
               // tvTime.text = model.time.toString()
                minutes = model.time?.toLong()!! / 60
                seconds = (model.time.toInt() % 60).toLong()
                minute = String.format("%02d", minutes)
                second = String.format("%02d", seconds)
                tvTime.text = "$minute : $second"
            }else if(position == 1){
                tvPosition.text = model.rank.toString()
                tvName.text = model.name.toString()
                minutes = model.time?.toLong()!! / 60
                seconds = (model.time.toInt() % 60).toLong()
                minute = String.format("%02d", minutes)
                second = String.format("%02d", seconds)
                tvTime.text = "$minute : $second"
            }else if(position ==2){
                tvPosition.text = model.rank.toString()
                tvName.text = model.name.toString()
                minutes = model.time?.toLong()!! / 60
                seconds = (model.time.toInt() % 60).toLong()
                minute = String.format("%02d", minutes)
                second = String.format("%02d", seconds)
                tvTime.text = "$minute : $second"
            }else if(position == 3){
                list.removeAt(list.size-1)
                //lLayResult.visibility =View.GONE
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArrayList(data: ArrayList<LeaderBoardDataList>) {
        if (list.size > 0) {
            list.clear()
        }
        list.addAll(data)
        notifyDataSetChanged()
    }
}