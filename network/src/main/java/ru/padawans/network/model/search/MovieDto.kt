package ru.padawans.network.model.search

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.search.Movie
import ru.padawans.network.BuildConfig
import ru.padawans.network.model.getPosterPath

class MovieDTO {
    @SerializedName("title")
    val title: String? = null

    @SerializedName("vote_average")
    val vote: Double? = null

    @SerializedName("poster_path")
    val poster: String? = null

    @SerializedName("id")
    val id: Int? = null

    fun convert(): Movie {
        val validPosterPath = getPosterPath(poster)

        return Movie(id ?: 0, title ?: "N/A", vote ?: 0.0, validPosterPath)
    }
}
