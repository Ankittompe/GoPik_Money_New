package com.qts.gopik_money.Pojo;

public class Store_data_for_MLloan_POJO {


    private String user_code;
    private String customercode;
    private String customer_sal;
    private String customer_name;
    private String customer_middl_name;
    private String customer_last_name;
    private String customer_gender;
    private String customer_mobile;
    private String customer_email;
    private String pin;
    private String state;
    private String city;
    private String loan_amount;
    private String loan_type;
    private String loan_category;
    private String loan_subcategory;



    public Store_data_for_MLloan_POJO(String user_code, String customercode, String customer_sal,String customer_name,
                                      String customer_middl_name,
                                      String customer_last_name,
                                      String  customer_gender,
                                      String customer_mobile,String customer_email,
                                      String pin, String state,
                                      String city, String loan_amount, String loan_type,String loan_category,String loan_subcategory) {

        this.user_code = user_code;
        this.customercode = customercode;
        this.customer_sal = customer_sal;
        this.customer_name = customer_name;
        this.customer_middl_name = customer_middl_name;
        this.customer_last_name = customer_last_name;
        this.customer_gender = customer_gender;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
        this.pin = pin;
        this.state = state;
        this.city = city;
        this.loan_amount = loan_amount;
        this.loan_type = loan_type;
        this.loan_category = loan_category;
        this.loan_subcategory = loan_subcategory;




    }
}
