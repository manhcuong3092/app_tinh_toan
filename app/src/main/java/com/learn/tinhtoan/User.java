package com.learn.tinhtoan;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private byte[] image;


    public User(int id, String name, String password, byte[] image) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.image = image;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
