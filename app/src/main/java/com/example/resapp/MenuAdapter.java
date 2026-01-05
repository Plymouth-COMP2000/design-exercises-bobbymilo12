package com.example.resapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    public interface OnMenuActionListener {
        void onEdit(MenuItem item);
        void onDelete(MenuItem item);
    }

    private final List<MenuItem> menuItems;
    private final Context context;
    private final boolean isStaffMode;
    private final OnMenuActionListener listener;

    public MenuAdapter(List<MenuItem> menuItems,
                       Context context,
                       boolean isStaffMode,
                       OnMenuActionListener listener) {
        this.menuItems = menuItems;
        this.context = context;
        this.isStaffMode = isStaffMode;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.menu_item_row, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);

        holder.tvName.setText(item.name);
        holder.tvPrice.setText("£" + item.price);
        holder.tvAllergens.setText("Allergens: " + item.allergens);

        Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.burger)
                .error(R.drawable.burger)
                .into(holder.ivImage);

        if (isStaffMode) {
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnEdit.setOnClickListener(v -> listener.onEdit(item));
            holder.btnDelete.setOnClickListener(v -> listener.onDelete(item));
        } else {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MenuItemActivity.class);
                intent.putExtra("menuItemId", item.id);
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

        MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMenuName);
            tvPrice = itemView.findViewById(R.id.tvMenuPrice);
            tvAllergens = itemView.findViewById(R.id.tvMenuAllergens);
            ivImage = itemView.findViewById(R.id.ivMenuImage);
            btnEdit = itemView.findViewById(R.id.btnEditMenu);
            btnDelete = itemView.findViewById(R.id.btnDeleteMenu);
        }
    }
}
