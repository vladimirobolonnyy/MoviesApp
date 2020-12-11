package ru.padawans.moviesapp.data.repository.mainfragment

import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.model.main.MovieGeneralInfo

interface TrendingRepository {
    fun getTrendingMovies(page:Int): Flow<List<MovieGeneralInfo>>
}