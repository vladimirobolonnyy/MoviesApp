package ru.padawans.moviesapp.data.model.upcoming

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.upcoming.Dto.DatesDto
import ru.padawans.moviesapp.data.model.upcoming.Dto.ResultsDto

data class UpcomingMovies(
    val results: List<Results>?,
    val page: Int?,
    val total_results: Int?,
    val dates: Dates?,
    val total_pages: Int?,
    val test:Int
)