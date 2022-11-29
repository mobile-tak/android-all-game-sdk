package com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity


data class SignUpRequest(

	var uname: String? = null,

	var language_id: String? = null,

	var userId: String? = null,
	
	var name: String? = null,

	var email: String? = null,

	var profileimage: String? = null,

	var device_type : String?=null,
	var device_token:String?=null,

	var device_id : String?=null,
	var is_guest :Boolean?=null
)
