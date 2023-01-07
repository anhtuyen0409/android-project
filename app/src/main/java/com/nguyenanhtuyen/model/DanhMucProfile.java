package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class DanhMucProfile implements Serializable {
    private int hinhAnh;
    private String tieuDe;
    private String noiDung;


    public DanhMucProfile() {
    }

    public DanhMucProfile(int hinhAnh, String tieuDe){
        this.hinhAnh = hinhAnh;
        this.tieuDe = tieuDe;
    }

    public DanhMucProfile(int hinhAnh, String tieuDe, String noiDung) {
        this.hinhAnh = hinhAnh;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;

    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }



    public int getHinhAnh() {
        return hinhAnh;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }


}
