package com.learn.tinhtoan.model;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private int userId;
    private String fullname;
    private String dateOfBirth;
    private String email;
    private int gender;
    private String phone;
    private String address;

    public UserProfile(int userId) {
        this.userId = userId;
        this.fullname = "";
        this.dateOfBirth = "";
        this.gender = 0;
        this.phone = "";
        this.address = "";
        this.email = "";
    }

    public UserProfile(int userId, String fullname, String dateOfBirth, String email, int gender, String phone, String address) {
        this.userId = userId;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
