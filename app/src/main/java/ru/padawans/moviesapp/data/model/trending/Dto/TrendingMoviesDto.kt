package ru.padawans.moviesapp.data.model.trending.Dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.trending.ResultsTrending
import ru.padawans.moviesapp.data.model.trending.TrendingMovies
import java.lang.RuntimeException


class TrendingMoviesDto(

    @SerializedName("results") val results: List<ResultsDto>?,
    @SerializedName("page") val page: Int?,
    @SerializedName("total_results") val totalResults: Int?,

    @SerializedName("total_pages") val totalPages: Int?
) {

    fun convert(): TrendingMovies {
        val resultsModel: List<ResultsTrending>? = results?.map { t -> t.convert() }

        if (resultsModel != null && page != null && totalResults != null && totalPages != null) {
            return TrendingMovies(resultsModel, page, totalResults, totalPages)
        } else {
            throw RuntimeException("Not valid parametrs")
        }

    }
}



