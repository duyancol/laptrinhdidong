package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Model.Giohang;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.untils.Untils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class ChitietnActivity extends AppCompatActivity {
TextView tensp,giasp,mota;
Button  buttonthem;
ImageView  imghinhanh;
Spinner spinner;
Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietn);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        buttonthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }
private void themGioHang(){
        if(Untils.manggiohang.size()>0){
            boolean flag=false;
            int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
            for(int i=0;i<Untils.manggiohang.size();i++){
                if(Untils.manggiohang.get(i).getIdsp()==sanPhamMoi.getId()){
                    Untils.manggiohang.get(i).setSoluong(soluong+Untils.manggiohang.get(i).getSoluong());
                    long gia=Long.parseLong(sanPhamMoi.getGiasp())*Untils.manggiohang.get(i).getSoluong();
                    Untils.manggiohang.get(i).setGiasp(gia);
                    flag=true;
                    System.out.println("duy 1");

                }
            }
            if(flag=false){

                long gia=Long.parseLong(sanPhamMoi.getGiasp())*soluong;
                Giohang giohang= new Giohang();
                giohang.setGiasp(gia);
                giohang.setSoluong(soluong);
                giohang.setIdsp(sanPhamMoi.getId());
                giohang.setTensp(sanPhamMoi.getTensp());
                giohang.setHinhsp(sanPhamMoi.getHinhanh());
                Untils.manggiohang.add(giohang);
                System.out.println("duy 2");
            }

        }else{
            int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
            long gia=Long.parseLong(sanPhamMoi.getGiasp())*soluong;
            Giohang giohang= new Giohang();
            giohang.setGiasp(gia);
            giohang.setSoluong(soluong);
            giohang.setIdsp(sanPhamMoi.getId());
            giohang.setTensp(sanPhamMoi.getTensp());
            giohang.setHinhsp(sanPhamMoi.getHinhanh());
            Untils.manggiohang.add(giohang);
            System.out.println("duy 3");
        }
    int totalitem=0;
    for(int i=0;i<Untils.manggiohang.size();i++){
        totalitem=totalitem+Untils.manggiohang.get(i).getSoluong();
    }
        badge.setText(String.valueOf(totalitem));
}
    private void initData() {
         sanPhamMoi= (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
       giasp.setText("Price: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"$");
       Integer[] so=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspiner= new ArrayAdapter<>(this, com.nex3z.notificationbadge.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspiner);
    }

    private void initView() {
        tensp =findViewById(R.id.chitietten);
        giasp=findViewById(R.id.chitiettengia);
        mota =findViewById(R.id.chitietmota);
        buttonthem=findViewById(R.id.buttonadd);
        spinner=findViewById(R.id.spiner);
        imghinhanh=findViewById(R.id.chitiethinhanh);
        toolbar =findViewById(R.id.toobar);
        badge =findViewById(R.id.menu_sl);
        FrameLayout frameLayout =findViewById(R.id.framelayou_giohang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang= new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if(Untils.manggiohang!=null){
            int totalitem=0;
            for(int i=0;i<Untils.manggiohang.size();i++){
                totalitem=totalitem+Untils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalitem));
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Untils.manggiohang!=null){
            int totalitem=0;
            for(int i=0;i<Untils.manggiohang.size();i++){
                totalitem=totalitem+Untils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalitem));
        }
    }
}