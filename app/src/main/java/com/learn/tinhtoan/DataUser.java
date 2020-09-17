package com.learn.tinhtoan;

public class DataUser {
    private int diem;
    private int soCauTraLoi;
    private int soCauDung;
    private int soCauCong;
    private int soCauTru;
    private int soCauNhan;
    private int soCauChia;
    private int soLanTinhToan;
    private int soLanMiniGame;

    public DataUser(int diem, int soCauTraLoi, int soCauDung, int soCauCong, int soCauTru, int soCauNhan, int soCauChia,
                    int soLanTinhToan, int soLanMiniGame) {
        this.diem = diem;
        this.soCauTraLoi = soCauTraLoi;
        this.soCauDung = soCauDung;
        this.soCauCong = soCauCong;
        this.soCauTru = soCauTru;
        this.soCauNhan = soCauNhan;
        this.soCauChia = soCauChia;
        this.soLanTinhToan = soLanTinhToan;
        this.soLanMiniGame = soLanMiniGame;
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

    public DataUser() {
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
