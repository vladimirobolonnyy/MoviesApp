package ru.padawans.domain.model.search

data class Movie(
    val id: Int,
    val title: String,
    val vote: Double,
    val poster: String?
)
