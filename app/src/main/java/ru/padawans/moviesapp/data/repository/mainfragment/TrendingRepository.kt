package ru.padawans.moviesapp.data.repository.mainfragment

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo

interface TrendingRepository {
    fun getTrendingMovies(page:Int): Flow<List<MovieGeneralInfo>>
    fun validTrendingMovies(  page: Int): Flow<List<MovieGeneralInfo>>
}