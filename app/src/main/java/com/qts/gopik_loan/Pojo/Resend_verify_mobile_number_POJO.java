package com.qts.gopik_loan.Pojo;

public class Resend_verify_mobile_number_POJO {
    private String user_code;
    private String customer_code;

    private String mobile_number;

    private String user_otp;

    public Resend_verify_mobile_number_POJO(String user_code,String customer_code,String mobile_number
            ,String user_otp) {

        this.user_code = user_code;
        this.customer_code = customer_code;
        this.mobile_number = mobile_number;
        this.user_otp = user_otp;




    }
}
