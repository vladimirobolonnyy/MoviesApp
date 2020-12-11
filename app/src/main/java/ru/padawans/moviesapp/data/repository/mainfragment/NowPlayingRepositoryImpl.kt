package ru.padawans.moviesapp.data.repository.mainfragment

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.padawans.moviesapp.data.model.main.MovieGeneralInfo


class NowPlayingRepositoryImpl(private val pagingSource: PagingSource = PagingSource(ContentTypes.NOW_PLAYING)) :
    NowPlayingRepository {


    override fun getNowPlayingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return getData(pagingSource, page)
    }
}