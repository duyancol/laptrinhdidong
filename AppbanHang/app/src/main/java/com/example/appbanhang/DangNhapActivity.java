package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangki;
    EditText email,pass;
    AppCompatButton btndangnhap;
    ApBanHang apBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initcontrol();

    }

    private void initView() {
        apBanHang= RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        txtdangki=findViewById(R.id.txtdangki);
        email=findViewById(R.id.txtemaildn);
        pass=findViewById(R.id.txtpassdn);
        btndangnhap=findViewById(R.id.btndangnhap);


    }

    private void initcontrol() {
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),DangKiActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email=email.getText().toString().trim();
                String str_pass=pass.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(),"bAN CHUA NHAP e MAIL",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(),"bAN CHUA NHAP Pass",Toast.LENGTH_LONG).show();
                }else {
                    compositeDisposable.add(apBanHang.dangNhap(str_email,str_pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()&&userModel.getResult().get(0).getEmail().equals("1@gmail.com")){
                                            Untils.user_current=userModel.getResult().get(0);
                                            Intent intent= new Intent(getApplicationContext(),AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else if(userModel.isSuccess()){
                                            Untils.user_current=userModel.getResult().get(0);
                                            Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                                    }

                            ));
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Untils.user_current.getEmail()!=null && Untils.user_current.getPass()!=null){
            email.setText(Untils.user_current.getEmail());
            pass.setText(Untils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}