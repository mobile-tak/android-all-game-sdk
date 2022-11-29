package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.model

import com.google.gson.annotations.SerializedName

data class PastPuzzleCrossWordResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CrosswordArrayItem(

	@field:SerializedName("crossword")
	val crossword: List<CrosswordItem?>? = null,

	@field:SerializedName("month")
	val month: String? = null
)

data class Data(

	@field:SerializedName("crossword_array")
	val crosswordArray: List<CrosswordArrayItem?>? = null
)

data class CrosswordItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("is_complete")
	val isComplete: Boolean? = null
)
