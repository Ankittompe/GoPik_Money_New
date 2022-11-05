package com.qts.gopik_money.Pojo;

public class Loan_calculation_POJO {
    private String brand;
    private String product_id;
    private String product_model;
    private String product_price;
    private String tenure;
    private String subsidy;
    private String down_payment;



    public Loan_calculation_POJO(String brand ,String product_id ,String product_model,String product_price ,String tenure,String subsidy,String down_payment ) {
        this.brand = brand;
        this.product_id = product_id;
        this.product_model = product_model;
        this.product_price = product_price;
        this.tenure = tenure;
        this.subsidy = subsidy;
        this.down_payment = down_payment;


    }

}
