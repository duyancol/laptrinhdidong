package com.example.appbanhang.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.Item;
import com.example.appbanhang.R;

import java.util.List;
import com.example.appbanhang.R;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder>{


    Context context;
    List<Item> itemList;

    public ChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public ChiTietAdapter(List<Item> itemList) {

        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.xem_chitiet,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item=itemList.get(position);
        holder.txten.setText(item.getTensp()+"");
        holder.txtsoluong.setText("So luong :"+item.getSoluong()+"");
        Glide.with(context).load(item.getHinhanh()).into(holder.imgchitiet);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

//    @NonNull
//    @Override
//    public DonHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.xem_chitiet,parent,false);
//        return new MyViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DonHangAdapter.MyViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView_chitiet;
        TextView txten,txtsoluong;
        ImageView imgchitiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           txten=itemView.findViewById(R.id.tensp_hoadon);
           txtsoluong=itemView.findViewById(R.id.soluong_hoadon);
           imgchitiet=itemView.findViewById(R.id.img_donhang);
            recyclerView_chitiet=itemView.findViewById(R.id.reccyleview_donhang);
        }
    }
}
