package com.thai.qlkh;

public class KhachHangModel {
    private int id;
    String HT,NS,DiaChi,SDT,avatar;

    public KhachHangModel(int id, String HT, String NS, String diaChi, String SDT, String avatar) {
        this.id = id;
        this.HT = HT;
        this.NS = NS;
        DiaChi = diaChi;
        this.SDT = SDT;
        this.avatar = avatar;
    }

    public KhachHangModel(String HT, String NS, String diaChi, String SDT, String avatar) {
        this.HT = HT;
        this.NS = NS;
        DiaChi = diaChi;
        this.SDT = SDT;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHT() {
        return HT;
    }

    public void setHT(String HT) {
        this.HT = HT;
    }

    public String getNS() {
        return NS;
    }

    public void setNS(String NS) {
        this.NS = NS;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
