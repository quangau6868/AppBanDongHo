package com.app.dongho.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.dongho.database.DBHelper;
import com.app.dongho.model.User;

public class UserDao {
    private DBHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    //register
    public int Register(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Email = ?", new String[]{user.getEmail()});
        if (cursor.getCount() > 0) {
            cursor.close();
            sqLiteDatabase.close();
            return -1;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", user.getUsername());
        contentValues.put("Email", user.getEmail());
        contentValues.put("Password", user.getPassword());
        contentValues.put("Role", 1);

        long check = sqLiteDatabase.insert("USER", null, contentValues);
        sqLiteDatabase.close();
        return (check != 1) ? 1 : 0;
    }

    // login
    public User CheckLogin(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Email = ? AND Password = ?", new String[]{user.getEmail(), user.getPassword()});

        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
            int role = cursor.getInt(cursor.getColumnIndexOrThrow("Role"));
            cursor.close();
            return new User(username, user.getEmail(), user.getPassword(), role);
        }
        cursor.close();
        return null;
    }

    // ham checkEmail
    public int checkEmail(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Email = ? ", new String[]{user.getEmail()});
        int result = 0;
        if (cursor.getCount() > 0) {
            result = -1;
        }
        cursor.close();
        sqLiteDatabase.close();
        return result;
    }

    //update Password
    public boolean updatePassword(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        // Kiểm tra nếu email có tồn tại trong cơ sở dữ liệu không
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE Email = ?", new String[]{user.getEmail()});
        if (cursor.getCount() <= 0) {
            Log.d("UserDao", "No user found with the email: " + user.getEmail());
            cursor.close();
            sqLiteDatabase.close();
            return false;
        }

        // Log thông tin người dùng tìm thấy
        cursor.moveToFirst();
        String emailFromDb = cursor.getString(cursor.getColumnIndex("Email"));
        String passwordFromDb = cursor.getString(cursor.getColumnIndex("Password"));
        Log.d("UserDao", "Found user - Email: " + emailFromDb + ", Password: " + passwordFromDb);
        cursor.close();

        // Cập nhật mật khẩu nếu email tồn tại
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", user.getPassword());

        // Kiểm tra số dòng bị ảnh hưởng
        int rowsAffected = sqLiteDatabase.update("USER", contentValues, "Email = ?", new String[]{user.getEmail()});
        Log.d("UserDao", "Rows affected: " + rowsAffected);

        sqLiteDatabase.close();
        return rowsAffected > 0;
    }
}


