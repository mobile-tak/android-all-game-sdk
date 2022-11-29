package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame

import com.google.gson.annotations.SerializedName

data class ShabdjaalGameResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ShabdjaalPlay(
	@field:SerializedName("word")
	val word: List<String?>? = null,

	@field:SerializedName("split")
	val split: List<String?>? = null
)

data class ShabdjaalBoradItem(

	@field:SerializedName("is_wrong_answer")
	var isWronAnswer :Boolean? = false,

	@field:SerializedName("cell_column")
	val cellColumn: String? = null,

	@field:SerializedName("letter")
	val letter: String? = null,

	@field:SerializedName("is_in_word")
	val isInWord: Int? = null,

	@field:SerializedName("cell_row")
	val cellRow: String? = null,

	var isHinted: Boolean = false,
)

data class Data(

	@field:SerializedName("shabdjaal_play")
	val shabdjaalPlay: ShabdjaalPlay? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("game_date")
	val gameDate: String? = null,

	@field:SerializedName("shabdjaal_borad")
	val shabdjaalBorad: List<ShabdjaalBoradItem>? = null,

	@field:SerializedName("today_leaderboard_status")
	var today_leaderboard_status: Boolean = false
)
