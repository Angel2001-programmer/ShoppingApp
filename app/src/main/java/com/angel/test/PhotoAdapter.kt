package com.angel.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angel.test.databinding.AdapterMovieBinding
import com.bumptech.glide.Glide

class MovieAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var movieList = mutableListOf<photo>()

    fun setMovies(photos: List<photo>) {
        this.movieList = photos.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.name.text = movie.title
        Glide.with(holder.itemView.context).load(movie.thumbnailUrl).into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}

class MainViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

}
