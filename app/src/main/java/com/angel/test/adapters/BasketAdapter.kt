package com.angel.test.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.databinding.BasketitemBinding
import com.angel.test.models.Product
import com.bumptech.glide.Glide

class BasketAdapter(val productList: ArrayList<Product>) : RecyclerView.Adapter<MainViewHolder1>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder1 {

        val inflater = LayoutInflater.from(parent.context)
        val binding = BasketitemBinding.inflate(inflater, parent, false)
        return MainViewHolder1(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder1, position: Int) {
        val basket = productList[position]
        Glide.with(
            holder.itemView.context
        )
            .load(basket.image).override(400, 400)
            .into(holder.binding.productImg)
        holder.binding.productTitle.text = basket.title
        holder.binding.productPrice.text = basket.price.toString()

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
class MainViewHolder1(val binding: BasketitemBinding) : RecyclerView.ViewHolder(binding.root)