package ru.padawans.moviesapp.data.repository.mainfragment

import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.model.main.MovieGeneralInfo

interface NowPlayingRepository {
    fun getNowPlayingMovies(page:Int): Flow<List<MovieGeneralInfo>>
}