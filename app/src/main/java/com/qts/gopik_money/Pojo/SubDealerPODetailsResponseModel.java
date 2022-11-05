package com.qts.gopik_money.Pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubDealerPODetailsResponseModel{

	@SerializedName("image")
	private String image;

	@SerializedName("code")
	private int code;

	public List<PayloadItemData> getPayload() {
		return payload;
	}

	public void setPayload(List<PayloadItemData> payload) {
		this.payload = payload;
	}

	@SerializedName("payload")
	private List<PayloadItemData> payload;

	@SerializedName("message")
	private String message;

	public String getImage(){
		return image;
	}

	public int getCode(){
		return code;
	}


	public String getMessage(){
		return message;
	}
}