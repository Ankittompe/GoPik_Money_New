package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class RejectPOModel {

	@SerializedName("code")
	private int code;
	@SerializedName("message")
	private String message;

	public String getMessage(){
		return message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
