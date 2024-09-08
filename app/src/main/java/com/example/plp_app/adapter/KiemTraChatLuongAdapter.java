package com.example.plp_app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plp_app.KiemTraChatLuongDetail;
import com.example.plp_app.R;
import com.example.plp_app.api.ChuyenSanXuatResponse;

import java.util.ArrayList;
import java.util.List;

public class KiemTraChatLuongAdapter extends RecyclerView.Adapter<KiemTraChatLuongAdapter.ChuyenViewHolder>{

    private final List<ChuyenSanXuatResponse.Item> mlistChuyenSanXuat;

    public KiemTraChatLuongAdapter(List<ChuyenSanXuatResponse.Item> mlistChuyenSanXuat) {
        this.mlistChuyenSanXuat = mlistChuyenSanXuat;
    }

    @NonNull
    @Override
    public ChuyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chuyen_san_xuat,parent,false);
        return new ChuyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuyenViewHolder holder, int position) {
        ChuyenSanXuatResponse.Item item = mlistChuyenSanXuat.get(position);
        if(item==null){
            return;
        }
        holder.nameChuyen.setText(item.getName());
        holder.codeChuyen.setText("Mã: " + item.getCode());
//        holder.slDaDem.setText("Sl da dem:");
        int totalQuantity = 0;
        List<ChuyenSanXuatResponse.TaskProduct> taskProducts = item.getTaskProducts(); // Giả sử bạn có phương thức getTaskProducts()
        List<ChuyenSanXuatResponse.OrderProduct> orderProducts = new ArrayList<>(); // Giả sử bạn có phương thức getTaskProducts()

        if (taskProducts != null) {
            for (ChuyenSanXuatResponse.TaskProduct taskProduct : taskProducts) {
                totalQuantity += taskProduct.getTarget();
                orderProducts.add(taskProduct.getOrderProduct());// Giả sử target là số lượng mà bạn cần cộng lại
            }
        }

        holder.tongSl.setText("Tổng SL: " + totalQuantity);
        holder.bind(orderProducts);
    }

    @Override
    public int getItemCount() {
        if(mlistChuyenSanXuat != null){
            return mlistChuyenSanXuat.size();
        }
        return 0;
    }

    public class ChuyenViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameChuyen;
        private final TextView codeChuyen;
        private final TextView slDaDem;
        private final TextView tongSl;
        //        private final TextView nameChiTiet;
        private final TextView tongSLdialog;
        private RecyclerView recyclerViewOrderproduct;
        public ChuyenViewHolder(@NonNull View itemView) {
            super(itemView);

            nameChuyen = itemView.findViewById(R.id.nameChuyen);
            codeChuyen = itemView.findViewById(R.id.codeChuyen);
            slDaDem = itemView.findViewById(R.id.SLdaDem);
            tongSl = itemView.findViewById(R.id.targetChuyen);
//            nameChiTiet = itemView.findViewById(R.id.nameChuyenChiTiet);
            tongSLdialog = itemView.findViewById(R.id.tongSLdialog);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDialog(v.getContext());
//                }
//            });
        }
        public void bind(final List<ChuyenSanXuatResponse.OrderProduct> orderProducts) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(v.getContext(), orderProducts);
                }
            });
        }
        public void showDialog(Context context, List<ChuyenSanXuatResponse.OrderProduct> orderProducts) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_item_chuyensanxuat);
            recyclerViewOrderproduct = dialog.findViewById(R.id.recyclerTaskProduct);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerViewOrderproduct.setLayoutManager(linearLayoutManager);

            DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerViewOrderproduct.addItemDecoration(itemDecoration);
            // Thiết lập Adapter cho RecyclerView
            OrderProductAdapter adapter = new OrderProductAdapter(context,orderProducts);
            recyclerViewOrderproduct.setAdapter(adapter);

            Button closeButton = dialog.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
    public static class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.TaskProductViewHolder> {
        private final List<ChuyenSanXuatResponse.OrderProduct> orderProductList;
        private final Context context;
        // Constructor
        public OrderProductAdapter(Context context, List<ChuyenSanXuatResponse.OrderProduct> orderProductList) {
            this.context = context;
            this.orderProductList = orderProductList;
        }

        @NonNull
        @Override
        public TaskProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderproduct, parent, false);
            return new TaskProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskProductViewHolder holder, int position) {
            ChuyenSanXuatResponse.OrderProduct orderProduct = orderProductList.get(position);
            if (orderProduct != null) {
                // Gán dữ liệu vào các view
                holder.tv_SLQA.setText("SL QA: " + orderProduct.getQuantityQA());
                holder.tv_SLSP.setText("SL SP: " + orderProduct.getQuantityProduct());
                holder.tv_TongSL.setText("Tổng SL: " + orderProduct.getTotalQuantity());
                holder.bind(orderProduct);
            }
        }

        @Override
        public int getItemCount() {
            return orderProductList != null ? orderProductList.size() : 0;
        }

        // ViewHolder class
        public class TaskProductViewHolder extends RecyclerView.ViewHolder {
            private final TextView tv_SLQA;
            private final TextView tv_SLSP;
            private final TextView tv_TongSL;

            public TaskProductViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_SLQA = itemView.findViewById(R.id.tv_SLQA);
                tv_SLSP = itemView.findViewById(R.id.tv_SLSP);
                tv_TongSL = itemView.findViewById(R.id.tv_tongSLORprodcut);
            }

            public void bind(ChuyenSanXuatResponse.OrderProduct orderProduct){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, KiemTraChatLuongDetail.class);
                        String id = orderProduct.getId();
                        intent.putExtra("idOrderProduct",id);
                        context.startActivity(intent);

                    }
                });
            }
        }
    }

}
