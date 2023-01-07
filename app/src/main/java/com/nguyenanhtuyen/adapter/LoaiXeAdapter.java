package com.nguyenanhtuyen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.LoaiXe;

public class LoaiXeAdapter extends ArrayAdapter<LoaiXe> {
    Activity context;
    int resource;
    public LoaiXeAdapter(@NonNull Activity context, int resource) {
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
        TextView txtTenLoaiXe = customView.findViewById(R.id.txtTenLoaiXe);
        //lấy loại ở vị trí position
        LoaiXe loaiXe = getItem(position);
        txtTenLoaiXe.setText(loaiXe.getTenLoaiXe());
        return customView;
    }
}
