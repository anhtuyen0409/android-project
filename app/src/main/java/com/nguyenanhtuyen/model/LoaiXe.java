package com.nguyenanhtuyen.model;

import java.io.Serializable;

public class LoaiXe implements Serializable {
    private int maLoaiXe;
    private String tenLoaiXe;
    private int Status;

    public LoaiXe() {
    }

    public LoaiXe(int maLoaiXe, String tenLoaiXe) {
        this.maLoaiXe = maLoaiXe;
        this.tenLoaiXe = tenLoaiXe;
    }

    public LoaiXe(int maLoaiXe, String tenLoaiXe, int status) {
        this.maLoaiXe = maLoaiXe;
        this.tenLoaiXe = tenLoaiXe;
        Status = status;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setMaLoaiXe(int maLoaiXe) {
        this.maLoaiXe = maLoaiXe;
    }

    public void setTenLoaiXe(String tenLoaiXe) {
        this.tenLoaiXe = tenLoaiXe;
    }

    public int getMaLoaiXe() {
        return maLoaiXe;
    }

    public String getTenLoaiXe() {
        return tenLoaiXe;
    }

    @Override
    public String toString() {
        return this.tenLoaiXe;
    }
}
