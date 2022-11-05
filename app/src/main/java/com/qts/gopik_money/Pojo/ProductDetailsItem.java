package com.qts.gopik_money.Pojo;

import com.google.gson.annotations.SerializedName;

public class ProductDetailsItem{

	@SerializedName("Breed")
	private String breed;

	@SerializedName("Price")
	private String price;

	@SerializedName("Tagid")
	private String tagid;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Weight")
	private String weight;

	public void setBreed(String breed){
		this.breed = breed;
	}

	public String getBreed(){
		return breed;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setTagid(String tagid){
		this.tagid = tagid;
	}

	public String getTagid(){
		return tagid;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
	}

	public ProductDetailsItem() {
	}

	public ProductDetailsItem(String breed, String price, String tagid, String gender, String weight) {
		this.breed = breed;
		this.price = price;
		this.tagid = tagid;
		this.gender = gender;
		this.weight = weight;
	}
}