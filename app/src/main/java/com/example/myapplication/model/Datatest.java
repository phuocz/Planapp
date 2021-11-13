package com.example.myapplication.model;

public class Datatest {
    public String name;
    public String mota;
    public int id;
    public int photo;

    public Datatest(){

    }

    public Datatest(String name, String mota, int photo,int id) {
        this.name = name;
        this.mota = mota;
        this.photo = photo;
        this.id=id;
    }

    // getter


    public String getName() {
        return name;
    }

    public String getMota() {
        return mota;
    }

    public int getPhoto() {
        return photo;
    }
    public int id() {
        return id;
    }


    //setter

    public void setName (String name) {
        this.name = name;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
    public void id(int id) {
        this.id = id;
    }
}
