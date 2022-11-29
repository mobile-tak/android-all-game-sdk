package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame

import com.google.gson.annotations.SerializedName

data class ShabdjaalGameRequest(

    /*@field:SerializedName("app_id")
    var appId: String? = null,*/

    @field:SerializedName("game_user_id")
    var game_user_id: String? = null,

    @field:SerializedName("game_date")
    var game_date: String? = null,

    @field:SerializedName("lang_id")
    var lang_id: String? = null,

    @field:SerializedName("app_id")
    var app_id: String? = null,

)
