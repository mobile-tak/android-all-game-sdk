package com.tvtoday.gamelibrary.crosswordgamesdk.model.gameUserUpdateToken

import com.google.gson.annotations.SerializedName

data class GameUserUpdateTokenResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("uname")
	val uname: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created")
	val created: String? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("device_type")
	val deviceType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("profileimage")
	val profileimage: String? = null
)
