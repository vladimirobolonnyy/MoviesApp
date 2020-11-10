package ru.padawans.moviesapp.data.model.upcoming.dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.upcoming.Dates
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.data.model.upcoming.db.UpcomingMoviesEntity
import java.lang.RuntimeException


class UpcomingMoviesDto(

    @SerializedName("results") val results: List<MovieGeneralInfoDto>?,
    @SerializedName("page") val page: Int?,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("dates") val dates: DatesDto?,
    @SerializedName("total_pages") val totalPages: Int?
) {

    fun convert(): UpcomingMovies {
        val resultsModel = results?.map { it.convert() }
        val dateModel: Dates? = dates?.converter()

        if (resultsModel != null && page != null && totalResults != null && dateModel != null && totalPages != null) {
            return UpcomingMovies(resultsModel, page, totalResults, dateModel, totalPages)
        } else {
            throw RuntimeException("Not valid parametrs")
        }

    }

    fun convertToDB(contentType:String):UpcomingMoviesEntity{
        if (results != null) {
            val upcomingMoviesDB = UpcomingMoviesEntity(
                page,
                totalResults,
                totalPages,
                contentType
            )

            return upcomingMoviesDB
        }else {
            throw RuntimeException("Not valid parametrs")
        }
    }
}



