package com.qts.gopik_money.Pojo;

public class Shopkeeper_profile_update_POJO {
    private String user_code;
    private String name;
    private String state;
    private String email;
    private String shop_name;
    private String shop_address;







    public Shopkeeper_profile_update_POJO(String user_code,String name,String state,
                                      String email, String shop_name
    , String shop_address) {

        this.user_code = user_code;
        this.name = name;

        this.state = state;
        this.email = email;
        this.shop_name = shop_name;
        this.shop_address = shop_address;



    }
}
