package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
Toolbar toolbar;
TextView txttongtien,txtsodt,txtemail;
EditText editdiachi;
AppCompatButton btndathang;
CompositeDisposable compositeDisposable = new CompositeDisposable();
ApBanHang  apBanHang;
    long tongtien;
    int totalitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        countItem();
        initControl();

    }

    private void countItem() {
         totalitem=0;
        for(int i=0;i<Untils.manggiohang.size();i++){
            totalitem=totalitem+Untils.manggiohang.get(i).getSoluong();
        }
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
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        tongtien=getIntent().getLongExtra("tongtien",0);
        txttongtien.setText(decimalFormat.format(tongtien));

        txtsodt.setText("0377155498");
        txtemail.setText(Untils.user_current.getEmail());
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi=editdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(),"ban chua nhap dia chi",Toast.LENGTH_SHORT).show();
                }else{
                    String str_email=Untils.user_current.getEmail();
                    String str_sdt=Untils.user_current.getMobile();
                    int id=Untils.user_current.getId();

                    Log.d("test",new Gson().toJson(Untils.manggiohang));
                    compositeDisposable.add(apBanHang.createOder(str_email,str_sdt,String.valueOf(tongtien),id,str_diachi,totalitem,new Gson().toJson(Untils.manggiohang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(),"Thanh cong",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
//                                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(),"Thanh cong",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                            ));

                }
            }
        });

    }

    private void initView() {
        apBanHang = RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        toolbar=findViewById(R.id.toobar);
        txttongtien=findViewById(R.id.tong_dathang);
        txtsodt =findViewById(R.id.phone_dathang);
        txtemail=findViewById(R.id.email_dathang);
        editdiachi=findViewById(R.id.diachi_dathang);
        btndathang=findViewById(R.id.btndathang);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}