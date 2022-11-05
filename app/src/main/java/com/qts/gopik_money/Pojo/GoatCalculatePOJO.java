package com.qts.gopik_money.Pojo;

public class GoatCalculatePOJO{
	private String no_of_goat;
	private String protct_insurnce;
	private String product_price;
	private String down_payment;
	private String hlth_kit;
	private String roi;
	private String tenure;

	public GoatCalculatePOJO(String no_of_goat, String protct_insurnce, String product_price, String down_payment, String hlth_kit, String roi, String tenure) {
		this.no_of_goat = no_of_goat;
		this.protct_insurnce = protct_insurnce;
		this.product_price = product_price;
		this.down_payment = down_payment;
		this.hlth_kit = hlth_kit;
		this.roi = roi;
		this.tenure = tenure;
	}

	public String getNo_of_goat() {
		return no_of_goat;
	}

	public void setNo_of_goat(String no_of_goat) {
		this.no_of_goat = no_of_goat;
	}

	public String getProtct_insurnce() {
		return protct_insurnce;
	}

	public void setProtct_insurnce(String protct_insurnce) {
		this.protct_insurnce = protct_insurnce;
	}

	public String getProduct_price() {
		return product_price;
	}

	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}

	public String getDown_payment() {
		return down_payment;
	}

	public void setDown_payment(String down_payment) {
		this.down_payment = down_payment;
	}

	public String getHlth_kit() {
		return hlth_kit;
	}

	public void setHlth_kit(String hlth_kit) {
		this.hlth_kit = hlth_kit;
	}

	public String getRoi() {
		return roi;
	}

	public void setRoi(String roi) {
		this.roi = roi;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
}
