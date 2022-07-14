package com.qts.gopik_loan.Pojo;

import java.io.Serializable;

public class AddressDTO implements Serializable {
	private String combinedAddress;
	private SplitAddressDTO splitAddress;

	public void setCombinedAddress(String combinedAddress){
		this.combinedAddress = combinedAddress;
	}

	public String getCombinedAddress(){
		return combinedAddress;
	}

	public void setSplitAddress(SplitAddressDTO splitAddress){
		this.splitAddress = splitAddress;
	}

	public SplitAddressDTO getSplitAddress(){
		return splitAddress;
	}

	@Override
 	public String toString(){
		return 
			"AddressDTO{" + 
			"combinedAddress = '" + combinedAddress + '\'' + 
			",splitAddress = '" + splitAddress + '\'' + 
			"}";
		}
}