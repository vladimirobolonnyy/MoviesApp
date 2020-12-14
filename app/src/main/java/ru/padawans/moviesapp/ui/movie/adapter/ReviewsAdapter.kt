package ru.padawans.moviesapp.ui.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.review_movie_item.view.*
import ru.padawans.moviesapp.R
import ru.padawans.domain.model.movie.reviews.ResultsItem

class ReviewsAdapter: RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    private val reviews = mutableListOf<ResultsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
       return ReviewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
       holder.bind(reviews.get(position))
    }

    override fun getItemCount(): Int {
       return reviews.size
    }

    fun addData(reviews: List<ResultsItem>) {
        this.reviews.addAll(reviews)
        notifyDataSetChanged()
    }


    fun removeAll(){
        reviews.clear()
        notifyDataSetChanged()
    }


    class ReviewsViewHolder(view: View):RecyclerView.ViewHolder(view){


        fun bind(movieReviews: ResultsItem){
            val avatarPath:String = movieReviews.authorDetails.avatarPath

            itemView.reviewerName.text = movieReviews.author
            itemView.createdAt.text = movieReviews.updatedAt
            itemView.rating.text = movieReviews.authorDetails.rating.toString()
            itemView.review.text = movieReviews.content

            Log.d("TAG", "bind: " + avatarPath)
            if (avatarPath!=""){
                Picasso.get()
                    .load(avatarPath)
                    .placeholder(R.drawable.ic_block)
                    .error(R.drawable.ic_block)
                    .centerCrop()
                    .resize(250, 250)
                    .transform(
                        RoundedCornersTransformation(
                            15,
                            0,
                            RoundedCornersTransformation.CornerType.LEFT
                        )
                    )
                    .into(itemView.reviewerAvatarImage)
            }

        }

        companion object{
            fun create(parent: ViewGroup): ReviewsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.review_movie_item, parent, false)

                return ReviewsViewHolder(view)
            }
        }
    }

}