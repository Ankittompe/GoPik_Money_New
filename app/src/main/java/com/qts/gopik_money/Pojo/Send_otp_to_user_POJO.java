package com.qts.gopik_money.Pojo;

public class Send_otp_to_user_POJO {
    private String user_code;
    private String customer_code;
    private String mobile_number;




    public Send_otp_to_user_POJO(String user_code, String customer_code,String mobile_number ) {

        this.user_code = user_code;
        this.customer_code = customer_code;
        this.mobile_number = mobile_number;

    }

}
