package com.app.dongho.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dongho.R;
import com.app.dongho.model.SanPham;
import com.app.dongho.dao.sanPhamDao;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SPAdapter extends RecyclerView.Adapter<SPAdapter.SanPhamViewHolder> {
    private Context context;
    private List<SanPham> sanPhamList;
    private sanPhamDao sanPhamDao;

    public SPAdapter(Context context, List<SanPham> sanPhamList, sanPhamDao sanPhamDao) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.sanPhamDao = sanPhamDao;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);

        // Gán dữ liệu vào các TextView
        holder.txtMaSP.setText("Mã SP: " + sanPham.getMaSP());
        holder.txtMaLoai.setText("Mã Loại: " + sanPham.getMaLoai());
        holder.txtTenSP.setText("Tên sản phẩm: " + sanPham.getTenSP());
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.txtGiaNhap.setText("Giá nhập: " + numberFormat.format(sanPham.getGiaNhap()) + " VND");
        holder.txtGiaBan.setText("Giá bán: " + numberFormat.format(sanPham.getGiaBan()) + " VND");
        holder.txtSoLuong.setText("Số lượng: " + sanPham.getSoLuong());
        holder.txtMoTa.setText("Mô tả: " + sanPham.getMoTa());

        // Xử lý hình ảnh
        byte[] hinhAnhBytes = sanPham.getHinhAnh();
        if (hinhAnhBytes != null && hinhAnhBytes.length > 0) {
            Bitmap hinhAnhBitmap = BitmapFactory.decodeByteArray(hinhAnhBytes, 0, hinhAnhBytes.length);
            holder.imgHinhAnh.setImageBitmap(hinhAnhBitmap);
        } else {
            holder.imgHinhAnh.setImageResource(R.drawable.icon_forder);
        }

        // Xử lý sự kiện cho nút Edit
        holder.ivEdit.setOnClickListener(v -> {
            showEditDialog(sanPham, position);
        });

        // Xử lý sự kiện cho nút Delete
        holder.ivDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa sản phẩm")
                    .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        boolean isDeleted = sanPhamDao.deleteSanPham(sanPham.getMaSP());
                        if (isDeleted) {
                            sanPhamList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    private void showEditDialog(SanPham sanPham, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_san_pham, null);
        builder.setView(dialogView);

        // Khai báo các EditText
        EditText edtMaLoai = dialogView.findViewById(R.id.etMaLoai);
        EditText edtTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText edtGiaNhap = dialogView.findViewById(R.id.etGiaNhap);
        EditText edtGiaBan = dialogView.findViewById(R.id.etGiaBan);
        EditText edtSoLuong = dialogView.findViewById(R.id.etSL);
        EditText edtMoTa = dialogView.findViewById(R.id.etMoTa);

        // Hiển thị thông tin sản phẩm hiện tại vào các EditText
        edtMaLoai.setText(String.valueOf(sanPham.getMaLoai()));
        edtTenSP.setText(sanPham.getTenSP());
        edtGiaNhap.setText(String.valueOf(sanPham.getGiaNhap()));
        edtGiaBan.setText(String.valueOf(sanPham.getGiaBan()));
        edtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
        edtMoTa.setText(sanPham.getMoTa());

        builder.setTitle("Chỉnh sửa sản phẩm")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    // Cập nhật dữ liệu sản phẩm từ EditText
                    try {
                        sanPham.setMaLoai(Integer.parseInt(edtMaLoai.getText().toString()));
                        sanPham.setTenSP(edtTenSP.getText().toString());
                        sanPham.setGiaNhap(Double.parseDouble(edtGiaNhap.getText().toString()));
                        sanPham.setGiaBan(Double.parseDouble(edtGiaBan.getText().toString()));
                        sanPham.setSoLuong(Integer.parseInt(edtSoLuong.getText().toString()));
                        sanPham.setMoTa(edtMoTa.getText().toString());

                        // Cập nhật sản phẩm trong cơ sở dữ liệu
                        boolean isUpdated = sanPhamDao.updateSanPham(sanPham);
                        if (isUpdated) {
                            notifyItemChanged(position);
                            Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Dữ liệu nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class SanPhamViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSP, txtMaLoai, txtTenSP, txtGiaBan, txtGiaNhap, txtSoLuong, txtMoTa;
        ImageView imgHinhAnh, ivEdit, ivDelete;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSP = itemView.findViewById(R.id.txtMaSP);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaNhap = itemView.findViewById(R.id.txtGiaNhap);
            txtGiaBan = itemView.findViewById(R.id.txtGiaBan);
            txtSoLuong = itemView.findViewById(R.id.SL);
            txtMoTa = itemView.findViewById(R.id.txtMoTa);
            imgHinhAnh = itemView.findViewById(R.id.imgHinhAnh);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

}


