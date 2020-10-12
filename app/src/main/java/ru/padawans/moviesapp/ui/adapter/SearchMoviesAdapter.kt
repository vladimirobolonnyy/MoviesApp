package ru.padawans.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.film_item.view.*
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.search.Movie

class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.MoviewViewHolder>() {

    private var listSearchableMovies = emptyList<Movie>()

    class MoviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movie_title: TextView = view.item_title
        val movie_poster: ImageView = view.item_poster
        val movie_vote: TextView = view.item_vote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return MoviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviewViewHolder, position: Int) {
        holder.movie_title.text = listSearchableMovies[position].title
        holder.movie_poster.downloadAndSetImage(listSearchableMovies[position].poster)
        holder.movie_vote.text = listSearchableMovies[position].vote.toString()
    }

    override fun getItemCount(): Int = listSearchableMovies.size

    fun setList(list: List<Movie>) {
        listSearchableMovies = list
        notifyDataSetChanged()
    }

    fun ImageView.downloadAndSetImage(path: String?) {
        val url = BuildConfig.BASE_IMG_URL + "w342" + path
        Picasso.get()
            .load(url)
            .resize(120, 180)
            .centerCrop()
            .placeholder(R.drawable.ic_block)
            .into(this)
    }
}
