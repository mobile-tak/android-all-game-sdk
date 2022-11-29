package com.tvtoday.gamelibrary.crosswordgamesdk.model.submitGame

import com.google.gson.annotations.SerializedName

data class SubmitGameReuquest(

	@field:SerializedName("game_status")
    var gameStatus: String? = null,

	@field:SerializedName("lang_id")
	var lang_id: String? = null,

	@field:SerializedName("game_user_id")
	var gameUserId: String? = null,

	@field:SerializedName("time")
	var time: String? = null,

	@field:SerializedName("game_date")
	var game_date: String? = null,

	@field:SerializedName("app_id")
	var app_id: String? = null,
)
