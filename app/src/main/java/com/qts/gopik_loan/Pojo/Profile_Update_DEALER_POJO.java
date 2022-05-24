package com.qts.gopik_loan.Pojo;

public class Profile_Update_DEALER_POJO {
    private String name;
    private String user_code;
    private String email;
    private String bank_name;
    private String acc_no;
    private String ifsc;
    private String branch;
    private String gst_no;



    public Profile_Update_DEALER_POJO(String name, String user_code,String email, String bank_name,
                             String acc_no,
                             String ifsc, String branch,String gst_no) {

        this.name = name;
        this.user_code = user_code;
        this.email = email;
        this.bank_name = bank_name;
        this.acc_no = acc_no;
        this.ifsc = ifsc;
        this.branch = branch;
        this.gst_no = gst_no;


    }
}
