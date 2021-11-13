package com.example.myapplication.model;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Comparator;
import java.util.Date;

public class DatatestAll_Cook {
    public String tacgia;
    public String name;
    public String mota;
    public String photo;
    public String phototg;
    public int countfav;
    public Date date;
    public int status;
    public int status_ct;
    public int id;
    public DatatestAll_Cook(){

    }

    public DatatestAll_Cook(String tacgia, String name, String mota, String photo, String phototg, int status, int id, int countfav, Date date,int status_ct) {
        this.tacgia=tacgia;
        this.name = name;
        this.mota = mota;
        this.photo = photo;
        this.phototg = phototg;
        this.status = status;
        this.status_ct = status_ct;
        this.countfav = countfav;
        this.id=id;
        this.date=date;
    }

    // getter
    public String getTacgia() {
        return tacgia;
    }

    public String getName() {
        return name;
    }

    public String getMota() {
        return mota;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhototg() {
        return phototg;
    }

    public int getstatus_ct() {
        return status_ct;
    }

    public int getCountfav() {
        return countfav;
    }

    public int getId() {
        return id;
    }

    public Date getDate(){return date;}

    //setter
    public void setTacgia (String tacgia) {
        this.tacgia = tacgia;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPhototg(String phototg) {
        this.phototg = phototg;
    }

    public void setstatus_ct(int status_ct) {
        this.status_ct = status_ct;
    }

    public void setCountfav(int countfav) {
        this.countfav = countfav;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
