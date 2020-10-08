package ru.padawans.moviesapp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.padawans.moviesapp.data.model.upcoming.Dto.UpcomingMoviesDto
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies


interface MainFragmentRepository {
    fun getUpcomingMovies(apiKey: String, language: String, page: Int): Single<UpcomingMovies>
}