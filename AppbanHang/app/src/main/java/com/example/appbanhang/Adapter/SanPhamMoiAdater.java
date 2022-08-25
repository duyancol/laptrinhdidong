package com.example.appbanhang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.ChitietnActivity;
import com.example.appbanhang.Interface.ItemclickInterface;
import com.example.appbanhang.Model.EventBus.SuaXoaEvent;
import com.example.appbanhang.R;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.untils.Untils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdater extends RecyclerView.Adapter<SanPhamMoiAdater.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdater(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi,parent,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi=array.get(position);
        holder.txtten.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        holder.txtgia.setText("Price: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"$");
        if(sanPhamMoi.getHinhanh().contains("http")){
            Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imghinhanh);
        }else {
            String hinh = Untils.BASE_URL+"images/"+sanPhamMoi.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imghinhanh);
        }
//        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imghinhanh);
        holder.setItemclickInterface(new ItemclickInterface() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent= new Intent(context, ChitietnActivity.class);
                    intent.putExtra("chitiet",sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(sanPhamMoi));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView txtgia,txtten;
        ImageView imghinhanh;
        private  ItemclickInterface itemclickInterface;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia=itemView.findViewById(R.id.itemsp_gia);
            txtten=itemView.findViewById(R.id.itemsp_ten);
            imghinhanh=itemView.findViewById(R.id.itemsp_image);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemclickInterface(ItemclickInterface itemclickInterface) {
            this.itemclickInterface = itemclickInterface;
        }

        @Override
        public void onClick(View view) {
            itemclickInterface.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0,0,getAdapterPosition(),"sua");
            contextMenu.add(0,1,getAdapterPosition(),"xoa");
        }

        @Override
        public boolean onLongClick(View view) {
            itemclickInterface.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
