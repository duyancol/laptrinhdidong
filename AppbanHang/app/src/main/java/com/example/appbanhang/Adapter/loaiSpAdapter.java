package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.LoaiSP;


import java.util.List;

public class loaiSpAdapter extends BaseAdapter {

    Context context;
    List<LoaiSP> array;

    public loaiSpAdapter(Context context, List<LoaiSP> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public  class ViewHolder{
        TextView texttensp;
        ImageView imageHinhanh;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder= null;
        if(view==null){
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.itemcategoty,null);
            viewHolder.texttensp=view.findViewById(R.id.item_tensp);
            viewHolder.imageHinhanh=view.findViewById(R.id.item_immage);
            view.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) view.getTag();

        }
        viewHolder.texttensp.setText(array.get(i).getTensanpham());
        Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imageHinhanh);
        return view;
    }

//    List<LoaiSP> array ;
//    Context context;
//
//    public loaiSpAdapter(Context context, List<LoaiSP> array) {
//
//        this.context = context;
//        this.array = array;
//    }
//
//
//
//    @Override
//    public int getCount() {
//        return array.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//    public  class  ViewHoder{
//        TextView textView;
//        ImageView imageView;
//}
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHoder viewHoder =null;
//        if(view==null){
//            viewHoder= new ViewHoder();
//            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view=layoutInflater.inflate(R.layout.itemcategoty,null);
//            viewHoder.textView=view.findViewById(R.id.item_tensp);
//            viewHoder.imageView=view.findViewById(R.id.item_immage);
//            view.setTag(viewHoder);
//        }else {
//            viewHoder=(ViewHoder) view.getTag();
//            viewHoder.textView.setText(array.get(i).getTensanpham());
//            Glide.with(context).load(array.get(i).getHinhanh()).into(viewHoder.imageView);
//        }
//
//        return view;
//    }

}
