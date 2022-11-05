package com.qts.gopik_money.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubDealerPOListResponseModel{
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ArrayList<PayloadItem> getPayload() {
		return payload;
	}

	public void setPayload(ArrayList<PayloadItem> payload) {
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Expose
	@SerializedName("code")
	private int code;
	@Expose
	@SerializedName("payload")
	private ArrayList<PayloadItem> payload;
	@Expose
	@SerializedName("message")
	private String message;

}