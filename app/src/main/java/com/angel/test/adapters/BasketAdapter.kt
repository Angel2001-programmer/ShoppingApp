package com.angel.test.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.databinding.BasketitemBinding
import com.angel.test.models.Product
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class BasketAdapter(val clientList: ArrayList<Product>) :
    RecyclerView.Adapter<BasketAdapter.MainViewHolder1>() {
    private lateinit var db: DatabaseReference

    fun deleteItem(pos: Int){
        db = FirebaseDatabase.getInstance().getReference("Products")
        var query = db.orderByChild("title").equalTo(clientList.get(pos).title)
        Log.d("TEST", "deleteItem: " + clientList.get(pos).title)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    i.getRef().removeValue()
                    clientList.removeAt(pos)
                    notifyItemChanged(pos)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Adapter", "onCancelled: ")
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder1 {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BasketitemBinding.inflate(inflater, parent, false)
        return MainViewHolder1(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder1, position: Int) {
        val basket = clientList[position]
        Glide.with(
            holder.itemView.context
        )
            .load(basket.image).override(400, 400)
            .into(holder.binding.productImg)
        holder.binding.productTitle.text = basket.title
        holder.binding.productPrice.text = basket.price.toString()
    }

    override fun getItemCount(): Int {
        return clientList.size
    }

    class MainViewHolder1(val binding: BasketitemBinding) : RecyclerView.ViewHolder(binding.root)
}