package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Single
import ru.padawans.moviesapp.data.model.trending.Dto.TrendingMoviesDto
import ru.padawans.moviesapp.data.model.trending.TrendingMovies

interface TrendingRepository {
    fun getTrendingMovies(apiKey: String, page: Int):Single<TrendingMovies>
}