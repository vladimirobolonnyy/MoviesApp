package ru.padawans.features.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.padawans.domain.model.main.MovieGeneralInfo

class MainMoviesAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<MainMoviesViewHolder>() {

    private var movies = mutableListOf<MovieGeneralInfo>()


    override fun onBindViewHolder(holder: MainMoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMoviesViewHolder {
        return MainMoviesViewHolder.create(parent, onItemClick)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateData(movies: List<MovieGeneralInfo>) {
        val diffResult = DiffUtil.calculateDiff(MoviesDiffUtilCallBack(this.movies, movies))
        this.movies.clear()
        this.movies.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeAll() {
        movies.clear()
        notifyDataSetChanged()
    }
}