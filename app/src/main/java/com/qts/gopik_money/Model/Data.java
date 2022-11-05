package com.qts.gopik_money.Model;

import com.google.gson.annotations.SerializedName;

public class Data{
	@SerializedName("total_submission")
	private int totalSubmission;
	@SerializedName("total_approval")
	private int totalApproval;
	@SerializedName("total_pending")
	private int totalPending;
	@SerializedName("total_scan")
	private int totalScan;

	public int getTotalSubmission(){
		return totalSubmission;
	}

	public int getTotalApproval(){
		return totalApproval;
	}

	public int getTotalPending(){
		return totalPending;
	}

	public int getTotalScan(){
		return totalScan;
	}
}
