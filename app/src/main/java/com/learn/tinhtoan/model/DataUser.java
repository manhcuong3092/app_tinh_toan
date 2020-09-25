package com.learn.tinhtoan.model;

import java.io.Serializable;

public class DataUser implements Serializable {
    private int UserId;
    private int diem;
    private int soCauTraLoi;
    private int soCauDung;
    private int soCauCong;
    private int soCauTru;
    private int soCauNhan;
    private int soCauChia;
    private int soCauDe;
    private int soCauTB;
    private int soCauKho;
    private int soLanTinhToan;
    private int soLanMiniGame;

    public DataUser(int UserId) {
        this.UserId = UserId;
        this.diem = 0;
        this.soCauTraLoi = 0;
        this.soCauDung = 0;
        this.soCauCong = 0;
        this.soCauTru = 0;
        this.soCauNhan = 0;
        this.soCauChia = 0;
        this.soCauDe = 0;
        this.soCauTB = 0;
        this.soCauKho = 0;
        this.soLanTinhToan = 0;
        this.soLanMiniGame = 0;
    }

    public DataUser(int UserId, int diem, int soCauTraLoi, int soCauDung, int soCauCong, int soCauTru, int soCauNhan,
                    int soCauChia, int soCauDe, int soCauTB, int soCauKho, int soLanTinhToan, int soLanMiniGame) {
        this.UserId = UserId;
        this.diem = diem;
        this.soCauTraLoi = soCauTraLoi;
        this.soCauDung = soCauDung;
        this.soCauCong = soCauCong;
        this.soCauTru = soCauTru;
        this.soCauNhan = soCauNhan;
        this.soCauChia = soCauChia;
        this.soCauDe = soCauDe;
        this.soCauTB = soCauTB;
        this.soCauKho = soCauKho;
        this.soLanTinhToan = soLanTinhToan;
        this.soLanMiniGame = soLanMiniGame;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getSoCauDe() {
        return soCauDe;
    }

    public void setSoCauDe(int soCauDe) {
        this.soCauDe = soCauDe;
    }

    public int getSoCauTB() {
        return soCauTB;
    }

    public void setSoCauTB(int soCauTB) {
        this.soCauTB = soCauTB;
    }

    public int getSoCauKho() {
        return soCauKho;
    }

    public void setSoCauKho(int soCauKho) {
        this.soCauKho = soCauKho;
    }

    public int getSoLanTinhToan() {
        return soLanTinhToan;
    }

    public void setSoLanTinhToan(int soLanTinhToan) {
        this.soLanTinhToan = soLanTinhToan;
    }

    public int getSoLanMiniGame() {
        return soLanMiniGame;
    }

    public void setSoLanMiniGame(int soLanMiniGame) {
        this.soLanMiniGame = soLanMiniGame;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public int getSoCauTraLoi() {
        return soCauTraLoi;
    }

    public void setSoCauTraLoi(int soCauTraLoi) {
        this.soCauTraLoi = soCauTraLoi;
    }

    public int getSoCauDung() {
        return soCauDung;
    }

    public void setSoCauDung(int soCauDung) {
        this.soCauDung = soCauDung;
    }

    public int getSoCauCong() {
        return soCauCong;
    }

    public void setSoCauCong(int soCauCong) {
        this.soCauCong = soCauCong;
    }

    public int getSoCauTru() {
        return soCauTru;
    }

    public void setSoCauTru(int soCauTru) {
        this.soCauTru = soCauTru;
    }

    public int getSoCauNhan() {
        return soCauNhan;
    }

    public void setSoCauNhan(int soCauNhan) {
        this.soCauNhan = soCauNhan;
    }

    public int getSoCauChia() {
        return soCauChia;
    }

    public void setSoCauChia(int soCauChia) {
        this.soCauChia = soCauChia;
    }
}
