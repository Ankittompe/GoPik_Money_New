package com.qts.gopik_loan.Pojo;

import java.io.Serializable;

public class GoatAdharvalidationResponseDTO implements Serializable {
	private int code;
	private String message;
	private PayloadDTO payload;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setPayload(PayloadDTO payload){
		this.payload = payload;
	}

	public PayloadDTO getPayload(){
		return payload;
	}

	@Override
 	public String toString(){
		return 
			"GoatAdharvalidationResponseDTO{" + 
			"code = '" + code + '\'' + 
			",message = '" + message + '\'' + 
			",payload = '" + payload + '\'' + 
			"}";
		}
}