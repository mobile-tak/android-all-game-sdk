package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.gameAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.tvtoday.gamelibrary.R
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalBoradItem

internal class ShabdjaalGameAdapter(
    private val context: Context,
    private val numbersList: ArrayList<ShabdjaalBoradItem>,
    ) : BaseAdapter() {
    var currentColor: ArrayList<Int> =  ArrayList()
    private var layoutInflater: LayoutInflater? = null

    private lateinit var tvInnerText: TextView
    private lateinit var relativeLay: RelativeLayout
    private lateinit var onItemClickListener: AdapterView.OnItemClickListener
    var selectedList: ArrayList<Int> =  ArrayList()

    override fun getCount(): Int {
        return numbersList!!.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.grid_inner_cell_layout_shabd, null)
        }
        relativeLay = convertView!!.findViewById(R.id.relativeLay)
        /* if(currentColor.contains(position)){
             relativeLay.setBackgroundColor(Color.parseColor("#60909090"));
         }else if(numbersList[position].isWronAnswer == true){
             relativeLay?.setBackgroundColor(Color.parseColor("#40FF2F2F"));
         }else{
             //relativeLay?.setBackgroundColor(Color.parseColor("#FF0000"));
         }*/
        tvInnerText = convertView!!.findViewById(R.id.tvInnerText)
        tvInnerText.text = numbersList[position].letter

       /* relativeLay = convertView.findViewById(R.id.relativeLay)
        rlCellCont = convertView.findViewById(R.id.rl_cell_cont)*/
        if(selectedList.contains(position)){
            relativeLay?.setBackgroundColor(Color.parseColor("#40ff0000"));
        }else{
            relativeLay?.setBackgroundResource(R.drawable.cell_bg_drawable);
        }
        return convertView
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }


    fun addSelectedList(list: MutableList<Int>) {
        selectedList.addAll(list)
    }

    fun clearList() {
        selectedList.clear()
    }

}