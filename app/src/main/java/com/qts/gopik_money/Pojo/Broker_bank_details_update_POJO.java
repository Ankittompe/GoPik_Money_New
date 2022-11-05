package com.qts.gopik_money.Pojo;

public class Broker_bank_details_update_POJO {
    private String user_code;
    private String acc_holdr_nm;
    private String acc_no;
    private String ifsc;
    private String branch;


    public Broker_bank_details_update_POJO(String user_code,String acc_holdr_nm,
                                            String acc_no,String ifsc
            ,String branch) {

        this.user_code = user_code;
        this.acc_holdr_nm = acc_holdr_nm;
        this.acc_no = acc_no;
        this.ifsc = ifsc;
        this.branch = branch;




    }
}
