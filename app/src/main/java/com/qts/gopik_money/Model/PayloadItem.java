package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayloadItem implements Serializable {

	@SerializedName("id")
	String id;

	@SerializedName("po_id")
	String po_id;
	@SerializedName("po_img")
	String po_img;
	@SerializedName("date")
	String date;
	@SerializedName("brand")
	String brand;
	@SerializedName("subdealer_id")
	String subdealer_id;
	@SerializedName("dealer_id")
	String dealer_id;
	@SerializedName("dealer_name")
	String dealer_name;
	@SerializedName("product")
	String product;
	@SerializedName("prodt_quantity")
	String prodt_quantity;
	@SerializedName("update_quantity")
	String update_quantity;
	@SerializedName("prodt_price")
	String prodt_price;
	@SerializedName("update_price")
	String update_price;
	@SerializedName("total_price")
	String total_price;
	@SerializedName("update_totl_prc")
	String update_totl_prc;
	@SerializedName("mod_totl_price")
	String mod_totl_price;
	@SerializedName("invoice_no")
	String invoice_no;
	@SerializedName("financer")
	String financer;
	@SerializedName("status")
	String status;
	@SerializedName("loan_limit")
	String loan_limit;
	@SerializedName("app_amt")
	String app_amt;
	@SerializedName("tenure")
	String tenure;
	@SerializedName("roi")
	String roi;
	@SerializedName("comments")
	String comments;
	@SerializedName("reason_of_rejection")
	String reason_of_rejection;
	@SerializedName("invoice_file")
	String invoice_file;
	@SerializedName("disvrsal_report")
	String disvrsal_report;
	@SerializedName("loan_no")
	String loan_no;
	@SerializedName("dealer_time")
	String dealer_time;
	@SerializedName("subdealer_action_time")
	String subdealer_action_time;
	@SerializedName("financer_action_time")
	String financer_action_time;
	@SerializedName("financer_disbursal_time")
	String financer_disbursal_time;
	@SerializedName("delivery_gen_time")
	String delivery_gen_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPo_id() {
		return po_id;
	}

	public void setPo_id(String po_id) {
		this.po_id = po_id;
	}

	public String getPo_img() {
		return po_img;
	}

	public void setPo_img(String po_img) {
		this.po_img = po_img;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSubdealer_id() {
		return subdealer_id;
	}

	public void setSubdealer_id(String subdealer_id) {
		this.subdealer_id = subdealer_id;
	}

	public String getDealer_id() {
		return dealer_id;
	}

	public void setDealer_id(String dealer_id) {
		this.dealer_id = dealer_id;
	}

	public String getDealer_name() {
		return dealer_name;
	}

	public void setDealer_name(String dealer_name) {
		this.dealer_name = dealer_name;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProdt_quantity() {
		return prodt_quantity;
	}

	public void setProdt_quantity(String prodt_quantity) {
		this.prodt_quantity = prodt_quantity;
	}

	public String getUpdate_quantity() {
		return update_quantity;
	}

	public void setUpdate_quantity(String update_quantity) {
		this.update_quantity = update_quantity;
	}

	public String getProdt_price() {
		return prodt_price;
	}

	public void setProdt_price(String prodt_price) {
		this.prodt_price = prodt_price;
	}

	public String getUpdate_price() {
		return update_price;
	}

	public void setUpdate_price(String update_price) {
		this.update_price = update_price;
	}

	public String getTotal_price() {
		return total_price;
	}

	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}

	public String getUpdate_totl_prc() {
		return update_totl_prc;
	}

	public void setUpdate_totl_prc(String update_totl_prc) {
		this.update_totl_prc = update_totl_prc;
	}

	public String getMod_totl_price() {
		return mod_totl_price;
	}

	public void setMod_totl_price(String mod_totl_price) {
		this.mod_totl_price = mod_totl_price;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getFinancer() {
		return financer;
	}

	public void setFinancer(String financer) {
		this.financer = financer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoan_limit() {
		return loan_limit;
	}

	public void setLoan_limit(String loan_limit) {
		this.loan_limit = loan_limit;
	}

	public String getApp_amt() {
		return app_amt;
	}

	public void setApp_amt(String app_amt) {
		this.app_amt = app_amt;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getRoi() {
		return roi;
	}

	public void setRoi(String roi) {
		this.roi = roi;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getReason_of_rejection() {
		return reason_of_rejection;
	}

	public void setReason_of_rejection(String reason_of_rejection) {
		this.reason_of_rejection = reason_of_rejection;
	}

	public String getInvoice_file() {
		return invoice_file;
	}

	public void setInvoice_file(String invoice_file) {
		this.invoice_file = invoice_file;
	}

	public String getDisvrsal_report() {
		return disvrsal_report;
	}

	public void setDisvrsal_report(String disvrsal_report) {
		this.disvrsal_report = disvrsal_report;
	}

	public String getLoan_no() {
		return loan_no;
	}

	public void setLoan_no(String loan_no) {
		this.loan_no = loan_no;
	}

	public String getDealer_time() {
		return dealer_time;
	}

	public void setDealer_time(String dealer_time) {
		this.dealer_time = dealer_time;
	}

	public String getSubdealer_action_time() {
		return subdealer_action_time;
	}

	public void setSubdealer_action_time(String subdealer_action_time) {
		this.subdealer_action_time = subdealer_action_time;
	}

	public String getFinancer_action_time() {
		return financer_action_time;
	}

	public void setFinancer_action_time(String financer_action_time) {
		this.financer_action_time = financer_action_time;
	}

	public String getFinancer_disbursal_time() {
		return financer_disbursal_time;
	}

	public void setFinancer_disbursal_time(String financer_disbursal_time) {
		this.financer_disbursal_time = financer_disbursal_time;
	}

	public String getDelivery_gen_time() {
		return delivery_gen_time;
	}

	public void setDelivery_gen_time(String delivery_gen_time) {
		this.delivery_gen_time = delivery_gen_time;
	}
}
