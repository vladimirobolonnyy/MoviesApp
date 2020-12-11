package ru.padawans.moviesapp.ui.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.trailer_movie_item.view.*
import ru.padawans.moviesapp.R

class TrailersAdapter(private val lifecycle: Lifecycle) :
    RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>() {

    private val trailers = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {

        return TrailersViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        holder.bind(trailers.get(position),lifecycle)
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    fun addTrailers(trailers: List<String>) {
        Log.d("TAG", "addTrailers: " + trailers.size)
        this.trailers.addAll(trailers)
        notifyDataSetChanged()
    }

    fun removeAll(){
        trailers.clear()
        notifyDataSetChanged()
    }

    class TrailersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(key: String,lifecycle: Lifecycle) {

            lifecycle.addObserver(itemView.videoWebView)
            itemView.videoWebView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    Log.d("TAG", "onReady: ")
                    youTubePlayer.cueVideo(key, 0.0F)
                }
            })
        }
        companion object{
            fun create(parent: ViewGroup): TrailersViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.trailer_movie_item, parent, false)

                return TrailersViewHolder(view)
            }
        }

    }
}