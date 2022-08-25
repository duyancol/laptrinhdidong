package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbanhang.Adapter.GiohangAdapter;
import com.example.appbanhang.Model.EventBus.TinhtongEvent;
import com.example.appbanhang.Model.Giohang;
import com.example.appbanhang.untils.Untils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
TextView giohangtrong,tongtien;
Toolbar toolbar;
RecyclerView recyclerView;
Button btnmuahang;
GiohangAdapter adapter;
    long tongtiensp;
//List<Giohang> giohangList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhtongtien();
    }

    private void tinhtongtien() {
         tongtiensp=0;
        for(int i=0;i<Untils.manggiohang.size();i++){
            tongtiensp=tongtiensp+Untils.manggiohang.get(i).getGiasp()*Untils.manggiohang.get(i).getSoluong();

        }

        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));

    }

    private void initControl() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Untils.manggiohang.size()==0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else {
            adapter = new GiohangAdapter(getApplicationContext(),Untils.manggiohang);
            recyclerView.setAdapter(adapter);

        }
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThanhToanActivity.class);
                intent.putExtra("tongtien",tongtiensp);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        giohangtrong =findViewById(R.id.txt_giohangtrong);
        tongtien =findViewById(R.id.txttongtien);
        toolbar=findViewById(R.id.toobar);
        recyclerView=findViewById(R.id.reccyleviewgiohang);
        btnmuahang=findViewById(R.id.btmuahang);
    }



    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhtien(TinhtongEvent event){
        if(event !=null){
            tinhtongtien();
        }
    }
}