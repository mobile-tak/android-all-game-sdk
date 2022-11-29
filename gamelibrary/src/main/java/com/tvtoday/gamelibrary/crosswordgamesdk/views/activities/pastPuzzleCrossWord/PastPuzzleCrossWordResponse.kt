package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord

import com.google.gson.annotations.SerializedName

data class PastPuzzleCrossWordResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CrosswordItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("is_complete")
	val isComplete: Boolean? = null
)

data class Data(

	@field:SerializedName("crossword_array")
	val crosswordArray: ArrayList<CrosswordArrayItem?>? = ArrayList()
)

data class CrosswordArrayItem(

	@field:SerializedName("crossword")
	var crossword: ArrayList<CrosswordItem?>? = ArrayList(),

	@field:SerializedName("month")
    var month: String? = null
)
