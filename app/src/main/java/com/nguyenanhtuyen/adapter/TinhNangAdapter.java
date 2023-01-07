package com.nguyenanhtuyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.nguyenanhtuyen.appthuexe.R;
import com.nguyenanhtuyen.model.TinhNang;
import java.util.ArrayList;

public class TinhNangAdapter extends RecyclerView.Adapter<TinhNangAdapter.ViewHolder> {
    ArrayList<TinhNang> listTinhNang;
    public TinhNangAdapter(ArrayList<TinhNang> listTinhNang){
        this.listTinhNang = listTinhNang;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder, parent, false);
        return new ViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(listTinhNang.get(position).getUrl(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.removeItem);
    }
    @Override
    public int getItemCount(){
        return listTinhNang.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView removeItem;
        public ViewHolder(View itemView){
            super(itemView);

            removeItem = itemView.findViewById(R.id.removeFeeder);
        }
    }
}
