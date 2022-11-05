package com.qts.gopik_money.Pojo;

import com.qts.gopik_money.Supply_Chain.PO_Product;

import java.util.ArrayList;

public class Po_add_POJO {


    private String user_code;
    private String user_name;
    private String brand;
    private String date;
    private ArrayList<PO_Product>product;
    private String total_price;


    public Po_add_POJO(String user_code,String   user_name,String brand,String date, ArrayList<PO_Product> mPoproduct, String total_price) {
        this.user_code = user_code;
        this.user_name = user_name;
        this.brand = brand;
        this.date = date;
        this.product = mPoproduct;
        this.total_price = total_price;
    }




}
