package com.angel.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.databinding.AdapterPhotoBinding
import com.bumptech.glide.Glide

class PhotoAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private lateinit var mListener: onitemClickListener

    interface onitemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onitemClickListener) {
        mListener = listener
    }

    var movieList = mutableListOf<valueItem>()

    fun setMovies(photos: List<valueItem>) {
        this.movieList = photos.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPhotoBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding, mListener)
    }

    // Set Text and Images.
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.title.text = movie.title
        holder.binding.price.text = "£" + movie.price.toString()
        Glide.with(
            holder.itemView.context
        )
            .load(movie.image).override(700, 700)
            .into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
//Custom onClickListener
class MainViewHolder(val binding: AdapterPhotoBinding, listener: PhotoAdapter.onitemClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    init {

        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }

    }
}

