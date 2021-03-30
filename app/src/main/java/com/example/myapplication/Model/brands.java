package com.example.myapplication.Model;

public class brands
{

    int id ;
    String name;


    public brands(int id) {
        this.id = id;
    }

    public brands(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
