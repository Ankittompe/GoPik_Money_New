package com.qts.gopik_loan.Pojo;

public class Broker_profile_update_POJO {
    private String name;
    private String user_code;
    private String email;
    private String mobile;
    private String bank_name;
    private String acc_no;
    private String ifsc;
    private String branch;
    private String state;
    private String address;




    public Broker_profile_update_POJO(String name,String user_code,
                                      String email,String mobile,String bank_name
            ,String acc_no,String ifsc,String branch,String state,String address) {
        this.name = name;
        this.user_code = user_code;
        this.email = email;
        this.mobile = mobile;
        this.bank_name = bank_name;
        this.acc_no = acc_no;
        this.ifsc = ifsc;
        this.branch = branch;
        this.state = state;
        this.address = address;


    }
}
