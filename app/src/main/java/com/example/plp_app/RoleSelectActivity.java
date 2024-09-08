package com.example.plp_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectActivity extends AppCompatActivity {
    private String selectedRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        Spinner spinner = findViewById(R.id.roleSelect_spinner);
        Button loginButton = findViewById(R.id.login_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_items, R.layout.role_item_layout);
        adapter.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = parent.getItemAtPosition(position).toString();  // Cập nhật giá trị lựa chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không có hành động gì khi không có lựa chọn
            }
        });
        Intent intent = new Intent(RoleSelectActivity.this, LoginActivity.class);
        loginButton.setOnClickListener(view -> {
            switch (selectedRole) {
                case "Chọn vai trò":
                    break;
                case "Chuyền sản xuất":
                    intent.putExtra("Role", "Chuyền sản xuất");
                    startActivity(intent);
                    break;
                case "Kiểm tra chất lượng":
                    intent.putExtra("Role", "Kiểm tra chất lượng");
                    startActivity(intent);
                    break;
                case "Chuyền trưởng":
                    intent.putExtra("Role", "Chuyền trưởng");
                    startActivity(intent);
                    break;

                case "Tổ trưởng QC":
                    intent.putExtra("Role", "Tổ trưởng QC");
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        });
    }
}