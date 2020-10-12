package ru.padawans.moviesapp.data.model.search

data class Response(
    val page: Int = 0,
    val totalResults: Int = 0,
    val totalPages: Int = 0,
    val results: List<Movie>?
)
