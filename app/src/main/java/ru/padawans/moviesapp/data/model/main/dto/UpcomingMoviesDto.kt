package ru.padawans.moviesapp.data.model.main.dto

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.main.Dates
import ru.padawans.domain.model.main.UpcomingMovies
import ru.padawans.moviesapp.data.model.main.db.UpcomingMoviesEntity
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
        
        return UpcomingMovies(
            resultsModel ?: emptyList(),
            page ?: -1,
            totalResults ?: -1,
            dateModel ?: Dates("", ""),
            totalPages ?: -1
        )
    }

    fun convertToDB(contentType: String): UpcomingMoviesEntity {
        if (results != null) {
            val upcomingMoviesDB = UpcomingMoviesEntity(
                page,
                totalResults,
                totalPages,
                contentType
            )

            return upcomingMoviesDB
        } else {
            throw RuntimeException("Not valid parametrs")
        }
    }
}



