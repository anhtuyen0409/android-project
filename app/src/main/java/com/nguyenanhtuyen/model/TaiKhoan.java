package com.nguyenanhtuyen.model;

import java.io.Serializable;
import java.util.Date;

public class TaiKhoan implements Serializable {
    private String hoTen;
    private String ngaySinh;
    private String sdt;
    private String tenHienThi;
    private String email;
    private String matKhau;
    private String xacNhanMatKhau;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenHienThi, String email, String matKhau, String xacNhanMatKhau) {
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
    }

    public TaiKhoan(String hoTen, String ngaySinh, String sdt, String tenHienThi, String email, String matKhau, String xacNhanMatKhau) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHoTen() {
        return hoTen;
    }


    public String getSdt() {
        return sdt;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setXacNhanMatKhau(String xacNhanMatKhau) {
        this.xacNhanMatKhau = xacNhanMatKhau;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public String getEmail() {
        return email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getXacNhanMatKhau() {
        return xacNhanMatKhau;
    }
}
