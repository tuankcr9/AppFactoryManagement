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

import com.example.plp_app.ChuyenActivity;
import com.example.plp_app.R;
import com.example.plp_app.api.ChuyenSanXuatResponse;

import java.util.List;

public class ChuyenSanXuatAdapter extends RecyclerView.Adapter<ChuyenSanXuatAdapter.ChuyenViewHolder>{

    private final List<ChuyenSanXuatResponse.Item> mlistChuyenSanXuat;

    public ChuyenSanXuatAdapter(List<ChuyenSanXuatResponse.Item> mlistChuyenSanXuat) {
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

        if (taskProducts != null) {
            for (ChuyenSanXuatResponse.TaskProduct taskProduct : taskProducts) {
                totalQuantity += taskProduct.getTarget(); // Giả sử target là số lượng mà bạn cần cộng lại
            }
        }

        holder.tongSl.setText("Tổng SL: " + totalQuantity);
        holder.bind(taskProducts);
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
        private RecyclerView recyclerViewTaskproduct;
        public ChuyenViewHolder(@NonNull View itemView) {
            super(itemView);

            nameChuyen = itemView.findViewById(R.id.nameChuyen);
            codeChuyen = itemView.findViewById(R.id.codeChuyen);
            slDaDem = itemView.findViewById(R.id.SLdaDem);
            tongSl = itemView.findViewById(R.id.targetChuyen);
            tongSLdialog = itemView.findViewById(R.id.tongSLdialog);

        }
        public void bind(final List<ChuyenSanXuatResponse.TaskProduct> taskProducts) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(v.getContext(), taskProducts);
                }
            });
        }
        public void showDialog(Context context, List<ChuyenSanXuatResponse.TaskProduct> taskProducts) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_item_chuyensanxuat);
            recyclerViewTaskproduct = dialog.findViewById(R.id.recyclerTaskProduct);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerViewTaskproduct.setLayoutManager(linearLayoutManager);

            DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            recyclerViewTaskproduct.addItemDecoration(itemDecoration);

            TaskProductAdapter adapter = new TaskProductAdapter(context,taskProducts);
            recyclerViewTaskproduct.setAdapter(adapter);

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
    public static class TaskProductAdapter extends RecyclerView.Adapter<TaskProductAdapter.TaskProductViewHolder> {
        private final List<ChuyenSanXuatResponse.TaskProduct> taskProductList;
        private final Context context;
        // Constructor
        public TaskProductAdapter(Context context, List<ChuyenSanXuatResponse.TaskProduct> taskProductList) {
            this.context = context;
            this.taskProductList = taskProductList;
        }

        @NonNull
        @Override
        public TaskProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taskproduct, parent, false);
            return new TaskProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskProductViewHolder holder, int position) {
            ChuyenSanXuatResponse.TaskProduct taskProduct = taskProductList.get(position);
            if (taskProduct != null) {
                // Gán dữ liệu vào các view
                holder.taskNameTextView.setText("Tên: " + taskProduct.getName());
                holder.taskQuantityTextView.setText("Mục tiêu: " + taskProduct.getTarget()  );
                holder.bind(taskProduct);
            }
        }

        @Override
        public int getItemCount() {
            return taskProductList != null ? taskProductList.size() : 0;
        }

        // ViewHolder class
        public class TaskProductViewHolder extends RecyclerView.ViewHolder {
            private final TextView taskNameTextView;
            private final TextView taskQuantityTextView;

            public TaskProductViewHolder(@NonNull View itemView) {
                super(itemView);
                taskNameTextView = itemView.findViewById(R.id.nameChuyenChiTiet);
                taskQuantityTextView = itemView.findViewById(R.id.tongSLdialog);
                // Initialize other views here
            }

            public void bind(ChuyenSanXuatResponse.TaskProduct taskProduct){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChuyenActivity.class);
                        String name = taskProduct.getName();
                        String id = taskProduct.getId();
                        intent.putExtra("nameTaskProduct",name);
                        intent.putExtra("idTaskProduct",id);
                        context.startActivity(intent);

                    }
                });
            }
        }
    }

}
