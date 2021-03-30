package com.example.myapplication.Model;

public class categories {

    int id;
    String name;
    int parent;
    image image;


    public categories(int id) {
        this.id = id;

    }

    public com.example.myapplication.Model.image getImage() {
        return image;
    }

    public void setImage(com.example.myapplication.Model.image image) {
        this.image = image;
    }

    public categories(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
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

    @Override
    public String toString() {
        return name;
    }
}