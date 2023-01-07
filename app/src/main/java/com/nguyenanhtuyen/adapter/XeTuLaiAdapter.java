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
import com.nguyenanhtuyen.model.XeTuLai;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class XeTuLaiAdapter extends ArrayAdapter<XeTuLai> {
    Activity context;
    int resource;
    public XeTuLaiAdapter(@NonNull Activity context, int resource) {
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
        ImageView imgHinhAnhTuLai = customView.findViewById(R.id.imgHinhAnhTuLai);
        TextView txtTenXeTuLai = customView.findViewById(R.id.txtTenXeTuLai);
        TextView txtGiaThueTuLai = customView.findViewById(R.id.txtGiaThueTuLai);
        TextView txtDiaChi = customView.findViewById(R.id.txtDiaChi);


        //lấy xe ở vị trí position
        XeTuLai xeTuLai = getItem(position);
        Glide.with(customView).load("http://192.168.1.50/dacn/assets/images/xe/"+xeTuLai.getHinhAnh()).into(imgHinhAnhTuLai);
        txtTenXeTuLai.setText(xeTuLai.getTenXe());
        Locale locale = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaThueTuLai.setText(decimalFormat.format(xeTuLai.getGiaThue())+" VNĐ/ngày");
        txtDiaChi.setText(xeTuLai.getDiaChi());
        return customView;
    }
}
