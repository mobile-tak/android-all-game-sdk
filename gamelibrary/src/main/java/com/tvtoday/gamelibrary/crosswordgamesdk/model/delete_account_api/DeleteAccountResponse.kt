package com.tvtoday.gamelibrary.crosswordgamesdk.model.delete_account_api

import com.google.gson.annotations.SerializedName

data class DeleteAccountResponse(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
