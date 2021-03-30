package com.example.myapplication.Model;

public class brand {




    int id;
    String name;

    public brand(String name , int id) {
        this.id = id;
        this.name = name;
    }

    public brand(int id) {
        this.id = id;
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
}
