package com.example.resapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    public interface OnMenuActionListener {
        void onEdit(MenuItem item);
        void onDelete(MenuItem item);
    }

    private List<MenuItem> menuItems;
    private Context context;
    private boolean isStaffMode;
    private OnMenuActionListener listener;

    public MenuAdapter(List<MenuItem> menuItems, Context context, boolean isStaffMode, OnMenuActionListener listener) {
        this.menuItems = menuItems;
        this.context = context;
        this.isStaffMode = isStaffMode;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item_row, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);

        holder.tvName.setText(item.getName());
        holder.tvPrice.setText("£" + item.getPrice());
        holder.tvAllergens.setText("Allergens: " + item.getAllergens());

        if (item.getImageUri() != null && !item.getImageUri().isEmpty()) {
            holder.ivImage.setImageURI(Uri.parse(item.getImageUri()));
        } else {
            holder.ivImage.setImageResource(R.drawable.burger); // fallback placeholder
        }

        if (isStaffMode) {
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(item);
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(item);
            });

            holder.itemView.setOnClickListener(null);

        } else {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MenuItemActivity.class);
                intent.putExtra("menuItemId", item.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvAllergens;
        ImageView ivImage;
        Button btnEdit, btnDelete;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAllergens = itemView.findViewById(R.id.tvAllergens);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnEdit = itemView.findViewById(R.id.btnEditMenu);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
