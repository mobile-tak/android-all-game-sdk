package com.tvtoday.gamelibrary.crosswordgamesdk.controller.`interface`

import android.view.View

interface RecyclerViewOnClick {
    fun onItemClicked( position:Int , viewType:Int , view: View)
}