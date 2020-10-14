package ru.padawans.moviesapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movies_load_state_footer_view_item.view.*
import kotlinx.android.synthetic.main.search_movie_item.view.*
import ru.padawans.moviesapp.R

class SearchMovieLoadStateViewHolder(
    view: View,
    retry: () -> Unit
) : RecyclerView.ViewHolder(view) {
    val errorMsg: TextView = view.loadstate_error_msg
    val progressBar: ProgressBar = view.loadstate_progress_bar
    val retryBtn: Button = view.loadstaet_retry_button

    init {
        retryBtn.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }
        progressBar.isVisible = loadState is LoadState.Loading
        retryBtn.isVisible = loadState !is LoadState.Loading
        errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): SearchMovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movies_load_state_footer_view_item, parent, false)
            return SearchMovieLoadStateViewHolder(view, retry)
        }
    }
}