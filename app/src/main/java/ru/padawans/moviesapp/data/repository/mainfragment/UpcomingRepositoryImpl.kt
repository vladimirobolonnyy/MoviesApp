package ru.padawans.movie

import ru.padawans.moviesapp.data.repository.mainfragment.UpcomingRepository


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.padawans.moviesapp.data.Storage
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.cache.Cache
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.upcoming.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.model.upcoming.db.UpcomingMoviesEntity
import ru.padawans.moviesapp.data.model.upcoming.dto.DatesDto
import ru.padawans.moviesapp.data.model.upcoming.dto.UpcomingMoviesDto
import ru.padawans.moviesapp.data.repository.mainFragment.ContentTypes

import ru.padawans.moviesapp.data.repository.mainFragment.PagingSource
import ru.padawans.moviesapp.di.NetworkModule
import ru.padawans.moviesapp.di.StorageModule
import java.lang.Exception


class UpcomingRepositoryImpl(
) : UpcomingRepository {
    private val pagingSource = PagingSource(ContentTypes.UPCOMING)

    var isDataFromDatabase = false
    var isResponseSuccessful = true

    override fun getUpcomingMovies(
        page: Int
    ): Flow<List<MovieGeneralInfo>> {
        Log.d("TAG", "getUpcomingMovies: ")
        return flow<List<MovieGeneralInfo>> { emit(pagingSource.getResponseUpcomingData(page)) }.flowOn(
            Dispatchers.IO
        ).onCompletion {
            isDataFromDatabase = pagingSource.isDataFromDatabase
            isResponseSuccessful = pagingSource.isResponseSuccessful
        }
    }

    override fun validUpcomingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return flow<List<MovieGeneralInfo>> { emit(pagingSource.isDataInDatabaseValid(page)) }.flowOn(
            Dispatchers.IO
        )
    }
}
