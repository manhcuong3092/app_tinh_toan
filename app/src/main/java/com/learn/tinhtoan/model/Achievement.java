package com.learn.tinhtoan.model;

public class Achievement {

    public static final int NO_TROPHY = 0;
    public static final int BRONZE_TROPHY = 1;
    public static final int SILVER_TROPHY = 2;
    public static final int GOLD_TROPHY = 3;

    private String title;
    private String current;
    private int trophy;

    public Achievement(String title, String current, int trophy) {
        this.title = title;
        this.current = current;
        this.trophy = trophy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public int getTrophy() {
        return trophy;
    }

    public void setTrophy(int trophy) {
        this.trophy = trophy;
    }
}
