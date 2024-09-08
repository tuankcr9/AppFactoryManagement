package com.example.plp_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.plp_app.adapter.ChuyenSanXuatAdapter;
import com.example.plp_app.api.ApiClient;
import com.example.plp_app.api.ApiService;
import com.example.plp_app.api.ChuyenSanXuatResponse;
import com.example.plp_app.api.TangSanLuongResponse;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ChuyenActivity extends AppCompatActivity {
    private TextView tv_SLuong;
    private TextView tv_SLTong;
    private Button btn_datlai;
    private TextView tv_namProduct;
    private ImageButton bt_tang;
    private ImageButton bt_giam;
    private CounterUpdateReceiver counterUpdateReceiver;
    private AudioManager audioManager;
    private String idtaskProduct;
    private int counter;
    private int quantity;
    private ImageButton btn_back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_detail);

        String nametaskProduct = getIntent().getStringExtra("nameTaskProduct");
        String[] listCongViec = {nametaskProduct};
        idtaskProduct = getIntent().getStringExtra("idTaskProduct");
        counter = 0;
        quantity = 0;
        tv_SLuong = findViewById(R.id.tv_slChuyen);
        bt_tang = findViewById(R.id.btn_plus);
        bt_giam = findViewById(R.id.btn_minus);
        tv_namProduct = findViewById(R.id.tv_productChuyen);
        tv_namProduct.setText(nametaskProduct);
        btn_back = findViewById(R.id.back_buttonChuyen);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_SLTong = findViewById(R.id.tv_sltong);

        btn_datlai = findViewById(R.id.btn_datlai);
        btn_datlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                updateCounterTextView(counter);
            }
        });

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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
        callApiPutTangSanLuong();
        bt_tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
                quantity = 1;
            }
        });

        bt_giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
                quantity = -1;
            }
        });
        Spinner spinner = findViewById(R.id.spinner_choncongviec);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_layout, listCongViec);
        spinner.setAdapter(adapter);
    }
    private void callApiPutTangSanLuong(){
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        retrofit2.Call<TangSanLuongResponse> call = apiService.tangSanLuong(idtaskProduct,quantity);
        call.enqueue(new Callback<TangSanLuongResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TangSanLuongResponse> call, Response<TangSanLuongResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int updatedTotalQuantity = response.body().getData().getTotalQuantityProduct();
                    tv_SLTong.setText("SL tổng: " + String.valueOf(updatedTotalQuantity));
                    Log.d("API", "Tăng sản lượng thành công!");
                } else {
                    Log.e("API", "Thất bại: " + response.message());
                }

            }

            @Override
            public void onFailure(retrofit2.Call<TangSanLuongResponse> call, Throwable t) {
                Toast.makeText(ChuyenActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(counterUpdateReceiver);
    }

    private void updateCounterTextView(int value) {
        tv_SLuong.setText("Số lượng: " + String.valueOf(value));
    }
    private class CounterUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("COUNTER_UPDATED")) {
                quantity = intent.getIntExtra("counter", 0);
                if(counter == 0 && quantity == -1){
                    return;
                }
                counter = counter + quantity;
                callApiPutTangSanLuong();
                updateCounterTextView(counter);
            }
        }
    }
}