package ru.padawans.domain.model.search

data class MovieSearchResponse(
    val page: Int = 0,
    val totalResults: Int = 0,
    val totalPages: Int = 0,
    val results: List<Movie>
)