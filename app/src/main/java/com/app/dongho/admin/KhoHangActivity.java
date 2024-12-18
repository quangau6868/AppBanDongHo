package com.app.dongho.admin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.dongho.R;
import com.app.dongho.adapter.KhoHangAdapter;
import com.app.dongho.dao.sanPhamDao;
import com.app.dongho.model.SanPham;
import java.util.List;

public class KhoHangActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KhoHangAdapter khoHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho_hang);

        recyclerView = findViewById(R.id.recyclerViewKhoHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sanPhamDao sanPhamDao = new sanPhamDao(this);
        List<SanPham> sanPhamList = sanPhamDao.getDanhsachSanPham(); // Lấy danh sách sản phẩm

        khoHangAdapter = new KhoHangAdapter(this, sanPhamList);
        recyclerView.setAdapter(khoHangAdapter);
    }
}
