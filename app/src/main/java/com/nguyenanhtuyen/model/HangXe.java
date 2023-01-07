package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class HangXe implements Serializable {
    private int maHangXe;
    private String tenHangXe;

    public HangXe() {
    }

    public HangXe(int maHangXe, String tenHangXe) {
        this.maHangXe = maHangXe;
        this.tenHangXe = tenHangXe;
    }

    public void setMaHangXe(int maHangXe) {
        this.maHangXe = maHangXe;
    }

    public void setTenHangXe(String tenHangXe) {
        this.tenHangXe = tenHangXe;
    }

    public int getMaHangXe() {
        return maHangXe;
    }

    public String getTenHangXe() {
        return tenHangXe;
    }

    @Override
    public String toString() {
        return this.tenHangXe;
    }
}
