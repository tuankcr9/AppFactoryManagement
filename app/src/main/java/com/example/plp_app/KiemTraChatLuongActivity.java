package com.example.plp_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plp_app.adapter.ChuyenSanXuatAdapter;
import com.example.plp_app.adapter.KiemTraChatLuongAdapter;
import com.example.plp_app.api.ApiClient;
import com.example.plp_app.api.ApiService;
import com.example.plp_app.api.ChuyenSanXuatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KiemTraChatLuongActivity extends AppCompatActivity {
    private ImageButton imageButtonBack;
    private RecyclerView recyclerViewChuyen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiem_tra_chat_luong);
        imageButtonBack = findViewById(R.id.imageButtonBackKTCL);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
        recyclerViewChuyen = findViewById(R.id.recyclerKiemtrachatluong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewChuyen.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewChuyen.addItemDecoration(itemDecoration);

        callApiGetChuyenSanXuat();
    }
    private void callApiGetChuyenSanXuat(){
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<ChuyenSanXuatResponse> call = apiService.getListChuyenSanXuat();
        call.enqueue(new Callback<ChuyenSanXuatResponse>() {
            @Override
            public void onResponse(Call<ChuyenSanXuatResponse> call, Response<ChuyenSanXuatResponse> response) {
                ChuyenSanXuatResponse chuyenSanXuatResponse = response.body();
                List<ChuyenSanXuatResponse.Item> items = chuyenSanXuatResponse.getData().getItems();
                items.removeIf(item -> item.getTaskProducts().isEmpty());
                KiemTraChatLuongAdapter kiemTraChatLuongAdapter = new KiemTraChatLuongAdapter(items);
                recyclerViewChuyen.setAdapter(kiemTraChatLuongAdapter);
            }

            @Override
            public void onFailure(Call<ChuyenSanXuatResponse> call, Throwable t) {
                Toast.makeText(KiemTraChatLuongActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KiemTraChatLuongActivity.this);
        builder.setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?")
                .setCancelable(false)
                .setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(KiemTraChatLuongActivity.this, RoleSelectActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        Button logoutButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        logoutButton.setTextColor(Color.RED);
    }
}
