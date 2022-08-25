package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Model.DonHang;
import com.example.appbanhang.R;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
private RecyclerView.RecycledViewPool viewPool= new RecyclerView.RecycledViewPool();
    Context  context;
    List<DonHang> donHangList;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang =donHangList.get(position);
        holder.txtdonhang.setText("IP :"+donHang.getId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView_chitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adapter
        ChiTietAdapter chiTietAdapter= new ChiTietAdapter(context,donHang.getItem());
        holder.recyclerView_chitiet.setLayoutManager(layoutManager);
        holder.recyclerView_chitiet.setAdapter(chiTietAdapter);
        holder.recyclerView_chitiet.setRecycledViewPool(viewPool);




    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView_chitiet;
        TextView txtdonhang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            recyclerView_chitiet=itemView.findViewById(R.id.reccyleview_donhang);
        }
    }
}
