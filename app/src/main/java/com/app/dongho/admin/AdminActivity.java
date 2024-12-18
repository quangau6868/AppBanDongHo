package com.app.dongho.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.dongho.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        LinearLayout linerloaisp = findViewById(R.id.linearloaisp);
        LinearLayout linersp = findViewById(R.id.linearsp);
        LinearLayout linertop10 = findViewById(R.id.lineartop10);
        LinearLayout linerdanhgia = findViewById(R.id.lineardanhgia);
        LinearLayout linearkhohang = findViewById(R.id.linearkhohang);
        LinearLayout lineardoanhthu = findViewById(R.id.lineardoanhthu);


        linerloaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, LoaiSPActivity.class));
            }
        });

        lineardoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, TKDoanhThuActivity.class));
            }
        });

        linearkhohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, KhoHangActivity.class));
            }
        });

        linersp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, SPActivity.class));
            }
        });

        linertop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminPayActivity.class));
            }
        });

        linerdanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminMessageActivity.class));
            }
        });

    }
}