package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity

import com.google.gson.annotations.SerializedName

data class GetLeaderBoardRequest(

	@field:SerializedName("game_user_id")
	var gameUserId: String? = null,

	@field:SerializedName("game_date")
    var game_date: String? = null,

	@field:SerializedName("lang_id")
	var lang_id: String? = null,

	@field:SerializedName("app_id")
	var app_id: String? = null,


)
