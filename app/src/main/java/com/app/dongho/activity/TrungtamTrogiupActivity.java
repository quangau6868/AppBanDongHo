package com.app.dongho.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.dongho.R;
import com.app.dongho.adapter.FAQAdapter;
import com.app.dongho.model.FAQ;

import java.util.ArrayList;
import java.util.List;

public class TrungtamTrogiupActivity extends AppCompatActivity {

    private RecyclerView faqRecyclerView;
    private FAQAdapter faqAdapter;
    private List<FAQ> faqList;
    private List<FAQ> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trungtam_trogiup);

        // Khởi tạo RecyclerView
        faqRecyclerView = findViewById(R.id.faqRecyclerView);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dữ liệu mẫu
        faqList = new ArrayList<>();
        // Dữ liệu mẫu với chủ đề rõ ràng
        faqList = new ArrayList<>();
        faqList.add(new FAQ("Làm thế nào để bảo quản đồng hồ đúng cách?", "Bảo quản"));
        faqList.add(new FAQ("Tôi muốn đổi hoặc trả sản phẩm, cần làm gì?", "Chính sách"));
        faqList.add(new FAQ("Hướng dẫn sử dụng chức năng bấm giờ trên đồng hồ thể thao", "Hướng dẫn"));
        faqList.add(new FAQ("Vì sao đồng hồ của tôi chạy chậm hoặc nhanh hơn bình thường?", "Bảo trì"));
        faqList.add(new FAQ("Chế độ bảo hành của AppWatch là gì?", "Chính sách"));
        faqList.add(new FAQ("Hướng dẫn mua hàng trên ứng dụng AppWatch", "Hướng dẫn"));
        faqList.add(new FAQ("Phương thức thanh toán nào được chấp nhận?", "Thanh toán"));
        faqList.add(new FAQ("Làm thế nào để kiểm tra đồng hồ chính hãng?", "Bảo quản"));

        // Gán adapter cho RecyclerView
        faqAdapter = new FAQAdapter(this, faqList);
        faqRecyclerView.setAdapter(faqAdapter);

        // Khởi tạo các nút danh mục
        Button btnGoiY = findViewById(R.id.btnGoiy);
        Button btnDatDoAn = findViewById(R.id.btnDatDoAn);
        Button btnDatGiaoHang = findViewById(R.id.btnDatGiaoHang);
        Button btnKhuyenMai = findViewById(R.id.btnKhuyenMai);

        // Sự kiện khi nhấn nút "Bảo quản"
        btnGoiY.setOnClickListener(view -> filterFAQ("Bảo quản"));

         // Sự kiện khi nhấn nút "Chính sách"
        btnDatDoAn.setOnClickListener(view -> filterFAQ("Chính sách"));

         // Sự kiện khi nhấn nút "Hướng dẫn"
        btnDatGiaoHang.setOnClickListener(view -> filterFAQ("Hướng dẫn"));

        // Sự kiện khi nhấn nút "Thanh toán"
        btnKhuyenMai.setOnClickListener(view -> filterFAQ("Thanh toán"));

    }

    // Lọc câu hỏi theo chủ đề
    private void filterFAQ(String category) {
        filteredList = new ArrayList<>();
        for (FAQ faq : faqList) {
            if (faq.getCategory().equals(category)) {
                filteredList.add(faq);
            }
        }
        faqAdapter.updateFAQList(filteredList);
    }
}
