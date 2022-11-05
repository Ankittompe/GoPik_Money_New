package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class GoatAllCalculateModel{

	@SerializedName("code")
	private int code;

	@SerializedName("payload")
	private Payload payload;

	@SerializedName("message")
	private String message;

	public int getCode(){
		return code;
	}

	public Payload getPayload(){
		return payload;
	}

	public String getMessage(){
		return message;
	}
}