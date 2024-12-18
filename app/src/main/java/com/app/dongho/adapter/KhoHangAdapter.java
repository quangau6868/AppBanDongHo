package com.app.dongho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.dongho.R;
import com.app.dongho.model.SanPham;
import java.util.List;

public class KhoHangAdapter extends RecyclerView.Adapter<KhoHangAdapter.KhoHangViewHolder> {
    private Context context;
    private List<SanPham> sanPhamList;

    public KhoHangAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public KhoHangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kho_hang, parent, false);
        return new KhoHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KhoHangViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);

        // Hiển thị thông tin sản phẩm
        holder.maSPTextView.setText("Mã sản phẩm: " + sanPham.getMaSP());
        holder.tenSPTextView.setText("Tên sản phẩm: " + sanPham.getTenSP());
        holder.soLuongTextView.setText("Số lượng: " + sanPham.getSoLuong());
        holder.soLuongDaBanTextView.setText("Số lượng đã bán: " + sanPham.getSoLuongDaBan());

        // Tính số lượng còn lại = Số lượng - Số lượng đã bán
        int soLuongConLai = sanPham.getSoLuong() - sanPham.getSoLuongDaBan();
        holder.soLuongConLaiTextView.setText("SL còn lại: " + soLuongConLai);
    }



    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class KhoHangViewHolder extends RecyclerView.ViewHolder {
        TextView maSPTextView, tenSPTextView, soLuongTextView, soLuongDaBanTextView,soLuongConLaiTextView;

        public KhoHangViewHolder(View itemView) {
            super(itemView);
            maSPTextView = itemView.findViewById(R.id.maSPTextView);
            tenSPTextView = itemView.findViewById(R.id.tenSPTextView);
            soLuongTextView = itemView.findViewById(R.id.soLuongTextView);
            soLuongDaBanTextView = itemView.findViewById(R.id.soLuongDaBanTextView);
            soLuongConLaiTextView = itemView.findViewById(R.id.soLuongConLaiTextView);
        }
    }
}
