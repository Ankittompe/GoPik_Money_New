package com.qts.gopik_money.Pojo;

public class Send_MLData_to_ICICI_POJO {
    private String user_code;
    private String customercode;
    private String customer_name;
    private String customer_middl_name;
    private String customer_last_name;
    private String customer_mobile;
    private String loan_amount;


    public Send_MLData_to_ICICI_POJO(String user_code,String customercode,
                                     String customer_name,String customer_middl_name,
                                     String customer_last_name,String customer_mobile,
                                     String loan_amount) {

        this.user_code = user_code;
        this.customercode = customercode;
        this.customer_name = customer_name;
        this.customer_middl_name = customer_middl_name;
        this.customer_last_name = customer_last_name;
        this.customer_mobile = customer_mobile;
        this.loan_amount = loan_amount;





    }
}
