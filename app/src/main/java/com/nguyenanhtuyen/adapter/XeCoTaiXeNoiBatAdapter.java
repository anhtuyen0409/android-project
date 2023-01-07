package com.nguyenanhtuyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.XeCoTaiXe;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class XeCoTaiXeNoiBatAdapter extends RecyclerView.Adapter<XeCoTaiXeNoiBatAdapter.XeCoTaiXeNoiBatViewHolder>{
    ArrayList<XeCoTaiXe> listXeCoTaiXeNoiBat;
    public XeCoTaiXeNoiBatAdapter(ArrayList<XeCoTaiXe> listXeCoTaiXeNoiBat){
        this.listXeCoTaiXeNoiBat = listXeCoTaiXeNoiBat;
    }
    @NonNull
    @Override
    public XeCoTaiXeNoiBatAdapter.XeCoTaiXeNoiBatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_xecotaixenoibat, parent, false);
        return new XeCoTaiXeNoiBatAdapter.XeCoTaiXeNoiBatViewHolder(item_view);
    }
    @Override
    public void onBindViewHolder(@NonNull XeCoTaiXeNoiBatAdapter.XeCoTaiXeNoiBatViewHolder holder, int position){
        XeCoTaiXe xe = listXeCoTaiXeNoiBat.get(position);
        Glide.with(holder.itemView.getContext()).load("http://192.168.1.50/dacn/assets/images/xe/"+xe.getHinhAnh()).into(holder.ivhinhanh);
        holder.txttenxe.setText(xe.getTenXe());
        //đổi định dạng tiền vnd
        Locale locale = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiathue.setText(decimalFormat.format(xe.getGiaThue())+" VNĐ/giờ");

    }
    @Override
    public int getItemCount(){
        return listXeCoTaiXeNoiBat.size();
    }
    public class XeCoTaiXeNoiBatViewHolder extends RecyclerView.ViewHolder{
        TextView txttenxe;
        TextView txtgiathue;
        ImageView ivhinhanh;
        public XeCoTaiXeNoiBatViewHolder(View itemView){
            super(itemView);
            txttenxe = itemView.findViewById(R.id.txttenxe);
            txtgiathue = itemView.findViewById(R.id.txtgiathue);
            ivhinhanh = itemView.findViewById(R.id.ivhinhanh);
        }
    }
}
