package com.qts.gopik_money.Pojo;

import com.google.gson.annotations.SerializedName;

public class PayloadItem{

	@SerializedName("date")
	private String date;

	@SerializedName("mod_totl_price")
	private String modTotlPrice;

	@SerializedName("po_id")
	private String poId;

	@SerializedName("loan_limit")
	private String loanLimit;

	@SerializedName("roi")
	private String roi;

	@SerializedName("subdealer_id")
	private String subdealerId;

	@SerializedName("financer_action_time")
	private String financerActionTime;

	@SerializedName("reason_of_rejection")
	private String reasonOfRejection;

	@SerializedName("financer_disbursal_time")
	private String financerDisbursalTime;

	@SerializedName("disvrsal_report")
	private String disvrsalReport;

	@SerializedName("app_amt")
	private String appAmt;

	@SerializedName("id")
	private int id;

	@SerializedName("brand")
	private String brand;

	@SerializedName("tenure")
	private String tenure;

	@SerializedName("invoice_file")
	private String invoiceFile;

	@SerializedName("dealer_id")
	private String dealerId;

	@SerializedName("prodt_quantity")
	private String prodtQuantity;

	@SerializedName("product")
	private String product;

	@SerializedName("comments")
	private String comments;

	@SerializedName("po_img")
	private String poImg;

	@SerializedName("total_price")
	private String totalPrice;

	@SerializedName("update_price")
	private String updatePrice;

	@SerializedName("loan_no")
	private String loanNo;

	@SerializedName("invoice_no")
	private String invoiceNo;

	@SerializedName("update_totl_prc")
	private String updateTotlPrc;

	@SerializedName("prodt_price")
	private String prodtPrice;

	@SerializedName("delivery_gen_time")
	private String deliveryGenTime;

	@SerializedName("update_quantity")
	private String updateQuantity;

	@SerializedName("dealer_time")
	private String dealerTime;

	@SerializedName("dealer_name")
	private String dealerName;

	@SerializedName("subdealer_action_time")
	private String subdealerActionTime;

	@SerializedName("subdealer_name")
	private String subdealerName;

	@SerializedName("financer")
	private String financer;

	@SerializedName("status")
	private String status;

	public String getDate(){
		return date;
	}

	public String getModTotlPrice(){
		return modTotlPrice;
	}

	public String getPoId(){
		return poId;
	}

	public String getLoanLimit(){
		return loanLimit;
	}

	public String getRoi(){
		return roi;
	}

	public String getSubdealerId(){
		return subdealerId;
	}

	public String getFinancerActionTime(){
		return financerActionTime;
	}

	public String getReasonOfRejection(){
		return reasonOfRejection;
	}

	public String getFinancerDisbursalTime(){
		return financerDisbursalTime;
	}

	public String getDisvrsalReport(){
		return disvrsalReport;
	}

	public String getAppAmt(){
		return appAmt;
	}

	public int getId(){
		return id;
	}

	public String getBrand(){
		return brand;
	}

	public String getTenure(){
		return tenure;
	}

	public String getInvoiceFile(){
		return invoiceFile;
	}

	public String getDealerId(){
		return dealerId;
	}

	public String getProdtQuantity(){
		return prodtQuantity;
	}

	public String getProduct(){
		return product;
	}

	public String getComments(){
		return comments;
	}

	public String getPoImg(){
		return poImg;
	}

	public String getTotalPrice(){
		return totalPrice;
	}

	public String getUpdatePrice(){
		return updatePrice;
	}

	public String getLoanNo(){
		return loanNo;
	}

	public String getInvoiceNo(){
		return invoiceNo;
	}

	public String getUpdateTotlPrc(){
		return updateTotlPrc;
	}

	public String getProdtPrice(){
		return prodtPrice;
	}

	public String getDeliveryGenTime(){
		return deliveryGenTime;
	}

	public String getUpdateQuantity(){
		return updateQuantity;
	}

	public String getDealerTime(){
		return dealerTime;
	}

	public String getDealerName(){
		return dealerName;
	}

	public String getSubdealerActionTime(){
		return subdealerActionTime;
	}

	public String getSubdealerName(){
		return subdealerName;
	}

	public String getFinancer(){
		return financer;
	}

	public String getStatus(){
		return status;
	}
}