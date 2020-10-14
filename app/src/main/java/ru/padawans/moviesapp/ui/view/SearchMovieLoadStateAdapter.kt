package ru.padawans.moviesapp.ui.view

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class SearchMovieLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<SearchMovieLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: SearchMovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SearchMovieLoadStateViewHolder {
        return SearchMovieLoadStateViewHolder.create(parent, retry)
    }
}