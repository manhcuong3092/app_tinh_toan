package com.learn.tinhtoan.model;

public class Achievement {

    public static final int NO_TROPHY = 0;
    public static final int BRONZE_TROPHY = 1;
    public static final int SILVER_TROPHY = 2;
    public static final int GOLD_TROPHY = 3;

    public static final int lv1Tolv2 = 100;
    public static final int lv2Tolv3 = 200;
    public static final int lv3Tolv4 = 300;
    public static final int lv4Tolv5 = 400;

    public static final String TITLE_LV0 = "Gà";
    public static final String TITLE_LV1 = "Tập sự";
    public static final String TITLE_LV2 = "Nghiệp dư";
    public static final String TITLE_LV3 = "Chuyên nghiệp";
    public static final String TITLE_LV4 = "Cao thủ";
    public static final String TITLE_LV5 = "VIP Pro";

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
