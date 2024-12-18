package com.app.dongho.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.dongho.database.DBHelper;
import com.app.dongho.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class sanPhamDao {
    private DBHelper dbHelper;

    public sanPhamDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Lấy danh sách sản phẩm kèm thông tin loại sản phẩm
    public ArrayList<SanPham> getDanhsachSanPham() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<SanPham> list = new ArrayList<>();

        String query = "SELECT SanPham.*, Loai_SP.TenLoai FROM SanPham " +
                "INNER JOIN Loai_SP ON SanPham.MaLoai = Loai_SP.MaLoai";
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int maSP = cursor.getInt(0);
                    int maLoai = cursor.getInt(1);
                    String tenSP = cursor.getString(2);
                    double giaNhap = cursor.getDouble(3);
                    double giaBan = cursor.getDouble(4);
                    int soLuong = cursor.getInt(5);
                    int soLuongDaBan = cursor.getInt(6);
                    String moTa = cursor.getString(7);
                    byte[] hinhAnh = cursor.getBlob(8);

                    SanPham sanPham = new SanPham(maSP, maLoai, tenSP, giaNhap, giaBan, moTa, soLuong,soLuongDaBan, hinhAnh);
                    list.add(sanPham);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("sanPhamDao", "Lỗi khi truy vấn danh sách sản phẩm: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
        return list;
    }

    // Kiểm tra mã loại hợp lệ
    public boolean isMaLoaiValid(int maLoai) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) FROM Loai_SP WHERE MaLoai = ?", new String[]{String.valueOf(maLoai)});
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) > 0;
            }
        } catch (Exception e) {
            Log.e("sanPhamDao", "Lỗi khi kiểm tra mã loại: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return false;
    }

    // Thêm sản phẩm mới
    public boolean addSanPham(SanPham sanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra mã loại
        if (!isMaLoaiValid(sanPham.getMaLoai())) {
            Log.e("SanPhamDao", "Mã loại không hợp lệ: " + sanPham.getMaLoai());
            return false; // Dừng tại đây
        } else {
            ContentValues values = new ContentValues();
            values.put("MaLoai", sanPham.getMaLoai());
            values.put("TenSP", sanPham.getTenSP());
            values.put("GiaNhap", sanPham.getGiaNhap());
            values.put("GiaBan", sanPham.getGiaBan());
            values.put("SoLuong", sanPham.getSoLuong());
            values.put("SoLuongDaBan", sanPham.getSoLuongDaBan());
            values.put("MoTa", sanPham.getMoTa());
            values.put("HinhAnh", sanPham.getHinhAnh());

            long result = db.insert("SanPham", null, values);
            return result != -1; // Trả về true nếu thêm thành công
        }
    }

    // Xóa sản phẩm
    public boolean deleteSanPham(int maSP) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("SanPham", "MaSP = ?", new String[]{String.valueOf(maSP)});
        db.close();
        return rowsDeleted > 0;
    }

    // Cập nhật sản phẩm
    public boolean updateSanPham(SanPham sanPham) {
        if (!isMaLoaiValid(sanPham.getMaLoai())) {
            Log.e("sanPhamDao", "Mã loại không hợp lệ: " + sanPham.getMaLoai());
            return false;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaLoai", sanPham.getMaLoai());
        values.put("TenSP", sanPham.getTenSP());
        values.put("GiaNhap", sanPham.getGiaNhap());
        values.put("GiaBan", sanPham.getGiaBan());
        values.put("SoLuong", sanPham.getSoLuong());
        values.put("SoLuongDaBan", sanPham.getSoLuongDaBan());
        values.put("MoTa", sanPham.getMoTa());
        values.put("HinhAnh", sanPham.getHinhAnh());

        int rowsAffected = db.update("SanPham", values, "MaSP = ?", new String[]{String.valueOf(sanPham.getMaSP())});
        db.close();
        return rowsAffected > 0;
    }

    // Lấy sản phẩm theo ID
    public SanPham getSanPhamById(int productId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SanPham sanPham = null;

        String query = "SELECT SanPham.*, Loai_SP.TenLoai FROM SanPham " +
                "INNER JOIN Loai_SP ON SanPham.MaLoai = Loai_SP.MaLoai WHERE SanPham.MaSP = ?";
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});

            if (cursor != null && cursor.moveToFirst()) {
                int maSP = cursor.getInt(0);
                int maLoai = cursor.getInt(1);
                String tenSP = cursor.getString(2);
                double giaNhap = cursor.getDouble(3);
                double giaBan = cursor.getDouble(4);
                int soLuong = cursor.getInt(5);
                String moTa = cursor.getString(7);
                byte[] hinhAnh = cursor.getBlob(8);

                sanPham = new SanPham(maSP, maLoai, tenSP, giaNhap, giaBan, moTa, soLuong, hinhAnh);
            }
        } catch (Exception e) {
            Log.e("SanPhamDao", "Lỗi khi lấy sản phẩm theo ID: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
        return sanPham;
    }

    // Lấy danh sách sản phẩm theo mã loại
    public ArrayList<SanPham> getSanPhamListByLoai(int maLoai) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<SanPham> list = new ArrayList<>();

        String query = "SELECT SanPham.*, Loai_SP.TenLoai FROM SanPham " +
                "INNER JOIN Loai_SP ON SanPham.MaLoai = Loai_SP.MaLoai WHERE SanPham.MaLoai = ?";
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query, new String[]{String.valueOf(maLoai)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int maSP = cursor.getInt(0);
                    int maLoaiSP = cursor.getInt(1);
                    String tenSP = cursor.getString(2);
                    double giaNhap = cursor.getDouble(3);
                    double giaBan = cursor.getDouble(4);
                    String moTa = cursor.getString(5);
                    int soLuong = cursor.getInt(7);
                    byte[] hinhAnh = cursor.getBlob(8);

                    SanPham sanPham = new SanPham(maSP, maLoaiSP, tenSP, giaNhap, giaBan, moTa, soLuong, hinhAnh);
                    list.add(sanPham);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("SanPhamDao", "Lỗi khi lấy danh sách sản phẩm theo loại: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
        return list;
    }

    // Tìm kiếm sản phẩm theo tên
    public List<SanPham> searchProducts(String query) {
        List<SanPham> results = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thay 'name' bằng 'TenSP' vì 'TenSP' là tên cột trong bảng SanPham
        String selection = "TenSP LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%"};

        Cursor cursor = db.query("SanPham", null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maSP = cursor.getInt(0);
                int maLoai = cursor.getInt(1);
                String tenSP = cursor.getString(2);
                double giaNhap = cursor.getDouble(3);
                double giaBan = cursor.getDouble(4);
                String moTa = cursor.getString(5);
                int soLuong = cursor.getInt(7);
                byte[] hinhAnh = cursor.getBlob(8);

                SanPham sanPham = new SanPham(maSP, maLoai, tenSP, giaNhap, giaBan, moTa, soLuong, hinhAnh);
                results.add(sanPham); // Thêm vào danh sách kết quả
            } while (cursor.moveToNext());
            cursor.close(); // Đảm bảo đóng con trỏ sau khi truy vấn xong
        }
        return results;
    }

}

