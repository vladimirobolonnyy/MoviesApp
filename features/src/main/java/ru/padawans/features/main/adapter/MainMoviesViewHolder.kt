package ru.padawans.features.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.upcoming_movie_item.view.*
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.features.BuildConfig
import ru.padawans.features.R

class MainMoviesViewHolder(view: View, val onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
companion object{
    fun create(parent: ViewGroup, onItemClick: (Int) -> Unit): MainMoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.upcoming_movie_item, parent, false)

        return MainMoviesViewHolder(view,onItemClick)
    }
}

    fun bind(result: MovieGeneralInfo) {
        itemView.upcoming_recycler_tv.text = result.title
        // w300 размер изображения https://developers.themoviedb.org/3/getting-started/images
        val imageUrl: String = result.posterPath
        itemView.ratingBar.rating = result.voteAverage.toFloat()/2
        itemView.vote_count_tv.text = result.voteCount.toString() +" " +  itemView.context.resources.getString(R.string.reviews)
        itemView.release_date.text = result.releaseDate
        if(result.adult){
            itemView.adult_marker_tv.text = "R"
            itemView.adult_marker_tv.visibility = View.VISIBLE
        }

            if (imageUrl.isNotEmpty()){
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_block)
                    .error(R.drawable.ic_block)
                    .centerCrop()
                    .resize(300, 700)
                    .transform(
                        RoundedCornersTransformation(
                            15,
                            0,
                            RoundedCornersTransformation.CornerType.TOP
                        )
                    )
                    .into(itemView.upcoming_recycler_iv)
            }




        itemView.setOnClickListener(View.OnClickListener {
            onItemClick(result.id)

        })
    }

}