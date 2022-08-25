package com.example.appbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbanhang.Adapter.SanPhamMoiAdater;
import com.example.appbanhang.Model.EventBus.SuaXoaEvent;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class addproductActivity extends AppCompatActivity {
    ImageView imageView ;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApBanHang apBanHang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdater moiAdater;
    SanPhamMoi sanPhamMoiSuaXoa;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        apBanHang= RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        initView();ActionToolBar();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThemSPActivity.class);
                startActivity(intent);
            }
        });
        getSpMoi();
    }
    private void getSpMoi() {
        compositeDisposable.add(apBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                mangSpMoi=sanPhamMoiModel.getResult();
                                moiAdater= new SanPhamMoiAdater(getApplicationContext(),mangSpMoi);
                                recyclerView.setAdapter(moiAdater);

                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Khong ket noi duoc"+throwable.getMessage(),Toast.LENGTH_LONG).show();

                        }
                ));
    }

    private void initView() {
        imageView =findViewById(R.id.layout_addproduct);
        toolbar=findViewById(R.id.toobar);

        recyclerView=findViewById(R.id.reccyleview_ql);
//        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
//        recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private void ActionToolBar() {
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
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("sua")){
            suaSanPham();
        }else if(item.getTitle().equals("xoa")){
            xoaSanPham();
        }
        return super.onContextItemSelected(item);
    }

    private void xoaSanPham() {
        compositeDisposable.add(apBanHang.xoaSanPham(sanPhamMoiSuaXoa.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if(messageModel.isSuccess()){
                                Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                                getSpMoi();
                            }else {
                                Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        },
                        throwable -> {
                            Log.d("Log",throwable.getMessage());

                        }

                ));
    }

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(),ThemSPActivity.class);
        intent.putExtra("sua",sanPhamMoiSuaXoa);
        startActivity(intent);


    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void evenSuaXoa(SuaXoaEvent event){
        if(event !=null){
            sanPhamMoiSuaXoa=event.getSanPhamMoi();


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}