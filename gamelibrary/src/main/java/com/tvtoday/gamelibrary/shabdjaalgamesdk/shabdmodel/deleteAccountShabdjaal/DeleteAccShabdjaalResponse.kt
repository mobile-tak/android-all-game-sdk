package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.deleteAccountShabdjaal

import com.google.gson.annotations.SerializedName

data class DeleteAccShabdjaalResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
