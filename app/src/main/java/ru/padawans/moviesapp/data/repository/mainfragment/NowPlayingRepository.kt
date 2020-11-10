package ru.padawans.moviesapp.data.repository.mainfragment

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies

interface NowPlayingRepository {
    fun getNowPlayingMovies(page:Int): Flow<List<MovieGeneralInfo>>
    fun validNowPlayingMovies(  page: Int): Flow<List<MovieGeneralInfo>>
}