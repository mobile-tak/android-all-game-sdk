package com.tvtoday.crosswordhindi.views.activity.homePage

import com.google.gson.annotations.SerializedName

data class AppUpdateResponse(

	@field:SerializedName("ios_version")
	val iosVersion: String? = null,

	@field:SerializedName("is_force_upgrade")
	val isForceUpgrade: String? = null,

	@field:SerializedName("android_version")
	val androidVersion: Int? = null
)
