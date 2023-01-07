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
import com.nguyenanhtuyen.model.XeCoTaiXe;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class XeCoTaiXeAdapter extends ArrayAdapter<XeCoTaiXe> {
    Activity context;
    int resource;
    public XeCoTaiXeAdapter(@NonNull Activity context, int resource) {
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
        ImageView imgHinhAnhXeCoTaiXe = customView.findViewById(R.id.imgHinhAnhXeCoTaiXe);
        TextView txtTenXeCoTaiXe = customView.findViewById(R.id.txtTenXeCoTaiXe);
        TextView txtGiaThueXeCoTaiXe = customView.findViewById(R.id.txtGiaThueXeCoTaiXe);
        TextView txtDiaChiCoTaiXe = customView.findViewById(R.id.txtDiaChi_CoTaiXe);

        //lấy xe ở vị trí position
        XeCoTaiXe xeCoTaiXe = getItem(position);
        Glide.with(customView).load("http://192.168.1.50/dacn/assets/images/xe/"+xeCoTaiXe.getHinhAnh()).into(imgHinhAnhXeCoTaiXe);
        txtTenXeCoTaiXe.setText(xeCoTaiXe.getTenXe());
        Locale locale = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaThueXeCoTaiXe.setText(decimalFormat.format(xeCoTaiXe.getGiaThue())+" VNĐ/giờ");
        txtDiaChiCoTaiXe.setText(xeCoTaiXe.getDiaChi());
        return customView;
    }
}
