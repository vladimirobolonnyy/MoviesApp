package ru.padawans.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.main.MovieGeneralInfo

interface TrendingRepository {
    fun getTrendingMovies(page:Int): Flow<List<MovieGeneralInfo>>
}