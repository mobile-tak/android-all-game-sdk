package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.submitGame

import com.google.gson.annotations.SerializedName

data class SubmitGameResponseShabd(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("game_status")
	val gameStatus: String? = null,

	@field:SerializedName("game_user_id")
	val gameUserId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("no_of_attempt")
	val noOfAttempt: Int? = null,

	@field:SerializedName("game_date")
    val game_date: String? = null
)
