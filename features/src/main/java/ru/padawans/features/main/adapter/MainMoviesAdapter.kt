package ru.padawans.features.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.padawans.domain.model.main.MovieGeneralInfo

class MainMoviesAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<MainMoviesViewHolder>() {

    private var movies = mutableListOf<MovieGeneralInfo>()
    private var lastPage: Int = 0
    private var prevPageSize: Int = 0

    override fun onBindViewHolder(holder: MainMoviesViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMoviesViewHolder {
        return MainMoviesViewHolder.create(parent, onItemClick)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addData(movies: List<MovieGeneralInfo>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun updateData(movies: List<MovieGeneralInfo>, size: Int, page: Int) {

        if (lastPage == page) {
            if (itemCount - prevPageSize > 0) {
                this.movies = this.movies.subList(0, itemCount - prevPageSize)
                notifyItemRangeRemoved(itemCount - prevPageSize, prevPageSize)//remove old items
            } else {
                val size1 = this.movies.size
                this.movies.clear()
                notifyItemRangeRemoved(0, size1)//remove old items
            }
            this.movies.addAll(movies)
        } else {
            this.movies.addAll(movies)
        }

        if (itemCount > 0) {
            notifyItemRangeInserted(itemCount - 1, movies.size)//add new items
        } else {
            notifyItemRangeInserted(0, movies.size)//add new items
        }

        lastPage = page
        prevPageSize = size
    }

    fun removeAll() {
        movies.clear()
        notifyDataSetChanged()
    }
}