package com.qts.gopik_money.Pojo;

import java.io.Serializable;

public class DataFromAadhaarDTO implements Serializable {
	private String maskedAadhaarNumber;
	private String name;
	private String fatherName;
	private String husbandName;
	private String relativeName;
	private String dob;
	private String gender;
	private String image;
	private String mobileHash;
	private String emailHash;
	private String generatedDateTime;
	private String file;
	private AddressDTO address;

	public void setMaskedAadhaarNumber(String maskedAadhaarNumber){
		this.maskedAadhaarNumber = maskedAadhaarNumber;
	}

	public String getMaskedAadhaarNumber(){
		return maskedAadhaarNumber;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFatherName(String fatherName){
		this.fatherName = fatherName;
	}

	public String getFatherName(){
		return fatherName;
	}

	public void setHusbandName(String husbandName){
		this.husbandName = husbandName;
	}

	public String getHusbandName(){
		return husbandName;
	}

	public void setRelativeName(String relativeName){
		this.relativeName = relativeName;
	}

	public String getRelativeName(){
		return relativeName;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setMobileHash(String mobileHash){
		this.mobileHash = mobileHash;
	}

	public String getMobileHash(){
		return mobileHash;
	}

	public void setEmailHash(String emailHash){
		this.emailHash = emailHash;
	}

	public String getEmailHash(){
		return emailHash;
	}

	public void setGeneratedDateTime(String generatedDateTime){
		this.generatedDateTime = generatedDateTime;
	}

	public String getGeneratedDateTime(){
		return generatedDateTime;
	}

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setAddress(AddressDTO address){
		this.address = address;
	}

	public AddressDTO getAddress(){
		return address;
	}

	@Override
 	public String toString(){
		return 
			"DataFromAadhaarDTO{" + 
			"maskedAadhaarNumber = '" + maskedAadhaarNumber + '\'' + 
			",name = '" + name + '\'' + 
			",fatherName = '" + fatherName + '\'' + 
			",husbandName = '" + husbandName + '\'' + 
			",relativeName = '" + relativeName + '\'' + 
			",dob = '" + dob + '\'' + 
			",gender = '" + gender + '\'' + 
			",image = '" + image + '\'' + 
			",mobileHash = '" + mobileHash + '\'' + 
			",emailHash = '" + emailHash + '\'' + 
			",generatedDateTime = '" + generatedDateTime + '\'' + 
			",file = '" + file + '\'' + 
			",address = '" + address + '\'' + 
			"}";
		}
}