package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.trending.TrendingMovies
import ru.padawans.moviesapp.di.provideApiService

class TrendingRepositoryImpl(private val movieApi: MovieApi = provideApiService()):TrendingRepository {

    override fun getTrendingMovies(apiKey: String, page: Int): Single<TrendingMovies> {
        return movieApi.getTrendingMovie(apiKey,page)
            .map { it.convert() }
            .subscribeOn(Schedulers.io())
    }
}