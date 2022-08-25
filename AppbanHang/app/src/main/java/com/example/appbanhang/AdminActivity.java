package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AdminActivity extends AppCompatActivity {
    LinearLayout themsp,thongke,home,oder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
        initControl();
    }

    private void initControl() {
        themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),addproductActivity.class);
                startActivity(intent);
            }
        });
        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThongKeActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),XemDonAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        themsp =findViewById(R.id.btnthemsp);
        thongke =findViewById(R.id.btnthongke);
        home =findViewById(R.id.btnhome);
        oder=findViewById(R.id.admin_oder);
    }
}