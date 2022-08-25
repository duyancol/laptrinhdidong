package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.appbanhang.Adapter.SanPhamMoiAdater;
import com.example.appbanhang.Model.LoaiSP;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import com.example.appbanhang.Adapter.loaiSpAdapter;
import com.nex3z.notificationbadge.NotificationBadge;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    loaiSpAdapter lspadapter;
    List<LoaiSP> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApBanHang apBanHang;
    ImageView imageView10;


    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdater moiAdater;
    NotificationBadge badge;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apBanHang = RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        Anhxa();
       ActionBar();
        if(isConnected(this)){
            ActionViewLiper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();


        }else{
            Toast.makeText(getApplicationContext(),"Khong co internet",Toast.LENGTH_LONG).show();
            ActionViewLiper();
            getLoaiSanPham();
        }
//        ActionViewLiper();
    }

    private void getEventClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),HomeActivity.class);

                        startActivity(trangchu);

                        break;
                    case 1:
                        Intent piza = new Intent(getApplicationContext(),PizaActivity.class);
                        piza.putExtra("loai",1);
                        startActivity(piza);

                        break;
                    case 2:
                        Intent coca = new Intent(getApplicationContext(),PizaActivity.class);
                        coca.putExtra("loai",2);
                        startActivity(coca);

                        break;
                    case 3:
                        Intent hamperger = new Intent(getApplicationContext(),PizaActivity.class);
                        hamperger.putExtra("loai",3);
                        startActivity(hamperger);

                        break;
                    case 6:
                        Intent oder = new Intent(getApplicationContext(),XemDonActivity.class);

                        startActivity(oder);

                        break;
                }
            }
        });
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
    private void getLoaiSanPham() {
//        compositeDisposable.add(apBanHang.getloaisp()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        loaiSPModel -> {
//                            if(loaiSPModel.isSuccess()){
//
//                                mangloaisp=loaiSPModel.getResult();
//                              lspadapter= new loaiSpAdapter(getApplicationContext(),mangloaisp);
//                              listView.setAdapter(lspadapter);
//                                Toast.makeText(getApplicationContext(),loaiSPModel.getResult().get(0).getHinhanh(),Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//                ));
        compositeDisposable.add(apBanHang.getloaisp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSPModel -> {
                            if(loaiSPModel.isSuccess()){
                               mangloaisp=loaiSPModel.getResult();
                               lspadapter= new loaiSpAdapter(getApplicationContext(),mangloaisp);
                               listView.setAdapter(lspadapter);
                                Toast.makeText(getApplicationContext(),loaiSPModel.getResult().get(0).getHinhanh(),Toast.LENGTH_LONG).show();
                            }
                        }
                ));

    }
    private void ActionBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void Anhxa() {
        toolbar=findViewById(R.id.toolbarmahinhchinh);
        viewFlipper =findViewById(R.id.viewLiper);
        recyclerView =findViewById(R.id.reccyleview);

        imageView10 =findViewById(R.id.imageView10);
        Glide.with(this).load(R.drawable.cat_3).apply(new RequestOptions().transform(new CenterCrop()).transform(new RoundedCorners(12))).into(imageView10);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        navigationView=findViewById(R.id.navigatview);
        listView = findViewById(R.id.listview);
        drawerLayout = findViewById(R.id.drawlayout);
        badge=findViewById(R.id.menu_sl);
        frameLayout=findViewById(R.id.framelayou_giohang);

        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if(Untils.manggiohang==null){
            Untils.manggiohang= new ArrayList<>();
        }else{
            int totalitem=0;
            for(int i=0;i<Untils.manggiohang.size();i++){
                totalitem=totalitem+Untils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalitem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });

//        loaiSpAdapter = new loaiSpAdapter(getApplicationContext(),mangloaisp);
//        listView.setAdapter(loaiSpAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalitem=0;
        for(int i=0;i<Untils.manggiohang.size();i++){
            totalitem=totalitem+Untils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalitem));
    }

    private void ActionViewLiper() {
        List<String> mangquancao =new ArrayList<>();
        mangquancao.add("https://www.pizzaexpress.vn/wp-content/uploads/2020/12/test.jpg");
        mangquancao.add("https://www.pizzaexpress.vn/wp-content/uploads/2022/02/Banner-Pizza-Web-18_9_2021-v2.png");
        mangquancao.add("https://coreldrawdesign.com/resources/previews/preview-very-delicious-pizza-restaurant-food-template-design-1602337614.jpg");
        for(int i=0;i<mangquancao.size();i++){
            ImageView imageView= new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquancao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobi =connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi!=null && wifi.isConnected())||(mobi !=null&& mobi.isConnected())){
            return  true;

        }else {
            return false;
        }

    }
    protected void  onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}