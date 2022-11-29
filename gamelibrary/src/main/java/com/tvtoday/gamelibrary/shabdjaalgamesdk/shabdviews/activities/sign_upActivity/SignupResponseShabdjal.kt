package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity

import com.google.gson.annotations.SerializedName

data class GameUserSignupResponse(

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

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("profileimage")
	val profileimage: String? = null,

	@field:SerializedName("device_id")
    val device_id: String? = null,

	@field:SerializedName("is_guest")
	val is_guest: Boolean? = null
)
