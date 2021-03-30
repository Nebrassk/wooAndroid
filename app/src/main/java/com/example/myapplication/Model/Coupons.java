package com.example.myapplication.Model;

public class Coupons {

  
    String code;
    String amount;


    public Coupons( String code, String amount) {
        this.code = code;
        this.amount = amount;
    }





    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
