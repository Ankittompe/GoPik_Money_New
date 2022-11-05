package com.qts.gopik_money.Pojo;

import java.io.Serializable;

public class ClientDataDTO implements Serializable {
	private String caseId;

	public void setCaseId(String caseId){
		this.caseId = caseId;
	}

	public String getCaseId(){
		return caseId;
	}

	@Override
 	public String toString(){
		return 
			"ClientDataDTO{" + 
			"caseId = '" + caseId + '\'' + 
			"}";
		}
}