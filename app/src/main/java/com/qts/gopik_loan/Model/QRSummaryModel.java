package com.qts.gopik_loan.Model;

import com.google.gson.annotations.SerializedName;

public class QRSummaryModel{

	@SerializedName("statusCode")
	private int statusCode;
	@SerializedName("data")
	private Data data;
	@SerializedName("message")
	private String message;
	@SerializedName("status")
	private boolean status;

	public int getStatusCode(){
		return statusCode;
	}

	public Data getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}
