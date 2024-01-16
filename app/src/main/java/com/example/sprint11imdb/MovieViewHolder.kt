package com.example.sprint11imdb

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

class MovieViewHolder(parent: ViewGroup):
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)){

    var image = parent.findViewById<ImageView>(R.id.image)
    var title = parent.findViewById<TextView>(R.id.title)
    var description = parent.findViewById<TextView>(R.id.description)

    fun bind(movie: Movie){
        if (movie.image != null) {
            image?.let {
                Glide.with(itemView)
                    .load(movie.image)
                    .centerCrop()
                    .placeholder(R.drawable.baseline_cloud_off_24)
                    .error(R.drawable.baseline_cloud_off_24)
                    .into(it)
            }
        }
        else
            image.setImageResource(R.drawable.baseline_cloud_off_24)

        title?.text = movie.title ?: ""
        description?.text = movie.description ?: ""
    }

}