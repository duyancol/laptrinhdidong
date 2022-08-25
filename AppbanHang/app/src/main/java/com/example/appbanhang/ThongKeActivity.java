package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    ApBanHang apBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apBanHang= RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
        initView();

        ActionToolBar();
        getdataChar();
    }

    private void getdataChar() {
        List<PieEntry> listdata=new ArrayList<>();
        compositeDisposable.add(apBanHang.getThongKe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if(thongKeModel.isSuccess()){
                                for (int i=0;i<thongKeModel.getResult().size();i++){
                                    String tensp=thongKeModel.getResult().get(i).getTensp();
                                    int tong = thongKeModel.getResult().get(i).getTong();
                                    listdata.add(new PieEntry(tong,tensp));
                                }
                                PieDataSet pieDataSet=new PieDataSet(listdata,"Thong ke");
                                PieData data=new PieData();
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter());
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                pieChart.setData(data);
                                pieChart.animateXY(2000,2000);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();
                            }

                        },
                        throwable -> {
                            Log.d("Log" ,throwable.getMessage());
                        }

                ));
    }

    private void initView() {
        toolbar=findViewById(R.id.toobar);
        pieChart=findViewById(R.id.idchart);
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
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}