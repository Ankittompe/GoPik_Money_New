package com.qts.gopik_money.Pojo;

import java.io.Serializable;

public class AadharResponsePOJODTO implements Serializable {
	private ResultDTO result;
	private int statusCode;
	private String requestId;
	private ClientDataDTO clientData;

	public void setResult(ResultDTO result){
		this.result = result;
	}

	public ResultDTO getResult(){
		return result;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public void setRequestId(String requestId){
		this.requestId = requestId;
	}

	public String getRequestId(){
		return requestId;
	}

	public void setClientData(ClientDataDTO clientData){
		this.clientData = clientData;
	}

	public ClientDataDTO getClientData(){
		return clientData;
	}

	@Override
 	public String toString(){
		return 
			"AadharResponsePOJODTO{" + 
			"result = '" + result + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",requestId = '" + requestId + '\'' + 
			",clientData = '" + clientData + '\'' + 
			"}";
		}
}