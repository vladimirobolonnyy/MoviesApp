package ru.padawans.network.model.main

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.network.BuildConfig
import ru.padawans.network.model.getPosterPath


class MovieGeneralInfoDto(

    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?
) {
    fun convert(): MovieGeneralInfo {
        val validPosterPath = getPosterPath(posterPath)

        return MovieGeneralInfo(
            popularity ?: 0.0,
            voteCount ?: 0,
            video!!,
            validPosterPath,
            id ?: 0,
            adult!!,
            backdropPath ?: "N/A",
            originalLanguage ?: "N/A",
            originalTitle ?: "N/A",
            genreIds ?: listOf(0),
            title ?: "N/A",
            voteAverage ?: 0.0,
            overview ?: "N/A",
            releaseDate ?: "N/A"
        )
    }

}