package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.di.provideApiService

class NowPlayingRepositoryImpl(private val movieApi: MovieApi = provideApiService()):NowPlayingRepository {

    override fun getNowPlayingMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Single<UpcomingMovies> {
        return movieApi.getNowPlayingMovies(apiKey,language,page)
            .map { it.convert() }
            .subscribeOn(Schedulers.io())
    }
}