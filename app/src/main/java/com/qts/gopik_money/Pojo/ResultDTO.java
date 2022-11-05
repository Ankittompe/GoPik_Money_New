package com.qts.gopik_money.Pojo;

import java.io.Serializable;

public class ResultDTO implements Serializable {
	private ConsentValidationDTO consentValidation;
	private String message;
	private DataFromAadhaarDTO dataFromAadhaar;

	public void setConsentValidation(ConsentValidationDTO consentValidation){
		this.consentValidation = consentValidation;
	}

	public ConsentValidationDTO getConsentValidation(){
		return consentValidation;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setDataFromAadhaar(DataFromAadhaarDTO dataFromAadhaar){
		this.dataFromAadhaar = dataFromAadhaar;
	}

	public DataFromAadhaarDTO getDataFromAadhaar(){
		return dataFromAadhaar;
	}

	@Override
 	public String toString(){
		return 
			"ResultDTO{" + 
			"consentValidation = '" + consentValidation + '\'' + 
			",message = '" + message + '\'' + 
			",dataFromAadhaar = '" + dataFromAadhaar + '\'' + 
			"}";
		}
}