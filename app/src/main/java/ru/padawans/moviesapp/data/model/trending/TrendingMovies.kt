package ru.padawans.moviesapp.data.model.trending



data class TrendingMovies(
    val results: List<ResultsTrending>?,
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
)