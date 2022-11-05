package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class GoatFinalCalculateModel {

	@SerializedName("code")
	private int code;

	@SerializedName("payload")
	private PayloadFinal payload;

	@SerializedName("message")
	private String message;

	public int getCode(){
		return code;
	}

	public PayloadFinal getPayload(){
		return payload;
	}

	public String getMessage(){
		return message;
	}
}