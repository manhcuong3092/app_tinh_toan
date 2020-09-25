package com.learn.tinhtoan.model;

import java.io.Serializable;

public class UserAchievement implements Serializable {
    private int userId;
    private int level;
    private String title;
    private int dungHet;
    private int saiHet;
    private int amDiem;
    private int clearHard;
    private int clearOperators;
    private int under15Seconds;
    private int clearAdd;
    private int clearSub;
    private int clearMul;
    private int clearDiv;
    private int clearMinigames;

    public UserAchievement(int userId, int level, String title, int dungHet, int saiHet, int amDiem, int clearHard, int clearOperators,
                           int under15Seconds, int clearAdd, int clearSub, int clearMul, int clearDiv, int clearMinigames) {
        this.userId = userId;
        this.level = level;
        this.title = title;
        this.dungHet = dungHet;
        this.saiHet = saiHet;
        this.amDiem = amDiem;
        this.clearHard = clearHard;
        this.clearOperators = clearOperators;
        this.under15Seconds = under15Seconds;
        this.clearAdd = clearAdd;
        this.clearSub = clearSub;
        this.clearMul = clearMul;
        this.clearDiv = clearDiv;
        this.clearMinigames = clearMinigames;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDungHet() {
        return dungHet;
    }

    public void setDungHet(int dungHet) {
        this.dungHet = dungHet;
    }

    public int getSaiHet() {
        return saiHet;
    }

    public void setSaiHet(int saiHet) {
        this.saiHet = saiHet;
    }

    public int getAmDiem() {
        return amDiem;
    }

    public void setAmDiem(int amDiem) {
        this.amDiem = amDiem;
    }

    public int getClearHard() {
        return clearHard;
    }

    public void setClearHard(int clearHard) {
        this.clearHard = clearHard;
    }

    public int getClearOperators() {
        return clearOperators;
    }

    public void setClearOperators(int clearOperators) {
        this.clearOperators = clearOperators;
    }

    public int getUnder15Seconds() {
        return under15Seconds;
    }

    public void setUnder15Seconds(int under15Seconds) {
        this.under15Seconds = under15Seconds;
    }

    public int getClearAdd() {
        return clearAdd;
    }

    public void setClearAdd(int clearAdd) {
        this.clearAdd = clearAdd;
    }

    public int getClearSub() {
        return clearSub;
    }

    public void setClearSub(int clearSub) {
        this.clearSub = clearSub;
    }

    public int getClearMul() {
        return clearMul;
    }

    public void setClearMul(int clearMul) {
        this.clearMul = clearMul;
    }

    public int getClearDiv() {
        return clearDiv;
    }

    public void setClearDiv(int clearDiv) {
        this.clearDiv = clearDiv;
    }

    public int getClearMinigames() {
        return clearMinigames;
    }

    public void setClearMinigames(int clearMinigames) {
        this.clearMinigames = clearMinigames;
    }
}
