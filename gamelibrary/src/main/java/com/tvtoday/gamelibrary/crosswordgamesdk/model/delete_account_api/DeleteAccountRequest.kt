package com.tvtoday.gamelibrary.crosswordgamesdk.model.delete_account_api

import com.google.gson.annotations.SerializedName

data class DeleteAccountRequest(

	@field:SerializedName("game_user_id")
    var gameUserId: String? = null,

    @field:SerializedName("language_id")
    var language_id: String? = null,

    @field:SerializedName("app_id")
    var app_id: String? = null,

)
