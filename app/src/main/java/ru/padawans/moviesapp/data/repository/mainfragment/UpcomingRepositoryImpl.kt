package ru.padawans.movie

import ru.padawans.domain.repository.UpcomingRepository


import android.util.Log
import kotlinx.coroutines.flow.*
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.moviesapp.data.repository.mainfragment.ContentTypes

import ru.padawans.moviesapp.data.repository.mainfragment.PagingSource
import ru.padawans.moviesapp.data.repository.mainfragment.getData


class UpcomingRepositoryImpl(
    private val pagingSource: PagingSource = PagingSource(ContentTypes.UPCOMING)
) : UpcomingRepository {


    override fun getUpcomingMovies(
        page: Int
    ): Flow<List<MovieGeneralInfo>> {
        Log.d("TAG", "getUpcomingMovies: ")
        return getData(pagingSource, page)
    }
}
