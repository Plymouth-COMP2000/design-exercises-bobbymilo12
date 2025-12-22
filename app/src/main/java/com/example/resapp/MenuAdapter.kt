package com.example.resapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val items: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgDish: ImageView = itemView.findViewById(R.id.imgDish)
        val tvDishName: TextView = itemView.findViewById(R.id.tvDishName)
        val tvDishPrice: TextView = itemView.findViewById(R.id.tvDishPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_row, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]

        holder.imgDish.setImageResource(item.image)
        holder.tvDishName.text = item.name
        holder.tvDishPrice.text = item.price

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MenuItemActivity::class.java)

            intent.putExtra("name", item.name)
            intent.putExtra("price", item.price)
            intent.putExtra("image", item.image)
            intent.putExtra("ingredients", item.ingredients)
            intent.putExtra("allergens", item.allergens)

            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int = items.size
}

