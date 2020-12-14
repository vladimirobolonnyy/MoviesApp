package ru.padawans.domain.model.movie.reviews




class ResultsItem (
    val authorDetails: AuthorDetails,
    val updatedAt: String,
    val author: String,
    val createdAt: String,
    val id: String,
    val content: String,
    val url: String
)