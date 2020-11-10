package ru.padawans.moviesapp.data.repository.mainfragment

import android.util.Log
import androidx.paging.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.data.model.upcoming.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.repository.mainFragment.ContentTypes
import ru.padawans.moviesapp.data.repository.mainFragment.PagingSource
import ru.padawans.moviesapp.di.NetworkModule
import ru.padawans.moviesapp.di.StorageModule


class NowPlayingRepositoryImpl():NowPlayingRepository {

    var isDataFromDatabase = false
    var isResponseSuccessful = true

    private val pagingSource = PagingSource(ContentTypes.NOW_PLAYING)

    override fun getNowPlayingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return flow<List<MovieGeneralInfo>> { emit(pagingSource.getResponseUpcomingData(page)) }.flowOn(
            Dispatchers.IO
        ).onCompletion {
            isDataFromDatabase =  pagingSource.isDataFromDatabase
            isResponseSuccessful = pagingSource.isResponseSuccessful
        }
    }

    override fun validNowPlayingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return flow<List<MovieGeneralInfo>> { emit(pagingSource.isDataInDatabaseValid(page)) }.flowOn(
            Dispatchers.IO
        )
    }
}