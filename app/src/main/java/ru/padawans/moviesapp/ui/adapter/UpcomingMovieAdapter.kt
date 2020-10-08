package ru.padawans.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.data.model.upcoming.Dto.ResultsDto
import ru.padawans.moviesapp.data.model.upcoming.Results

class UpcomingMovieAdapter: RecyclerView.Adapter<UpcomingMovieAdapter.UpcomingMovieViewHolder>() {

    private var results:MutableList<Results> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        return UpcomingMovieViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
       holder.bind(results[position])
    }

    override fun getItemCount(): Int {
       return results.size
    }

    fun addData(results: List<Results>?){
        this.results.clear()
        if (results != null) {
            this.results = results.toMutableList()
        }
        notifyDataSetChanged()
    }

    class  UpcomingMovieViewHolder(inflater: LayoutInflater,parent: ViewGroup):RecyclerView.ViewHolder(inflater.inflate(
        R.layout.upcoming_movie_item,parent,false)){

        private val text:TextView = itemView.findViewById(R.id.upcoming_recycler_tv)
        private val image:ImageView = itemView.findViewById(R.id.upcoming_recycler_iv)

        fun bind(result: Results){
            text.text = result.title
            // w300 размер изображения https://developers.themoviedb.org/3/getting-started/images
            val imageUrl:String = BuildConfig.BASE_IMG_URL + "w300"+ result.poster_path
            Picasso.get()
                .load(imageUrl)
                .resize(100, 200)
                .centerCrop()
                .into(image)
        }
    }
}