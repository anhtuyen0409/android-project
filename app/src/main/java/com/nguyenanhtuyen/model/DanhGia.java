package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    private String hinhAnh;
    private String tenHienThi;
    private double diem;
    private String noiDung;

    public DanhGia() {
    }

    public DanhGia(String hinhAnh, String tenHienThi, double diem, String noiDung) {
        this.hinhAnh = hinhAnh;
        this.tenHienThi = tenHienThi;
        this.diem = diem;
        this.noiDung = noiDung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
