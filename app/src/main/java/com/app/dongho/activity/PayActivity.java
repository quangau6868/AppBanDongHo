package com.app.dongho.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dongho.R;
import com.app.dongho.adapter.PayAdapter;
import com.app.dongho.dao.ThanhToanDao;
import com.app.dongho.model.ThanhToan;

import java.util.List;

public class PayActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPay;
    private PayAdapter payAdapter;
    private ThanhToanDao thanhToanDao;
    private TextView emptyCartMessage, thanhToanTitle;
    private boolean isAdmin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // Trong PayActivity, bạn sử dụng giá trị `isAdmin` từ SharedPreferences hoặc logic xác thực của mình
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Initializing views
        recyclerViewPay = findViewById(R.id.recyclerViewPay);
        emptyCartMessage = findViewById(R.id.emptyCartMessage1);
        thanhToanTitle = findViewById(R.id.Thanh_toán);

        thanhToanDao = new ThanhToanDao(this);

        // Set up RecyclerView
        recyclerViewPay.setLayoutManager(new LinearLayoutManager(this));

        // Get list of ThanhToan
        List<ThanhToan> thanhToanList = thanhToanDao.getAllThanhToan();

        // Check if there are any ThanhToan
        if (thanhToanList.isEmpty()) {
            emptyCartMessage.setVisibility(View.VISIBLE);
            thanhToanTitle.setVisibility(View.GONE);
            recyclerViewPay.setVisibility(View.GONE);
        } else {
            emptyCartMessage.setVisibility(View.GONE);
            thanhToanTitle.setVisibility(View.VISIBLE);
            recyclerViewPay.setVisibility(View.VISIBLE);

            // Setting up adapter for RecyclerView, truyền thêm isAdmin vào Adapter
            payAdapter = new PayAdapter(this, thanhToanList, isAdmin);
            recyclerViewPay.setAdapter(payAdapter);
        }
    }

    // Cập nhật trạng thái giỏ hàng
    public void updateCartStatus() {
        List<ThanhToan> thanhToanList = thanhToanDao.getAllThanhToan();
        if (thanhToanList.isEmpty()) {
            emptyCartMessage.setVisibility(View.VISIBLE);
            thanhToanTitle.setVisibility(View.GONE);
            recyclerViewPay.setVisibility(View.GONE);
        }
    }
}
