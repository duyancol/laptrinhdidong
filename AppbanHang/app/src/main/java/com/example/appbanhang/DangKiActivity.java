package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;

public class DangKiActivity extends AppCompatActivity {
    EditText email,pass,repass,mobile,username;
    AppCompatButton button;
    ApBanHang apBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        initview();
        initControl();
    }

    private void initControl() {
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dangKi();
    }
});
    }

    private void dangKi() {
        String str_email=email.getText().toString().trim();
        String str_pass=pass.getText().toString().trim();
        String str_repass=repass.getText().toString().trim();
        String str_mobile=mobile.getText().toString().trim();
        String str_username=username.getText().toString().trim();
        if(TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(),"bAN CHUA NHAP e MAIL",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(),"bAN CHUA NHAP Pass",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(),"bAN CHUA NHAP RePass",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(str_mobile)) {
            Toast.makeText(getApplicationContext(), "bAN CHUA NHAP sdt", Toast.LENGTH_LONG).show();
        } else if(TextUtils.isEmpty(str_username)) {
        Toast.makeText(getApplicationContext(), "bAN CHUA NHAP username", Toast.LENGTH_LONG).show();
    }
        else {
            if(str_pass.equals(str_repass)){
                compositeDisposable.add(apBanHang.dangKi(str_email,str_pass,str_username,str_mobile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()){
                                        Toast.makeText(getApplicationContext(), "Thanh cong", Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                },
                                throwable -> {
                                    Untils.user_current.setEmail(str_email);
                                    Untils.user_current.setPass(str_pass);

                                    Toast.makeText(getApplicationContext(), "Thanh cong", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                        ));


            }else {
                Toast.makeText(getApplicationContext(), "Khng khop", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void initview() {
        apBanHang= RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        email=findViewById(R.id.emaildk);
        pass=findViewById(R.id.passdk);
        repass=findViewById(R.id.repassdk);
        mobile =findViewById(R.id.mobile);
        button=findViewById(R.id.btndangki);
        username=findViewById(R.id.username);

        
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}