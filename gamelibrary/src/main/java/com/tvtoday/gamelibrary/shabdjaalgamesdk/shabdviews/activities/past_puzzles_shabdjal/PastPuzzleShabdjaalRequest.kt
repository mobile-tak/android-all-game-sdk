package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal

import com.google.gson.annotations.SerializedName

data class PastPuzzleShabdjaalRequest(

	@field:SerializedName("game_user_id")
    var gameUserId: String? = null,

    @field:SerializedName("lang_id")
    var lang_id: String? = null,

    @field:SerializedName("app_id")
    var app_id: String? = null,


)
