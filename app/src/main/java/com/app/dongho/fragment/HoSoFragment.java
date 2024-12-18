package com.app.dongho.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.dongho.R;
import com.app.dongho.activity.ChinhSachActivity;
import com.app.dongho.activity.ForgotPasswordActivity;
import com.app.dongho.activity.LoginActivity;
import com.app.dongho.activity.PayActivity;
import com.app.dongho.activity.TroGiupActivity;

public class HoSoFragment extends Fragment {

    private View mView;
    private TextView text_user, text_gmail;
    private Button hoadon, logout, button_update_password, button_danh_gia,
            button_chinh_sach, button_facebook, button_chia_se, button_ve_toi;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_ho_so, container, false);
        // Khởi tạo các thành phần
        text_user = mView.findViewById(R.id.text_user);
        text_gmail = mView.findViewById(R.id.text_email);
        hoadon = mView.findViewById(R.id.button_hoa_don);
        logout = mView.findViewById(R.id.button_logout);
        button_danh_gia = mView.findViewById(R.id.button_danh_gia);
        button_update_password = mView.findViewById(R.id.button_update_password);
        button_chinh_sach = mView.findViewById(R.id.button_chinh_sach);
        button_facebook = mView.findViewById(R.id.button_facebook);
        button_chia_se = mView.findViewById(R.id.button_chia_se);
        button_ve_toi = mView.findViewById(R.id.button_ve_toi);

        // Hiển thị username và email từ Intent
        if (getActivity() != null) {
            String username = getActivity().getIntent().getStringExtra("username");
            String email = getActivity().getIntent().getStringExtra("email");

            if (username != null) {
                text_user.setText(username);
            }
            if (email != null) {
                text_gmail.setText(email);
            }
        }

        button_danh_gia.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), TroGiupActivity.class);
            startActivity(intent);
        });

        button_chinh_sach.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ChinhSachActivity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện khi nhấn vào nút "đổi mật khẩu"
        button_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        button_chia_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "App Đồng Hồ");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Tải ứng dụng Đồng  tại: https://example.com");
                startActivity(Intent.createChooser(shareIntent, "Chia sẻ ứng dụng qua:"));
            }
        });

        // Xử lý sự kiện khi nhấn vào nút "Hóa đơn"
        hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PayActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn vào nút "Đăng xuất"
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý đăng xuất (có thể xóa thông tin đăng nhập và chuyển về màn hình đăng nhập)
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        button_facebook.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=61570306146187"));
            startActivity(browserIntent);
        });

        button_ve_toi.setOnClickListener(view -> {
            showAboutAppDialog();
        });

        return mView;
    }

    private void showAboutAppDialog() {
        // Tạo dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_about_app, null);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();

        // Ánh xạ các view trong layout dialog
        TextView title = dialogView.findViewById(R.id.dialogTitle);
        TextView introduction = dialogView.findViewById(R.id.dialogIntroduction);
        TextView ownerInfo = dialogView.findViewById(R.id.dialogOwnerInfo);
        TextView supportEmail = dialogView.findViewById(R.id.dialogSupportEmail);
        Button btnUnderstand = dialogView.findViewById(R.id.dialogButtonUnderstand);

        // Thiết lập nội dung
        title.setText("Ứng dụng đồng hồ");
        introduction.setText("AppWatch là ứng dụng cung cấp giải pháp mua sắm và quản lý đồng hồ chất lượng, giúp người dùng dễ dàng tìm kiếm sản phẩm phù hợp và nâng tầm phong cách.");
        ownerInfo.setText("Chủ sở hữu: Công ty TNHH AppWatch Việt Nam");
        supportEmail.setText("Email hỗ trợ: support@appwatch.vn");

        // Xử lý khi nhấn nút "Đã hiểu"
        btnUnderstand.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị dialog
        dialog.setCancelable(false); // Không cho phép thoát dialog khi nhấn ra ngoài
        dialog.show();
    }

}