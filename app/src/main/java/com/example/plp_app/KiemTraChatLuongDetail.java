package com.example.plp_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plp_app.adapter.DefectCodeAdapter;
import com.example.plp_app.api.ApiClient;
import com.example.plp_app.api.ApiService;
import com.example.plp_app.api.DashboardsResponse;
import com.example.plp_app.api.DefectCodesResponse;
import com.example.plp_app.api.QAProductResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KiemTraChatLuongDetail extends AppCompatActivity  implements DefectCodeAdapter.OnDefectSelectedListener {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;
    private Button captureButton;
    private ImageView imageView;
    private RecyclerView defectCodeRecyclerView;
    private RecyclerView selectedDefectsRecyclerView;
    private DefectCodeAdapter defectAdapter;
    private DefectCodeAdapter selectedDefectsAdapter;
    private List<String> defectCodes;
    private Button btn_datlai;
    private TextView tv_Soluongtong;
    private TextView tv_SLdat;
    private TextView tv_SLkhongdat;
    private int spanCount = 4;
    private int spacing = 8;
    private boolean includeEdge = false;
    private String idOrderProduct;
    private ImageButton imb_backbutton;
    private int SLkhongdat = 0;
    private int SLdat = 0;
    private int SLtong = 0;
    private Button btn_BaoCao;
    private Button btn_XoaLoi;
    private CounterUpdateReceiver counterUpdateReceiver;
    private List<String> getdefectCodes = new ArrayList<>();
    private String datetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_tra_chat_luong_detail);

        idOrderProduct = getIntent().getStringExtra("idOrderProduct");

        captureButton = findViewById(R.id.captureButton);
        imageView = findViewById(R.id.imageView);
        btn_datlai = findViewById(R.id.resetButton);
        tv_Soluongtong = findViewById(R.id.quantity);
        tv_SLdat = findViewById(R.id.slDat);
        tv_SLkhongdat = findViewById(R.id.slKhongDat);
        btn_BaoCao = findViewById(R.id.reportButton);
        btn_XoaLoi = findViewById(R.id.deleteButton);
        imb_backbutton = findViewById(R.id.backButton);
        imb_backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Lấy giá trị từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SLkhongdat = sharedPreferences.getInt("SLkhongdat", 0);
        SLdat = sharedPreferences.getInt("SLdat", 0);
        SLtong = sharedPreferences.getInt("SLtong", 0);

        // Tiếp tục với các thiết lập khác...
        updateCounterTextView();
        btn_datlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SLkhongdat = 0;
                SLdat = 0;
                SLtong = 0;
                updateCounterTextView();
            }
        });
        counterUpdateReceiver = new CounterUpdateReceiver();
        IntentFilter filter = new IntentFilter("COUNTER_UPDATED");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(counterUpdateReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        }
        // Khởi động VolumeButtonService
        Intent serviceIntent = new Intent(this, VolumeButtonService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(KiemTraChatLuongDetail.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(KiemTraChatLuongDetail.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                } else {
                    openCamera();
                }
            }
        });

        defectCodeRecyclerView = findViewById(R.id.defectRecyclerView);
        selectedDefectsRecyclerView = findViewById(R.id.selectedDefectsRecyclerView);

        CallApiGetDefectCodes();

        selectedDefectsAdapter = new DefectCodeAdapter(this, new ArrayList<>(), null);
        selectedDefectsRecyclerView.setAdapter(selectedDefectsAdapter);
        selectedDefectsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btn_XoaLoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_BaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SLkhongdat += 1;
                CallApiPostQAProduct(defectCodes);
            }
        });

    }
    public void CallApiPostQAProduct( List<String> defectCodes){
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<QAProductResponse> call = apiService.createQAProduct(idOrderProduct,defectCodes,1 );
        call.enqueue(new Callback<QAProductResponse>() {
            @Override
            public void onResponse(Call<QAProductResponse> call, Response<QAProductResponse> response) {
                if (response.isSuccessful()) {
                    QAProductResponse qaProductResponse = response.body();
                    Toast.makeText(KiemTraChatLuongDetail.this, "Báo cáo thành công", Toast.LENGTH_LONG).show();
                    updateCounterTextView();
                } else {
                    SLkhongdat -= 1;
                    updateCounterTextView();
                    Toast.makeText(KiemTraChatLuongDetail.this, "Mã lỗi không tồn tại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QAProductResponse> call, Throwable t) {
                Toast.makeText(KiemTraChatLuongDetail.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(counterUpdateReceiver);
    }
    public void CallApiGetDefectCodes(){
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<DefectCodesResponse> call = apiService.getDefectcode(20);
        call.enqueue(new Callback<DefectCodesResponse>() {
            @Override
            public void onResponse(Call<DefectCodesResponse> call, Response<DefectCodesResponse> response) {
                DefectCodesResponse defectCodesResponse = response.body();
                List<DefectCodesResponse.Item> defectcodes = defectCodesResponse.getData().getItems();
                for(DefectCodesResponse.Item defectcode : defectcodes){
                    String code = defectcode.getCode();
                    if(!code.equals("0")){
                        getdefectCodes.add(code);
                    }
                }
                defectAdapter = new DefectCodeAdapter(KiemTraChatLuongDetail.this, getdefectCodes, KiemTraChatLuongDetail.this);
                defectCodeRecyclerView.setAdapter(defectAdapter);
                defectCodeRecyclerView.setLayoutManager(new GridLayoutManager(KiemTraChatLuongDetail.this, 4));
            }

            @Override
            public void onFailure(Call<DefectCodesResponse> call, Throwable t) {
                Toast.makeText(KiemTraChatLuongDetail.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDefectSelected(List<String> selectedDefects) {
        defectCodes = selectedDefects;
        selectedDefectsAdapter = new DefectCodeAdapter(this, selectedDefects, null);
        selectedDefectsRecyclerView.setAdapter(selectedDefectsAdapter);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Cần cung cấp quyền sử dụng máy ảnh để dùng chức năng này.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            captureButton.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Lưu giá trị vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("SLkhongdat", SLkhongdat);
        editor.putInt("SLdat", SLdat);
        editor.putInt("SLtong", SLtong);
        editor.apply();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SLkhongdat", SLkhongdat);
        outState.putInt("SLdat", SLdat);
        outState.putInt("SLtong", SLtong);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            SLkhongdat = savedInstanceState.getInt("SLkhongdat");
            SLdat = savedInstanceState.getInt("SLdat");
            SLtong = savedInstanceState.getInt("SLtong");
        }
        updateCounterTextView();
    }

    private void updateCounterTextView() {
        SLtong = SLkhongdat + SLdat;
        tv_SLkhongdat.setText("SL không đạt: " + SLkhongdat);
        tv_SLdat.setText("SL đạt: " + SLdat);
        tv_Soluongtong.setText("Số lượng: " + SLtong);
    }
    private class CounterUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("COUNTER_UPDATED")) {
                int quantity = intent.getIntExtra("counter", 0);
                if(quantity == -1||quantity == 1){
                    SLdat = SLdat + 1;
                    List<String> defectCodes = Arrays.asList("0");
                    CallApiPostQAProduct(defectCodes);
                }
            }
        }
    }

}
