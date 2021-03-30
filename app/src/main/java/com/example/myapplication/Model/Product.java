package com.example.myapplication.Model;

import java.util.List;

public class Product {


    int id;
    String name;
    String price;
    String description;
    String regular_price;
    String status;
    List<images> images;
    List<categories> categories;


    public Product(int id, String name, String description, String price, String regular_price, String status, List<com.example.myapplication.Model.images> images, List<com.example.myapplication.Model.categories> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.regular_price = regular_price;
        this.status = status;
        this.images = images;
        this.categories = categories;
    }

    public Product(String name, String description,  String regular_price) {
        this.name = name;
        this.description = description;
        this.regular_price = regular_price;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<com.example.myapplication.Model.images> getImages() {
        return images;
    }

    public void setImages(List<com.example.myapplication.Model.images> images) {
        this.images = images;
    }

    public List<com.example.myapplication.Model.categories> getCategories() {
        return categories;
    }

    public void setCategories(List<com.example.myapplication.Model.categories> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}