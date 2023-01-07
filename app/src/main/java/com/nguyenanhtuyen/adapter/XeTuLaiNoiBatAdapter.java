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
import com.nguyenanhtuyen.model.XeTuLai;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class XeTuLaiNoiBatAdapter extends RecyclerView.Adapter<XeTuLaiNoiBatAdapter.XeTuLaiNoiBatViewHolder>{
    ArrayList<XeTuLai> listXeTuLaiNoiBat;
    public XeTuLaiNoiBatAdapter(ArrayList<XeTuLai> listXeTuLaiNoiBat){
        this.listXeTuLaiNoiBat = listXeTuLaiNoiBat;
    }
    @NonNull
    @Override
    public XeTuLaiNoiBatAdapter.XeTuLaiNoiBatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_xetulainoibat, parent, false);
        return new XeTuLaiNoiBatAdapter.XeTuLaiNoiBatViewHolder(item_view);
    }
    @Override
    public void onBindViewHolder(@NonNull XeTuLaiNoiBatAdapter.XeTuLaiNoiBatViewHolder holder, int position){
        XeTuLai xe = listXeTuLaiNoiBat.get(position);
        Glide.with(holder.itemView.getContext()).load("http://192.168.1.50/dacn/assets/images/xe/"+xe.getHinhAnh()).into(holder.ivhinhanh);
        holder.txttenxe.setText(xe.getTenXe());

        //đổi định dạng tiền vnd
        Locale locale = new Locale("vi","VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiathue.setText(decimalFormat.format(xe.getGiaThue())+" VNĐ/ngày");
    }
    @Override
    public int getItemCount(){
        return listXeTuLaiNoiBat.size();
    }
    public class XeTuLaiNoiBatViewHolder extends RecyclerView.ViewHolder{
        TextView txttenxe;
        TextView txtgiathue;
        ImageView ivhinhanh;
        public XeTuLaiNoiBatViewHolder(View itemView){
            super(itemView);
            txttenxe = itemView.findViewById(R.id.txttenxe);
            txtgiathue = itemView.findViewById(R.id.txtgiathue);
            ivhinhanh = itemView.findViewById(R.id.ivhinhanh);
        }
    }
}
