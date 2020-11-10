package ru.padawans.moviesapp.data.repository.mainfragment

import android.util.Log
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.padawans.movie.UpcomingRepositoryImpl
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.upcoming.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.repository.mainFragment.ContentTypes
import ru.padawans.moviesapp.data.repository.mainFragment.PagingSource
import ru.padawans.moviesapp.di.StorageModule


class TrendingRepositoryImpl():TrendingRepository {
    private val pagingSource = PagingSource(ContentTypes.TRENDING)

    var isDataFromDatabase = pagingSource.isDataFromDatabase
    var isResponseSuccessful = pagingSource.isResponseSuccessful

    override fun getTrendingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return flow<List<MovieGeneralInfo>> { emit(pagingSource.getResponseUpcomingData(page)) }.flowOn(
            Dispatchers.IO
        ).onCompletion {
            isDataFromDatabase =  pagingSource.isDataFromDatabase
             isResponseSuccessful = pagingSource.isResponseSuccessful
        }
    }

    override fun validTrendingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return flow<List<MovieGeneralInfo>> { emit(pagingSource.isDataInDatabaseValid(page)) }.flowOn(
            Dispatchers.IO
        )
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
        private const val CONTENT_TYPE = "TRENDING"
    }

}