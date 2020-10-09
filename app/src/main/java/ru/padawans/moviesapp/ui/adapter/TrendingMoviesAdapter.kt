package ru.padawans.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.trending.ResultsTrending

class TrendingMoviesAdapter: RecyclerView.Adapter<TrendingMoviesAdapter.TrendingMoviesViewHolder>() {

    private var results:MutableList<ResultsTrending> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return TrendingMoviesViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun addData(results: List<ResultsTrending>?){
        this.results.clear()
        if (results != null) {
            this.results = results.toMutableList()
        }
        notifyDataSetChanged()
    }

    class  TrendingMoviesViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.trending_movie_item,parent,false)){

        private val titleTextView: TextView = itemView.findViewById(R.id.trending_title_recycler_tv)
        private val posterImageView: ImageView = itemView.findViewById(R.id.trending_poster_recycler_iv)
        private val overviewTextView:TextView = itemView.findViewById(R.id.trending_overview_recycler_tv)

        fun bind(result: ResultsTrending){
            titleTextView.text = result.title
            // w300 размер изображения https://developers.themoviedb.org/3/getting-started/images
            val imageUrl:String = BuildConfig.BASE_IMG_URL + "w300"+ result.posterPath
            Picasso.get()
                .load(imageUrl)
                .into(posterImageView)
            overviewTextView.text = result.overview
        }
    }
}