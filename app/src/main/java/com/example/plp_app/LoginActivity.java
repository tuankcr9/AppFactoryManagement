package com.example.plp_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.SharedPreferences;
import com.example.plp_app.api.ApiClient;
import com.example.plp_app.api.ApiService;
import com.example.plp_app.api.LoginResponse;
import com.example.plp_app.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextTaiKhoan;
    private EditText editTextMatKhau;
    private Button buttonDangnhap;
    private String taikhoan;
    private String matkhau;
    private String role;
    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "accessToken";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTaiKhoan = findViewById(R.id.editTextTaiKhoan);
        editTextMatKhau = findViewById(R.id.editTextPassword);
        Intent intent = getIntent();
        role = intent.getStringExtra("Role");
        buttonDangnhap = findViewById(R.id.buttonDangnhap);

        buttonDangnhap.setOnClickListener(v -> login());

    }

    private void login(){
        taikhoan = editTextTaiKhoan.getText().toString().trim();
        matkhau = editTextMatKhau.getText().toString().trim();
        User user = new User(taikhoan, matkhau);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<LoginResponse> call = apiService.login(user);
        if(taikhoan.isEmpty()){
            editTextTaiKhoan.setError("Bạn chưa nhập tài khoản");
            editTextTaiKhoan.requestFocus();
        }else if(matkhau.isEmpty()) {
            editTextMatKhau.setError("Bạn chưa nhập mật khẩu");
            editTextMatKhau.requestFocus();
        }else {
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null) {
                            String accessToken = loginResponse.getData().getTokenResponse().getAccessToken();
                            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(TOKEN_KEY, accessToken);
                            editor.apply();
                            GoToNextActivity();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void GoToNextActivity(){
        if(role.equals("Chuyền sản xuất")) {
            Intent intent = new Intent(LoginActivity.this, ChuyenSanXuatActivity.class);
            startActivity(intent);
        }
        if(role.equals("Kiểm tra chất lượng")) {
            Intent intent = new Intent(LoginActivity.this, KiemTraChatLuongActivity.class);
            startActivity(intent);
        }
        if(role.equals("Chuyền trưởng")) {
            Intent intent = new Intent(LoginActivity.this, ChartActivity.class);
            startActivity(intent);
        }
        if(role.equals("Tổ trưởng QC")) {
            Intent intent = new Intent(LoginActivity.this, ChartActivity.class);
            startActivity(intent);
        }
    }
}
