package com.qts.gopik_loan.Pojo;

import java.io.Serializable;

public class ConsentValidationDTO implements Serializable {
	private boolean status;
	private String providedName;
	private Object percentageOfMatch;

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setProvidedName(String providedName){
		this.providedName = providedName;
	}

	public String getProvidedName(){
		return providedName;
	}

	public void setPercentageOfMatch(Object percentageOfMatch){
		this.percentageOfMatch = percentageOfMatch;
	}

	public Object getPercentageOfMatch(){
		return percentageOfMatch;
	}

	@Override
 	public String toString(){
		return 
			"ConsentValidationDTO{" + 
			"status = '" + status + '\'' + 
			",providedName = '" + providedName + '\'' + 
			",percentageOfMatch = '" + percentageOfMatch + '\'' + 
			"}";
		}
}