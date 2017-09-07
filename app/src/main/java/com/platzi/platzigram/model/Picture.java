package com.platzi.platzigram.model;

/**
 * Created by Julian on 8/31/17.
 */

public class Picture {
    //vamos a recibir una url desde internet para mostrarla...aqui se genera el model u Objeto POJO que el adapter recibirá en el Array
    private String picture;
    private String userName;
    private String time;
    private String like_number = "0";

    public Picture(String picture, String userName, String time, String like_number) {
        this.picture = picture;
        this.userName = userName;
        this.time = time;
        this.like_number = like_number;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLike_number() {
        return like_number;
    }

    public void setLike_number(String like_number) {
        this.like_number = like_number;
    }
}
