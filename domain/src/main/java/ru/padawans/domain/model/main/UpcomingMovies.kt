package ru.padawans.domain.model.main

data class UpcomingMovies(
    var results: List<MovieGeneralInfo>,
    val page: Int,
    val totalResults: Int,
    val dates: Dates,
    val totalPages: Int,
)