package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity

import com.google.gson.annotations.SerializedName

data class GetLeaderBoardCrossWordResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: ArrayList<LeaderBoardDataList> = ArrayList(),

	@field:SerializedName("status")
	val status: String? = null
)

data class LeaderBoardDataList(

	@field:SerializedName("game_user_id")
	val gameUserId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null
)
