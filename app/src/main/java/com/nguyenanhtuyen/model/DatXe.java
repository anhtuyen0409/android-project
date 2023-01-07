package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class DatXe implements Serializable {
    private int maDatXe;
    private int maThanhVien;
    private String ngayDat;
    private String ngayKetThuc;
    private String viTriXe;
    private String viTriNhanXe;

    public DatXe() {
    }

    public DatXe(int maDatXe, int maThanhVien, String ngayDat, String ngayKetThuc, String viTriXe, String viTriNhanXe) {
        this.maDatXe = maDatXe;
        this.maThanhVien = maThanhVien;
        this.ngayDat = ngayDat;
        this.ngayKetThuc = ngayKetThuc;
        this.viTriXe = viTriXe;
        this.viTriNhanXe = viTriNhanXe;
    }

    public int getMaDatXe() {
        return maDatXe;
    }

    public void setMaDatXe(int maDatXe) {
        this.maDatXe = maDatXe;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
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

    public String getViTriXe() {
        return viTriXe;
    }

    public void setViTriXe(String viTriXe) {
        this.viTriXe = viTriXe;
    }

    public String getViTriNhanXe() {
        return viTriNhanXe;
    }

    public void setViTriNhanXe(String viTriNhanXe) {
        this.viTriNhanXe = viTriNhanXe;
    }
}
