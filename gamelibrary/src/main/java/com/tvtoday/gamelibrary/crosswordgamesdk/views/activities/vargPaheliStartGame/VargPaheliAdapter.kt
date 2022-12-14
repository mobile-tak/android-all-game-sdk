package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame

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

internal class VargPaheliAdapter(
    private val context: Context,
    private val numbersList: ArrayList<CorsswordBoradItem>,
    ) :BaseAdapter() {
    var currentCellIndex :Int = 0
    var currentAnswerIndexes: ArrayList<Int> =  ArrayList()
    private var layoutInflater: LayoutInflater? = null
    private var relativeLay: RelativeLayout? = null
    private lateinit var tvInnerText: TextView
    private lateinit var tvCellNo: TextView
    private lateinit var rlCellCont: RelativeLayout
    private lateinit var onItemClickListener: AdapterView.OnItemClickListener

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
            convertView = layoutInflater!!.inflate(R.layout.grid_inner_cell_layout, null)
        }
        tvInnerText = convertView!!.findViewById(R.id.tvInnerText)
        tvCellNo = convertView.findViewById(R.id.tvCellNo)
        relativeLay = convertView.findViewById(R.id.relativeLay)
        rlCellCont = convertView.findViewById(R.id.rl_cell_cont)
        //tvInnerText.text = numbersList?.get(position).toString()
        if(position == currentCellIndex){
            relativeLay?.setBackgroundColor(Color.parseColor("#909090"));
        }else if(currentAnswerIndexes.contains(position)){
            relativeLay?.setBackgroundColor(Color.parseColor("#60909090"));
        }else if(numbersList[position].isFreezed == false && numbersList[position].isWronAnswer == true){
            relativeLay?.setBackgroundColor(Color.parseColor("#40FF2F2F"));
        }else{
            relativeLay?.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        if(numbersList.get(position).cellInfo == "0"){
            tvCellNo.text= " "
        }else if(numbersList.get(position).cellInfo == "-1"){
            tvCellNo.text= " "
            relativeLay?.setBackgroundColor(Color.parseColor("#000000"));
        } else{
            tvCellNo.text = numbersList.get(position).cellInfo
        }

        if(numbersList.get(position).value != null){
            tvInnerText.text = numbersList.get(position).value
        }
        /*rlCellCont.setOnClickListener(View.OnClickListener {
            if(onItemClickListener != null){
              //  onItemClickListener.onItemClick(null,convertView, 0, position);
            }
        })*/

        return convertView
    }

    fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }



}