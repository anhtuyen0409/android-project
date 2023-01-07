package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class HinhThucThanhToan implements Serializable {
    private int maHinhThuc;
    private String tenHinhThuc;

    public HinhThucThanhToan() {
    }

    public HinhThucThanhToan(int maHinhThuc, String tenHinhThuc) {
        this.maHinhThuc = maHinhThuc;
        this.tenHinhThuc = tenHinhThuc;
    }

    public int getMaHinhThuc() {
        return maHinhThuc;
    }

    public void setMaHinhThuc(int maHinhThuc) {
        this.maHinhThuc = maHinhThuc;
    }

    public String getTenHinhThuc() {
        return tenHinhThuc;
    }

    public void setTenHinhThuc(String tenHinhThuc) {
        this.tenHinhThuc = tenHinhThuc;
    }

    @Override
    public String toString() {
        return this.tenHinhThuc;
    }
}
