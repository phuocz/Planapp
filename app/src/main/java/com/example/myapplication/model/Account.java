package com.example.myapplication.model;

public class Account {
    public  int id;
    public String name;
    public String email;
    public String pass;
    public String photo;
    public  int status_bl;
    public  int status_post;
    public Account() {
    }

    public Account(int id,String name, String email, String pass,String photo,int status_bl,int status_post) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.photo = photo;
        this.status_bl = status_bl;
        this.status_post = status_post;
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

    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }

    public String getpass() {
        return pass;
    }
    public void setpass(String pass) {
        this.pass = pass;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getstatus_post() {
        return status_post;
    }
    public void setstatus_post(int status_post) {
        this.status_post = status_post;
    }

    public int getstatus_bl() {
        return status_bl;
    }
    public void setstatus_bl(int status_bl) {
        this.status_bl = status_bl;
    }



}

