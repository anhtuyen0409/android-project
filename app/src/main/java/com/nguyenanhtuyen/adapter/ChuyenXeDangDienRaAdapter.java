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
import com.nguyenanhtuyen.model.ChuyenXeDangDienRa;


public class ChuyenXeDangDienRaAdapter extends ArrayAdapter<ChuyenXeDangDienRa> {
    Activity context;
    int resource;
    public ChuyenXeDangDienRaAdapter(@NonNull Activity context, int resource) {
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
        TextView txtNgayDatXe = customView.findViewById(R.id.txtNgayDatXe_Chuyen);
        TextView txtNgayKetThuc = customView.findViewById(R.id.txtNgayKetThuc_Chuyen);
        TextView txtTenHinhThuc = customView.findViewById(R.id.txtHinhThucThanhToan_Chuyen);
        TextView txtTongThanhToan = customView.findViewById(R.id.txtTongThanhToan_Chuyen);


        //lấy xe ở vị trí position
        ChuyenXeDangDienRa chuyenXeDangDienRa = getItem(position);
        txtNgayDatXe.setText(chuyenXeDangDienRa.getNgayDat());
        txtNgayKetThuc.setText(chuyenXeDangDienRa.getNgayKetThuc());
        txtTenHinhThuc.setText(chuyenXeDangDienRa.getTenHinhThuc());
        txtTongThanhToan.setText(chuyenXeDangDienRa.getTongThanhToan()+"");

        return customView;
    }
}
