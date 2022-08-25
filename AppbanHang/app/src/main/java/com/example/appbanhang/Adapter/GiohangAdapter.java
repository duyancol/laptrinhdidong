package com.example.appbanhang.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.InterfaceClickListener;
import com.example.appbanhang.Model.EventBus.TinhtongEvent;
import com.example.appbanhang.Model.Giohang;
import com.example.appbanhang.R;
import com.example.appbanhang.untils.Untils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.MyViewHolder> {
    Context context;
    List<Giohang> giohangList;

    public GiohangAdapter(Context context, List<Giohang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Giohang  giohang=giohangList.get(position);
        holder.itemghten.setText(giohang.getTensp());
        holder.itemghsoluong.setText(giohang.getSoluong()+" ");
        Glide.with(context).load(giohang.getHinhsp()).into(holder.itemgiohang_img);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        holder.itemghgia.setText("Price: "+decimalFormat.format(giohang.getGiasp())+"$");
        long gia =giohang.getSoluong()*giohang.getGiasp();
        holder.itemghgia2.setText(decimalFormat.format(gia));
        holder.setInterfaceClickListener(new InterfaceClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if(giatri==1){
                    if (giohangList.get(pos).getSoluong()>1){
                        int soluongmoi=giohangList.get(pos).getSoluong()-1;
                        giohangList.get(pos).setSoluong(soluongmoi);

                        holder.itemghsoluong.setText(giohangList.get(pos).getSoluong()+" ");
                        long gia =giohangList.get(pos).getSoluong()*giohangList.get(pos).getGiasp();
                        holder.itemghgia2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhtongEvent());
                    }else if(giohangList.get(pos).getSoluong()==1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thong bao");
                        builder.setMessage(" ban co muon xoa khoi gio hang");
                        builder.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Untils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("huy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        builder.show();

                    }

                }else if (giatri==2){
                    if (giohangList.get(pos).getSoluong()<10){
                        int soluongmoi=giohangList.get(pos).getSoluong()+1;
                        giohangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.itemghsoluong.setText(giohangList.get(pos).getSoluong()+" ");
                    long gia =giohangList.get(pos).getSoluong()*giohangList.get(pos).getGiasp();
                    holder.itemghgia2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhtongEvent());

                }
//                holder.itemghsoluong.setText(giohangList.get(pos).getSoluong()+" ");
//                long gia =giohangList.get(pos).getSoluong()*giohangList.get(pos).getGiasp();
//                holder.itemghgia2.setText(decimalFormat.format(gia));
//                EventBus.getDefault().postSticky(new TinhtongEvent());
            }
        });


    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
ImageView itemgiohang_img,imgtru,imgcong;
TextView itemghten,itemghgia,itemghsoluong,itemghgia2;
InterfaceClickListener interfaceClickListener ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemgiohang_img=itemView.findViewById(R.id.item_giohang_imange);
            itemghten =itemView.findViewById(R.id.item_giohang_ten);
            itemghgia =itemView.findViewById(R.id.item_giohang_gia);
            itemghsoluong =itemView.findViewById(R.id.item_giohang_soluong);
            itemghgia2 =itemView.findViewById(R.id.item_giohanggia2);
            imgtru=itemView.findViewById(R.id.item_giohang_tru);
            imgcong=itemView.findViewById(R.id.item_giohang_cong);
            //event
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setInterfaceClickListener(InterfaceClickListener interfaceClickListener) {
            this.interfaceClickListener = interfaceClickListener;
        }

        @Override
        public void onClick(View view) {
            if(view==imgtru){
                interfaceClickListener.onImageClick(view,getAdapterPosition(),1);
                //1 tru


            }else if (view ==imgcong){
                //2 cong
                interfaceClickListener.onImageClick(view,getAdapterPosition(),2);

            }
        }
    }
}
