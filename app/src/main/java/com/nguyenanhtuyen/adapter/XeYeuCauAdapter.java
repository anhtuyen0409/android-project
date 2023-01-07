package com.nguyenanhtuyen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.YeuCauDatXe;


public class XeYeuCauAdapter extends ArrayAdapter<YeuCauDatXe> {
    Activity context;
    int resource;
    public XeYeuCauAdapter(@NonNull Activity context, int resource) {
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
        ImageView imgHinhAnhKhachHangYeuCau = customView.findViewById(R.id.imgHinhAnhKhachHangYeuCau);
        TextView txtTenHienThiYeuCau = customView.findViewById(R.id.txtTenHienThi_YeuCau);
        TextView txtViTriXe = customView.findViewById(R.id.txtViTriXe);
        TextView txtDiemNhanXe = customView.findViewById(R.id.txtDiemNhanXe);
        TextView txtTongThanhToan = customView.findViewById(R.id.txtTongThanhToan_YeuCau);

        //lấy xe ở vị trí position
        YeuCauDatXe yeuCauDatXe = getItem(position);
        Glide.with(customView).load("http://192.168.1.50/dacn/assets/images/user/"+yeuCauDatXe.getHinhAnh()).into(imgHinhAnhKhachHangYeuCau);
        txtTenHienThiYeuCau.setText(yeuCauDatXe.getTenHienThi());
        txtViTriXe.setText(yeuCauDatXe.getViTriBatDau());
        txtDiemNhanXe.setText(yeuCauDatXe.getViTriKetThuc());
        txtTongThanhToan.setText(yeuCauDatXe.getTongThanhToan()+"");

        return customView;
    }
}
