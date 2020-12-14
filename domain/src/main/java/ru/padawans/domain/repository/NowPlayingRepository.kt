package ru.padawans.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.main.MovieGeneralInfo

interface NowPlayingRepository {
    fun getNowPlayingMovies(page:Int): Flow<List<MovieGeneralInfo>>
}