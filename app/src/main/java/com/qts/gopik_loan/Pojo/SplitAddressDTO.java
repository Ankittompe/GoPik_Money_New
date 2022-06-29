package com.qts.gopik_loan.Pojo;

import java.io.Serializable;

public class SplitAddressDTO implements Serializable {
	private String district;
	private String country;
	private String subdistrict;
	private String pincode;
	private String state;
	private String street;
	private String postOffice;
	private String location;
	private String landmark;
	private String houseNumber;
	private String vtcName;

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setSubdistrict(String subdistrict){
		this.subdistrict = subdistrict;
	}

	public String getSubdistrict(){
		return subdistrict;
	}

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public String getPincode(){
		return pincode;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setPostOffice(String postOffice){
		this.postOffice = postOffice;
	}

	public String getPostOffice(){
		return postOffice;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setLandmark(String landmark){
		this.landmark = landmark;
	}

	public String getLandmark(){
		return landmark;
	}

	public void setHouseNumber(String houseNumber){
		this.houseNumber = houseNumber;
	}

	public String getHouseNumber(){
		return houseNumber;
	}

	public void setVtcName(String vtcName){
		this.vtcName = vtcName;
	}

	public String getVtcName(){
		return vtcName;
	}

	@Override
 	public String toString(){
		return 
			"SplitAddressDTO{" + 
			"district = '" + district + '\'' + 
			",country = '" + country + '\'' + 
			",subdistrict = '" + subdistrict + '\'' + 
			",pincode = '" + pincode + '\'' + 
			",state = '" + state + '\'' + 
			",street = '" + street + '\'' + 
			",postOffice = '" + postOffice + '\'' + 
			",location = '" + location + '\'' + 
			",landmark = '" + landmark + '\'' + 
			",houseNumber = '" + houseNumber + '\'' + 
			",vtcName = '" + vtcName + '\'' + 
			"}";
		}
}