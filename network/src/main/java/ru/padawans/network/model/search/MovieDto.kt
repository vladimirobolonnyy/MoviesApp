package ru.padawans.network.model.search

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.search.Movie

class MovieDTO {
    @SerializedName("title")
    val title: String? = null

    @SerializedName("vote_average")
    val vote: Double? = null

    @SerializedName("poster_path")
    val poster: String? = null

    @SerializedName("id")
    val id: Int? = null

    fun convert(): Movie = Movie(id ?: 0, title ?: "N/A", vote ?: 0.0, poster)
}
