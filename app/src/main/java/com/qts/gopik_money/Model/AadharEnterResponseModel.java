package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class AadharEnterResponseModel{

	@SerializedName("code")
	private int code;

	@SerializedName("payload")
	private PayloadAadhar payload;

	@SerializedName("message")
	private String message;

	public int getCode(){
		return code;
	}

	public PayloadAadhar getPayload(){
		return payload;
	}

	public String getMessage(){
		return message;
	}
}