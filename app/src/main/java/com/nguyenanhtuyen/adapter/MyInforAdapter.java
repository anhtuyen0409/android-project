package com.nguyenanhtuyen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.XeTuLai;

import java.text.DecimalFormat;

public class MyInforAdapter implements GoogleMap.InfoWindowAdapter {
    Activity context;
    XeTuLai xeTuLai;
    public MyInforAdapter(Activity context, XeTuLai xeTuLai){
        this.context = context;
        this.xeTuLai = xeTuLai;
    }
    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_xetulaimaps, null);
        TextView txtTenXeTuLaiMaps = view.findViewById(R.id.txtTenXeTuLaiMaps);
        TextView txtGiaThueXeTuLaiMaps = view.findViewById(R.id.txtGiaThueXeTuLaiMaps);
        TextView txtMoTaXeTuLaiMaps = view.findViewById(R.id.txtMoTaXeTuLaiMaps);
        ImageView imgHinhAnhXeTuLaiMaps = view.findViewById(R.id.imgHinhAnhXeTuLaiMaps);

        txtTenXeTuLaiMaps.setText("Tên xe: "+xeTuLai.getTenXe());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaThueXeTuLaiMaps.setText("Giá thuê: "+decimalFormat.format(xeTuLai.getGiaThue())+" VNĐ/ngày");
        txtMoTaXeTuLaiMaps.setText("Tình trạng: "+xeTuLai.getMoTa());
        Glide.with(view).load("http://192.168.1.50/dacn/assets/images/xe/"+xeTuLai.getHinhAnh()).into(imgHinhAnhXeTuLaiMaps);
        return view;
    }
}
