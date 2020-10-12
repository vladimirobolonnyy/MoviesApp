package ru.padawans.moviesapp.data.model.search.Dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.search.Movie

class MovieDTO {
    @SerializedName("title")
    val title: String? = null

    @SerializedName("vote_average")
    val vote: Double? = null

    @SerializedName("poster_path")
    val poster: String? = null

    fun convert(): Movie = Movie(title ?: "N/A", vote ?: 0.0, poster)
}
