package com.nguyenanhtuyen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.DanhGia;


public class DanhGiaAdapter extends ArrayAdapter<DanhGia> {
    Activity context;
    int resource;
    public DanhGiaAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource,null);

        //khai báo các control
        ImageView imgHinhAnhKhachHangDanhGia = customView.findViewById(R.id.imgHinhAnhKhachHangDanhGia);
        TextView txtTenHienThiDanhGia = customView.findViewById(R.id.txtTenHienThi_DanhGia);
        RatingBar ratingBarDiemDanhGia = customView.findViewById(R.id.ratingDiemDanhGia);
        TextView txtNoiDungDanhGia = customView.findViewById(R.id.txtNoiDung_DanhGia);

        //lấy danh gia ở vị trí position
        DanhGia danhGia = getItem(position);
        Glide.with(customView).load("http://192.168.1.50/dacn/assets/images/user/"+danhGia.getHinhAnh()).into(imgHinhAnhKhachHangDanhGia);
        txtTenHienThiDanhGia.setText(danhGia.getTenHienThi());
        ratingBarDiemDanhGia.setRating((float) danhGia.getDiem());
        txtNoiDungDanhGia.setText(danhGia.getNoiDung());

        return customView;
    }
}
