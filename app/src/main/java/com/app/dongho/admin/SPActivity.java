package com.app.dongho.admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dongho.R;
import com.app.dongho.adapter.SPAdapter;
import com.app.dongho.dao.sanPhamDao;
import com.app.dongho.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SPActivity extends AppCompatActivity {

    private sanPhamDao spdao;
    private RecyclerView recyclerViewSP;
    private ArrayList<SanPham> list;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imgHinhAnh;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spactivity);

        recyclerViewSP = findViewById(R.id.recyclerViewSP1);
        FloatingActionButton floatAdd = findViewById(R.id.floatAdd1);

        spdao = new sanPhamDao(this);
        loadData();

        floatAdd.setOnClickListener(view -> showDiaLogThem());
    }

    private void loadData() {
        list = spdao.getDanhsachSanPham();
        recyclerViewSP.setLayoutManager(new LinearLayoutManager(this));
        SPAdapter adapter = new SPAdapter(this, list, spdao);
        recyclerViewSP.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showDiaLogThem() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_san_pham_1); // layout đã định nghĩa
        dialog.setCancelable(false);

        // Gán imgHinhAnh vào biến toàn cục
        imgHinhAnh = dialog.findViewById(R.id.imgHinhAnh);
        EditText edtTenSP = dialog.findViewById(R.id.etTenSP);
        EditText edtMaLoai = dialog.findViewById(R.id.etMaLoai);
        EditText edtGiaNhap = dialog.findViewById(R.id.etGiaNhap);
        EditText edtGiaBan = dialog.findViewById(R.id.etGiaBan);
        EditText edtSoLuong = dialog.findViewById(R.id.etSL);
        EditText edtMoTa = dialog.findViewById(R.id.etMoTa);


        // Thiết lập sự kiện chọn hình ảnh
        imgHinhAnh.setOnClickListener(v -> openGallery());

        // Nút Hủy
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Nút Lưu
        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String tenSP = edtTenSP.getText().toString().trim();
            String maLoaiStr = edtMaLoai.getText().toString().trim();
            String giaNhapStr = edtGiaNhap.getText().toString().trim();
            String giaBanStr = edtGiaBan.getText().toString().trim();
            String soLuongStr = edtSoLuong.getText().toString().trim();
            String moTa = edtMoTa.getText().toString().trim();

            // Kiểm tra dữ liệu bắt buộc
            if (maLoaiStr.isEmpty() || giaBanStr.isEmpty() || giaNhapStr.isEmpty() || soLuongStr.isEmpty()) {
                Toast.makeText(SPActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int maLoai = Integer.parseInt(maLoaiStr);
                double giaNhap = Double.parseDouble(giaNhapStr);
                double giaBan = Double.parseDouble(giaBanStr);
                int soLuong = Integer.parseInt(soLuongStr);

                // Kiểm tra mã loại hợp lệ
                if (!spdao.isMaLoaiValid(maLoai)) {
                    Toast.makeText(SPActivity.this, "Mã loại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem số lượng có hợp lệ không (số lượng phải > 0)
                if (soLuong <= 0) {
                    Toast.makeText(SPActivity.this, "Số lượng phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra nếu mô tả có số liệu không hợp lệ (số lượng hoặc ký tự không mong muốn)
                if (moTa.isEmpty()) {
                    Toast.makeText(SPActivity.this, "Mô tả không thể để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chuyển đổi hình ảnh thành byte array
                byte[] hinhAnhByteArray = imageToByteArray(imgHinhAnh);
                if (hinhAnhByteArray == null) {
                    Toast.makeText(SPActivity.this, "Vui lòng chọn hình ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm sản phẩm mới
                SanPham sanPham = new SanPham(maLoai, tenSP, giaNhap, giaBan, moTa, soLuong, hinhAnhByteArray);
                spdao.addSanPham(sanPham);

                loadData();
                Toast.makeText(SPActivity.this, "Sản phẩm đã được thêm!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            } catch (IllegalArgumentException e) {
                Toast.makeText(SPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imgHinhAnh.setImageURI(data.getData());
            Toast.makeText(this, "Ảnh đã được chọn!", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] imageToByteArray(ImageView imageView) {
        if (imageView.getDrawable() == null) {
            return null;
        }
        Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
