package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class ChuyenXeDangDienRa implements Serializable {
    private int maDatXe;
    private String ngayDat;
    private String ngayKetThuc;
    private String tenHinhThuc;
    private int tongThanhToan;

    public ChuyenXeDangDienRa() {
    }

    public ChuyenXeDangDienRa(int maDatXe, String ngayDat, String ngayKetThuc, String tenHinhThuc, int tongThanhToan) {
        this.maDatXe = maDatXe;
        this.ngayDat = ngayDat;
        this.ngayKetThuc = ngayKetThuc;
        this.tenHinhThuc = tenHinhThuc;
        this.tongThanhToan = tongThanhToan;
    }

    public int getMaDatXe() {
        return maDatXe;
    }

    public void setMaDatXe(int maDatXe) {
        this.maDatXe = maDatXe;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTenHinhThuc() {
        return tenHinhThuc;
    }

    public void setTenHinhThuc(String tenHinhThuc) {
        this.tenHinhThuc = tenHinhThuc;
    }

    public int getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(int tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }
}
