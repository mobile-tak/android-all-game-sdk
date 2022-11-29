package com.tvtoday.gamelibrary.shabdamgamesdk.model.deleteAccount;

import com.google.gson.annotations.SerializedName;

public class DeleteAccountRequest{

	@SerializedName("game_user_id")
	private String gameUserId;

	@SerializedName("app_id")
	private String app_id;

	@SerializedName("language_id")
	private String language_id;

	public void setGameUserId(String gameUserId){
		this.gameUserId = gameUserId;
	}

	public void setApp_id(String app_id){
		this.app_id = app_id;
	}

	public void setLanguageId(String language_id){
		this.language_id = language_id;
	}

	public String getGameUserId(){
		return gameUserId;
	}

	public String getApp_id(){
		return app_id;
	}

	public String getLanguageId(){
		return language_id;
	}
}