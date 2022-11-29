package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.adapter

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.AnswerModelShabd

class AnswerShabdAdapter(
    private var activity: Activity,
    private val answerList: ArrayList<AnswerModelShabd>
) : RecyclerView.Adapter<AnswerShabdAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(activity)
            .inflate(R.layout.rv_answer_inner_shabd_layout, parent, false)

        return ViewHolder(item)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = answerList[position]
        holder.tvAnswerr.paintFlags = Paint.ANTI_ALIAS_FLAG
        holder.tvAnswerr.text = model.answer

        if(model.isAnswered){
            holder.tvAnswerr.paintFlags = holder.tvAnswerr.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun getItemCount(): Int {
        return answerList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvAnswerr: TextView = view.findViewById(R.id.tvAnswerr)
    }
}