package com.angel.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.databinding.AdapterPhotoBinding
import com.bumptech.glide.Glide

class BasketAdapter() {
//    : RecyclerView.Adapter<MainViewHolder1>
//    private lateinit var mListener: onitemClickListener
//
//    interface onitemClickListener{
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(listener: onitemClickListener){
//        mListener = listener
//    }
//
//    var basketList = mutableListOf<basketList>()
//
//    fun setBasket(photos: List<valueItem>) {
//        this.basketList = photos.toMutableList()
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder1 {
//
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = AdapterPhotoBinding.inflate(inflater, parent, false)
//        return MainViewHolder1(binding)
//    }
//
//    override fun onBindViewHolder(holder: MainViewHolder1, position: Int) {
//        val basket = basketList[position]
//        Glide.with(
//            holder.itemView.context)
//            .load(basket.image).override(700, 700)
//            .into(holder.binding.image)
//        holder.binding.title.text = basket.title
//        holder.binding.price.text = "£" + basket.price.toString()
//    }
//
//    override fun getItemCount(): Int {
//        return basketList.size
//    }
//}
//
//class MainViewHolder1(val binding: AdapterPhotoBinding, listener: PhotoAdapter.onitemClickListener) : RecyclerView.ViewHolder(binding.root) {
//    init {
//
//        itemView.setOnClickListener {
//            listener.onItemClick(adapterPosition)
//        }
//
//    }
}