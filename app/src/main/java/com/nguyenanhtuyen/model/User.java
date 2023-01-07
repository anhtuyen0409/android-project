package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class User implements Serializable {
    private int maThanhVien;
    private String tenHienThi;
    private String email;
    private String matKhau;
    private String xacNhanMatKhau;
    private String hoTen;
    private String ngaySinh;
    private String sdt;
    private String HinhAnh;
    private int maLoaiThanhVien;
    private int status;

    public User() {
    }

    public User(int maThanhVien, String tenHienThi, String email, String matKhau, String xacNhanMatKhau) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
    }

    public User(int maThanhVien, String tenHienThi, String email, String matKhau, String xacNhanMatKhau, String hoTen, String ngaySinh, String sdt, int maLoaiThanhVien) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.maLoaiThanhVien = maLoaiThanhVien;
    }

    public User(int maThanhVien, String tenHienThi, String email, String matKhau, String xacNhanMatKhau, int maLoaiThanhVien) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
        this.maLoaiThanhVien = maLoaiThanhVien;
    }

    public User(int maThanhVien, String tenHienThi, String email, String matKhau, String xacNhanMatKhau, String hoTen, String ngaySinh, String sdt, String hinhAnh, int maLoaiThanhVien) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        HinhAnh = hinhAnh;
        this.maLoaiThanhVien = maLoaiThanhVien;
    }

    public User(int maThanhVien, String tenHienThi, String email, String matKhau, String xacNhanMatKhau, String hoTen, String ngaySinh, String sdt, String hinhAnh, int maLoaiThanhVien, int status) {
        this.maThanhVien = maThanhVien;
        this.tenHienThi = tenHienThi;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhanMatKhau = xacNhanMatKhau;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        HinhAnh = hinhAnh;
        this.maLoaiThanhVien = maLoaiThanhVien;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getHoTen() {
        return hoTen;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getMaLoaiThanhVien() {
        return maLoaiThanhVien;
    }

    public void setMaLoaiThanhVien(int maLoaiThanhVien) {
        this.maLoaiThanhVien = maLoaiThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
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

    public int getMaThanhVien() {
        return maThanhVien;
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
