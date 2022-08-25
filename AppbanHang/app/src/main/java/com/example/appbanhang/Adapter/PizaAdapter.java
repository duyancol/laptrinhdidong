package com.example.appbanhang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.ChitietnActivity;
import com.example.appbanhang.Interface.ItemclickInterface;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.R;

import java.text.DecimalFormat;
import java.util.List;

public class PizaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
Context context;
List<SanPhamMoi> array;
private static final int VIEW_TYPE_DATA=0;
private static  final int VIEW_TYPE_LOADING=1;


    public PizaAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_piza,parent,false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        SanPhamMoi sanPhamMoi=array.get(position);
//        holder.tensp.setText(sanPhamMoi.getTensp());
//        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
//        holder.giasp.setText("Price: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"$");
//        holder.mota.setText(sanPhamMoi.getMota());
//        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.hinhanh);
//
//
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_DATA){
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_piza,parent,false);
       return new MyViewHolder(view);

        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load,parent,false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolder){
            MyViewHolder viewHolder= (MyViewHolder) holder;
                    SanPhamMoi sanPhamMoi=array.get(position);
        viewHolder.tensp.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        viewHolder.giasp.setText("Price: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"$");
        viewHolder.mota.setText(sanPhamMoi.getMota());
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(viewHolder.hinhanh);
        viewHolder.setItemclickListener(new ItemclickInterface() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent= new Intent(context, ChitietnActivity.class);
                    intent.putExtra("chitiet",sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        }else {
            LoadingViewHolder loadingViewHolder= (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }
public class LoadingViewHolder extends RecyclerView.ViewHolder{
ProgressBar progressBar;
    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar=itemView.findViewById(R.id.processbar);
    }
}
    public class  MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp,giasp,mota;
        ImageView hinhanh;
        private ItemclickInterface itemclickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp=itemView.findViewById(R.id.itempiza_ten);
            giasp=itemView.findViewById(R.id.itempiza_gia);
            mota=itemView.findViewById(R.id.itempiza_mota);
            hinhanh=itemView.findViewById(R.id.itempiza_image);

            itemView.setOnClickListener(this);
        }

        public void setItemclickListener(ItemclickInterface itemclickListener) {
            this.itemclickListener = itemclickListener;
        }

        @Override
        public void onClick(View view) {
            itemclickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
