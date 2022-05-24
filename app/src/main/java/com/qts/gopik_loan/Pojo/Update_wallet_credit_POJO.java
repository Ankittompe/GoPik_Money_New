package com.qts.gopik_loan.Pojo;

public class Update_wallet_credit_POJO {
    private String user_code;
    private String wallet_type;
    private String txn_amount;
    private String status;
    private String razorpayment_id;



    public Update_wallet_credit_POJO(String user_code, String wallet_type,String txn_amount, String status,String razorpayment_id ) {

        this.user_code = user_code;
        this.wallet_type = wallet_type;
        this.txn_amount = txn_amount;
        this.status = status;
        this.razorpayment_id = razorpayment_id;

    }
}
