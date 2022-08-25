package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhang.Adapter.DonHangAdapter;
import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonAdminActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    ApBanHang apBanHang;
    RecyclerView redonhang;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_admin);
        initView();
        initToobar();
        getOrder();
    }
    private void getOrder() {
        compositeDisposable.add(apBanHang.xemDonHangAdmin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter =new DonHangAdapter(getApplicationContext(),donHangModel.getResult());
                           redonhang.setAdapter(adapter);


                        },
                        throwable -> {

                        }
                ));
    }



    private void initToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        apBanHang= RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        redonhang=findViewById(R.id.reccyleview_donhang);
        toolbar=findViewById(R.id.toobar);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        redonhang.setLayoutManager(layoutManager);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}