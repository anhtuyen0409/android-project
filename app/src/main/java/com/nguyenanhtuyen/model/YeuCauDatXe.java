package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class YeuCauDatXe implements Serializable {
    private int maThanhVien;
    private String tenHienThi;
    private String hinhAnh;
    private String viTriBatDau;
    private String viTriKetThuc;
    private int tongThanhToan;
    private int maDatXe;

    public YeuCauDatXe() {
    }

    public YeuCauDatXe(int maThanhVien, String tenHienThi, String hinhAnh, String viTriBatDau, String viTriKetThuc, int tongThanhToan) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.hinhAnh = hinhAnh;
        this.viTriBatDau = viTriBatDau;
        this.viTriKetThuc = viTriKetThuc;
        this.tongThanhToan = tongThanhToan;
    }

    public YeuCauDatXe(int maThanhVien, String tenHienThi, String hinhAnh, String viTriBatDau, String viTriKetThuc, int tongThanhToan, int maDatXe) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.hinhAnh = hinhAnh;
        this.viTriBatDau = viTriBatDau;
        this.viTriKetThuc = viTriKetThuc;
        this.tongThanhToan = tongThanhToan;
        this.maDatXe = maDatXe;
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

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getViTriBatDau() {
        return viTriBatDau;
    }

    public void setViTriBatDau(String viTriBatDau) {
        this.viTriBatDau = viTriBatDau;
    }

    public String getViTriKetThuc() {
        return viTriKetThuc;
    }

    public void setViTriKetThuc(String viTriKetThuc) {
        this.viTriKetThuc = viTriKetThuc;
    }

    public int getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(int tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }
}
