package ru.padawans.moviesapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo

class UpcomingMoviesAdapter(private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<UpcomingMoviesViewHolder>() {

    private val movies = mutableListOf<MovieGeneralInfo>()

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        return UpcomingMoviesViewHolder.create(parent, onItemClick)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addData(movies: List<MovieGeneralInfo>) {

            this.movies.addAll(movies)
            notifyDataSetChanged()
    }
    fun updateData(movies: List<MovieGeneralInfo>){
        var v = 0
        for (i in   (this.movies.lastIndex-movies.size+1)..this.movies.lastIndex  ){
            this.movies.removeAt(i)
            this.movies.add(i,movies.get(v))
            v++
        }
        notifyItemRangeRemoved(this.movies.lastIndex-movies.size+1,movies.size)
        notifyItemRangeInserted(this.movies.lastIndex-movies.size+1,movies.size)
    }
}