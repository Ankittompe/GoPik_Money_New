package com.qts.gopik_money.Pojo;

public class OEMDetailsPOJO {

    String user_code;
    String year_in_business;
    String year_in_business_with_oem;
    String revenue_percentage_with_oem;

    public OEMDetailsPOJO(String user_code, String year_in_business, String year_in_business_with_oem, String revenue_percentage_with_oem) {
        this.user_code = user_code;
        this.year_in_business = year_in_business;
        this.year_in_business_with_oem = year_in_business_with_oem;
        this.revenue_percentage_with_oem = revenue_percentage_with_oem;
    }
}
