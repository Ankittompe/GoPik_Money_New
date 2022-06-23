package com.qts.gopik_loan.Supply_Chain;

public class PO_Product {
    /*{
        "Name" : "Mega T 15",
            "Price" : "100",
            "quantity" : "2"
    },
*/




    private String quantity;


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Name;

    public PO_Product( String Name,String Price, String quantity) {
        this.Name = Name;
        this.Price = Price;
        this.quantity = quantity;
    }


    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    private String  Price;
}
