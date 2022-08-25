package com.example.appbanhang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appbanhang.Model.MessageModel;
import com.example.appbanhang.Model.SanPhamMoi;
import com.example.appbanhang.databinding.ActivityThemSpactivityBinding;
import com.example.appbanhang.retrofit.ApBanHang;
import com.example.appbanhang.retrofit.RetrofitClient;
import com.example.appbanhang.untils.Untils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class ThemSPActivity extends AppCompatActivity {
Spinner spinner;
int loai;
ActivityThemSpactivityBinding binding;
ApBanHang apBanHang;
CompositeDisposable compositeDisposable= new CompositeDisposable();
String mediaPath;
SanPhamMoi sanPhamSua;
boolean flag =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityThemSpactivityBinding.inflate(getLayoutInflater());
        apBanHang = RetrofitClient.getInstance(Untils.BASE_URL).create(ApBanHang.class);
//        setContentView(R.layout.activity_them_spactivity);
        setContentView(binding.getRoot());
        initView();
        initControl();
        initData();
        Intent intent=getIntent();
        sanPhamSua= (SanPhamMoi) intent.getSerializableExtra("sua");
        if(sanPhamSua==null){
            //them
            flag=false;

        }else {
            //sua
            flag=true;
            binding.btnthemsp1.setText("Sua san pham");
            //show data
            binding.motaAdd.setText(sanPhamSua.getMota());
            binding.giaAdd.setText(sanPhamSua.getGiasp()+"");
            binding.tenAdd.setText(sanPhamSua.getTensp());
            binding.imageAdd.setText(sanPhamSua.getHinhanh());

            binding.spinerAddpd.setSelection(sanPhamSua.getLoai());

        }


    }

    private void initData() {
        List<String> stringList= new ArrayList<>();
        stringList.add("loai 1");
        stringList.add("loai 2");
        stringList.add("loai 3");
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,com.nex3z.notificationbadge.R.layout.support_simple_spinner_dropdown_item,stringList);
        spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               loai=i;

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        binding.btnthemsp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false){
                    themsp();
                    Intent intent = new Intent(getApplicationContext(),addproductActivity.class);
                    startActivity(intent);
                }else {
                    suaSanPham();
                    Intent intent = new Intent(getApplicationContext(),addproductActivity.class);
                    startActivity(intent);
                }

            }
        });
        binding.imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ThemSPActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });


    }

    private void suaSanPham() {
        String arr_ten =binding.tenAdd.getText().toString().trim();
        String arr_gia =binding.giaAdd.getText().toString().trim();
        String arr_hinhanh =binding.imageAdd.getText().toString().trim();
        String arr_mota =binding.motaAdd.getText().toString().trim();
        if(TextUtils.isEmpty(arr_ten)||TextUtils.isEmpty(arr_gia)||TextUtils.isEmpty(arr_mota)||TextUtils.isEmpty(arr_hinhanh)||loai==0){
            Toast.makeText(getApplicationContext(),"Vui long nhap day du",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apBanHang.updatesp(arr_ten,arr_gia,arr_hinhanh,arr_mota,(loai-1),sanPhamSua.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(),"Thanh cong",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(getApplicationContext(),"K Thanh cong",Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            }
                    ));


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath=data.getDataString();
        uploadMultipleFiles();
        Log.d("Log","onActivityResult: "+mediaPath);
    }

    private void themsp() {
        String arr_ten =binding.tenAdd.getText().toString().trim();
        String arr_gia =binding.giaAdd.getText().toString().trim();
        String arr_hinhanh =binding.imageAdd.getText().toString().trim();
        String arr_mota =binding.motaAdd.getText().toString().trim();
        if(TextUtils.isEmpty(arr_ten)||TextUtils.isEmpty(arr_gia)||TextUtils.isEmpty(arr_mota)||TextUtils.isEmpty(arr_hinhanh)||loai==0){
            Toast.makeText(getApplicationContext(),"Vui long nhap day du",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apBanHang.insertSp(arr_ten,arr_gia,arr_hinhanh,arr_mota,(loai-1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(),"Thanh cong",Toast.LENGTH_LONG).show();

                                }else {
                                    Toast.makeText(getApplicationContext(),"K Thanh cong",Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            }
                    ));


        }
    }

    private void initControl() {

    }
    private  String getPath(Uri uri){
        String result;
        Cursor cursor =getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            result=uri.getPath();
        }else {
            cursor.moveToFirst();
            int index=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result=cursor.getString(index);
            cursor.close();
        }
        return result;
    }
    // Uploading Image/Video
    private void uploadMultipleFiles() {
        Uri uri=Uri.parse(mediaPath);

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(getPath(uri));

        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);


        Call<MessageModel> call = apBanHang.uploadFile(fileToUpload1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call <MessageModel> call, Response<MessageModel> response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.imageAdd.setText(serverResponse.getName());
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Log.v("Response", serverResponse.toString());
                }

            }
            @Override
            public void onFailure(Call <MessageModel> call, Throwable t) {
                Log.d("log",t.getMessage());

            }
        });
    }
    private void initView() {
        spinner=findViewById(R.id.spiner_addpd);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}