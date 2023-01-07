package com.nguyenanhtuyen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.DanhMucProfile;

public class DanhMucProfileAdapter extends ArrayAdapter<DanhMucProfile> {
    Activity context;
    int resource;
    public DanhMucProfileAdapter(@NonNull Activity context, int resource) {
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
        ImageView imgHinh = customView.findViewById(R.id.imgHinh);
        TextView txtTieuDe = customView.findViewById(R.id.txtTieuDe);
        TextView txtNoiDung = customView.findViewById(R.id.txtNoiDung);

        //lấy danh mục ở vị trí position
        DanhMucProfile danhMucProfile = getItem(position);
        imgHinh.setImageResource(danhMucProfile.getHinhAnh());
        txtTieuDe.setText(danhMucProfile.getTieuDe());
        txtNoiDung.setText(danhMucProfile.getNoiDung());

        return customView;
    }
}
