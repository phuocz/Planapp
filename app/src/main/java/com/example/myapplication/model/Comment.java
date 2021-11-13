package com.example.myapplication.model;

public class Comment {
    public  int id;
    public int id_user;
    public int id_noidung;
    public String user_name;
    public String comment;
    public String photo_user;
    public Comment() {
    }

    public Comment(int id,int id_user,int id_noidung,String comment,String photo_user,String user_name) {
        this.id = id;
        this.id_noidung = id_noidung;
        this.id_user = id_user;
        this.comment = comment;
        this.photo_user = photo_user;
        this.user_name = user_name;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_noidung() {
        return id_noidung;
    }
    public void setId_noidung(int id_noidung) {
        this.id_noidung = id_noidung;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto_user() {
        return photo_user;
    }
    public void setPhoto_user(String photo_user) {
        this.photo_user = photo_user;
    }

    public String getUse_name() {
        return user_name;
    }
    public void setUse_name(String user_name) {
        this.user_name = user_name;
    }



}

