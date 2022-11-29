package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.response

import com.google.gson.annotations.SerializedName

data class PastMonthlyPuzzleShabdjaalResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("shabdjaal_array")
	val shabdjaalArray: ArrayList<ShabdjaalArrayItem?>? = ArrayList()
)

data class ShabdjaalItemMonth(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("is_complete")
	val isComplete: Boolean? = null
)

data class ShabdjaalArrayItem(

	@field:SerializedName("shabdjaal")
	val shabdjaal: ArrayList<ShabdjaalItemMonth?>? = ArrayList(),

	@field:SerializedName("month")
	val month: String? = null
)
