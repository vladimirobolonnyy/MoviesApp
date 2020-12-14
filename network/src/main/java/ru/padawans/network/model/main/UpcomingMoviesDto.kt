package ru.padawans.network.model.main

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.main.Dates
import ru.padawans.domain.model.main.UpcomingMovies


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

}



