package com.example.sprint11imdb

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(): RecyclerView.Adapter<MovieViewHolder>() {
    var movies = ArrayList<Movie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(parent)

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }
}