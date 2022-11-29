package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PastPuzzleShabdjaalResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)
@Parcelize
data class Data(
	@field:SerializedName("shabdjaal_array")
	val shabdjaalArray: ArrayList<ShabdjaalArrayItem?>? = ArrayList()
) : Parcelable

@Parcelize
data class ShabdjaalArrayItem(

	@field:SerializedName("shabdjaal")
	var shabdjaal: ArrayList<ShabdjaalItem?>? = ArrayList(),

	@field:SerializedName("month")
    var month: String? = null
) : Parcelable

@Parcelize
data class ShabdjaalItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("is_complete")
	val isComplete: Boolean? = null
) : Parcelable
