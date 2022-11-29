package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.gameUserUpdateToken

import com.google.gson.annotations.SerializedName

data class TokenRequestShabdjal(

	@field:SerializedName("game_user_id")
	var gameUserId: String? = null,

	@field:SerializedName("device_token")
	var deviceToken: String? = null,

	@field:SerializedName("device_type")
	var deviceType: String? = null,

	@field:SerializedName("language_id")
	var language_id: String? = null,

	@field:SerializedName("app_id")
	var app_id: String? = null,
)
