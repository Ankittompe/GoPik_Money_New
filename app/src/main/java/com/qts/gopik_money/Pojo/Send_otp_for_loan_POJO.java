package com.qts.gopik_money.Pojo;

public class Send_otp_for_loan_POJO {
    private String user_code;
    private String customer_name;
    private String customer_mobile;
    private String customer_email;
    private String customer_address;
    private String customer_dob;
    private String loan_type;
    private String state;



    public Send_otp_for_loan_POJO(String user_code, String customer_name,String customer_mobile,
                                 String customer_email,
                                 String customer_address,String customer_dob,String loan_type,String state ) {

        this.user_code = user_code;
        this.customer_name = customer_name;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
        this.customer_address = customer_address;
        this.customer_dob = customer_dob;
        this.loan_type = loan_type;
        this.state = state;

    }
}
