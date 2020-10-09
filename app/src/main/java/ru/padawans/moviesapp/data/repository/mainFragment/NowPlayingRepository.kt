package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Single
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies

interface NowPlayingRepository {
    fun getNowPlayingMovies(apiKey: String, language: String, page: Int): Single<UpcomingMovies>
}