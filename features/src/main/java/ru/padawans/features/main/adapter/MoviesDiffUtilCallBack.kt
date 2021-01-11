package ru.padawans.features.main.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.padawans.domain.model.main.MovieGeneralInfo

class MoviesDiffUtilCallBack(
    private val oldMovies: List<MovieGeneralInfo>,
    private val newMovies: List<MovieGeneralInfo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldMovies.size
    }

    override fun getNewListSize(): Int {
        return newMovies.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldMovies[oldItemPosition]
        val newMovie = newMovies[newItemPosition]
        return oldMovie.id == newMovie.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldMovies[oldItemPosition]
        val newMovie = newMovies[newItemPosition]
        return oldMovie == newMovie
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}