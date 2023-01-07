package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class XeTuLai implements Serializable {
    private int maXe;
    private String tenXe;
    private int giaThue;
    private String hinhAnh;
    private String moTa;
    private int maNhaSanXuat;
    private int maLoaiXe;
    private int maThanhVien;
    private int status;
    private String diaChi;
    private double kinhDo;
    private double viDo;

    public XeTuLai() {
    }

    public XeTuLai(String tenXe, int giaThue, String hinhAnh) {
        this.tenXe = tenXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
    }

    public XeTuLai(int maXe, String tenXe, int giaThue, String hinhAnh) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
    }

    public XeTuLai(int maXe, String tenXe, int giaThue, String hinhAnh, int maThanhVien) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
        this.maThanhVien = maThanhVien;
    }

    public XeTuLai(int maXe, String tenXe, int giaThue, String hinhAnh, int maThanhVien, int status) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
        this.maThanhVien = maThanhVien;
        this.status = status;
    }

    public XeTuLai(int maXe, String tenXe, int giaThue, String hinhAnh, int maThanhVien, int status, double kinhDo, double viDo) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
        this.maThanhVien = maThanhVien;
        this.status = status;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
    }

    public XeTuLai(int maXe, String tenXe, int giaThue, String hinhAnh, String moTa, int maNhaSanXuat, int maLoaiXe, int maThanhVien, int status, String diaChi, double kinhDo, double viDo) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.giaThue = giaThue;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.maNhaSanXuat = maNhaSanXuat;
        this.maLoaiXe = maLoaiXe;
        this.maThanhVien = maThanhVien;
        this.status = status;
        this.diaChi = diaChi;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaNhaSanXuat() {
        return maNhaSanXuat;
    }

    public void setMaNhaSanXuat(int maNhaSanXuat) {
        this.maNhaSanXuat = maNhaSanXuat;
    }

    public int getMaLoaiXe() {
        return maLoaiXe;
    }

    public void setMaLoaiXe(int maLoaiXe) {
        this.maLoaiXe = maLoaiXe;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public double getViDo() {
        return viDo;
    }

    public void setViDo(double viDo) {
        this.viDo = viDo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public int getMaXe() {
        return maXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenXe() {
        return tenXe;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }
}
