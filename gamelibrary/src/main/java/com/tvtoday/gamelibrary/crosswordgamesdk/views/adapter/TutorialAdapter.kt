package com.tvtoday.gamelibrary.crosswordgamesdk.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tvtoday.gamelibrary.R


class TutorialAdapter(private val context: Context, private val list: List<Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.view_pager_inner_tutorial_, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var slide_image: ImageView = itemView.findViewById(R.id.iv_skip_image)

        fun bindData(image: Int?) {
            Glide.with(context).load(image).into(slide_image)
        }

    }
}