package com.example.myapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class Product2 {

    String name;
    String regular_price;
    List<images> images;
    ArrayList<brands> brands;


    public Product2(String name, String regular_price, List<com.example.myapplication.Model.images> images, ArrayList<com.example.myapplication.Model.brands> brands) {
        this.name = name;
        this.regular_price = regular_price;
        this.images = images;
        this.brands = brands;
    }

    public ArrayList<com.example.myapplication.Model.brands> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<com.example.myapplication.Model.brands> brands) {
        this.brands = brands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public List<com.example.myapplication.Model.images> getImages() {
        return images;
    }

    public void setImages(List<com.example.myapplication.Model.images> images) {
        this.images = images;
    }


}
