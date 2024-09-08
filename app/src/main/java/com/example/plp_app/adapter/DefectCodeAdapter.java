package com.example.plp_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plp_app.R;

import java.util.ArrayList;
import java.util.List;

public class DefectCodeAdapter extends RecyclerView.Adapter<DefectCodeAdapter.ViewHolder> {

    private List<String> data;
    private List<String> selectedDefects;
    private OnDefectSelectedListener listener;
    private LayoutInflater inflater;

    public interface OnDefectSelectedListener {
        void onDefectSelected(List<String> selectedDefects);
    }

    public DefectCodeAdapter(Context context, List<String> data, OnDefectSelectedListener listener) {
        this.data = data;
        this.selectedDefects = new ArrayList<>();
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_defect_code, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String defectCode = data.get(position);
        holder.button.setText(defectCode);

        updateButtonColor(holder.button, selectedDefects.contains(defectCode));

        holder.button.setOnClickListener(v -> {
            if (selectedDefects.contains(defectCode)) {
                selectedDefects.remove(defectCode);
            } else {
                selectedDefects.add(defectCode);
            }
            updateButtonColor(holder.button, selectedDefects.contains(defectCode));
            listener.onDefectSelected(selectedDefects);
        });
    }

    private void updateButtonColor(Button button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundResource(R.drawable.selected_defect_item_background);
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundResource(R.drawable.defect_item_background);
            button.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        Button buttonXoaLoi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.defectItembtn);
            buttonXoaLoi = itemView.findViewById(R.id.deleteButton);
        }

    }
}